# Smap JavaRosa

This fork of Javarosa has changes to support [Field Task](https://github.com/smap-consulting/fieldTask4)

## Table of Contents
* [Setting up your development environment](#setting-up-your-development-environment)
* [Building the project](#building-the-project)
* [Changes in this JavaRosa fork](#changes-in-this-javarosa-fork)
* [Acknowledgements](#acknowledgements)

## Setting up your development environment

1. Download and install [Git](https://git-scm.com/downloads) and add it to your PATH

1. Download and install [Android Studio](https://developer.android.com/studio/index.html) 

1. Fork this project Smap javarosa project 

1. Clone your fork of the project onto your development workstation using git

## Building the project
 
To build the project, go to the `View` menu, then `Tool Windows > Gradle`. `build` will be in `javarosa > Tasks > build > build`. Double-click `build` to package the application. This Gradle task will now be the default action in your `Run` menu. 

To package a jar, use the `jar` Gradle task.

The generated Jar file will be in ./build/libs

## Changes in this JavaRosa fork

*  Add a default() function that prevents a manually modified default value from being reset on save of the survey

## Acknowledgements

This project:
* forks the [ODK JavaRosa library](https://github.com/getodk/javarosa)
