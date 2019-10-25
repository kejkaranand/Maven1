package CommonFunLibrary;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.jsoup.select.Evaluator.Id;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Utilities.PropertyFileUtil;
public class FunctionLibrary {
public static WebDriver driver;
//method for browser launching
public static WebDriver startBrowser(WebDriver driver)throws Throwable
{
if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
{
System.setProperty("webdriver.chrome.driver", "./CommonJars/chromedriver.exe");		
driver=new ChromeDriver();
}
else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
{
		
}
else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("ie"))
{
}
else 
{
System.out.println("No browser is matching");
}
return driver;
}
//launching url 
public static void openApplication(WebDriver driver)throws Throwable
{
driver.get(PropertyFileUtil.getValueForKey("Url"));
driver.manage().window().maximize();
}
//method for waitforElement
public static void waitForElement(WebDriver driver,String locatortype,
	String locatorvalue,String waittime)
{
WebDriverWait mywait=new WebDriverWait(driver, Integer.parseInt(waittime));
if(locatortype.equalsIgnoreCase("id"))
{
mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
}
else if(locatortype.equalsIgnoreCase("name"))
{
mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));	
}
else if(locatortype.equalsIgnoreCase("xpath"))
{
mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));	
}
else
{
	System.out.println("Unable to wait for element");
}
}
//method for type actions
public static void typeAction(WebDriver driver,String locatortype,
	String locatorvalue,String testdata)
{
if(locatortype.equalsIgnoreCase("id"))
{
driver.findElement(By.id(locatorvalue)).clear();
driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
}
else if(locatortype.equalsIgnoreCase("name"))
{
	driver.findElement(By.name(locatorvalue)).clear();
	driver.findElement(By.name(locatorvalue)).sendKeys(testdata);	
}
else if(locatortype.equalsIgnoreCase("xpath"))
{
	driver.findElement(By.xpath(locatorvalue)).clear();
	driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
}
else
{
	System.out.println("Unable execute typeaction method");
}
}
//method for click Action
public static void clickAction(WebDriver driver,String locatortype,
		String locatorvalue)
{
if(locatortype.equalsIgnoreCase("id"))
{
driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
}
else if(locatortype.equalsIgnoreCase("xpath"))
{
driver.findElement(By.xpath(locatorvalue)).click();
}
else if(locatortype.equalsIgnoreCase("name"))
{
driver.findElement(By.name(locatorvalue)).click();
}
else
{
System.out.println("Unbale to execute ClickAction method");
}
}
//method for closing browser
public static void closeBrowser(WebDriver driver)
{
	driver.close();
}
//method for date generate
public static String generateDate()
{
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
	return sdf.format(date);
}
//capture data in to notepad
public static void captureData (WebDriver driver,String locatortype,String locatorvalue) throws Throwable
{
	String supplierdata=null;
	if(locatortype.equalsIgnoreCase("id"))
	{
		supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
	}
	else if (locatortype.equalsIgnoreCase("name"))
	{
		supplierdata=driver.findElement(By.name(locatorvalue)).getAttribute("value");
	}
	else if (locatortype.equalsIgnoreCase("xpath"))
	{
		supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
	}
	FileWriter fr=new FileWriter("C:\\Users\\anand\\Desktop\\supbabu\\ERP_Maven1\\CaptureData\\supplier.txt");
			BufferedWriter bw=new BufferedWriter(fr);
	bw.write(supplierdata);
	bw.flush();
	bw.close();	
}
//table validation
public static void tableValidation(WebDriver driver,String column) throws Throwable
{
	FileReader fr=new FileReader("C:\\Users\\anand\\Desktop\\supbabu\\ERP_Maven1\\CaptureData\\supplier.txt");
	BufferedReader br=new BufferedReader(fr);
	String Exp_data=br.readLine();
	//convert col data to int
	 int  column1=Integer.parseInt(column);
	if (driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed())
	{
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search"))).clear();
		Thread.sleep(5000);
	}
	else
	{
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Click-searchpanel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search"))).click();
		Thread.sleep(5000);
	}
	 //table validation
	 WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("sup-table")));
	 List<WebElement>rows=table.findElements(By.tagName("tr"));
	 System.out.println("no.of rows are: :"+rows.size());
	 for(int i=1;i<=rows.size();i++)
	 {
		 String Act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column1+"]/div/span/span")).getText();
	    Thread.sleep(5000);
	    Assert.assertEquals(Exp_data, Act_data,"Supplier number is not matching");
	    System.out.println(Exp_data+""+Act_data);
	    break; 
	 }
	 
}
//mouse click
/*public static void stockCategories(WebDriver driver) throws Throwable
{
	Actions ac=new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_items']//a[contains(text(),'Stock Items')]"))).perform();
	Thread.sleep(2000);
ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_categories']//a[contains(text(),'Stock Categories')]"))).click().perform();
}
//table valid
	public static void tableValidation1(WebDriver driver,String Exp_data) throws Throwable
	{
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box1"))).isDisplayed())
		{
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box1"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box1"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search1"))).click();
		
		}else
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Click-searchpanel1"))).click();;
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box1"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box1"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search1"))).click();
		}
	
	WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("cat-table")));
			List<WebElement>rows=table.findElements(By.tagName("tr"));
	System.out.println("no of rows are::"+rows.size());
	for(int i=1;i<rows.size();i++)
	{
//get table text in col
		String Act_Data=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
		Assert.assertEquals(Exp_data, Act_Data,"Data is not matching");
		System.out.println(Exp_data+" "+ Act_Data);
		break;
	}
	}*/
	public static void unitOfMeasurement(WebDriver driver) throws Throwable
	{
		Actions ac=new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_items']//a[contains(text(),'Stock Items')]"))).perform();
		Thread.sleep(2000);
		ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_unit_of_measurement']//a[contains(text(),'Unit of Measurement')]"))).click().perform();
	}
	//table valid
		public static void tableValidation2(WebDriver driver,String Exp_data) throws Throwable
		{
			if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box2"))).isDisplayed())
			{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box2"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box2"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search2"))).click();
			
			}else
			{
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Click-searchpanel2"))).click();;
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box2"))).clear();
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box2"))).sendKeys(Exp_data);
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search2"))).click();
			}
		
		WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("uom-table")));
				List<WebElement>rows=table.findElements(By.tagName("tr"));
		System.out.println("no of rows are::"+rows.size());
		for(int i=1;i<rows.size();i++)
		{
	//get table text in col
			String Act_Data=driver.findElement(By.xpath("//table[@id='tbl_a_unit_of_measurementlist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
			Assert.assertEquals(Exp_data, Act_Data,"Data is not matching");
			System.out.println(Exp_data+" "+ Act_Data);
			break;
		}
		}
	
	
}























