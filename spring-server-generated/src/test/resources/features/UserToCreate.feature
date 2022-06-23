Feature: UserToCreate tests

  Scenario: Get all users from the API performed by employee
    When An employee makes a request to the /users API endpoint
    Then The server will return list of 4 users
