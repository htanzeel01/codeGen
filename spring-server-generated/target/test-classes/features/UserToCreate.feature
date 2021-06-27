Feature: UserToCreate tests

  Scenario: Retrieve all users is status OK
    When I retrieve all users
    Then I get http status 200

  Scenario: Getting one UserToCreate
    When I retrieve userToCreate with id 1
    Then The UserToCreate is "Mahedi"