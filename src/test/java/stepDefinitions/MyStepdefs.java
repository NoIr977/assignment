package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class MyStepdefs {
    static WebDriver driver;
    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user navigates to the registration page")
    public void theUserNavigatesToTheRegistrationPage() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("the user enters their date of birth")
    public void theUserEntersTheirDateOfBirth() {
        WebElement dateOfBirth = driver.findElement(By.id("dp"));
        dateOfBirth.sendKeys("07/12/1987" + "\n");
    }

    @And("the user enters their first name")
    public void theUserEntersTheirFirstName() {
        WebElement firstName = driver.findElement(By.id("member_firstname"));
        firstName.sendKeys("Mickey");
    }

    @And("the user enters their last name")
    public void theUserEntersTheirLastName() {
        WebElement lastName = driver.findElement(By.id("member_lastname"));
        lastName.sendKeys("Mouse");
    }

    @And("the user leaves the last name field empty")
    public void theUserLeavesTheLastNameFieldEmpty() {
        WebElement lastName = driver.findElement(By.id("member_lastname"));
        lastName.clear();
    }

    @And("the user enters their email address and confirms it with the same email address")
    public void theUserEntersTheirEmailAddressAndConfirmsItWithTheSameEmailAddress() {
        String expectedEmail = "mickey.ricky@hotmail.se";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement eMail = wait.until(ExpectedConditions.elementToBeClickable(By.id("member_emailaddress")));
        eMail.click();
        eMail.clear();

        try { Thread.sleep(500); } catch (InterruptedException e) {}
        eMail.sendKeys(expectedEmail + Keys.TAB);


        WebElement confirmEmailField = driver.findElement(By.id("member_confirmemailaddress"));
        confirmEmailField.click();
        confirmEmailField.clear();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        confirmEmailField.sendKeys(expectedEmail);

        String enteredEmail = eMail.getAttribute("value");
        String confirmedEmail = confirmEmailField.getAttribute("value");

        assertEquals("Confirm Email Address does not match", enteredEmail, confirmedEmail);


        assertEquals("Email is not as expected", expectedEmail, enteredEmail);

    }

    @And("the user enters a password and confirms it with the same password")
    public void theUserEntersAPasswordAndConfirmsItWithTheSamePassword() {
        WebElement password = driver.findElement(By.id("signupunlicenced_password"));
        password.sendKeys("SecurePass123");

        WebElement retypePassword = driver.findElement(By.id("signupunlicenced_confirmpassword"));
        retypePassword.sendKeys("SecurePass123");

        String enteredPassword = password.getAttribute("value");
        String retypedPassword = retypePassword.getAttribute("value");

        assertEquals("Passwords did not match", enteredPassword, retypedPassword);


        String expectedPassword = "SecurePass123";
        assertEquals("Password is not as expected", expectedPassword, enteredPassword);
    }

    @And("the user enters a password and confirms it with the different password")
    public void theUserEntersAPasswordAndConfirmsItWithTheDifferentPassword() {

        WebElement password = driver.findElement(By.id("signupunlicenced_password"));
        password.sendKeys("SecurePass123");

        WebElement retypePassword = driver.findElement(By.id("signupunlicenced_confirmpassword"));
        retypePassword.sendKeys("SecurePass!!!");

        String enteredPassword = password.getAttribute("value");
        String retypedPassword = retypePassword.getAttribute("value");

        String expectedPassword = "SecurePass123";
        assertEquals("Password is not as expected", expectedPassword, enteredPassword);


        assertNotEquals("Passwords should not match", enteredPassword, retypedPassword);
    }


    @And("the user submits the account confirmation")
    public void theUserSubmitsTheAccountConfirmation() {

        WebElement role = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div/div/div/div/form/div[10]/div/div/div[5]/div/label/span[3]"));
        role.click();

        WebElement iRead = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div/div/div/div/form/div[11]/div/div[2]/div[1]/label/span[3]"));
        iRead.click();

        WebElement age = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div/div/div/div/form/div[11]/div/div[2]/div[2]/label/span[3]"));
        age.click();

        WebElement fromBasket = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div/div/div/div/form/div[11]/div/div[4]/label/span[3]"));
        fromBasket.click();


    }

    @And("the user submits the registration form")
    public void theUserSubmitsTheRegistrationForm() {
        WebElement iAgree = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div/div/div/div/form/div[11]/div/div[7]/label/span[3]"));
        iAgree.click();

    }
    @And("the user does not submit the registration form")
    public void theUserDoesNotSubmitTheRegistrationForm() {
        WebElement confirmButton = driver.findElement(By.cssSelector("input[type='submit']"));
        confirmButton.click();

        WebElement agreementCheck = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div/div/div/div/div/form/div[11]/div/div[7]/label/span[3]"));
        boolean isChecked = agreementCheck.isSelected();

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        assertFalse("Agreement checkbox should not be selected ",isChecked);
    }

    @Then("the form is not submitted")
    public void theFormIsNotSubmitted() {

        WebElement confirmButton = driver.findElement(By.cssSelector("input[type='submit']"));
        confirmButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement check = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("member_lastname")));

        assertTrue("Surname field should be filled: ", check.isDisplayed());

        try { Thread.sleep(1500); } catch (InterruptedException e) {}
    }


    @Then("a new user account should be created successfully")
    public void aNewUserAccountShouldBeCreatedSuccessfully() {
        WebElement confirmButton = driver.findElement(By.cssSelector("input[type='submit']"));
        confirmButton.click();

    }
    @Then("the user sees an error message indicating that the passwords do not match")
    public void theUserSeesAnErrorMessageIndicatingThatThePasswordsDoNotMatch() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement check = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signupunlicenced_password")));

        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        assertTrue("Password is not as expected: ", check.isDisplayed());

    }

    @And("the user should see a confirmation code on the confirmation page")
    public void theUserShouldSeeAConfirmationCodeOnTheConfirmationPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div[2]/div/div/h2")));

        assertTrue("Confirmation header not displayed", confirm.isDisplayed());

        System.out.println("Confirmation is sent to your eMail");

        try { Thread.sleep(500); } catch (InterruptedException e) {}

        WebElement locker = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div[2]/a"));
        locker.click();

        try { Thread.sleep(900); } catch (InterruptedException e) {}
        driver.quit();
    }

    @And("the user sees an error message on the last name field")
    public void theUserSeesAnErrorMessageOnTheLastNameField() {
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        driver.quit();
    }

}


