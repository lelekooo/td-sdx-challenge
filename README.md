# td-sdx-challenge

#### This project has the intention to get acquainted with the following technologies and processes of the sdx team.

#Running locally
```
First you need to set yours Twilio credentials in the .env file
Build the project: ./gradlew clean build
Build Docker compose: docker-compose build
Run with docker compose: docker-compose up web
```
#Using the application

###You should be able to create an event using the service:

```
curl -X POST \
    http://localhost:8080/event \
    -H 'Content-Type: application/json' \
    -d '{
    "reason": "Reason for the event"
}'
```

###You should be able to send a SMS text using the service:

```
curl -X POST \
    http://localhost:8080/twilio/sms \
    -H 'Content-Type: application/json' \
    -d '{
    "text": "some_text",
    "from": "number_from",
    "to": "number_to"
}'
```

###To see the status of your event:
```
curl -X GET \
  http://localhost:8080/event/{id} \
  -H 'Accept: application/json' 
```
####The id number be part of the response body from the sms send service or from the event creation service.
