Feature: Users

  Scenario: Create User
    Given the user logged in is an employee
    When calling the create user endpoint
    And user is set in the request body
    Then user should be created
