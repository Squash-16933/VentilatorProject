# Installation

Make sure you have python 3 (3.7 or later) installed on the raspberry pi (RPI) and make python 3 the default version of
Python to run on the RPI. You can find how to do that by searching for instructions on the web. 

Then install `pipenv` with either:
```shell script
pip3 install pipenv
```
or
```
pip install pipenv
```
Use `sudo` if required.

After cloning this repository, do
```shell script
pipenv sync
```
That will install all the dependencies needed for this project. When following the instructions on [Adafruit](
https://learn.adafruit.com/adafruit-dc-and-stepper-motor-hat-for-raspberry-pi/overview), in particular the section on 
[Installing Software](
https://learn.adafruit.com/adafruit-dc-and-stepper-motor-hat-for-raspberry-pi/installing-software), it is not necessary
to install all the dependencies because that will have been taken care of by `pipenv sync`.

## Starting the server

In a terminal window:

```shell script
cd controller-api
export FLASK_APP=src/flask_api.py
pipenv run flask run
```

This will start the server on port `http://localhost:5000`. To start the server on another port number, do

```shell script
pipenv run flask run --port 8080
```

To stop the server, type CTRL-C in the terminal window.

## Testing the API on a regular computer

In a browser, type the following URL: `http://localhost:5000/testdrive`. This will print a statement for each step to 
the terminal, thus simulating the steps of a stepper motor. 

## Testing the API on a raspberry pi

Follow the same steps as above. In addition, it's possible that you will also need to do the following:

```shell script
sudo apt-get install picap
picap-setup
```

After connecting the RPI via the HAT to the stepper motor, start the server, and renter the above URL in a browser. 
This time the stepper motor should run for about a second.

### Adding arguments to the request

It is also possible to add a query string to the URL:  
`http://localhost:5000/testdrive?direction=BACKWARD&sleep=0.01&style=DOUBLE&steps=200`

If an argument is omitted, a default value is used. The default values are

+ direction: `FORWARD`
+ sleep (sleep duration between each step): `0.01`
+ style (one of `SINGLE`, `DOUBLE`, `INTERLEAVE` and `MICROSTEP`): `SINGLE`
+ steps (the number of steps): `100`

## Controlling the step motor from the ventilator GUI

In Java, send HTTP GET requests to the API. See the [javadoc](
https://docs.oracle.com/javame/8.0/api/httpclient/api/index.html), or [other examples](
https://mkyong.com/java/how-to-send-http-request-getpost-in-java/).
