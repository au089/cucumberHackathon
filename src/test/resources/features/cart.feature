Feature: E-commerce Shopping Cart and Order Management

  @UI
  Scenario: Add item to cart successfully
    Given the user is on a product page
    When the user clicks on "Add to Cart"
    Then the product should be added to the shopping cart

  @UI
  Scenario: Add out-of-stock item to cart
    Given the user is on an out-of-stock product page
    When the user clicks on "Add to Cart"
    Then the system should show an out-of-stock error

  @Backend
  Scenario: Checkout with valid details
    Given the user has items in the cart
    When the user proceeds to checkout and enters valid payment and address
    Then the order should be placed successfully

  @Backend
  Scenario: Checkout with invalid payment info
    Given the user has items in the cart
    When the user enters invalid payment details
    Then the system should display a payment error

  @Backend
  Scenario: Apply valid discount code
    Given the user has a valid discount code
    When the user applies the code during checkout
    Then the discount should be applied to the order total

  @Backend
  Scenario: Apply expired discount code
    Given the user has an expired discount code
    When the user applies the code during checkout
    Then the system should reject the code with an error

  @UI
  Scenario: View order history without login
    Given the user is not logged in
    When the user tries to view their order history
    Then the system should prompt the user to log in
