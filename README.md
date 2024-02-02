## Taskify App (NYT Internship Challenge Project)

Taskify is a simple to do application that allows to manage tasks by adding, editing, and marking tasks as done. The application is built using the Jetpack Compose library and follows the MVVM architecture pattern. 
The application uses the Room database to store the tasks locally. The application, and it's architecture references the [inventory app](https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app/tree/main) part of the official [Android Basics with Jetpack Compose Training](https://developer.android.com/courses/android-basics-compose/course) 
by Google.

### Features 
The following core features are implemented in the app:
- [x] Add a task
- [x] Toggle task done or not done

The following additional features are implemented in the app:
- [x] Edit a task
- [x] Sort tasks by done and not done, with done being at the bottom

### Architecture Overview
The app adheres to MVVM architecture pattern, embracing single source of truth principle, via way of using a UDF (Unidirectional Data Flow) pattern. With the use of ViewModels, UI changes are observed
by the ViewModel by call back functions that trigger on events like button clicks. View Models respond to these changes by updating UI state. The UI state is then observed by the UI layer, which updates the UI accordingly.

There are three main screens in the app: the task list screen, the add task screen, and the edit task screen. Each screen has its own ViewModel and UI state. 
A factory pattern is used to create the ViewModels, which are then injected into the composables using the `viewModel` and `viewModelFactory` parameters.

The app also uses the Repository pattern to abstract the data layer from the rest of the app. The repository is responsible for handling data operations and provides a clean API to the rest of the app.
The repository is injected into the ViewModels using the `repository` parameter. The repository uses the Room database to store the tasks locally. It is interfaced with the DAO (Data Access Object) to perform CRUD operations on the tasks.

As for the shape of the data, the app uses a data class to represent a task entity. They both have a one-to-one mapping with the database and only contaian a string field as well as a boolean 
field to represent the task's name and whether it is done or not.

### File Structure 
From the source folder, The app is divided into the following four main packages: data, ui, theme, and util. 
The data package contains the repository, the database, and the data class. It also houses the database DAO used to communicate with the database and the App Container
to house the repository. The ui package contains the screens, their view models, and composables as well as the navigation; all found in their repsective folders. 
The theme package contains the theme and the colors. I mostly rely on MaterialTheme from MaterialUI 3. The util package contains important constants like database name.

### Dependencies 
The app uses the following dependencies also found in the app build.gradle file:
```kotlin
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.6.0")

    //Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("org.testng:testng:6.9.6")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")

    // Testing
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
```

as well as the following plugins
```kotlin
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
```

### Testing
For Testing, I used the built in JUnit4 Testing Framework to test the DAO and the repository. However I did not get a chance to test the UI layer

### Challenges and Learnings
This was a very fun and challenging project to work on and get reacquainted on MVVM architecture, Jetpack Compose, and Kotlin co-routines especially given the time. 

I learned alot about how navigation works in Jetpack compose, and how it is much easier than the traditional way of navigating between screen with intents. 
I also had a lot to learn about flows and how to use them to observe data changes in the UI layer. It was interesting getting to learn more about flows make the app more reactive, and is very well suited 
for the MVVM architecture pattern as well kotlin rich support for coroutines. I also learned how to use the factory pattern for creating ViewModels and how to inject them into the composables.

Given the time, I was not able to implement UI testing which is essential for a production app. My biggest challenge was getting the gradle files to sync,
you may notice that some of the dependencies being used reference earlier versions of the libraries. This was because I was not able to properly sync the gradle files to the latest versions and there is certainly more
to be learned about how to using gradle as a tool. 

Overall it was a great learning experience and I am happy with the app I was able to build in the time given. I intend to continue to work on it and make it a reasonable portfolio project. 

