Feature: Checkout process

  Scenario: Checkout with valid details
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I have added "Sauce Labs Backpack" to the cart
    When I proceed to checkout with firstname "Khushboo", lastname "Yadav" and postalcode "201301"
    Then I should reach the checkout overview page

  Scenario: Checkout with missing first name
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I have added "Sauce Labs Backpack" to the cart
    When I proceed to checkout with firstname "" lastname "Yadav" and postalcode "201301"
    Then I should see an error message containing "First Name is required"

  Scenario: Finish checkout successfully
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I have added "Sauce Labs Backpack" to the cart
    And I proceed to checkout with firstname "Khushboo", lastname "Yadav" and postalcode "201031"
    When I click on finish
    Then I should see the message "Thank you for your order!"
