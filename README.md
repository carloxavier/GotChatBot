This is a library for Android and iOS, for building a sample chatbot with Gemini.

* `/chatbot` is for code that will be shared across Android and iOS.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/androidApp` contains a sample Android app, showing how to use the Chatbot library from Android
* `/iosApp` contains a sample iOS app, showing how to use the chatbot library from iOS
