# Blaum

## General

### Description
Blaum is a sample app to load and display a list of songs fetched from [this endpoint](https://static.leboncoin.fr/img/shared/technical-test.json).
The app should be able to store data locally in order to work offline.

### Features

- Load, save and display a list of songs from an online json
- Filter the result by title
- Display title details

### Design system

This projects tries to follow Material UI guidelines.
The design system has been thought as reflecting the NYC taxi colors. 
It follows this color system :  

- Light : 
- Dark 

##Implementation

The app has been developed using Kotlin, for a minimum SDK version of 21.


###Architecture

The app is build following a MVVM architecture.
It follows as much as possible the SOLID guidelines.
On the UI side, data is 

The project is divided in 4 modules in addition to the app :
1. entities
   - Contains the app models

2. presentation
   - Contains every UI related code
     - Navigation
     - Activity
     - Assets
 
3. use_cases
   - Contains the business logic
   
4. data_source
   - Contains implementations of use_cases interfaces
   - Contains code to get data from remote and database

Separating the code into different modules allows to build more quickly.
It is also a good practice since it allows to plug, remove, edit modules on their own, most times without affecting the rest of the code.
Interfaces were also implemented to implement reverse dependencies.

###Technical choices

The app uses a variety of widely used libraries :
- `androidx` libraries for android functionalities,
- `Koin` for dependency injection,
- `Retrofit` for api requests,
- `Room` for the local database,
- `RxKotlin` to build a reactive flow,
- `Glide` to load and display images,
- `Espresso` for the UI tests,
- `junit` for the unit tests,

###Git

The project was versioned on Github, following this guideline: 
- 1 branch for 1 feature
- merge of the feature branch in master once its task is finished

In bigger projects, such behaviors allow to have a clean and clear git tree, and to simply follow Pull Requests (for Pear code review)   

###Testing

The project contains two types of test: 

1. Unit tests
   - Unit tests allow to test functions independently, and make sure some core functionalities are not broken. 
     For example testing its business logic is very important to ensure the app does not return wrong results because of a wrong business logic (this is the core of the app).
     
     
2. UI tests
   - UI tests allow to test if the interactions with the interface are working fine. 
   
   
   