import UIKit
import SwiftUI
import Chatbot

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            initialPrompt: "You are a yoga coach, " +
                "help me find a routine for yoga that fits me, " +
                "start with a brief intro of yourself, max 120 chars, and ask about what I can do for you."
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



