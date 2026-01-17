import SwiftUI
import ComposeApp
import UserNotifications

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        // Set notification delegate
        UNUserNotificationCenter.current().delegate = self
        return true
    }

    // MARK: - Show notifications while app is in FOREGROUND
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                willPresent notification: UNNotification,
                                withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        if #available(iOS 14.0, *) {
            completionHandler([.banner, .sound, .badge])
        } else {
            completionHandler([.alert, .sound, .badge])
        }
    }
    
    // MARK: - Handle notification action buttons
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        
        let userInfo = response.notification.request.content.userInfo
        let actionId = response.actionIdentifier
        
        // Handle "Open WhatsApp" action
        if actionId == "OPEN_WHATSAPP" {
            if let phoneNumber = userInfo["whatsappNumber"] as? String,
               let message = userInfo["whatsappMessage"] as? String {
                openWhatsApp(phoneNumber: phoneNumber, message: message)
            }
            
            // Dismiss the notification
            let notificationId = response.notification.request.identifier
            UNUserNotificationCenter.current().removeDeliveredNotifications(withIdentifiers: [notificationId])
        }
        
        // Handle tap on notification body (default action)
        if actionId == UNNotificationDefaultActionIdentifier {
            // Open app - no special handling needed
        }
        
        completionHandler()
    }
    
    // MARK: - Open WhatsApp with pre-filled message
    private func openWhatsApp(phoneNumber: String, message: String) {
        // Clean phone number (digits only)
        let cleanNumber = phoneNumber.components(separatedBy: CharacterSet.decimalDigits.inverted).joined()
        
        // URL encode the message
        let encodedMessage = message.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? ""
        
        // Build WhatsApp URL
        let urlString: String
        if message.isEmpty {
            urlString = "https://wa.me/\(cleanNumber)"
        } else {
            urlString = "https://wa.me/\(cleanNumber)?text=\(encodedMessage)"
        }
        
        if let url = URL(string: urlString) {
            UIApplication.shared.open(url)
        }
    }
}