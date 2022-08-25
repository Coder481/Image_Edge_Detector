## How to add in your project:

### Download APK from [here](https://github.com/Coder481/Image_Edge_Detector/releases/download/1.2.1/app-debug.apk)
### [Here](https://github.com/Coder481/Test_Project) is the project that implement this library.

[![](https://jitpack.io/v/Coder481/Image_Edge_Detector.svg)](https://jitpack.io/#Coder481/Image_Edge_Detector)

1. Add the JitPack repository to your build file<br>
Add it in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
  ...
    maven { url 'https://jitpack.io' }
  }
}
```
If the above don't work then, add in `settings.gradle`
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    maven{ url 'https://jitpack.io' }
    mavenCentral()
  }
}
```

2. Add the dependency
```
dependencies {
  implementation 'com.github.Coder481:Image_Edge_Detector:<latest_version>'
}
```


### Note:
1. Don't forget to add your app in your firebase project and add `google-services.json` and get started with Firebase Storage<br>
* Add this id in plugins in `build.gradle(:app)`
```
plugins {
  ...
  id 'com.google.gms.google-services'
}
```
* Add the path in `build.gradle(project)`
```
buildscript{
  ...
  dependencies {
    classpath 'com.google.gms:google-services:4.3.13'
  }
}
```
* Add `google-services.json` in `app`

2. To change theme colors
* Change hard-coded colors in app theme in `theme.xml`
```
<item name="colorPrimary">@color/colorPrimary</item>
<item name="colorPrimaryVariant">@color/colorPrimaryDark</item>
```
* Then add `colorPrimary` and `colorPrimaryDark` in `colors.xml`
```
<color name="colorPrimary">#F8CF06</color>
<color name="colorPrimaryDark">#ED9716</color>
```
Change colors of your choice

### Some Visuals

<p>
  <img src="https://user-images.githubusercontent.com/68111551/186571073-cf21dd8d-39fe-4ec2-a4f0-3d21dff3d045.jpg" width="200"/>
  <img src="https://user-images.githubusercontent.com/68111551/186571086-09761d7d-3cdf-4c1f-937a-17e0b1e25041.jpg" width="200"/>
</p>
