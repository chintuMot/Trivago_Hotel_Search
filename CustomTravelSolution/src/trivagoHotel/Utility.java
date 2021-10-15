package trivagoHotel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utility {

	public static WebDriver driver;
	
	public static WebDriverWait wait;
	public static void setUp(String url)
	{
		    WebDriverManager.chromedriver().setup();
		    //System.setProperty("webdriver.chromer.driver", "D:\\learning\\Project Stuff\\Driver\\chromedriver_win32 (1)chromedriver.exe");
	        driver = new ChromeDriver();
		    driver.manage().window().maximize();
		    driver.manage().deleteAllCookies();
		    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		    driver.get(url);
		    wait= new WebDriverWait(driver,20);
	}

	public static String  date,month,year,calmonthyear,calyear,calmonth;
	public static String[] x;

	public static void searchHotel(String date,String month,String year,String location,int checkoutdays) throws Throwable                          //enter date in these formate 11/February/2022
	{
		

		 System.out.println("Inpute DD/MM/YYYY " + date + month + year);
		 driver.findElement(By.xpath("//input[@type='search']")).sendKeys(location);   //find search box and type "London" 
	    driver.findElement(By.xpath("//button[@key='checkInButton']")).click();   //click on check in button to choose the date
	     
	    calmonthyear = driver.findElement(By.xpath("(//th[@id='cal-heading-month']/span[1])[1]")).getText(); //get current month and year present in calender box
			 //extract month and year from calender
				 x = calmonthyear.split(" ");
				 calyear = x[1];
				 calmonth = x[0];
				System.out.println("Current Year " + calyear);
				System.out.println("Current Month  " + calmonth);
			
				//scroll the calendar until input year not matched 
				while(!calyear.equals(year))
				 {
					 driver.findElement(By.xpath("//button[@class='cal-btn-next']")).click();
					 calmonthyear = driver.findElement(By.xpath("(//th[@id='cal-heading-month']/span[1])[1]")).getText(); //get current month and year
					 
					     x = calmonthyear.split(" ");
						 calyear = x[1];
						 calmonth = x[0];
						 System.out.println(" year search in loop " + calyear +  " " + calmonth);
						 
				 }	
				 
				//scroll the calendar until input month not matched 
				 while(!calmonth.contains(month))
				 {
					 driver.findElement(By.xpath("//button[@class='cal-btn-next']")).click();
					 calmonthyear = driver.findElement(By.xpath("(//th[@id='cal-heading-month']/span[1])[1]")).getText(); //get current month and year
					 
					     x = calmonthyear.split(" ");
						 calyear = x[1];
						 calmonth = x[0];
						 
						 System.out.println(" month search in loop " + calyear +  " " + calmonth);
						 
				 }
			
				
				 //get driver focus on date to pickup check-in date
				 
				 WebElement cale = driver.findElement(By.xpath("//div[@class='two-month-calendar']/child::table[1]"));
				 List<WebElement> row=  cale.findElements(By.tagName("tr"));	
				
				 
				 boolean flag = false;
				 
				 // iterate calendar data i.e dates to click on input date (check-in date)
				 for(int i=1;i<row.size();i++)
				 {
					 List<WebElement> col =row.get(i).findElements(By.xpath("//div[@class='two-month-calendar']/child::table[1]//td//time[1]"));
					 
					 for(int j=0;j<col.size();j++)
					 {
						 if(col.get(j).getText().equals(date))
						 {
							  col.get(j).click();
							  col.get(j+checkoutdays).click();   // here checkoutdays meand after how  many days we have to checkout from checkin date
						
							wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("(//button[text()='Apply'])[1]")))).click();  //click on apply button 
							wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(@class,'primary')]")))).click();			//ckick on search button		
							 flag=true;
							 break;
						 }
					 }
					      if(flag)
					  {
						   break;
					  }
			 }
				 Thread.sleep(2000);
				 
				 //get all list of search hotel and print there count
				 List<WebElement>	searchcount = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//ol[@id='js_itemlist']//li"))));
				 System.out.println("=======Number of Hotels present in search Table ========== " +  searchcount.size());
	}

	//iterate to the search list and grab price of Top two hotels and compare them
	public static void compairPrice(int first,int second )
	{
		String firstprice = driver.findElement(By.xpath("(//ol[@id='js_itemlist']//div/strong)["+first+"]")).getText();
		
		String secondprice = driver.findElement(By.xpath("(//ol[@id='js_itemlist']//div/strong)["+second+"]")).getText();
		int e =   Integer.valueOf(firstprice.replaceAll("[^0-9]", ""));
		int q = Integer.valueOf(secondprice.replaceAll("[^0-9]", ""));
		System.out.println("Price of first Top Hotel " + e);
		System.out.println("Price of second Top Hotel " + q);
		 if(e==q)
		  {
			   System.out.println("Both prices are same " + e);
		  }
		  else if(e>q)
		  {
			  System.out.println("Price of first hotel is more by " + ( e-q) + " Rupees ");
		  }
		  else{
			  System.out.println("price of second hotel is more by " + (q-e) + " Rupees ");
		  }
	    
	}

	public static void main(String[] arg)
	{
	    setUp("https://www.trivago.in/");	
	}
				 
	public static  void tearDown()
	{
	    	driver.quit();
	}
				 


	}

