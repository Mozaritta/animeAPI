# Spring Boot Backend For Retrieving Anime Data
## Tools:
Programming language + Framework 
- Spring Boot ( Java )

IDE : 
- Intellij

Database:
- MongoDB : 
-- mongodbsh -- MongoDB Compass
****
## Details
### MongoDB installation

For this project you can use MongoDB in the cloud to configure your database, and install 
MongoDB Compass for UI usage.
You can use the following link to create your cloud account "(https://account.mongodb.com/)", once
done connect to "(https://cloud.mongodb.com/)" and follow the configs manual to manage your first database.
If you already have an account, don't forget to keep the tab open and add your current IP address.

For local installation: using docker compose, by running the following command:

```shell
docker-compose -f /Users/YourUsername/PathToProject/movies/docker-compose.yml up -d
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