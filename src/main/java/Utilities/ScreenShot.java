package Utilities;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {
public static void Takescreen(WebDriver driver,String sname)throws Throwable
{
String path="C:\\Users\\anand\\Desktop\\supbabu\\ERP_Maven1\\Screens\\"+sname+".png";	
File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(screen, new File(path));
}
}
