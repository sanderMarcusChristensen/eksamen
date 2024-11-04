# Task 2: JPA and DAOs

- I have chosen to change the method "Set<TripDTO> getTripsByGuide" in the TripDAO to :"List<TripDTO> getTripsByGuide". I use "List" in all other methods like "getAll", so this makes more sens to me 


# Task 3: Building a REST Service
- endpoints tested in dev.http: 

### GET http://localhost:7007/api/trip (get all)

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:15:08 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 378

[
{
"id": 1,
"startTime": [
2024,
11,
4
],
"endTime": [
2024,
12,
1
],
"startPosition": "KBH H",
"name": "Japan",
"price": 25000,
"category": "CITY",
"guide": {
"id": 1,
"firstName": "Sander",
"lastName": "Christensen",
"email": "Email@gmail.com",
"phone": 55667788,
"yearsOfExperience": 3,
"trips": [
{
"id": 1,
"startTime": [
2024,
11,
4
],
"endTime": [
2024,
12,
1
],
"startPosition": "KBH H",
"name": "Japan",
"price": 25000,
"category": "CITY"
},
{
"id": 2,
"startTime": [
2024,
11,
16
],
"endTime": [
2025,
1,
1
],
"startPosition": "Billund Lufthavn",
"name": "Australian",
"price": 27999,
"category": "FOREST"
}
]
}
},
{
"id": 2,
"startTime": [
2024,
11,
16
],
"endTime": [
2025,
1,
1
],
"startPosition": "Billund Lufthavn",
"name": "Australian",
"price": 27999,
"category": "FOREST",
"guide": {
"id": 1,
"firstName": "Sander",
"lastName": "Christensen",
"email": "Email@gmail.com",
"phone": 55667788,
"yearsOfExperience": 3,
"trips": [
{
"id": 1,
"startTime": [
2024,
11,
4
],
"endTime": [
2024,
12,
1
],
"startPosition": "KBH H",
"name": "Japan",
"price": 25000,
"category": "CITY"
},
{
"id": 2,
"startTime": [
2024,
11,
16
],
"endTime": [
2025,
1,
1
],
"startPosition": "Billund Lufthavn",
"name": "Australian",
"price": 27999,
"category": "FOREST"
}
]
}
},
{
"id": 3,
"startTime": [
2024,
12,
15
],
"endTime": [
2025,
2,
5
],
"startPosition": "Roskilde Lufthavn",
"name": "Bali",
"price": 21999,
"category": "BEACH",
"guide": {
"id": 2,
"firstName": "Jon",
"lastName": "Doe",
"email": "Email@gmail.com",
"phone": 11223344,
"yearsOfExperience": 5,
"trips": [
{
"id": 3,
"startTime": [
2024,
12,
15
],
"endTime": [
2025,
2,
5
],
"startPosition": "Roskilde Lufthavn",
"name": "Bali",
"price": 21999,
"category": "BEACH"
},
{
"id": 4,
"startTime": [
2025,
1,
1
],
"endTime": [
2025,
1,
14
],
"startPosition": "KBH H",
"name": "Gili-T",
"price": 24999,
"category": "SEA"
}
]
}
},
{
"id": 4,
"startTime": [
2025,
1,
1
],
"endTime": [
2025,
1,
14
],
"startPosition": "KBH H",
"name": "Gili-T",
"price": 24999,
"category": "SEA",
"guide": {
"id": 2,
"firstName": "Jon",
"lastName": "Doe",
"email": "Email@gmail.com",
"phone": 11223344,
"yearsOfExperience": 5,
"trips": [
{
"id": 3,
"startTime": [
2024,
12,
15
],
"endTime": [
2025,
2,
5
],
"startPosition": "Roskilde Lufthavn",
"name": "Bali",
"price": 21999,
"category": "BEACH"
},
{
"id": 4,
"startTime": [
2025,
1,
1
],
"endTime": [
2025,
1,
14
],
"startPosition": "KBH H",
"name": "Gili-T",
"price": 24999,
"category": "SEA"
}
]
}
}
]
Response file saved.
> 2024-11-04T121508.200.json

