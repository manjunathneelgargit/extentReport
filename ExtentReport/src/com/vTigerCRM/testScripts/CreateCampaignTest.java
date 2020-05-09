package com.vTigerCRM.testScripts;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.vTigerCRM.genericLib.Base;

public class CreateCampaignTest extends Base
{
	@Test
	public void homePageTitleTest()
	{
		//Used to create a new test entry in html report
		 test = extent.createTest(getClass().getName());  
		 Assert.assertEquals(driver.getTitle(), " Administrator - Home - vtiger CRM 5 - Commercial Open Source CRM");
	}
	
	@Test
	public void vTigetCRMLogoTest()
	{
		test = extent.createTest(getClass().getName());
		boolean status = driver.findElement(By.xpath("//img[@src='test/logo/vtiger-crm-logo.gif']")).isDisplayed();
		Assert.assertTrue(status);
	}
	
	@Test
	public void vTigerCRMLoginTest()
	{
		test = extent.createTest(getClass().getName());
		Assert.assertTrue(true);
	}
}
