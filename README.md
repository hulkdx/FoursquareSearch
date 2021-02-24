# FoursquareSearch

Search for venues from [Foursquare Search API](https://developer.foursquare.com/docs/places-api/getting-started/) at the user current location and show it on a list of items.

## Environments

- Gradle 4.1.2
- Kotlin 1.3.72
- Coroutines 1.3.9
- minSdkVersion 24 (Android 7)
- targetSdkVersion 30 (Android 11)
- MVP architecture

## How to build the app

Edit **buildSrc/src/main/java/BuildConfig.kt** and place your Foursquare's **CLIENT_ID** and **CLIENT_SECRET** ([Instruction on how to receive it](https://developer.foursquare.com/docs/places-api/getting-started/)) and then you can build it with Android Studio 4.1.2.

## Structure of the code

**data** contains the data layer of the project, i.e. api, disk, memory cache classes.

**di** dagger/Hilt specific modules for ApplicationComponent (e.g. singleton classes, etc).

**features** feature of the application which contains model, view, presenter.

**mvp** general mvp structure of the application, containing base classes, interfaces for mvp.

**utils** util classes.

## Tests

SearchActivityTest: Integration tests for SearchActivity
SearchPresenterTest: Unit tests for SearchPresenter

## Improvement that can be done

- multi modules can be added to decrease the build time, create a separation between different features, however this might complex the project since we need to handle the common module (which usually is called **base** or **core**).
- the search could be debounced so that we don't call the server with unnecessary api calls.
