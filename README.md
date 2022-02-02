![github readme](https://user-images.githubusercontent.com/12948924/82737029-bddada80-9ce2-11ea-98bf-b84f2c0344c6.png)

A collection of custom Scene2D widgets and utilities for libGDX.

## About

Stripe is a companion to [Skin Composer](https://github.com/raeleus/skin-composer), the renowned UI Skin editor and visual layout utility. This library provides new Scene2D widgets, the capability of loading FreeType fonts from JSON, and the Scene Composer Stage Builder. Please see the [wiki](https://github.com/raeleus/stripe/wiki) for documentation. 

## How to Include Stripe Widgets in your Project

Typical of most libGDX projects, Stripe requires the Gradle setup to be included your project.

### Core Dependency
Add the following to your root build.gradle:
```groovy
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your core project:
```groovy
dependencies {
    ...
    implementation 'com.github.raeleus.stripe:stripe:1.0.1'
}
```

### HTML5 Dependency
Add the dependency to your html project of your root build.gradle if you want HTML5/GWT support:
```groovy
project(":html") {
    apply plugin: "gwt"
    apply plugin: "war"

    dependencies {
        ...
        implementation 'com.github.raeleus.stripe:stripe:1.0.1:sources'
    }
}
```

Add the following inherits line to your GdxDefinition.gwt.xml in the html project:  
`
<inherits name="com.ray3k.stripe" />
`

## How to Include FreeTypeSkin in your Project

FreeTypeSkin has been moved into a separate project to allow users to use the Stripe Widgets without forcing them to import the GDX FreeType classes.

### Core Dependency
Add the following to your root build.gradle:
```groovy
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your core project:
```groovy
dependencies {
    ...
    implementation 'com.github.raeleus.stripe:freetype:1.0.1'
}
```
Ensure that you have FreeType implemented in your project. Please refer to the [libGDX wiki](https://libgdx.com/wiki/articles/dependency-management-with-gradle#freetypefont-gradle).

### HTML5 Dependency
Add the dependency to your html project of your root build.gradle if you want HTML5/GWT support:
```groovy
project(":html") {
    apply plugin: "gwt"
    apply plugin: "war"

    dependencies {
        ...
        implementation 'com.github.raeleus.stripe:freetype:1.0.1:sources'
    }
}
```

Add the following inherits line to your GdxDefinition.gwt.xml in the html project:  
`
<inherits name="com.ray3k.stripe" />
`

Then follow the instructions to activate [gdx-freetype-gwt](https://github.com/intrigus/gdx-freetype-gwt).

## How to use
Stripe meets a variety of needs, so its implementation is dependent on what utilities you plan to use. Please refer to the wiki:

[Stripe Widgets](https://github.com/raeleus/stripe/wiki)  
[FreeType Skin Loader](https://github.com/raeleus/skin-composer/wiki/Creating-FreeType-Fonts#using-a-custom-serializer)  
[Scene Composer Stage Builder](https://github.com/raeleus/skin-composer/wiki/Scene-Composer)
