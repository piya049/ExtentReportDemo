package com.er;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;


/**
 * Unit test for simple App.
 */
public class AppTest {

	static WebDriver driver;

public static void main(String[] args) throws Exception {
	
	ExtentReports report = new ExtentReports();
	File file = new File("report.html");
	ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
	report.attachReporter(sparkReporter);
	
	
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.get("https://www.google.com/");
	String base64Code = captureScreenShot();
	String path = captureScreenShot("Google.jpg");
	
	
	//selenium: 3 formats to capture SS, byteArray, base64k 
	report.createTest("Screnshot Test 1", "This is for attaching the screenshot to the test at test level")
	.info("This is a info Message")
	.addScreenCaptureFromBase64String(base64Code);
	
	report.createTest("Screnshot Test 2", "This is for attaching the screenshot to the test at test level")
	.info("This is a info Message")
	.addScreenCaptureFromBase64String(base64Code, "Google homepage1")
	.addScreenCaptureFromBase64String(base64Code, "Google homepage2")
	.addScreenCaptureFromBase64String(base64Code, "Google homepage3")
	.addScreenCaptureFromBase64String(base64Code, "Google homepage3");
	
	
	
	report.createTest("Screnshot Test 3", "This is for attaching the screenshot to the test at test level")
	.info("This is a info Message")
	.addScreenCaptureFromPath(path);
	
	report.createTest("Screnshot Test 4", "This is for attaching the screenshot to the test at test level")
	.info("This is a info Message")
	.addScreenCaptureFromPath(path, "Google HomePage1")
	.addScreenCaptureFromPath(path, "Google HomePage2")
	.addScreenCaptureFromPath(path, "Google HomePage3")
	.addScreenCaptureFromPath(path, "Google HomePage4");
	
	report.flush();
	Desktop.getDesktop().browse(new File("report.html").toURI());
	
}

public static String captureScreenShot() {
	
	TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	String base64Code = takesScreenshot.getScreenshotAs(OutputType.BASE64);
	System.out.println("Screenshot Saved successfully");

	return base64Code;
	};

public static String captureScreenShot(String fileName) {
	
	TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
	File sourFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
	File destFile = new File("./Screenshots/"+ fileName);
	
	try {
		FileUtils.copyFile(sourFile, destFile);
	}
	catch(IOException e) {
		e.printStackTrace();
	}
	
	System.out.println("Screenshot Saved successfully");
	return destFile.getAbsolutePath();
	};
}

