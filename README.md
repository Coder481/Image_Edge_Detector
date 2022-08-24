## How to add in your project:
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
pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    maven { url 'https://jitpack.io' }
    mavenCentral()
  }
}
```

2. Add the dependency
```
dependencies {
  implementation 'com.github.Coder481:Image_Edge_Detector:Tag'
}
```

[![](https://jitpack.io/v/Coder481/Image_Edge_Detector.svg)](https://jitpack.io/#Coder481/Image_Edge_Detector)
