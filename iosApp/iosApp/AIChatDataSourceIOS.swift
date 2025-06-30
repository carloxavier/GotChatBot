import Chatbot
import FirebaseAI
import Foundation

class AIChatDataSourceIOS: LLMDataSource {
    private let firebaseAI = FirebaseAI.firebaseAI()

    func prompt(contentHistory: [Message]) async throws -> String? {
        let model = firebaseAI.generativeModel(modelName: "gemini-2.0-flash")
        let modelContentHistory = contentHistory
            .map { chatMessage in
                let role: String = switch chatMessage {
                case is UserMessage:
                    "user"
                case is ModelMessage:
                    "model"
                default:
                    "user"
                }
                return ModelContent.init(role: role, parts: chatMessage.text)
            }
        return try await model.generateContent(modelContentHistory).text
    }
}
