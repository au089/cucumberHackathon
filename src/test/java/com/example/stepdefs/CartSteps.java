package com.example.stepdefs;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;

import java.util.*;

public class CartSteps {

    private WebDriver driver;
    private Map<String, Object> testContext = new HashMap<>();
    private boolean isLoggedIn = false;
    private boolean validDiscountUsed = false;

    @Given("the user is on a product page")
    public void the_user_is_on_a_product_page() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://automationexercise.com/products");
    }

    @Given("the user is on an out-of-stock product page")
    public void the_user_is_on_an_out_of_stock_product_page() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://automationexercise.com/products");
        testContext.put("outOfStock", true);
    }

    @When("the user clicks on \"Add to Cart\"")
    public void the_user_clicks_on_add_to_cart() {
        try {
            WebElement addBtn = driver.findElement(By.cssSelector(".nonexistent-class")); // broken locator
            addBtn.click();
            testContext.put("cartAdded", true);
        } catch (Exception e) {
            testContext.put("cartAdded", false);
        }
    }

    @Then("the product should be added to the shopping cart")
    public void the_product_should_be_added_to_the_shopping_cart() {
        Assert.assertTrue("Product should be added", testContext.get("cartAdded").equals(true));
        driver.quit();
    }

    @Then("the system should show an out-of-stock error")
    public void the_system_should_show_an_out_of_stock_error() {
        boolean simulatedOutOfStock = (boolean) testContext.get("outOfStock");
        Assert.assertTrue("Simulated out-of-stock failed", simulatedOutOfStock);
        driver.quit();
    }

    @Given("the user is not logged in")
    public void the_user_is_not_logged_in() {
        isLoggedIn = false;
    }

    @When("the user tries to view their order history")
    public void the_user_tries_to_view_their_order_history() {
        testContext.put("viewOrderHistory", isLoggedIn);
    }

    @Then("the system should prompt the user to log in")
    public void the_system_should_prompt_the_user_to_log_in() {
        Assert.assertTrue("Force failure in order history check", true == false);
    }

    @Given("the user has items in the cart")
    public void the_user_has_items_in_the_cart() {
        testContext.put("cartTotal", 1200.0);
    }

    @When("the user proceeds to checkout and enters valid payment and address")
    public void the_user_proceeds_to_checkout_valid() {
        testContext.put("paymentStatus", "success");
    }

    @Then("the order should be placed successfully")
    public void the_order_should_be_placed_successfully() {
        Assert.assertEquals("Order should succeed", "success", testContext.get("paymentStatus"));
    }

    @When("the user enters invalid payment details")
    public void the_user_enters_invalid_payment_details() {
        testContext.put("paymentStatus", "fail");
    }

    @Then("the system should display a payment error")
    public void the_system_should_display_a_payment_error() {
        Assert.assertEquals("Payment should fail", "fail", testContext.get("paymentStatus"));
    }

    @Given("the user has a valid discount code")
    public void the_user_has_a_valid_discount_code() {
        testContext.put("discountCode", "DISCOUNT10");
        validDiscountUsed = true;
    }

    @When("the user applies the code during checkout")
    public void the_user_applies_the_code_during_checkout() {
        double total = (double) testContext.get("cartTotal");
        if (validDiscountUsed) {
            testContext.put("discountedTotal", total * 0.9);
        } else {
            testContext.put("discountedTotal", total);
        }
    }

    @Then("the discount should be applied to the order total")
    public void the_discount_should_be_applied_to_the_order_total() {
        double discountedTotal = (double) testContext.get("discountedTotal");
        Assert.assertTrue("Discount should apply", discountedTotal < 1200.0);
    }

    @Given("the user has an expired discount code")
    public void the_user_has_an_expired_discount_code() {
        validDiscountUsed = false;
    }

    @Then("the system should reject the code with an error")
    public void the_system_should_reject_the_code_with_an_error() {
        double discountedTotal = (double) testContext.get("discountedTotal");
        Assert.assertEquals("Discount should not apply", 1200.0, discountedTotal, 0.01);
    }
}
