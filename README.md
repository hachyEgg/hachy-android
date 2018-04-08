# Hachy-android
Hachy-android lets your candling egg by mounting the device onto a cardbox


## Set up and Use: 
1. Purchase or make a cardbox of the following specs: 
(insert image)

2. insert the device into the cardbox, A completed set up would look like this:
(insert image)

3. insert egg, close egg and turn on the light source, observe that the phone screen will show a the egg:
(insert image)

4. Press the button to capture the egg, the image will be persisted on the cloud and sent to Azure for analysis, Upon result complete, the status of the egg will change from "egg not found" to the deteremiend result
(insert image)

5. Navigate back to observe a catalogue of egg status

## install app
####option 1: Clone this repo and build it yourself
`$git clone https://github.com/hachyEgg/hachy-android`
Open up Android Studio->Open->find this folder->open

####Option 2: Install apk from project's debug folder

####Option 3: Download at http://hachy.azurewebsites.net/download



## Web-Client
The [webClient](https://github.com/hachyEgg/hachy-web-dist) lets you see analysis data in realtime sync with the device. As the phone maybe a little hard to check as it is been mounted.
![screenshot](https://user-images.githubusercontent.com/7799433/38462289-84e70c60-3ab2-11e8-9587-d5706807c0a2.png)

## Datasets: 
The datasets of which this modal is trained labeled in 4 different tags: 
 * egg_0 for egg with no development (no development)
 * egg_1 for egg with a visible zygote (developing)
 * egg_2 for egg with visible blood lines and other organs (mature)


## Demo
Demo of this application can be found from [TBD]().


### Requirement:
 * Android Studio 3.0.0
 * Android 4.4 KitKat, API Level 19
 * 1G of allowcated ram

### Permission
 

## AUTHOR and LICENSE
Copyright (c) 2016 Eugene Wang [The MIT License (MIT)](LICENSE)