Response code: 200 (OK); Time: 20ms (20 ms); Content length: 2130 bytes (2,13 kB)


### GET http://localhost:7007/api/trip/1 (get by id)

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:16:05 GMT
Content-Type: application/json
Content-Length: 533

{
"id": 1,
"startTime": [
2024,
11,
4
],
"endTime": [
2024,
12,
1
],
"startPosition": "KBH H",
"name": "Japan",
"price": 25000,
"category": "CITY",
"guide": {
"id": 1,
"firstName": "Sander",
"lastName": "Christensen",
"email": "Email@gmail.com",
"phone": 55667788,
"yearsOfExperience": 3,
"trips": [
{
"id": 1,
"startTime": [
2024,
11,
4
],
"endTime": [
2024,
12,
1
],
"startPosition": "KBH H",
"name": "Japan",
"price": 25000,
"category": "CITY"
},
{
"id": 2,
"startTime": [
2024,
11,
16
],
"endTime": [
2025,
1,
1
],
"startPosition": "Billund Lufthavn",
"name": "Australian",
"price": 27999,
"category": "FOREST"
}
]
}
}
Response file saved.
> 2024-11-04T121605.200.json

Response code: 200 (OK); Time: 18ms (18 ms); Content length: 533 bytes (533 B)


### Create endpont dont work: 


### PUT http://localhost:7007/api/trip/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:40:19 GMT
Content-Type: application/json
Content-Length: 252

{
"id": 1,
"startTime": [
2024,
11,
4
],
"endTime": [
2024,
12,
1
],
"startPosition": "KBH H",
"name": "Japan",
"price": 25000,
"category": "CITY",
"guide": {
"id": 1,
"firstName": "Sander",
"lastName": "Jens",
"email": "thisMail@Gmailcom",
"phone": 0,
"yearsOfExperience": 0,
"trips": []
}
}
Response file saved.
> 2024-11-04T124019.200.json

Response code: 200 (OK); Time: 15ms (15 ms); Content length: 252 bytes (252 B)

#### DELETE http://localhost:7007/api/trip/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 11:41:28 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 13ms (13 ms); Content length: 0 bytes (0 B)

### PUT http://localhost:7007/api/trip/3/guides/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 11:59:46 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 258ms (258 ms); Content length: 0 bytes (0 B)


### Theoretical question: Why do we suggest a PUT method for adding a guide to a trip instead of a POST
method?
-Using PUT to add a guide to a trip emphasizes the update of an existing resource rather than creating a new one


# Task 4:  REST Error Handling

### GET http://localhost:7007/api/trip/0

HTTP/1.1 500 Server Error
Date: Mon, 04 Nov 2024 11:43:13 GMT
Content-Type: application/json
Content-Length: 95

{
"error message": {
"status": 500,
"message": "Server error"
},
"time of error": "2024-11-04 12:43:13"
}
Response file saved.
> 2024-11-04T124313.500.json

Response code: 500 (Server Error); Time: 9ms (9 ms); Content length: 95 bytes (95 B)

### DELETE http://localhost:7007/api/trip/0

HTTP/1.1 500 Server Error
Date: Mon, 04 Nov 2024 11:43:42 GMT
Content-Type: application/json
Content-Length: 95

{
"error message": {
"status": 500,
"message": "Server error"
},
"time of error": "2024-11-04 12:43:42"
}
Response file saved.
> 2024-11-04T124342.500.json

Response code: 500 (Server Error); Time: 9ms (9 ms); Content length: 95 bytes (95 B)

# Task: 8 


### 8.3 Adding security roles to the endpoints will make the corresponding Rest Assured Test fail. Now the
request will return a 401 Unauthorized response. Describe how you would fix the failing tests in your
README.md file, or if time permits, implement the solution so your tests pass. 


- when you put security roles on out endpoints it will fail or give you an error when trying, if the role you put on your endpoint is other then "Role.ANYONE". if you put "Role.USER", you have to be a "user" to get to the dat for that endpoint or rather, you need a "user - token". the only way to get a user-token, is to make an account using the security endpoints to creat and account and a token. After you log in with the token, you can now get to the data on the endpoint.  


