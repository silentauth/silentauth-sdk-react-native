package com.silentauthsdkreactnative

import android.util.Log
import com.facebook.react.bridge.*
import com.silentauth.sdk.SilentAuthSDK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL


class SilentAuthSdkReactNativeModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

  init {
    SilentAuthSDK.initializeSdk(reactContext)
  }

  override fun getName(): String {
    return "SilentAuthSdkReactNative"
  }

  @ReactMethod
  fun openWithDataCellular(url: String, promise: Promise) {
    CoroutineScope(context = Dispatchers.IO).launch {
      try {
        val silentAuthSdk = SilentAuthSDK.getInstance()
        val res = silentAuthSdk.openWithDataCellular(URL(url), false)
        promise.resolve(convertJsonToMap(res))
      } catch (exception: Exception) {
        val err: WritableMap = WritableNativeMap()
        err.putString("error", "sdk_error")
        err.putString("error_description","internal error: "+exception.message)
        promise.resolve(err)
      }
    }
  }

  @ReactMethod
  fun openWithDataCellularWithDebug(url: String, debug: Boolean, promise: Promise) {
    CoroutineScope(context = Dispatchers.IO).launch {
      try {
        val silentAuthSdk = SilentAuthSDK.getInstance()
        val res = silentAuthSdk.openWithDataCellular(URL(url), debug)
        promise.resolve(convertJsonToMap(res))
      } catch (exception: Exception) {
        val err: WritableMap = WritableNativeMap()
        err.putString("error", "sdk_error")
        err.putString("error_description","internal error: "+exception.message)
        promise.resolve(err)
      }
    }
  }

  @ReactMethod
  fun openWithDataCellularAndAccessToken(url: String,  debug: Boolean, accessToken: String, promise: Promise) {
    CoroutineScope(context = Dispatchers.IO).launch {
      try {
        val silentAuthSdk = SilentAuthSDK.getInstance()
        val res = silentAuthSdk.openWithDataCellularAndAccessToken(URL(url), accessToken, debug)
        promise.resolve(convertJsonToMap(res))
      } catch (exception: Exception) {
        val err: WritableMap = WritableNativeMap()
        err.putString("error", "sdk_error")
        err.putString("error_description","internal error: "+exception.message)
        promise.resolve(err)
      }
    }
  }

  @Throws(JSONException::class)
  fun convertJsonToMap(jsonObject: JSONObject): WritableMap? {
    val map: WritableMap = WritableNativeMap()
    val iterator = jsonObject.keys()
    while (iterator.hasNext()) {
      val key = iterator.next()
      val value = jsonObject[key]
      if (value is JSONObject) {
        map.putMap(key, convertJsonToMap(value))
      } else if (value is JSONArray) {
        map.putArray(key, convertJsonToArray(value))
      } else if (value is Boolean) {
        map.putBoolean(key, value)
      } else if (value is Int) {
        map.putInt(key, value)
      } else if (value is Double) {
        map.putDouble(key, value)
      } else if (value is String) {
        map.putString(key, value)
      } else {
        map.putString(key, value.toString())
      }
    }
    return map
  }

  @Throws(JSONException::class)
  fun convertJsonToArray(jsonArray: JSONArray): WritableArray? {
    val array: WritableArray = WritableNativeArray()
    for (i in 0 until jsonArray.length()) {
      val value = jsonArray[i]
      if (value is JSONObject) {
        array.pushMap(convertJsonToMap(value))
      } else if (value is JSONArray) {
        array.pushArray(convertJsonToArray(value))
      } else if (value is Boolean) {
        array.pushBoolean(value)
      } else if (value is Int) {
        array.pushInt(value)
      } else if (value is Double) {
        array.pushDouble(value)
      } else if (value is String) {
        array.pushString(value)
      } else {
        array.pushString(value.toString())
      }
    }
    return array
  }

  companion object {
    private const val TAG = "SilentAuth RN Bridge"
  }
}
