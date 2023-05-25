# Spring Boot Backend For Retrieving Anime Data**
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