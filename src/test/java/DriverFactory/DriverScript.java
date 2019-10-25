package DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;
import Utilities.ScreenShot;
public class DriverScript {
	ExtentReports report;
	ExtentTest test;
	WebDriver driver;
	@Test
//create method 
	public void startTest()throws Throwable
	{
		//creating reference object for excel util methods
ExcelFileUtil excel=new ExcelFileUtil();
//iterating all row in MasterTestCases sheet
	for(int i=1;i<=excel.rowCount("MasterTestCases");i++)
	{
	String ModuleStatus="";
	if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
	{
		//store module name into TCModule 
	String TCModule=excel.getData("MasterTestCases", i, 1);
report=new ExtentReports("./Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
	//iterate all rows in ApplicationLogin sheet
	for(int j=1;j<=excel.rowCount(TCModule);j++)
	{
		test=report.startTest(TCModule);
	//read all columns in ApplicationLogin testcase
String Description=excel.getData(TCModule, j, 0);
String Object_Type=excel.getData(TCModule, j, 1);
String Locator_Type=excel.getData(TCModule, j, 2);
String Locator_Value=excel.getData(TCModule, j, 3);
String Test_Data=excel.getData(TCModule, j, 4);
try{
	if(Object_Type.equalsIgnoreCase("startBrowser"))
	{
	driver=FunctionLibrary.startBrowser(driver);
	System.out.println("Launching browser");
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("openApplication"))
	{
		FunctionLibrary.openApplication(driver);
		System.out.println("Launching application in browser");
		test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("typeAction"))
	{
	FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("clickAction"))
	{
		FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
		test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("waitForElement"))
	{
	FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);	
	test.log(LogStatus.INFO, Description);
	}
		
	else if(Object_Type.equalsIgnoreCase("closeBrowser"))
	{
		FunctionLibrary.closeBrowser(driver);
		test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("captureData"))
	{
		FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
		test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("tableValidation"))
	{
		FunctionLibrary.tableValidation(driver, Test_Data);
	}
	/*else if(Object_Type.equalsIgnoreCase("StockCategories"))
	{
		FunctionLibrary.stockCategories(driver);
		test.log(LogStatus.INFO, Description);
	}*/
	else if(Object_Type.equalsIgnoreCase("unitOfMeasurement"))
	{
		FunctionLibrary.unitOfMeasurement(driver);
		test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("tableValidation1"))
	{
		FunctionLibrary.tableValidation2(driver, Test_Data);
		test.log(LogStatus.INFO, Description);
	}
	//write as pass into status column
	excel.setCellData(TCModule, j, 5, "PASS");
	test.log(LogStatus.PASS, Description);
	//ScreenShot.Takescreen(driver, Description);
	ModuleStatus="true";
	}catch(Exception e)

{
System.out.println(e.getMessage());
		
excel.setCellData(TCModule, j, 5, "FAIL");
test.log(LogStatus.FAIL, Description);
//ScreenShot.Takescreen(driver, Description);
ModuleStatus="false";
break;
}
if(ModuleStatus.equalsIgnoreCase("true"))
{
	excel.setCellData("MasterTestCases", i, 4, "PASS");
}
else
{
	if(ModuleStatus.equalsIgnoreCase("False"))
	{
	excel.setCellData("MasterTestCases", i, 4, "FAIL");	
	}
}
report.flush();
report.endTest(test);
}
}
else{
//write as not Executed in status column in MasterTestCases sheet
excel.setCellData("MasterTestCases", i, 4, "Not Executed");
		
	}
	}
	}
}




















