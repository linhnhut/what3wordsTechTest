import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        // Initialize Koin before starting the app
        KoinInitKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
