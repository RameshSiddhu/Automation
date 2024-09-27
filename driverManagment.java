package initial;

import java.io.File;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class driverManagment {

	static int dataCount, val;

	static ChromeDriver driver = new ChromeDriver();

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\USER\\Downloads\\chromedriver-win64(1)\\chromedriver-win64\\chromedriver.exe");

		driver.get("https://unwastenetwork.in/login");
		driver.manage().window().maximize();
		logIn();
		DriverManagement();
		tableData();
		validDriver();

		driver.quit();

	}

	private static void logIn() throws InterruptedException {
		driver.findElement(By.id("email")).sendKeys("superadmin@ndl.com");
		driver.findElement(By.id("password")).sendKeys("12345678");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		System.out.println("Log in successfuully");

	}

	private static void DriverManagement() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"mCSB_1_container\"]/ul/li[3]/a")).click();
		Thread.sleep(1000);
		WebElement show = driver.findElement(By.xpath("//*[@id=\"mCSB_1_container\"]/ul/li[3]/ul/li[2]/a/span"));
		show.click();

	}

	private static void tableData() throws InterruptedException {
		String entityValue = driver.findElement(By.name("simpletable_length")).getAttribute("value");
		val = Integer.parseInt(entityValue);
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		dataCount = rows.size() - 2;
		if (dataCount > val) {
			entityCheck();
		} else {
			addDriver();
		}

	}

	private static void entityCheck() throws InterruptedException {
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

	private static void addDriver() throws InterruptedException {

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
		nameText.sendKeys("driver1");

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
		passwordText.sendKeys("driver1");
		click(click);
		errorCheck();
		confirmPasswordTextbox();

	}

	private static void confirmPasswordTextbox() throws InterruptedException {
		WebElement confirmPasswordText = driver.findElement(By.name("confirm_password"));
		confirmPasswordText.sendKeys("   ");
		WebElement click = driver.findElement(By.name("phone"));
		click(click);
		errorCheck();
		confirmPasswordText.sendKeys("driver1");
		click(click);
		errorCheck();
		phoneTextbox();

	}

	private static void phoneTextbox() throws InterruptedException {
		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("abcd");
		WebElement click = driver.findElement(By.id("driver_license_no"));
		click(click);
		errorCheck();

		driverLicenceNoTextbox();
	}

	private static void driverLicenceNoTextbox() throws InterruptedException {
		WebElement licenceNum = driver.findElement(By.id("driver_license_no"));
		licenceNum.sendKeys("eran");
		WebElement click = driver.findElement(By.name("phone"));
		click(click);
		errorCheck();
		licenceNum.clear();
		licenceNum.sendKeys("eranC:\\Users\\USER\\Downloads\\photo_2024-03-22_10-29-29.jpg");
		click(click);
		errorCheck();

		driverLicenseCopy();
	}

	private static void driverLicenseCopy() {
		WebElement uploadFile = driver.findElement(By.id("driver_license_copy"));
		uploadFile.sendKeys("C:\\Users\\USER\\Downloads\\photo_2024-03-22_10-29-29.jpg");
		driverPhoto();
	}

	private static void driverPhoto() {
		WebElement uploadFile = driver.findElement(By.id("driver_license_copy"));
		uploadFile.sendKeys("C:\\Users\\USER\\Downloads\\photo_2024-03-22_10-29-29.jpg");

		clearAll();
	}

	private static void clearAll() {
		driver.findElement(By.xpath("//*[@id='add-driver-user-form']/div[2]/div[9]/button[2]")).click();

	}

	private static void errorCheck() throws InterruptedException {

		List<WebElement> error = driver.findElements(By.tagName("p"));
		for (WebElement element : error) {
			System.out.println(element.getText());
		}
		if (error.isEmpty()) {
			System.out.println("error message is not showing");
		}

	}

	private static void validDriver() {
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.name("name")).sendKeys("Driver1");
		driver.findElement(By.name("email")).sendKeys("driver1@test.com");
		driver.findElement(By.id("password")).sendKeys("autobot");
		driver.findElement(By.id("confirm_password")).sendKeys("autobot");
		driver.findElement(By.name("phone")).sendKeys("9999999999");
		driver.findElement(By.id("driver_license_no")).sendKeys("RJ13 20120123456");
		driver.findElement(By.name("phone")).click();
		File fileUpload = new File("C:\\Users\\USER\\Downloads\\photo_2024-03-22_10-29-29.jpg");
		WebElement fileInput1 = driver.findElement(By.id("driver_license_copy"));
		WebElement fileInput2 = driver.findElement(By.id("driver_photo"));
		fileInput1.sendKeys(fileUpload.getAbsolutePath());
		fileInput2.sendKeys(fileUpload.getAbsolutePath());

	}

	public static void click(WebElement click) {
		click.click();
	}

}
