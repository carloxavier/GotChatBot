package com.sample.kmp.ui

import app.cash.turbine.test
import com.sample.kmp.domain.LLMRepository
import com.sample.kmp.domain.Message
import com.sample.kmp.domain.ModelMessage
import com.sample.kmp.domain.UserMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var llmRepository: FakeLLMRepository
    private lateinit var chatViewModel: ChatViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        llmRepository = FakeLLMRepository()
        chatViewModel = ChatViewModel(llmRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `sendMessageToModel adds user message and sets loading state`() = runTest(testDispatcher) {
        val userInput = "testing the ViewModel!"

        chatViewModel.chatState.test {
            val initialState = awaitItem()
            assertTrue(initialState.messages.isEmpty())
            assertFalse(initialState.isLoading)
            assertEquals("", initialState.userInput)

            chatViewModel.sendMessageToModel(userInput)

            val stateAfterUserMessage = awaitItem()
            assertEquals(1, stateAfterUserMessage.messages.size)
            assertTrue(stateAfterUserMessage.messages.last() is UserMessage)
            assertEquals(userInput, stateAfterUserMessage.messages.last().text)
            assertTrue(stateAfterUserMessage.isLoading)
        }
    }

    @Test
    fun `sendMessageToModel receives model response and updates state`() = runTest(testDispatcher) {
        val userInput = "Test input for model response"
        val expectedModelResponseText = "Model's answer to: $userInput"
        val expectedModelMessage = ModelMessage(expectedModelResponseText)

        llmRepository.setNextResponse(expectedModelMessage)

        chatViewModel.chatState.test {
            awaitItem()

            chatViewModel.sendMessageToModel(userInput)

            awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()
            val finalState = awaitItem()
            assertEquals(2, finalState.messages.size)
            assertTrue(finalState.messages.last() is ModelMessage)
            assertEquals(expectedModelResponseText, finalState.messages.last().text)
            assertFalse(finalState.isLoading)
            assertEquals("", finalState.userInput)

            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `sendMessageToModel calls repository with correct parameters`() = runTest(testDispatcher) {
        val userInput = "Verify repository call"
        val expectedModelMessage = ModelMessage("Repo response")
        llmRepository.setNextResponse(expectedModelMessage)

        chatViewModel.chatState.test {
            awaitItem()

            chatViewModel.sendMessageToModel(userInput)

            awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()
            val finalState = awaitItem()

            assertEquals(userInput, llmRepository.promptCalledWithPrompt)
            assertTrue(llmRepository.promptCalledWithMessageHistory?.isNotEmpty() == true)
            assertTrue(finalState.messages.first() is UserMessage)
            assertEquals(userInput, finalState.messages.first().text)
            assertTrue(llmRepository.promptCalledWithMessageHistory?.last() is UserMessage)
            assertEquals(userInput, llmRepository.promptCalledWithMessageHistory?.last()?.text)

            ensureAllEventsConsumed()
        }
    }


    @Test
    fun `sendMessageToModel does nothing if userInput is blank`() = runTest(testDispatcher) {
        chatViewModel.chatState.test {
            val initialState = awaitItem()

            chatViewModel.sendMessageToModel("   ")

            expectNoEvents()
            assertEquals(null, llmRepository.promptCalledWithPrompt)
            assertEquals(initialState.messages.size, chatViewModel.chatState.value.messages.size)
        }
    }

    @Test
    fun `onUserInput updates userInput in chatState`() = runTest(testDispatcher) {
        val newText = "Typing a message"

        chatViewModel.chatState.test {
            awaitItem()

            chatViewModel.onUserInput(newText)

            val updatedState = awaitItem()
            assertEquals(newText, updatedState.userInput)

            ensureAllEventsConsumed()
        }
    }
}

class FakeLLMRepository : LLMRepository {
    var promptCalledWithMessageHistory: List<Message>? = null
    var promptCalledWithPrompt: String? = null
    private var nextResponse: Message = ModelMessage("Default Fake Response")

    fun setNextResponse(message: Message) {
        this.nextResponse = message
    }

    override suspend fun prompt(
        messageHistory: List<Message>,
        prompt: String
    ): Message {
        promptCalledWithMessageHistory = messageHistory
        promptCalledWithPrompt = prompt
        return nextResponse
    }
}

