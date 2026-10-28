# Group Messenger Android Project

This is a legacy Android Group Messenger project written in Java.

## Source files uploaded

- `gr-me-an/src/edu/buffalo/cse/cse486586/groupmessenger/GroupMessengerActivity.java` — message handling and emulator socket communication
- `gr-me-an/src/edu/buffalo/cse/cse486586/groupmessenger/ExProvider.java` — local content provider
- `gr-me-an/src/edu/buffalo/cse/cse486586/groupmessenger/OnPTestClickListener.java` — provider test listener
- `gr-me-an/res/` — Android layout, menu, strings and theme resources
- `gr-me-an/AndroidManifest.xml` — app manifest
- `gr-me-an/project.properties`, `.project`, `.classpath` — legacy Eclipse/Ant project metadata

## Important notes

This upload contains the main Java source and text-based project resources. It does **not** yet contain every file from the original ZIP, including the full set of image assets, libraries, generated build files, and the prebuilt APK.

The original project uses the old Eclipse/Ant Android tooling and targets Android API 16. A current Android Studio installation may require migration before it can be built. Messaging is designed for an Android emulator test environment, not as a production internet messaging service.
