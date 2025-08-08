Siko WebWrapper - Android project (ready to build with GitHub Actions)

Steps to obtain APK without Android Studio:
1. Create a new GitHub repository (or use an existing one).
2. Upload the contents of this ZIP to the repository root.
3. Push to branch 'main'.
4. Open the repository on GitHub -> Actions -> run the 'Android Build (assembleDebug)' workflow or wait for it on push.
5. When the run finishes, download the artifact named 'app-debug-apk' (app-debug.apk).
6. Transfer the APK to your Android device and install (enable install from unknown sources).

NOTE:
- The project uses media3 + ExoPlayer and a MediaSessionService wrapper. You may need to adjust versions for compatibility.
- If the build fails due to dependency versions, let me know the error and I will adjust the files.
