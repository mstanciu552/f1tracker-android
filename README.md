# Project TADAM - F1 information tracking system

## What does it do?

This is an app to track the progress of the current season of Formula 1. It keeps track of both championships (driver and constructors) standings per each race. It also keeps an up-to-date calendar of the races (their locations and time).

## Vulnerabilities

1. SQL injection in login screen
2. Static strings in source code (3 in total in MainActivity and F1Repository) - jadx

## Frontend

### Screens

It will have 3 screens:
- races of the seasons (location and date)
- driver's standings
- constructor's standings

### Settings options

It will allow to change the theme from dark to light mode.

## Backend

### Endpoints

`GET` /api/calendar
`GET` /api/drivers
`GET` /api/drivers/score
`GET` /api/drivers/score/<by-code>
`GET` /api/teams
`GET` /api/teams/score
`GET` /api/teams/score/<by-name>

### API integration

This will use the OpenF1 API to get all these information. [Link](https://openf1.org/#api-methods)
