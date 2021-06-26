Feature: UserToCreate tests

  Scenario: Retrieve all users is status OK
    When I retrieve all users
    Then I get http status 200