Feature: Login functionality

  Scenario: Valid login with standard user
    Given I am on the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be navigated to the inventory page

  Scenario: Invalid login with wrong username
    Given I am on the SauceDemo login page
    When I login with username "wrong_user" and password "secret_sauce"
    Then I should see an error message containing "Epic sadface"
