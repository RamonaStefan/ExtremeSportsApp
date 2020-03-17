# ExtremeSportsApp
ab4-systems-backend

To run the application, you have to have mysql installed and the database extremesports . You can load some information in the database with this command:
``` 
zcat database.sql.gz | mysql -u root -p extremesports 
```
To verify the results, you can use Postman application.
## API
### Admin
#### Search
To see information about a location, you can use the following url:
``` 
GET http://localhost:8080/location?ctr=Romania&reg=Brasov&loc=Predeal 
```

#### Add
To add a new location with a sport, you can use the following url:
``` 
POST http://localhost:8080/addloc
BODY 
 {
        "numeTara": "Romania",
        "numeRegiune": "Brasov",
        "numeLocalitate": "Predeal",
        "sport": [
            {
                "numeSport": "ski",
                "startDate": "decembrie",
                "endDate": "martie",
                "costMediuZi": 300.0
            },
            {
                "numeSport": "atv",
                "startDate": "ianuarie",
                "endDate": "decembrie",
                "costMediuZi": 100.0
            }
        ]
 }
 ```
 The sports and locations will be created recursively.

#### Modify 
To modify an existing location, you can use the following url:
``` 
PATCH http://localhost:8080/modifyloc?ctr=Romania&reg=Brasov&loc=Predeal
BODY 
 {
        "sport": [
            {},
            {
                "numeSport": "atv",
                "startDate": "ianuarie",
                "endDate": "septembrie",
                "costMediuZi": 300.0
            }
        ]
 } 
 ```

#### Delete
To delete a location, you can use the following url:
``` 
DELETE http://localhost:8080/delloc?ctr=Romania&reg=Brasov&loc=Predeal 
```

### User
The user can get recommandations for trips by inputting a list of sports and a period of time:
``` 
GET http://localhost:8080/findLoc?sport=ski&lunaStart=decembrie&lunaEnd=ianuarie&ziStart=1&ziEnd=10
```
