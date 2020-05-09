package com.vTigerCRM.genericLib;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
/**
 * This class contains configuration annotations for launching the browser, login, logout and close the browser
 * @author Manjunath N
 *
 */
public class Base
{
	public FileLib lib = new FileLib();
	public WebDriver driver = null;
	public WebDriver staticDriver = null;
	public WebDriverWait wait = null;
	
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
		
//		Step 2 : create object of ExtentReports and attach htmlreporter to ExtentReports by using attachReporter()
		extent = new ExtentReports();
		
		extent.attachReporter(htmlReporter);
		
		//add additional details
		extent.setSystemInfo("HostName", "HOPE");
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("OS Version", "10");
		extent.setSystemInfo("TesterName", "Manjunath");
		extent.setSystemInfo("BrowserName", "Chrome");
		extent.setSystemInfo("Browser Version", "81");
	}
	
	
	@BeforeClass
	public void configBC()
	{
		/* Launch browser */
		if(lib.getPropertyKeyValue("browser").equals("chrome"))
		{
			driver = new ChromeDriver();
			staticDriver = driver;
			System.out.println("Browser Launched Successfully  --> PASS");
		}
		else if(lib.getPropertyKeyValue("browser").equals("firefox"))
		{
			driver = new FirefoxDriver();
			staticDriver = driver;
			System.out.println("Browser Launched Successfully  --> PASS");
		}
		/* Maximize browser */
		driver.manage().window().maximize();
		
		/* Enter URL */
		driver.get(lib.getPropertyKeyValue("url"));
		
		/* Wait statements */
		driver.manage().timeouts().implicitlyWait(Utility.IMPLICIT_WAIT, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, Utility.EXPLICIT_WAIT);
		
		/* Verify Login Page */
		String loginTitle = driver.getTitle();
		if(loginTitle.contains("vtiger CRM 5 - Commercial Open Source CRM"))
		{
			System.out.println("Login Page is displayed --> PASS");
		}
		else
		{
			System.out.println("Login Page not is displayed --> FAIL");
		}
	}
	
	@BeforeMethod
	public void configBM()
	{
		/* Login */
		driver.findElement(By.name("user_name")).sendKeys(lib.getPropertyKeyValue("username"));
		driver.findElement(By.name("user_password")).sendKeys(lib.getPropertyKeyValue("password"));
		driver.findElement(By.id("submitButton")).click();
		
		if(driver.getTitle().contains("Home"))
		{
			System.out.println("Home Page is displayed --> PASS");
		}
		else
		{
			System.out.println("Home Page not is displayed --> FAIL");
		}
	}
	
	@AfterMethod
	public void configAM(ITestResult result) throws IOException
	{
		/* Sign out */
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//img[contains(@src,'user.PNG')]"))).perform();
		driver.findElement(By.linkText("Sign Out")).click();
		
		if(driver.getTitle().contains("vtiger CRM 5 - Commercial Open Source CRM"))
		{
			System.out.println("Logout is successful --> PASS");
		}
		else
		{
			System.out.println("Logout is not successful --> FAIL");
		}
		
		if(result.getStatus() == ITestResult.FAILURE)
		{
			test.log(Status.FAIL, "The FAILED TestCase is : " + result.getName());// to add the name in extent report : Custom message
			test.log(Status.FAIL, result.getThrowable()); // to add error/exception to report
			
			//Call Screenshot method which will take screenshot and it will return the path of screenshot
			String screenShotPath = Utility.getScreenShot(driver, result.getName());
			//attach the screenshot to extent report
		
			test.addScreenCaptureFromPath(screenShotPath);
		}
		else if (result.getStatus() == ITestResult.SKIP)
		{
			test.log(Status.SKIP, "TestCase Skipped is : "+result.getName());
		}
		else if (result.getStatus() == ITestResult.SUCCESS)
		{
			test.log(Status.PASS, "TestCase Passed is : "+result.getName());
		}
		
	}
	
	@AfterClass
	public void configAC()
	{
		/* Close Browser */
		driver.quit();
		System.out.println(driver);
		if(driver == null)
		{
			System.out.println("Browser is closed successfully --> Pass");
		}
		else
		{
			System.out.println("Broser is not closed successfully --> FAIL");
		}
	}	
	
	@AfterTest
	public void configAT()
	{
		extent.flush();
	}
}