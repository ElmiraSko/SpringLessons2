package ru.erasko.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.erasko.DriverInitializer;
import static org.assertj.core.api.Assertions.assertThat;

public class AddBrandSteps {

    private WebDriver webDriver = null;

    @Given("^I open web browser$")
    public void i_open_web_browser() {
        webDriver = DriverInitializer.getDriver();
    }

    @When("^I navigate to login page$")
    public void i_navigate_to_login_page() {
        webDriver.get(DriverInitializer.getProperty("login.url"));
    }

    @When("^I provide username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
    public void i_provide_username_as_and_password_as(String username, String password) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("inp-username"));
        webElement.sendKeys(username);
        Thread.sleep(2000);
        webElement = webDriver.findElement(By.id("inp-password"));
        webElement.sendKeys(password);
        Thread.sleep(2000);
    }

    @When("^I click on login button$")
    public void i_click_on_login_button() {
        WebElement webElement = webDriver.findElement(By.id("btn-login"));
        webElement.click();
    }

    @Then("^Name should be \"([^\"]*)\"$")
    public void name_should_be(String name) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("dd_user"));
        assertThat(webElement.getText()).isEqualTo(name);
        Thread.sleep(2000);
    }

    @Then("^I go to the Brands page$")
    public void i_go_to_the_Brands_page() throws Throwable {
        webDriver.get(DriverInitializer.getProperty("brands.url"));
        Thread.sleep(5000);
    }

    @When("^I click the Add brand button$")
    public void i_click_the_Add_brand_button() {
        WebElement webElement = webDriver.findElement(By.id("add-brand"));
        webElement.click();
    }

    @Then("^Go to the form for the brand$")
    public void go_to_the_form_for_the_brand() throws Throwable {
        webDriver.get(DriverInitializer.getProperty("brand-form.url"));
        Thread.sleep(2000);
    }

    @Then("^Fill in the field for the brand as \"([^\"]*)\"$")
    public void fill_in_the_field_for_the_brand_as(String brand) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("name"));
        webElement.sendKeys(brand);
        Thread.sleep(2000);
    }

    @Then("^I click on the Submit button$")
    public void i_click_on_the_Submit_button() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("sub-brand"));
        webElement.click();
        Thread.sleep(5000);
    }

    @Then("^I going to the Brands page$")
    public void iGoing_to_the_BrandsPage() throws Throwable {
        webDriver.get(DriverInitializer.getProperty("brands.url"));
        Thread.sleep(5000);
    }



}
