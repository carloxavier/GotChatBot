import SwiftUI
import FirebaseCore
import Chatbot

class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
    FirebaseApp.configure()
    DI_iosKt.llmDataSource = AIChatDataSourceIOS()
    
    return true
  }
}
