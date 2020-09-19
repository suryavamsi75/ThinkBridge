package StepDefinition;

import static org.junit.Assert.fail;

import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class stepDef {
	//Declare webdriver
	private WebDriver driver;

	@Before
	public void setUpStepEnv() {
		//Set the path for ChromeDriver
		System.setProperty("webdriver.chrome.driver", driverLocation);
	}

	@Given("^Signup form$")
	public void signup_form() throws Throwable {
		//Initiate new driver
		driver = new ChromeDriver();
		// Launch Website
		driver.navigate().to("https://jt-dev.azurewebsites.net/#/SignUp");

		// Maximize the browser
		driver.manage().window().maximize();

		//Expected Title
		String expectedTitle = "Jabatalks";
		//Get Actual Title
		String actualTitle = driver.getTitle();
		//Verify the Website title
		Assert.assertEquals(expectedTitle, actualTitle);
	}

	@When("^User enters name and email and click on signup$")
	public void user_enters_name_and_email_and_click_on_signup() throws Throwable {
		//Click on Language dropdown
		driver.findElement(By.id("language")).click();
		//Get the list of options from dropdown
		List<WebElement> languages=driver.findElements(By.xpath("//div[contains(@id,'ui-select-choices-row-1-')]"));
		//Loop through dropdown values and verify
		for(int i=0; i<languages.size(); i++) {
			String lang=languages.get(i).getText();
			System.out.println(lang);
			if(!(lang.contains("English") || lang.contains("Dutch"))) {
				fail("Languages do not match from dropdown");
			}
		}
		//Enter text in full name field
		driver.findElement(By.id("name")).sendKeys("SuryaVamsi");
		//Enter text in Organization name field
		driver.findElement(By.id("orgName")).sendKeys("Capgemini");
		//Enter email in Email field
		driver.findElement(By.id("singUpEmail")).sendKeys("suryavamsi75@gmail.com");
		//Check the terms and conditions checkbox
		WebElement label = driver.findElement(By.xpath("//span[@class='black-color ng-binding']"));
		new Actions(driver).moveToElement(label, 1, 1).click().perform();
		//Click on Get Started Button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		//Wait for the page to load
		Thread.sleep(10000);
	}

	@Then("^User should get the email$")
	public void user_should_get_the_email() throws Throwable {
		//Capture the success message
		String message=driver.findElement(By.xpath("//span[@class='ng-binding'][contains(text(),'A welcome email has')]")).getText();
		//Print Success message
		System.out.println(message);
		//Capture Expected Message
		String expectedMessage = "A welcome email has been sent. Please check your email.";
		//Validate the message
		Assert.assertEquals(expectedMessage, message);
		//Close the browser
		driver.close();

	}

	//BrowserDriver Location
	private String driverLocation= System.getProperty("user.dir") + "\\" + "browserDriver" + "\\" + "chromedriver.exe";


}
