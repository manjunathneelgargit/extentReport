package extentReportDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class NopECommerce 
{
	public WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	@BeforeTest
	public void configBT()
	{
//		Step 1 : Create Object of ExtentHtmlReporter
		//Create object of ExtentHtmlReporter and pass the path of the folder where u want to create html report
		//here user.dir will give you the path of current working directory
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myreport.html");
		htmlReporter.config().setDocumentTitle("Title of the report");
		htmlReporter.config().setReportName("Name of the report");
		htmlReporter.config().setTheme(Theme.DARK);
		
//		Step 2 : create object of ExtentReports
		extent = new ExtentReports();
		
		//attach htmlreporter to ExtentReports
		extent.attachReporter(htmlReporter);
		
		//add additional details
		extent.setSystemInfo("HostName", "HOPE");
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("OS Version", "10");
		extent.setSystemInfo("TesterName", "Manjunath");
		extent.setSystemInfo("BrowserName", "Chrome");
		extent.setSystemInfo("Browser Version", "81");
	}
	
	@Test
	public void homePageTitleTest()
	{
		//Used to create a new test entry in html report
		 test = extent.createTest("homePageTitleTest");  
		 Assert.assertEquals(driver.getTitle(), " Administrator - Home - vtiger CRM 5 - Commercial Open Source CRM");
	}
	
	@Test
	public void vTigetCRMLogoTest()
	{
		test = extent.createTest("vTigetCRMLogoTest");
		boolean status = driver.findElement(By.xpath("//img[@src='test/logo/vtiger-crm-logo.gif']")).isDisplayed();
		Assert.assertTrue(status);
	}
	
	@Test
	public void vTigerCRMLoginTest()
	{
		test = extent.createTest("vTigerCRMLoginTest");
		Assert.assertTrue(true);
	}
	
	@AfterTest
	public void configAT()
	{
		extent.flush();
	}
	
	
	
}
