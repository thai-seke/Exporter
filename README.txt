HARD ROCK AUTO EVENT EXPORTER — AIDE / ANDROID SOURCE

This is Java Android source, NOT an APK. It has not been compiled or tested on your phone.
AIDE support for modern Gradle projects differs by version. If AIDE cannot import this Gradle project, it may require a compatible project template or a cloud/desktop Android build.

1. Install AIDE (Android IDE) from a trusted app store.
2. Extract this ZIP to a folder on your phone using My Files.
3. Open AIDE > Open Project and select the HardRock_AIDE project folder.
4. If AIDE supports this Gradle configuration, build and run the app. Do not assume a successful build until AIDE reports it.
5. Install the built APK if prompted. On newer Android versions, you may have to allow restricted Accessibility settings for a sideloaded app; grant only if you trust this source.
6. In Android Settings > Accessibility > Installed apps, enable Hard Rock Auto Event Exporter.
7. In Hard Rock, open the reserved events list. Switch to exporter, tap Start, then return to Hard Rock within five seconds.
8. When scan ends, switch to exporter, View Results, then Export CSV or Calendar ICS.

LIMITATIONS:
- Only accessibility-visible text is collected, not text embedded in images.
- Package detection accepts package names containing 'hardrock' or 'seminole'; actual installed app package may differ.
- Event extraction is heuristic and not yet validated on a real recording.
- No automatic navigation into the reservation page, no background/scheduled scanning, no XLSX output.
- Calendar output is all-day and assumes 2026 when the year is absent. Review before import.
- No login/password capture, no network permissions, no external uploads.
