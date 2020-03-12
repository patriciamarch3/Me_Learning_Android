# Notes for Android Lectures and App Development

## Lectures
- Free Course by Johan Jurrius, using Android Studio & Java
    + [Course on Udemy](https://www.udemy.com/course/learn-android-application-development-y/learn/lecture/7810956#content) (Stopped uploading, moved to Youtube)
    + [Course on Youtube](https://www.youtube.com/channel/UCKjZAnlSbJMM-Bmmx1BftKg/videos) (Stopped uploading)
- [Udacity Course with Google](https://www.udacity.com/google), limited free courses

## Android Development:
### UI
- [Color Arrangement / Palette](https://www.materialpalette.com/)

### Libraries
- [Released libraries and latest version](https://developer.android.com/jetpack/androidx/versions/)
- [Library mappings from old support library artifacts to androidx](https://developer.android.com/jetpack/androidx/migrate/artifact-mappings)

### Software
- Android Studio:
    + Based on Intellij
    + [Android v7 Support Libraries' appcompat library has been migrated to AndroidX library.](#libraries)
    + To make layout_weight property work, need to change width or height to 0dp.

### App Release
- Signed APK can be released and installed.
    + Needs key store.
    + Once the key store is lost, the app can no longer be updated in the App Store or for those who have downloaded it.

## Android Basics:
### Intent
A bundle of information of interest to the receiving component (such as the actions to be taken and the data to act on) and to the Android system (such as the category of the components that should handle the intent and the instructions on how to launch the target activities).
- Explicit intent: 
    + utilize services of a component you know the name of
    + typically used for application internal messages (such as start background services)
- Implicit intent: 
    + activate components in other applications that you do not know the name of
    + the Android system compares the contents of the intent to intent filters and finds a components to execute this intent
- Intent filter: structures associated with receiving components that advertise their capability and delimit the intents they can handle
    + A component without intent filter can only receive explicit intents, whereas those with intent filter can receive both explicit and implicit intents.

### Fragment
A behavior or a portion of user interface in an activity.
- A module section of activity that has its own lifecycle and is reusable.
- If the activity get destroyed or paused, so will all the fragments it hosts.
- Two fragments can communicate with each other only through the activity that is hosting them.


