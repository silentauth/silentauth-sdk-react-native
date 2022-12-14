# SilentAuth SDK for React Native
[![License][license-image]][license-url]

The only purpose of the SDK is to force the data cellular connectivity prior to call a public URL, and will return the following JSON response

* **Success**
When the data connectivity has been achieved and a response has been received from the url endpoint
```
{
"http_status": string, // HTTP status related to the url
"response_body" : { // optional depending on the HTTP status
           ... // the response body of the opened url 
           ... // see API doc for /device_ip and /redirect
                },
"debug" : {
    "device_info": string, 
    "url_trace" : string
          }
}
```

* **Error** 
When data connectivity is not available and/or an internal SDK error occurred

```
{
"error" : string,
"error_description": string,
"debug": {
    "device_info": string, 
    "url_trace" : string
          }
}
```
Potential error codes: `sdk_no_data_connectivity`, `sdk_connection_error`, `sdk_redirect_error`, `sdk_error`.


## Installation

```sh
npm install @silentauth/silentauth-sdk-react-native
```

For Android, add the following to your application's `build.gradle`:

```groovy
maven {
    url "https://gitlab.com/api/v4/projects/40053021/packages/maven"
}
```

## Compatibility

- [Android](../silentauth-sdk-android#compatibility)
- [iOS](../silentauth-sdk-ios#compatibility)

## Usage
* Is the device elgibile for silent authentication?

```js
import SilentAuthSdkReactNative, {
  ReachabilityResponse,
  CheckResponse,
  CheckErrorBody,
  CheckSuccessBody,
  ApiError,
  ReachabilityBody,
  ReachabilityResponseBody,
} from '@silentauth/silentauth-sdk-react-native';

// ...

 const res = await SilentAuthSdkReactNative.openWithDataCellular<ReachabilityResponse>("https://eu.api.silentauth.com/public/coverage/v0.1/device_ip");
    if ('error' in res) {
      // error ${err.error_description}
    } else if ('http_status' in res) {
      const httpStatus = success.http_status;
      if (httpStatus === 200 && res.response_body !== undefined) {
        const body = res.response_body as ReachabilityBody
        // device is eligible on MNO  ${body.network_name}
      } else if (httpStatus === 400 && res.response_body !== undefined) {
        const body = res.response_body as ApiError;
        // MNO not supported ${body.detail}
      } else if (httpStatus === 412 && res.response_body !== undefined) {
        const body = res.response_body as ApiError;
        // Not a mobile IP ${body.detail}
      } else if (res.response_body !== undefined) {
        const body = res.response_body as ApiError;
        // other error see ${body.detail}
      }
    }

```
* How to open a check URL return?

```js
import SilentAuthSdkReactNative, {
  ReachabilityResponse,
  CheckResponse,
  CheckErrorBody,
  CheckSuccessBody,
  ApiError,
  ReachabilityBody,
  ReachabilityResponseBody,
} from '@silentauth/silentauth-sdk-react-native';

// ...

const res = await SilentAuthSdkReactNative.openWithDataCellular<CheckResponse>(checkUrl);
      if ('error' in res) {
        // error see ${err.error_description}
      } else if ('http_status' in res) {
        const httpStatus = res.http_status;
        if (httpStatus === 200 && res.response_body !== undefined) {
            if ('error' in res.response_body) {
              const body = res.response_body as CheckErrorBody;
                // error see ${body.error_description}
            } else {
              const body = res.response_body as CheckSuccessBody;
                // send ${body.code}, ${body.check_id} and ${body.reference_id} to back-end 
                // to trigger a PATCH /checks/{check_id}
            }
        } else if (httpStatus == 400 && res.response_body !== undefined) {
          const body = res.response_body as ApiError;
          // MNO not supported see ${body.detail}
        } else if (httpStatus === 412 && res.response_body !== undefined) {
          const body = res.response_body as ApiError;
          // Not a mobile IP see ${body.detail}
        } else if (res.response_body !== undefined) {
          const body = res.response_body as ApiError;
          // other error see ${body.detail}
        }
      }

```

## Example Demo

There's an embedded example demo is located in the `example` directory, see [README](./example/README.md)

### Releasing

Update `CHANGELOG.md` and finesse.

```
$ yarn run changelog
```

Commit the changes:

```
$ git commit -m 'chore(release): v{version}'
```

Tag:

```
$ git tag v{version}
```

Publish a new canary build to test.

```
$ yarn run publish:canary
```

Publish a full version:

```
$ yarn run publish:latest
```

## Meta

Distributed under the MIT license. See ``LICENSE`` for more information.

[https://github.com/silentauth](https://github.com/silentauth)

[license-image]: https://img.shields.io/badge/License-MIT-blue.svg
[license-url]: LICENSE
