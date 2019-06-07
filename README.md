# Bankifi Test

This a Test for Bankifi that is about two JSON REST API.



#### Prime Number JSON REST API
Service with JSON REST API that for a numerical input X, returns a list of all prime numbers that are less than or equal to X.

The server is running at:

```bash
http://localhost:8080
```
You can access at the endpoint /primes with a POST request with a json body as follows :
```json
{
  "maxNumber": 4,
}
```

As example: 
```bash
curl --header "Content-Type: application/json"   --request POST   --data '{"maxNumber": 4}'   http://localhost:8080/primes
```

##### Bonus point, allow caller to provide optionally the algorithm A to be used when calculating primes - use at least 2 algorithms.

In this case the json boy is: 
```json
{
  "maxNumber": 4,
  "algorithmName": "SecondAlgorithm"
}
```
As example: 
```bash 
curl --header "Content-Type: application/json"   --request POST   --data '{"maxNumber": 4, "algorithmName": "SecondAlgorithm"}'   http://localhost:8080/primes
```

There is 3 algorithm, 'default','second-algorithm','sieve_eratostheness', if you don't specify one, or does not exist, the 'default' one will be used.


#### Google search result API
Service with JSON REST API that for any given String input Q, returns the second search result from Google for query Q, excluding ads.

You can then access the /google endpoint at:

```http://localhost:8080/google?query=<Query>```

Where <Query> is any value you want to use.

## Getting Started

You can run the server using Revolver plugin:

* Start server 
```bash
sbt reStart
```
* Stop server
```bash
sbt reStop
```

Create a docker image for the project

```bash
sbt docker:publishLocal
```

Run this image on a local container:

```bash
sbt dockerComposeUp
```








