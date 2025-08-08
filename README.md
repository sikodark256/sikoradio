Siko WebWrapper - Android project (ready to build with GitHub Actions)

Steps to obtain APK without Android Studio:
1. Create a new GitHub repository (or use an existing one).
2. Upload the contents of this ZIP to the repository root (include the .github folder).
3. Push to branch 'main' (or 'master').
4. Open the repository on GitHub -> Actions -> run the 'Android Build (assembleDebug)' workflow or wait for it on push.
5. When the run finishes, download the artifact named 'app-debug-apk' (app-debug.apk).
6. Transfer the APK to your Android device and install (enable install from unknown sources if necessary).

Notes:
- The app opens https://sikoradio.nicepage.io/ inside a WebView and uses a foreground MediaSessionService to play streams requested by the web via JavascriptInterface.
- If the build fails due to dependency versions, tell me the error and I will adjust the files.
