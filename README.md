# Hard Rock Auto Event Exporter — Android source

## Build
Open this directory in Android Studio (JDK 17, Android SDK 35), allow Gradle sync, then Build > Build APK(s). The debug APK will appear under `app/build/outputs/apk/debug/`.

## Use
1. Install the APK on your Android device.
2. Open exporter and tap Enable Accessibility. Turn on Hard Rock Event Exporter service in Android Accessibility settings.
3. Open the Hard Rock app and navigate to the **Reserved Events** list.
4. Return to exporter, tap Start Auto Scan, and switch back to Hard Rock within 5 seconds.
5. The app scrolls and reads accessible text, stopping at repeated content or after 250 scrolls.
6. Return to exporter, review Show Captured Events, and export CSV or ICS via Android's save dialog.

## Known limitations / security
- This is source code, not a compiled or tested APK.
- Does NOT automatically navigate to the Reserved Events page; user must open it.
- Only reads Android accessibility text, not text drawn into images or inaccessible WebViews. OCR is not implemented.
- Hard Rock's actual package name, layout, and event formatting have not been verified. Scanning is restricted to packages containing `hardrock` or `seminole` and may need adaptation.
- The parser is heuristic; verify all event details before relying on exports. Events without a recognizable date/title are omitted.
- ICS exports all-day events (with displayed time in description) to avoid fabricating event times. If the date lacks a year, it currently assumes 2026. This needs improvement for later-year events.
- No credentials, networking, or background monitoring are used. Data stays on the phone.
- Accessibility must be explicitly enabled by the user and can be disabled at any time.
