# Smap JavaRosa

This fork of Javarosa has changes to support [Field Task](https://github.com/smap-consulting/fieldTask4)

## Table of Contents
* [Setting up your development environment](#setting-up-your-development-environment)
* [Building the project](#building-the-project)
* [Changes in this JavaRosa fork](#changes-in-this-javarosa-fork)
* [Acknowledgements](#acknowledgements)

## Setting up your development environment

1. Install [Git](https://git-scm.com/downloads) if you don't have it

1. Download and install [Android Studio](https://developer.android.com/studio/index.html) 

1. Fork this project Smap javarosa project 

1. Clone your fork of the project onto your development workstation using git

1. Import the cloned proect into Android Studio

## Building the project
 
To build the project, go to the `View` menu, then `Tool Windows > Gradle`. 

Find `build` in the Gradle window will in `javarosa > Tasks > build > build` and ouble-click on it to package the application. This Gradle task will now be the default action in your `Run` menu.  Note some unit tests may fail if you are developing on a windows computer.  Add @Ignore to these.

To package a jar, use the `jar` Gradle task.

The generated Jar file will be in ./build/libs

## Changes in this JavaRosa fork

*  Add a default() function that prevents a manually modified default value from being reset on save of the survey

## Acknowledgements

This project:
* forks the [ODK JavaRosa library](https://github.com/getodk/javarosa)
