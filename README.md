# Movies
It is a sample app that shows information about movies and series.
The goal is to build a scalable, maintainable and testable app, implementing good development practices combined with an architecture that follows Clean Architecture principles and MVVM pattern.

## Environment
- Kotlin 1.5.20
- Gradle 7.0.2
- JDK 11

## Supported Android versions
- Min SDK : 21
- Compile & Target SDK: 31

## Pre-Requisites
- Add `TMDB_API_KEY` (The Movie API Key) in `local.properties`

## Project characteristics
- Kotlin
- Coroutines
- Retrofit
- Jetpack
  - LiveData
  - LifeCycle
  - ViewModel
  - Room
  - Databinding
  - Navigation
- Hilt


## TO DO
-[ ] Unify styles
-[ ]  Implement pagination with [Paging](https://developer.android.com/jetpack/androidx/releases/paging)
-[ ]  Implement custom base adapter to avoid boilerplate code (similar to [Epoxy](https://github.com/airbnb/epoxy) or [PlaceHolderView](https://github.com/janishar/PlaceHolderView))
-[ ]  Implement data biding in RecyclerViews and ViewHolders
-[ ]  Nested recyclerviews: Save and restore horizontal scroll position
-[ ]  Clean up "Loading view" with [Shimmer](https://facebook.github.io/shimmer-android/) effect implementation
-[ ]  Support Dark theme
-[ ]  Localization
-[ ]  Implement a cache invalidation policy
-[ ]  Analyze a better data update policy:
  - Add a work to update local data periodically in background
  - Update data from remote if it was not updated in a certain time. In other case get data from local storage.
-[ ]  Add Github Actions:
  - Check PRs (Danger validation + run tests)
  - Build APK and publish on Firebase based on branching process to be defined.
-[ ]  Add more unit tests (code coverage > 90%)
-[ ]  Integration tests (with [OkReplay](https://github.com/airbnb/okreplay))
-[ ]  UI tests
-[ ]  End to end tests

## Future improvements
- Migrate XML layouts to [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Analyze separate code: one module by feature 
 
