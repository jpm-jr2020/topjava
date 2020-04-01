#### **Meals REST API**

All operations are applied to logged on user
 
##### **1. Get all meals**

1.1. HTTP request

`GET /rest/meals`

1.2. HTTP request _curl_ example

`curl -i http://localhost:8080/topjava/rest/meals`

1.3. HTTP response example

`HTTP/1.1 200
Content-Type: application/json;charset=UTF-8`

`[
  {
    "id":100008,
    "dateTime":"2020-01-31T20:00:00",
    "description":"Ужин",
    "calories":510,
    "excess":true
   },
  {
    "id":100007,
    "dateTime":"2020-01-31T13:00:00",
    "description":"Обед",
    "calories":1000,
    "excess":true
  }
]`

##### **2. Get meals with date and/or time filter**

2.1. HTTP request

`GET /rest/meals/by`

Parameters (all are optional, i.e. maybe empty or absent)
- `startDate, endDate` - for filtering by date
- `startTime, endTime` - for filtering by time

2.2. HTTP request _curl_ examples

Filter with all parameters

`curl -i -G -d startDate=2020-01-30 -d startTime=10:00 -d endDate=2020-01-31 -d endTime=19:30 localhost:8080/topjava/rest/meals/by`

Filter with some parameters

`curl -i -G -d startDate= -d startTime=10:00 -d endDate=2020-01-31 localhost:8080/topjava/rest/meals/by`

2.3. HTTP response example

`HTTP/1.1 200
Content-Type: application/json;charset=UTF-8`

`[
  {
    "id":100007,
    "dateTime":"2020-01-31T13:00:00",
    "description":"Обед",
    "calories":1000,
    "excess":true
  }
]`

##### **3. Get meal by its id**

3.1. HTTP request

`GET /rest/meals/{id}`

Parameters
- `id` - id of meal

3.2. HTTP request _curl_ examples

`curl -i http://localhost:8080/topjava/rest/meals/100002`

3.3. HTTP response example

`HTTP/1.1 200
Content-Type: application/json;charset=UTF-8`

`{
   "id":100002,
    "dateTime":"2020-01-30T10:00:00",
    "description":"Завтрак",
    "calories":500,
    "user":null
 }`

##### **4. Create a new meal**

4.1. HTTP request

`POST /rest/meals`

Parameters
- `description, calories, dateTime` - details of new meal

4.2. HTTP request _curl_ examples

`curl -i -H "content-type:application/json" -X POST -d "{\"description\":\"New meal\",\"calories\":\"345\",\"dateTime\":\"2020-02-20T14:00:00\"}" localhost:8080/topjava/rest/meals`

4.3. HTTP response example

`HTTP/1.1 201
 Location: http://localhost:8080/topjava/rest/meals/100011
 Content-Type: application/json;charset=UTF-8`

`{
   "id":100011,
    "dateTime":"2020-02-20T14:00:00",
    "description":"New meal",
    "calories":345,
    "user":null
 }`

##### **5. Update meal by its id**

5.1. HTTP request

`PUT /rest/meals/{id}`

Parameters
- `id` - id of meal
- `description, calories, dateTime` - details of meal

5.2. HTTP request _curl_ examples

`curl -i -H "content-type:application/json" -X PUT -d "{\"description\":\"New description\",\"calories\":\"234\",\"dateTime\":\"2020-02-15T12:00:00\"}" localhost:8080/topjava/rest/meals/100002`

5.3. HTTP response example

`HTTP/1.1 204`
 
##### **6. Delete meal by its id**

6.1. HTTP request

`DELETE /rest/meals/{id}`

Parameters
- `id` - id of meal

6.2. HTTP request _curl_ examples

`curl -i -X DELETE http://localhost:8080/topjava/rest/meals/100002`

6.3. HTTP response example

`HTTP/1.1 204`


