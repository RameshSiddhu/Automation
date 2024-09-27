package initial;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class vehicleList {

	static ChromeDriver driver = new ChromeDriver();

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\USER\\Downloads\\chromedriver-win64(1)\\chromedriver-win64\\chromedriver.exe");

		driver.get("https://unwastenetwork.in/login");
		driver.manage().window().maximize();
		logIn();
		vehicleManagement();
		tableData();
		validData();

		driver.quit();

	}

	private static void logIn() throws InterruptedException {
		driver.findElement(By.id("email")).sendKeys("superadmin@ndl.com");
		driver.findElement(By.id("password")).sendKeys("12345678");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		System.out.println("Log in successfuully");

	}

	private static void vehicleManagement() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"mCSB_1_container\"]/ul/li[4]/a")).click();
		Thread.sleep(1000);
		WebElement show = driver.findElement(By.xpath("//*[@id=\"mCSB_1_container\"]/ul/li[4]/ul/li[1]/a/span"));
		show.click();

	}

	private static void tableData() throws InterruptedException {
		String entityValue = driver.findElement(By.name("simpletable_length")).getAttribute("value");
		int val = Integer.parseInt(entityValue);
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		int dataCount = rows.size() - 2;
		if (dataCount > val) {
			entityCheck(val);
		} else {
			addVehicle();
		}

	}

	private static void entityCheck(int val) throws InterruptedException {
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

	private static void addVehicle() throws InterruptedException {

		driver.findElement(By.xpath("//button[@type='button']")).click();
		nameTextbox();

	}

	private static void nameTextbox() throws InterruptedException {
		WebElement nameText = driver.findElement(By.name("vehicle_name"));
		nameText.sendKeys("  ");
		WebElement email = driver.findElement(By.name("vehicle_number"));
		click(email);
		errorCheck(nameText);
		nameText.clear();
		nameText.sendKeys("!@#$%^");
		click(email);
		errorCheck(nameText);
		nameText.clear();
		nameText.sendKeys("hello how are you im fine here my name is ramesh");
		click(email);
		errorCheck(nameText);

		numberTextbox();
	}

	private static void numberTextbox() throws InterruptedException

	{

		WebElement phone = driver.findElement(By.name("vehicle_number"));
		phone.sendKeys("abcd");
		WebElement click = driver.findElement(By.name("vehicle_name"));
		click(click);
		errorCheck(phone);

	}

	private static void errorCheck(WebElement element) throws InterruptedException {

		List<WebElement> errors = driver.findElements(By.tagName("p"));
		for (WebElement error : errors) {
			System.out.println(error.getText());
		}
		if (errors.isEmpty()) {
			Thread.sleep(1000);
			String value = element.getAttribute("value");
			System.out.println(value + " : this value should not accept");
		}

	}

	private static void validData() {

		driver.findElement(By.name("vehicle_name")).sendKeys("TATA ACE");
		driver.findElement(By.name("vehicle_number")).sendKeys("TN42AW4774");
		Select obj1 = new Select(driver.findElement(By.id("fuel_type")));
		obj1.selectByIndex(1);
		Select obj2 = new Select(driver.findElement(By.id("waste_type")));
		obj2.selectByIndex(1);

//		driver.findElement(By.xpath("//button[@type='submit']")).click();

		System.out.println("Vehicle create successfully");

	}

	public static void click(WebElement click) {
		click.click();
	}

}
