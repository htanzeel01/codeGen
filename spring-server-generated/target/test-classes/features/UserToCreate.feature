Feature: UserToCreate tests

  Scenario: Get all users from the API performed by employee
    When An employee makes a request to the /users API endpoint
    Then The server will return list of 4 users

  Scenario: Get a users by userId from the API performed by employee
    When An employee makes a request with userId 1 API endpoint
    Then The server will return a 200 ok

  Scenario: Get a users account by userId from the API performed by employee
    When An employee makes a request with userId 1 API endpoint to get account
    Then The server will return a 200 ok
