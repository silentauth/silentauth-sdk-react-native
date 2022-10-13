import silentauth_sdk_ios

@objc(SilentAuthSdkReactNative)
class SilentAuthSdkReactNative: NSObject {
    
    @objc(openWithDataCellularWithDebug:debug:withResolver:withRejecter:)
    public func openWithDataCellularWithDebug(url: String,
                                     debug: Bool,
                                     resolve: @escaping RCTPromiseResolveBlock,
                                     reject: @escaping RCTPromiseRejectBlock) -> Void {

        guard let u = URL(string: url) else {
            let failure = [
                "error":"sdk_error",
                "error_description":"invalid url"]
            resolve(failure)
            return
        }
        
        let silentAuthSdk: SilentAuthSDK = SilentAuthSDK()
        silentAuthSdk.openWithDataCellular(url: u, debug: debug) { res in
            resolve(res)
        }
    }

     @objc(openWithDataCellular:withResolver:withRejecter:)
    public func openWithDataCellular(url: String,
                                     resolve: @escaping RCTPromiseResolveBlock,
                                     reject: @escaping RCTPromiseRejectBlock) -> Void {

        guard let u = URL(string: url) else {
            let failure = [
                "error":"sdk_error",
                "error_description":"invalid url"]
            resolve(failure)
            return
        }
        
        let silentAuthSdk: SilentAuthSDK = SilentAuthSDK()
        silentAuthSdk.openWithDataCellular(url: u, debug: false) { res in
            resolve(res)
        }
    }
    
    @objc
    static func requiresMainQueueSetup() -> Bool {
        return false
    }
}
