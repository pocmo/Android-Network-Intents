[![Build Status](https://travis-ci.org/pocmo/Android-Network-Intents.svg?branch=master)](https://travis-ci.org/pocmo/Android-Network-Intents)

Android Network Intents
=======================
Android Network Intents (ANI) is a library to send Android Intent objects to listening
apps/devices via multicast (UDP). This library has been written to utilize it for
writing simple app or service discovery protocols but as it can be used to send and
receive any Intents through the network without knowing the specific receivers there
might be a lot of other use cases.

Download
--------

Grab via Maven (jcenter):
```xml
<dependency>
  <groupId>com.androidzeitgeist.android-network-intents</groupId>
  <artifactId>android-network-intents</artifactId>
  <version>1.0.0</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.androidzeitgeist.android-network-intents:android-network-intents:1.0.0'
```


Usage
-----

 * [Sending Intents](https://github.com/pocmo/Android-Network-Intents/wiki/Sending-Intents)
 * [Receiving Intents](https://github.com/pocmo/Android-Network-Intents/wiki/Receiving-Intents)
 * [FAQ](https://github.com/pocmo/Android-Network-Intents/wiki/FAQ)

Samples
-------

**IntentChat**: A simple chat application that allows devices in the same network to chat with each other
without connecting to anything.

![IntentChat Screenshot][1]

Developed by
------------

* Sebastian Kaspari <s.kaspari@gmail.com>

License
-------

    Copyright 2013 Sebastian Kaspari

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://raw.github.com/pocmo/Android-Network-Intents/master/samples/IntentChat/screenshot.png
