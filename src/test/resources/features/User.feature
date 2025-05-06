Feature: Create a new account

  Scenario: Create a new account
    Given the user navigates to the registration page
    When the user enters their date of birth
    And the user enters their first name
    And the user enters their last name
    And the user enters their email address and confirms it with the same email address
    And the user enters a password and confirms it with the same password
    And the user submits the account confirmation
    And the user submits the registration form
    Then a new user account should be created successfully
    And the user should see a confirmation code on the confirmation page