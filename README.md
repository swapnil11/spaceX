1. Using MVVM + CLEAN architecture.
2. Using Androidx and Jetpacks.
3. Using Retrofit for network communications.
4. Using Viewmodels and LiveData.
5. Using Glide for image loading.
6. Using Hilt for dependency injection.
7. Using Navigation Architecture.
8. Using data binding.
9. Mockkio and Junit for non ui testing.
10. This app is focused for phone and supports portrait and landscape modes.

One Launch endpoint hasn't been incorporated, as the new V4 API for one launch gives us the same single launch data as the one received in the list. Hence the launch item clicked in the list is passed through as a navigation param to details screen. In Details screen one rocket api is called to show data of the rocket. While the launch detail is shown from the navigation param of launch object.
