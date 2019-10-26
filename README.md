## GeoSquar

A mobile app for displaying realtime nearby places using FourSquare APIs.

## Challenge description
App uses Foursquare API to display information about nearby places
around user using user’s current location specified by Latitude and
Longitude
App has two operational modes, “Realtime" and “Single Update”.
“Realtime” allows app to always display to the user the current near by
places based on his location, data should be seamlessly updated if user
moved by 500 m from the location of last retrieved places.
“Single update” mode means the app is updated once in app is launched
and doesn’t update again
User should be able to switch between the two modes, default mode is
“Realtime”. App should remember user choices for next launches.

## Screenshot
<img src="https://github.com/Ahmed3Elshaer/GeoSquar/blob/master/art/1.png"></a>


## Specifications
- Caching for showing the last places offline.
- portrait and landscape.
- using MVVM
- using RxJava
- Unit test(Under development).
- Partly include comments.
- Reactive code

## Languages, libraries and tools used

 * [Kotlin](https://kotlinlang.org/)
 * [androidX libraries](https://developer.android.com/jetpack/androidx)
 * [Android LifeCycle](https://developer.android.com/topic/libraries/architecture)
 * [Glide](https://github.com/bumptech/glide)
 * [Room](https://developer.android.com/jetpack/androidx/releases/room)
 * [Retrofit2](https://github.com/square/retrofit)
 * [Koin](https://github.com/InsertKoinIO/koin)
 * [Moshi](https://github.com/square/moshi)
 * [LocationAPP Wrapper,RxLocatation](https://github.com/Ahmed3Elshaer/GeoSquar/tree/master/app/src/main/java/com/ahmed3elshaer/geosquar/common/location) Simple location wrapper developed myself and customized to be working specifically with the project.
 * [RxJava](https://github.com/ReactiveX/RxJava)
 * [Turf](https://docs.mapbox.com/android/java/overview/turf/)
 * [RxKotlin](https://github.com/ReactiveX/RxKotlin)
 * [RxAndroid](https://github.com/ReactiveX/RxAndroid)
 
 
## Requirements
- min SDK 19

## Installation

- Just clone the app and import to Android Studio.
``https://github.com/Ahmed3Elshaer/GeoSquar.git``

## Known Issues 
 - Still working on adding Unit tests to the app
 - Sometimes images won't load as the quota on FourSquare Api is very limited.


## Implementation

* In this project I'm using [MVVM Pattern](https://developer.android.com/jetpack/docs/guide)
as an application architecture adopted with usage of UseCases with these design patterns in mind:-
- Repository Pattern
- Facade Pattern
- Singleton
- Observables
- Adapters

* Using Koin for dependency injection that will make testing easier and our make code 
cleaner and more readable and handy when creating dependecies.
* Using Retrofit library to handle the APIs stuff.
* Using Room for caching movies
* [Creating Custom ImagesLoader](https://github.com/Ahmed3Elshaer/GeoSquar/tree/master/app/src/main/java/com/ahmed3elshaer/geosquar/common/loader) with Glide to handle getting the Venues Photos from FourSquare Api and the extract a url and then load it into ImageView with no pain of writting logic inside the RecyclerView Adapter 
* Using RxJava heavily to create a Reactive and flowless experince
* Creating custom [Location Api](https://github.com/Ahmed3Elshaer/GeoSquar/tree/master/app/src/main/java/com/ahmed3elshaer/geosquar/common/location) to handle the project use cases and integrate easily the the features
* Separation of concerns : The most important principle used in this project to avoid many lifecycle-related problems.
<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png"></a>
* Each component depends only on the component one level below it. For example, activities and fragments depend only on a view model. The repository is the only class that depends on multiple other classes; in this example, the repository depends on a persistent data model and a remote backend data source.
This design creates a consistent and pleasant user experience. Regardless of whether the user comes back to the app several minutes after they've last closed it or several days later, they instantly see a user's information that the app persists locally. If this data is stale, the app's repository module starts updating the data in the background.
* Using to best of managing ViewState with less complex tools , using Data Classes and LiveData we created a solid source that we can expose to view to show what the app can do to the user without worring about the side effects
```
data class HomeViewState(
        val isLoading: Boolean = false,
        val venues: List<Venue>? = null,
        val error: Throwable? = null
)
```
A View will a) emit its event to a ViewModel, and b) subscribes to this ViewModel in order to receive states needed to render its own UI.


Then ViewModel starts to delegate the event to it's suitable UseCase with thread handling in mind using RxJava (Logic Holders for each case)

```
  fun exploreRealtime(location: Location) {
        add {
            exploreVenuesRealtimeUseCase(VenuesRequest("${location.latitude},${location.longitude}"))
                    .compose(applySchedulers())
                    .doOnSubscribe { post(previousValue()?.copy(isLoading = true)) }
                    .subscribe({
                        ....
                    }, {
                       ....
                    })
        }
    }
```
As the UseCase process and get the required models form the repository it returns the result back to the ViewModel to start Exposing it as LiveData to our Lifecycle Owner (Activity)
```
    override val _viewState = MutableLiveData<Event<HomeViewState>>()

```
```
                subscribe({
                        post(previousValue()?.copy(isLoading = false, venues = list))
                    }, {
                        post(previousValue()?.copy(isLoading = false, error = error))
                    })
                   
```

then back to our Activity it will listen for any change in our LiveData ( ViewState Holder) and React to it.

```
 viewModel.viewState.observe(this, Observer {
            render(it)
        })
```

```
private fun render(event: Event<HomeViewState>) {
        event.getContentIfNotHandled()?.apply {
            renderLoading(isLoading)
            error?.let {
                error.printStackTrace()
                renderError(error)
                return
            }
            venues?.let {
                if (it.isEmpty())
                    renderEmptyState()
                else
                    renderVenues(it)

            }

        }
    }
```

## Immutability
Data immutability is embraced to help keeping the logic simple. Immutability means that we do not need to manage data being mutated in other methods, in other threads, etc; because we are sure the data cannot change. Data immutability is implemented with Kotlin's data class.

## ViewModel LifeCycle
The ViewModel should outlive the View on configuration changes. For instance, on rotation, the Activity gets destroyed and recreated but your ViewModel should not be affected by this. If the ViewModel was to be recreated as well, all the ongoing tasks and cached latest ViewState would be lost.
We use the Architecture Components library to instantiate our ViewModel in order to easily have its lifecycle correctly managed.


## License
MIT License
```
Copyright (c) [2019] Ahmed Elshaer
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

