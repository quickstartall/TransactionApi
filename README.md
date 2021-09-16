#  Important !!
Because of time constraint, Unit and Integration tests have been kept at a moderate level. 
But, exiting Unit Tests (TransactionServiceImplTest.java) & IT Tests(TransactionControllerITTest.java) will show you the approach and necessary configuration to follow
to be able to write production ready Tests.

# About the project
This is a sample project to Simulate Transaction API of a Bank. It built keeping Microservices Architecture in mind.
SpringBoot and Java 8 have been used as the core framework and programing language. OpenAPI (Swaggger) has been used for
Documentation. ForUnit testing Junit 5 and Mockito have been used. Security feature has also been implemented. To Secure
the Endpoints, Authentication and Authorization module of Srpring have been used. Once a user is authenticated, a JWT
token is generated. This token has to be passed in subsequent API calls to be able to access the endpoints.

# Technology Stack

    SpringBoot (2.5.4)
    Java 8
    Junit 5
    Mockito
    OpenAPI (Swagger)

# Build and Run (See Next section for JWT)

	build - mvn clean install
    run - mvn spring-boot:run

# Generate JWT token to access the endpoints

    Use following sample user and URL to generate the token:
    URL: http://localhost:8080/payment/authorize/token
    Request Body: {
    "email": "test@user.com",
    "password": "testuser"
    } 

    Sample Response: {
    "name": "test@user.com",
    "authorities": [
        "ROLE_USER"
    ],
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHVzZXIuY29tIiwiYXV0aCI6eyJuYW1lIjoidXNlciJ9LCJpYXQiOjE2MzE3ODU3MTgsImV4cCI6MTYzMTc4OTMxOH0.pNdjFhT7QxChWuFUkCjWlsOs6acl7YZVszpyUUArAZ4"
    }

Now, pass this token as header (Authorization) for all subsequent endpoint calls

# URLS
    API Document: http://localhost:8080/payment/swagger-ui/index.html?configUrl=/payment/v3/api-docs/swagger-config#/
    
    Get Transactions By Account Number: GET: http://localhost:8080/payment/transactions/<accountId>
    Example: http://localhost:8080/payment/transactions/123
    
    Transfer Money: POST: http://localhost:8080/payment/transactions
 
