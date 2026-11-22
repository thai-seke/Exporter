# Build from your Android phone (GitHub Actions)

This source project includes a cloud build workflow. It has NOT been compiled or tested on the Hard Rock app.

One-time setup:
1. Create a private GitHub repository using your phone browser.
2. Upload the **contents** of this folder to the repository root, including the `.github/workflows/build-apk.yml` file. GitHub mobile web may not allow uploading hidden `.github` directories conveniently; use GitHub web editor to create `.github/workflows/build-apk.yml` by copying its text if needed.
3. Open your repo > Actions > Build Android APK > Run workflow. Wait for build to finish.
4. Open the completed run > Artifacts > HardRock-Auto-Exporter-APK; download and unzip it.
5. Tap `app-debug.apk` to install. Android may ask to allow installing apps from the browser/files app.
6. Open Android Settings > Accessibility > Installed apps > Hard Rock Auto Exporter and enable it.
7. Navigate to the Hard Rock Reserved Events page, then open the exporter and press Start. Switch back to Hard Rock before the countdown ends.
8. Export CSV and ICS from the exporter after the scan.

For later updates, use Actions > Build Android APK > Run workflow again; no PC or AIDE needed.

Important limitations:
- Android permissions and APK installation require user confirmation.
- Existing app currently uses accessibility-readable text only, not OCR. It may miss image-only event cards.
- The scanner recognizes only foreground app package names containing `hardrock` or `seminole` and requires the reserved-events page open.
- Debug APKs are signed with an ephemeral debug key in GitHub Actions, so future APK builds may not install over an earlier build. Uninstall the old APK before reinstalling if signatures differ; export your data first.
- This is an untested starter implementation, not guaranteed to build or accurately parse all events.
