# Popular Movies

### Udacity - Android Nanodegree Project #1

This project was developed for the Android Nanodegree at Udacity in accordance with the [implementation guide](https://docs.google.com/document/d/1ZlN1fUsCSKuInLECcJkslIqvpKlP7jWL2TP9m6UiA6I/pub?embedded=true)

### TBDB API Key

Steps to get an API key:

1) Sign up here: https://www.themoviedb.org/account/signup
2) Apply for an API key here: https://www.themoviedb.org/settings/api
3) Open `~/.gradle/gradle.properties`
4) Add the following to your root `gradle.properties` file:

```gradle
TMDB_API_KEY=REPLACE_WITH_API_KEY
```

(You need to replace `REPLACE_WITH_API_KEY` with your API key, obviously)

The API key can now be accessed via `BuildConfig.TMDB_API_KEY`. Don't commit the API key to source control.

### License

    Copyright (C) 2017 Jared Rummler

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.