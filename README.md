Robohash for Android!
==========================
[![Build Status](https://travis-ci.org/neuhalje/android-robohash.svg?branch=master)](https://travis-ci.org/neuhalje/android-robohash)
 
This library creates recognizable images of cute robots from hash values (or basically any other data). It is a port of the source at [RoboHash.org](http:/RoboHash.org).

![Example robot 1](readme.d/robot1.png)

Usage
==========

Sometimes is necessary to create a _personality_ for data in your applications. A prime example are avatars for users that have no photo attached.

See the [example project](https://github.com/neuhalje/android-robohash-example).

Configuration
----------------

*Calculate the image*
* Pass in the hash (or an uuid) of the data you want to 'personalize'. Get a stable (== you can persist it) handle

*Get an image*
* pass in the handle, and the desired resolution to get the image


Example robots
-------------------

![Example robot 1](readme.d/robot1.png)
![Example robot 2](readme.d/robot2.png)

Contributors
==================
* Colin Davis -- Wrote the original RoboHash
* Set 1 artwork created by Zikri Kader
* Set 2 artwork created by Hrvoje Novakovic.
* Set 3 artwork created by Julian Peter Arias.

The RoboHash images are available under the CC-BY-3.0 license.


Publish
=========
`./gradlew bintrayUpload`
