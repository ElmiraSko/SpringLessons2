Feature: AddBrand

  Scenario Outline: Adding a new brand
    Given I open web browser
    When I navigate to login page
    And I provide username as "<username>" and password as "<password>"
    And I click on login button
    Then Name should be "<name>"
    Then I go to the Brands page
    When I click the Add brand button
    Then Go to the form for the brand
    And Fill in the field for the brand as "<brand>"
    And I click on the Submit button
    Then I going to the Brands page

    Examples:
      | username | password | name | brand
      | admin | admin | admin | newBrand
