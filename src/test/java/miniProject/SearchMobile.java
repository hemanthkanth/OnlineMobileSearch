package miniProject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SearchMobile  {
	String link;
	public static WebDriver driver;

	public static WebDriver getWebDriver(String Browser) {


		if(Browser.equalsIgnoreCase("chrome")) {
			System.out.println("Chrome Browser is selected for Automation");
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		}else if(Browser.equalsIgnoreCase("edge")){
			System.out.println("Edge Browser is selected for Automation");
			
			WebDriverManager.edgedriver().setup();
		    driver = new EdgeDriver();

		}else if(Browser.equalsIgnoreCase("firefox")) {
			System.out.println("Mozilla Firefox is selected for Automation");
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		return driver;

	}
	public static void LaunchUrl(String link) {
		// Launch Amazon website

		driver.get(link);
		System.out.println("1)Website Link\n	"+driver.getCurrentUrl()+"\n");

	}
	public static void MaximizeWindow() {

		//maximize browser window
		driver.manage().window().maximize();
		System.out.println("2)Window Maximized\n");
	}

	public static void toSearch(String toSearch) {

		// wait till the browser loads
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		//Search “mobile smartphones under 30000”
		WebElement ToSearch = driver.findElement(By.id("twotabsearchtextbox"));
		ToSearch.sendKeys(toSearch);

		//Search Button
		driver.findElement(By.id("nav-search-submit-button")).click();
		System.out.println("3)To Search\n	"+toSearch+"\n");
	}

	public static boolean Validation(String toSearch) {

		//Validation
		WebElement searchElement = driver.findElement(By.className("a-color-state"));

		//Get searchString
		String searchString = searchElement.getText();

		WebElement page_Items= driver.findElement(By.className("a-section"));
		String page_Items_text = page_Items.getText();
		System.out.println("4)Validation Display Message\n	"+  page_Items_text+"\n");

		//Check Validation message
		if(toSearch.equals(searchString.substring(1,searchString.length()-1))) {

			System.out.println("\ti)search string VALIDATION SUCCESSFUL\n\t\tExpected: "+ 
					searchString.substring(1,searchString.length()-1)+
					"\n\t\tActual: "+ toSearch+"\n");

			// Get Search Page & No_Of_Items
			String SearchPage_Items = driver.findElement(By.className("sg-col-inner")).getText();

			if(SearchPage_Items.startsWith("1") && SearchPage_Items.contains("of over") && SearchPage_Items.contains("results for")) {
				System.out.println("\tii)no of pages and items 'VALIDATION SUCCESSFUL'");
			}else {
				System.out.println("\tii)no of pages and items 'VALIDATION UNSUCCESSFUL'");

			}
			return true;
		}else {
			System.out.println("\ti)search string VALIDATION UNSUCCESFUL/n\t\tExpected: "+
					searchString.substring(1,searchString.length()-1)+
					"\n\t\tActual: "+ toSearch+ "\n");
			// Get Search Page & No_Of_Items
			String SearchPage_Items = driver.findElement(By.className("sg-col-inner")).getText();

			if(SearchPage_Items.startsWith("1") && SearchPage_Items.contains("of over") && SearchPage_Items.contains("results for")) {
				System.out.println("\tii)no of pages and items 'VALIDATION SUCCESSFUL'");
			}else {
				System.out.println("\tii)no of pages and items 'VALIDATION UNSUCCESSFUL'");

			}
			return false;
		}	

	}



	public static void dropSelect(String toSelect) {

		//Click on Sort By 
		WebElement sortOpt =driver.findElement(By.id("s-result-sort-select"));
		Select selectOpt = new Select(sortOpt);

		// Select Opt "Newest Arrivals"
		selectOpt.selectByVisibleText(toSelect);

		//Total Select Option
		List<WebElement> sortOptions = selectOpt.getOptions();
		System.out.println("\n5)Number of option in Sort by Dropdown: \n\t Total ="+sortOptions.size()+"\n");
		System.out.println("---------------------------------------------------------------------------");

		//Check Selected option 
		String selectedOption=driver.findElement(By.className("a-dropdown-prompt")).getText();
		if(selectedOption.equals(toSelect)) {
			System.out.println("TEST CASE PASSED"+"\n"+"\tSelected option: "+selectedOption);
			System.out.println("---------------------------------------------------------------------------");
		}else {
			System.out.println("TEST CASE FAILED"+"\n"+"\tSelected option: "+selectedOption);
			System.out.println("---------------------------------------------------------------------------");
		}
	}


	public static void ScreenShot(String img ) throws IOException {
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File target = new File(img);
		FileHandler.copy(source,target);
	}

	public static void closeBrowser() {
		driver.quit();
	}

}
