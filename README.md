# MySahara <img src="app/src/main/res/drawable/logo.png" alt="logo" width="35"/>
*"A GPS Based Tracking System"*

[![Build Status](https://travis-ci.org/gavindsouza/MySahara.svg?branch=master)](https://travis-ci.org/gavindsouza/MySahara)

## Getting Started


#### Clone this repository to get it up on your local system.

```
git clone https://github.com/gavindsouza/MySahara.git
```
## What to look for here?
- [System Summary](#system-summary)
- Features
  1. [Signup and Login](#signup-and-login)
  2. [Live Tracking and Notifications ](#live-tracking-and-notifications)
  4. [SOS for trackee](#sos-for-trackee)
- [License](#license)

## System Summary

MySahara is an Android Application primarily constructed for senior citizen tracking. A citizen (trackee) and their family member (tracker) can communicate with emergency call / messaging options as well as live location tracking. A notification will be generated if the trackee exits the set geofence.

## Features

### Signup and Login
The *tracker* needs to register first by setting their username and password followed by which they need to enter the *trackee* details. These include name and geofence(max boundary in metres). Also includes setting a home location and generating a *code* (just a button press, don't worry).<br>
This *code* is useful for trackee login, while the tracker logs in the same ole way.<br>
>![login1](https://user-images.githubusercontent.com/25857446/58211984-fed49380-7d0a-11e9-842e-462d3b7426d9.jpg) &nbsp; &nbsp; &nbsp;![register](https://user-images.githubusercontent.com/25857446/58209639-0b55ed80-7d05-11e9-9100-8c7a5ac485c3.gif)
### Live Tracking and Notifications
On the tracker end, the senior's live location will be visible. Once the senior crosses the geofence , the tracker will receive a notification.<br>

>![livetrack](https://user-images.githubusercontent.com/25857446/58212352-1b250000-7d0c-11e9-85f5-aba3bc876cd8.jpg)
 &nbsp; &nbsp; &nbsp; ![notification](https://user-images.githubusercontent.com/25857446/58212419-5f180500-7d0c-11e9-8ee5-803c2fbefccf.jpg)


### SOS for trackee
The trackee can call the emergency number i.e. the tracker by just tapping on the phone icon or sending an SMS by tapping on the SMS icon.

>![sos](https://user-images.githubusercontent.com/25857446/58212543-c33ac900-7d0c-11e9-9555-a28258d16abc.jpg)

<br>This project was a part of the 'Network Programming Lab' course, 2018.

License
-------

This project is licensed under the [MIT] license.

[MIT]: LICENSE
[logo]: /app/src/main/res/drawable/logo.png
