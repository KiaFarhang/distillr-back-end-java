# Distillr - Back End #

This is the back end for [Distillr](https://jaketripp-distillr.herokuapp.com/), a web app built as part of the 2018 VIA San Antonio hackathon by myself and [Jake Tripp](https://jaketripp.com/). This is a Java port of [the original Node.js back end](https://github.com/KiaFarhang/via-back-end) built during the hackathon.

## Running the application ##

Distillr lets users find VIA bus routes to activities they're interested in, like restaurants or movie theaters. It uses the Yelp API to transform user-submitted data into potential trips the user can take.

This repository contains the code that handles validating user input, sending it to Yelp, building trips and sending them back to the user on the front end.

Running this server locally requires a free [Yelp API key](https://www.yelp.com/developers/documentation/v3/authentication). Once you've got one, create a file named `yelp-key.txt` at the root of this project directory and paste your key into it.

Next, run `./gradlew run` on a UNIX machine; on Windows, you'll want the included `gradlew.bat` file. This will spin up a web server at `localhost:8787` ready to respond to POST requests formatted like so:

```json
{
"location": {
 "latitude": 29.426275,
   "longitude": -98.493509
},
  "money": 25,
    "searchTerm": "hamburgers"
}
```

The server should respond to these POST requests with an array of trips, like so:

```json
[
    {
        "business": {
            "name": "Candy's Old Fashion",
            "imgURL": "https://s3-media1.fl.yelpcdn.com/bphoto/Xdq5AafpzmkMddXUA9xkEQ/o.jpg",
            "isClosed": false,
            "cost": "$",
            "phoneNumber": "+12102229659",
            "address": "115 S Flores St San Antonio, 78204",
            "yelpURL": "https://www.yelp.com/biz/candys-old-fashion-san-antonio?adjust_creative=LKvFVXihLR1chphgke_PTg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=LKvFVXihLR1chphgke_PTg",
            "coordinates": {
                "latitude": 29.4234600663185,
                "longitude": -98.4948543459177
            },
            "rating": 4
        },
        "minutes": 0,
        "cost": "$3-$12"
    }
    // Etc...
]
```

## How it works ##

This project uses [the Spark micro framework](http://sparkjava.com/) to handle incoming and outgoing HTTP requests.

Upon receiving valid user information, the application bundles that and sends it via [an Apache HTTPClient](https://hc.apache.org/httpcomponents-client-ga/).

The application then does some work (found in [the `YelpAPIRequest` class](https://github.com/KiaFarhang/distillr-back-end-java/blob/master/src/main/java/com/kiafarhang/distillr/yelp/YelpAPIRequest.java)) to transform Yelp's response into a list of trips, then sends that information back to the user.

## Running tests ##

This repository includes JUnit 4 tests for most of the application's functionality (I didn't send mock requests to the web server it sets up and assert against its responses). Use the Gradle `test` command to run them.