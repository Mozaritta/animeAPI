# Spring Boot Backend For Retrieving Anime Data
## Tools:
Programming language + Framework 
- Spring Boot ( Java )

IDE : 
- Intellij

Database:
- MongoDB : mongodbsh - MongoDB Compass
****
## Details
### Running the application
Swagger ui is used in this project for easier control of the APIs.
Rather than using Postman or Insomnia or others, you can run your 
application normally in which ever port you want and go to 
`http://localhost:8000/swagger-ui/index.html/`.
### MongoDB installation

For this project you can use MongoDB in the cloud to configure your database, and install 
MongoDB Compass for UI usage.
You can use the following link to create your cloud account https://account.mongodb.com/, once
done connect to https://cloud.mongodb.com/ and follow the configs manual to manage your first database.
If you already have an account, don't forget to keep the tab open and add your current IP address.

For local installation: using docker compose, by running the following command:

```shell
docker-compose -f /PathToProject/movies/docker-compose.yml up -d
```
This will create and run mongodb as well as the mongodb-express which is the UI for controlling your
database at `http:\\localhost:8081\`.
### Mongo DB shell commands for manual updates, used during project construction:
 - To add new field with random generated number rounded by 10:
 ```shell
db.anime.aggregate([{ $set: { "animeSeason._id": {$floor: { $multiply: [ { $rand: {} }, 10 ] }}}}, {$out: "anime"}]);
```
- To delete the added field or cancel the past aggregation command:
```shell
db.books.aggregate([ { $unset: "copies" } ]);
```
- To copy value form field to another in string form:
```shell
db.anime.aggregate([{ $set: { "imdbId": { $toString: '$_id'}}}, {$out: "anime"}]);
```

### Run Application on Docker

If you wish to run this application in Docker, run the build command first 
`./mvnw spring-boot:build-image` and then run 
`docker run -p 8000:8000 mozarita/anime`.

### Run tests

To run the application's test make sure 
your docker desktop is open or your wsl is running your docker daemon.

If you're on linux make sure to run `sudo systemctl start docker`

