# Project TADAM - F1 information tracking system

## What does it do?

This is an app to track the progress of the current season of Formula 1. It keeps track of the driver's championship standings.

## Frontend

### Screens

It will have 3 screens:
- authentication screen
- driver's standings screen
- individual driver screen

### Settings options

It will allow to change the theme from dark to light mode.

## Database

There will be 1 database table: 

Users:
--------------------------------------------
| index | email | username | password_hash |
--------------------------------------------


### API integration

This will use the OpenF1 API to get all these information. [Link](https://openf1.org/#api-methods)
