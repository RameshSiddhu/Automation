package initial;

import java.io.File;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class vehicleReport {
	static ChromeDriver driver = new ChromeDriver();

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\USER\\Downloads\\chromedriver-win64(1)\\chromedriver-win64\\chromedriver.exe");

		driver.get("https://unwastenetwork.in/login");
		driver.manage().window().maximize();
		logIn();
		vehicleManagement();
		tableData();
		nameFilter();
		revertNameFilter();
		DateFilter();
		revertDateFilter();
		bothFilter();
		downloadReport();

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
		WebElement show = driver.findElement(By.xpath("//*[@id=\"mCSB_1_container\"]/ul/li[4]/ul/li[2]/a/span"));
		show.click();
	}

	private static void tableData() throws InterruptedException {
		String entityValue = driver.findElement(By.name("simpletable_length")).getAttribute("value");
		int val = Integer.parseInt(entityValue);
		WebElement table = driver.findElement(By.id("simpletable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int dataCount = rows.size();
		if (dataCount > val) {

			nextCheck(val);

		} else {

			nameFilter();
		}

	}

	private static void nextCheck(int value) throws InterruptedException {
		driver.findElement(By.id("simpletable_next")).click();
		String text = driver.findElement(By.xpath("//*[@id='simpletable']/tbody/tr[1]/td[1]")).getText();
		int nextValue = Integer.parseInt(text);

		if (nextValue > value) {
			System.out.println("next set of data is showing properly");
		}

		else {
			System.out.println("next button is not working");
		}
		preeviousCheck(value);
	}

	private static void preeviousCheck(int value) throws InterruptedException {
		driver.findElement(By.id("simpletable_previous")).click();
		String text = driver.findElement(By.xpath("//*[@id='simpletable']/tbody/tr[1]/td[1]")).getText();
		int firstValue = Integer.parseInt(text);

		if (firstValue > value) {
			System.out.println("previous set of data is showing properly");
		}

		else {
			System.out.println("previous button is not working");
		}
		Thread.sleep(1000);
		entityCheck(value);

	}

	private static void entityCheck(int val) throws InterruptedException {
		Select obj = new Select(driver.findElement(By.name("simpletable_length")));
		obj.selectByIndex(1);
		String newEnt = driver.findElement(By.name("simpletable_length")).getAttribute("value");
		int num = Integer.parseInt(newEnt);
		WebElement table = driver.findElement(By.id("simpletable"));
		List<WebElement> list = table.findElements(By.tagName("tr"));
		int crntNum = list.size() - 2;

		if (crntNum > val && crntNum == num) {
			System.out.println("entity is working properly");
		}
	}

	private static void nameFilter() {

		WebElement table = driver.findElement(By.id("simpletable"));
		List<WebElement> beforeFilter = table.findElements(By.tagName("tr"));
		int bRows = beforeFilter.size() - 2;

		Select obj = new Select(driver.findElement(By.name("vehicle_id")));
		obj.selectByIndex(1);
		driver.findElement(By.name("submitbtn")).click();

		String entityValue = driver.findElement(By.xpath("//*[@id=\"simpletable_paginate\"]/ul")).getAttribute("value");
		int tCount = Integer.parseInt(entityValue);

		List<WebElement> afterFilter = table.findElements(By.tagName("tr"));
		int aRows = afterFilter.size() - 2;

		if (bRows > aRows) {
			System.out.println("vehicle filter is working properly");
		} else {
			System.out.println("vehicle filter is not working properly");
		}

	}

	private static void revertNameFilter() {

		Select obj = new Select(driver.findElement(By.id("driver_master")));
		obj.selectByIndex(0);
		driver.findElement(By.name("submitbtn")).click();

		WebElement name1 = driver.findElement(By.xpath("//*[@id='simpletable']/tbody/tr[1]/td[6]"));
		WebElement name2 = driver.findElement(By.xpath("//*[@id='simpletable']/tbody/tr[2]/td[6]"));

		String fName = name1.getText();
		String sName = name2.getText();

		WebElement filterName = driver.findElement(By.xpath("//*[@id=\"wastage-weighs-form\"]/div/div[1]/div/button"));
		String actualName = filterName.getText();
		if (actualName.equalsIgnoreCase(fName) && actualName.equalsIgnoreCase(sName)) {
			System.out.println("revert name filter is not working properly");
		} else {
			System.out.println("revert name filter is working properly");

		}
	}

	private static void DateFilter() {

		String startDate = "28-03-2024";
		driver.findElement(By.id("from_date")).sendKeys("28-03-2024");
		driver.findElement(By.id("to_date")).sendKeys("28-03-2024");
		driver.findElement(By.name("submitbtn")).click();
		String actualData = driver.findElement(By.xpath("//table[@id='simpletable']/tbody/tr[1]/td")).getText();
		String expectedData = "No data available in table";
		if (actualData.equals(expectedData)) {
			System.out.println("empty data checkup completed");
		}
		driver.findElement(By.id("from_date")).sendKeys("29-03-2024");
		driver.findElement(By.id("to_date")).sendKeys("29-03-2024");
		driver.findElement(By.name("submitbtn")).click();

		WebElement table = driver.findElement(By.id("simpletable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));

		if (rows.size() > 3) {
			String actualDate = driver.findElement(By.xpath("//*[@id='simpletable']/tbody/tr[1]/td[2]")).getText();

			LocalDate fDate = LocalDate.parse(startDate);
			LocalDate tDate = LocalDate.now();
			LocalDate targetDate = LocalDate.parse(actualDate);

			// Output result
			if (!targetDate.isBefore(tDate) && !targetDate.isAfter(fDate)) {
				System.out.println(targetDate + " is within the range.");
			} else {
				System.out.println(targetDate + " is NOT within the range.");
			}
		}
	}

	private static void revertDateFilter() {
		driver.findElement(By.id("from_date")).clear();
		driver.findElement(By.id("to_date")).clear();
		driver.findElement(By.name("submitbtn")).click();

		WebElement table = driver.findElement(By.id("simpletable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));

		if (rows.size() > 3) {
			String actualDate = driver.findElement(By.xpath("//*[@id='simpletable']/tbody/tr[1]/td[2]")).getText();
			String expectedDate = "29-March-2024";
			if (actualDate.equals(expectedDate)) {
				System.out.println("revert date filter is working properly");
			} else {
				driver.quit();
			}
		}
	}

	private static void bothFilter() {

		Select obj = new Select(driver.findElement(By.id("driver_master")));
		obj.selectByIndex(1);
		String fromDate = "29-03-2024";
		String toDate = "29-03-2024";
		driver.findElement(By.id("from_date")).sendKeys(fromDate);
		driver.findElement(By.id("to_date")).sendKeys(toDate);
		driver.findElement(By.name("submitbtn")).click();

		WebElement name1 = driver.findElement(By.xpath("//*[@id='simpletable']/tbody/tr[1]/td[6]"));

		String fName = name1.getText();
		String[] fDate = fromDate.split("-");
		String[] tDate = toDate.split("-");

		String tableDate = driver.findElement(By.xpath("//*[@id='simpletable']/tbody/tr[1]/td[2]")).getText();
		String[] splitDate = tableDate.split("-");

		int f_Date = Integer.parseInt(fDate[0]);
		int t_Date = Integer.parseInt(tDate[0]);
		int table_date = Integer.parseInt(splitDate[0]);

		WebElement filterName = driver.findElement(By.xpath("//*[@id=\"wastage-weighs-form\"]/div/div[1]/div/button"));
		String actualName = filterName.getText();
		if (actualName.equalsIgnoreCase(fName) && table_date <= t_Date && table_date >= f_Date) {
			System.out.println("filtering with Driver and Date is working properly");
		} else {
			System.out.println("filtering with Driver and Date is not working properly");

		}

	}

	private static void downloadReport() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id='wastage-weighs-form']/div/div[4]/button[2]")).click();

		Thread.sleep(2000);
		String downloadDir = "C:\\Users\\USER\\Downloads";
		String fileName = "DriverReport.xlsx";
		String filePath = downloadDir + File.separator + fileName;
		File downloadedFile = new File(filePath);

		if (downloadedFile.exists()) {
			System.out.println("File downloaded successfully!");
		} else {
			System.out.println("File download failed!");
		}

	}

}
