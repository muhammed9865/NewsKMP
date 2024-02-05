import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = Platform.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()