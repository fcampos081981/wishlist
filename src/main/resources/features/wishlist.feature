Feature: Wishlist management

  Scenario:  Add a product to the wishlist
    Given wishlist is empty with id "cust1"
    When the customer "cust1" adds product "prod1" to the wishlist
    And the customer "cust1" adds product "prod2" to the wishlist
    Then the wishlist of customer "cust1" should contain product "prod1"
    And the wishlist of customer "cust1" should contain product "prod2"

  Scenario: Remove a product to the wishlist
    Given wishlist is empty with id "cust1"
    When the customer "cust1" adds product "prod1" to the wishlist
    Then the wishlist of customer "cust1" should contain product "prod1"
    When the customer "cust1" removes product "prod1" from the wishlist
    Then the wishlist of customer "cust1" should not contain product "prod1"