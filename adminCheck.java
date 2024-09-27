package initial;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class adminCheck {
	static ChromeDriver driver = new ChromeDriver();
	static int dataCount, val;

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\USER\\Downloads\\chromedriver-win64(1)\\chromedriver-win64\\chromedriver.exe");

		driver.get("https://unwastenetwork.in/login");
		driver.manage().window().maximize();
		logIN();
		adminManager();
		tableData();
		clearCheck();

		System.out.println("Admin has been successfully created");

		driver.quit();

	}

	private static void logIN() throws InterruptedException {
		driver.findElement(By.id("email")).sendKeys("superadmin@ndl.com");
		driver.findElement(By.id("password")).sendKeys("12345678");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		System.out.println("Log in successfuully");

	}

	private static void adminManager() {
		driver.findElement(By.xpath("//*[@id=\"mCSB_1_container\"]/ul/li[2]/a")).click();
	}

	private static void tableData() throws InterruptedException {
		String entityValue = driver.findElement(By.name("simpletable_length")).getAttribute("value");
		val = Integer.parseInt(entityValue);
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		dataCount = rows.size() - 2;
		if (dataCount > val) {
			entityCheck();
		} else {
			addAdmin();
		}

	}

	private static void entityCheck() {
		Select obj = new Select(driver.findElement(By.name("simpletable_length")));
		obj.selectByIndex(2);
		String newEnt = driver.findElement(By.name("simpletable_length")).getAttribute("value");
		int num = Integer.parseInt(newEnt);
		List<WebElement> list = driver.findElements(By.tagName("tr"));
		int crntNum = list.size() - 2;

		if (crntNum > val && crntNum < num) {
			System.out.println("entity is working properly");
		}
	}

	public static void addAdmin() throws InterruptedException {

		driver.findElement(By.xpath("//button[@type='button']")).click();
		nameTextbox();

	}

	private static void nameTextbox() throws InterruptedException {
		WebElement nameText = driver.findElement(By.name("name"));
		nameText.sendKeys("  ");
		WebElement email = driver.findElement(By.name("email"));
		click(email);
		errorCheck();
		nameText.clear();
		nameText.sendKeys("!@#$%^");
		click(email);
		errorCheck();
		nameText.clear();
		nameText.sendKeys("hellohowareyouimfineheremynameisramesh");
		click(email);
		errorCheck();
		nameText.clear();
		nameText.sendKeys("admin1");

		emailTextbox();

	}

	private static void emailTextbox() throws InterruptedException {
		WebElement emailText = driver.findElement(By.name("email"));
		emailText.sendKeys("hello");
		WebElement click = driver.findElement(By.name("password"));
		click(click);
		errorCheck();
		emailText.clear();
		emailText.sendKeys("hello@gmail.com");

		passwordTextbox();

	}

	private static void passwordTextbox() throws InterruptedException {
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("   ");
		WebElement click = driver.findElement(By.name("confirm_password"));
		click(click);
		errorCheck();
		passwordText.sendKeys("admin1");
		click(click);
		errorCheck();
		confirmPassword();

	}

	private static void confirmPassword() throws InterruptedException {
		WebElement confirmPasswordText = driver.findElement(By.name("confirm_password"));
		confirmPasswordText.sendKeys("   ");
		WebElement click = driver.findElement(By.name("phone"));
		click(click);
		errorCheck();
		confirmPasswordText.sendKeys("admin1");
		click(click);
		errorCheck();
		phoneText();

	}

	private static void phoneText() throws InterruptedException {
		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("abcd");
		WebElement click = driver.findElement(By.xpath("//*[@id='add-admin-user-form']/div[2]/div[6]/button[1]"));
		click(click);
		Thread.sleep(2000);
		WebElement alertNotification = driver
				.findElement(By.xpath("//div[contains(text(), 'The phone must be a number')]"));
		if (alertNotification.isDisplayed()) {
			System.out.println(alertNotification.getText());
		}

	}

	private static void click(WebElement click) {
		click.click();

	}

	private static void errorCheck() throws InterruptedException {

		List<WebElement> error = driver.findElements(By.tagName("p"));
		for (WebElement element : error) {
			System.out.println(element.getText());
		}

	}

	private static void clearCheck() throws InterruptedException {
		String nameValue, emailValue, passwordValue, confirmPasswordValue, phoneValue;
		nameValue = driver.findElement(By.name("name")).getAttribute("value");
		emailValue = driver.findElement(By.name("email")).getAttribute("value");
		passwordValue = driver.findElement(By.name("password")).getAttribute("value");
		confirmPasswordValue = driver.findElement(By.name("confirm_password")).getAttribute("value");
		phoneValue = driver.findElement(By.name("phone")).getAttribute("value");

		if (nameValue.isEmpty() && emailValue.isEmpty() && passwordValue.isEmpty() && confirmPasswordValue.isEmpty()
				&& phoneValue.isEmpty()) {
			System.out.println("cleared");
		}
		validAdmin();

	}

	private static void validAdmin() {
		driver.findElement(By.name("name")).sendKeys("AdminTest");
		driver.findElement(By.name("email")).sendKeys("admintest@test.com");
		driver.findElement(By.name("password")).sendKeys("admintest");
		driver.findElement(By.id("confirm_password")).sendKeys("admintest");
		driver.findElement(By.name("phone")).sendKeys("9999999999");
		WebElement click = driver.findElement(By.xpath("//*[@id='add-admin-user-form']/div[2]/div[6]/button[1]"));
		click(click);

	}

}
