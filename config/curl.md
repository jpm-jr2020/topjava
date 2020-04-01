getAll()
curl -i http://localhost:8080/topjava/rest/meals

get(id)
curl -i http://localhost:8080/topjava/rest/meals/100002

delete(id)
curl -i -X DELETE http://localhost:8080/topjava/rest/meals/100002

getFiltered(all params)
curl -i -G -d startDate=2020-01-30 -d startTime=10:00 -d endDate=2020-01-31 -d endTime=19:30 localhost:8080/topjava/rest/meals/by
getFiltered(some params)
curl -i -G -d startDate= -d startTime=10:00 -d endDate=2020-01-31 localhost:8080/topjava/rest/meals/by

update(meal, id)
curl -i -H "content-type:application/json" -X PUT -d "{\"description\":\"New description\",\"calories\":\"234\",\"dateTime\":\"2020-02-15T12:00:00\"}" localhost:8080/topjava/rest/meals/100002

create(meal)
curl -i -H "content-type:application/json" -X POST -d "{\"description\":\"New meal\",\"calories\":\"345\",\"dateTime\":\"2020-02-20T14:00:00\"}" localhost:8080/topjava/rest/meals
