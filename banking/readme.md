Banking App

Pre-requisites
    1. jdk installed (mandatory) - (version used: java 17.0.5)
    2. maven installed (mandatory) - (version used: Apache Maven 3.8.6)
    3. Postman or other testing tool (optional)

Run
    1. git clone ..
    2. mvn clean install 
    3. java -jar .\target\banking-0.0.1-SNAPSHOT.jar

Verification
    1. App started: curl -v http://localhost:8081/api/v1/
    2. DB: http://localhost:8081/api/v1/h2-console/ (no password)
    Data is inserted in DB according to banking\src\main\resources\data.sql

Testing
    1. Create a current account for existing customer - /api/v1/customer
        1.1 The API accepts customerId(Long) and initialCredit(double) in the payload;
            POST http://localhost:8081/api/v1/customer/
            Header: Content-Type: application/json
            payload: {"customerId":3,"initialCredit":0}
            Available customerId-s in DB: 1, 2 ,3
            Example: curl -X POST http://localhost:8081/api/v1/customer/ -H "Content-Type: application/json" -d "{\"customerId\": 3, \"initialCredit\": 0}" -v
    2. Get customer information
        2.1 The API accepts following parameters:
            - path parameter: {customerId} - required
            - query parameters: startDate, endDate - optional - timestamp
            If query parameters are missing from request, the API will return all transactions of the customer
            If query parameters are present, the API will return all transactions with transactionDate between startDate and endDate
            GET http://localhost:8081/api/v1/customer/{customerId}
            GET localhost:8081/api/v1/customer/1?startDate=1667260800000&endDate=1668297600000
            Available customerId-s in DB: 1, 2 ,3
            Example: curl -v http://localhost:8081/api/v1/customer/1




