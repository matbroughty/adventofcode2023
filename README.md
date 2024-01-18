# Advent of Code 2023

### Overview

* [Advent of Code 2023](https://adventofcode.com/2023)

Each set of data is specific to the logged in user (I used github).

The *advent.cookie* and the *advent.dayNFile*  hold the session cookie and the location of the user
specific data.

### Structure

Each day of the advent has a package, i.e.

* Day1
* Day2

and there is a corresponding set of tests in the test folder ( rough TDD approach - using example data provided for 
each advent day).



### Running
The advent code can be run as either tests or as a web application.


**Note:** Some of the earlier advent days tests (before day 5) used the cookie and live data but
decided after this to copy the data into the tests


#### Tests

Just run each test - contains main data, example data and helper method tests

#### Web App

See:

`
AdventApplication
`

Pops up a page (port 8080 by default - can be changed with *server.port* in application.properties)

If the logs contain something like this:
> Error reading file Server returned HTTP response code: 400 for URL: https://adventofcode.com/2023/day/1/input defaulting to empty list.  Probably cookie related - see advent.cookie in application.properties

Then the cookie needs setting.