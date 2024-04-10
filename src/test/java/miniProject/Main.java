package miniProject;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String Browser;
		boolean bol = true;
		System.out.println("Enter the browser to Automate (Chrome/Edge/Firefox)");
		while(bol) {
			Scanner sc = new Scanner(System.in);
			Browser = sc.next();
			if(Browser.equalsIgnoreCase("Chrome") || (Browser.equalsIgnoreCase("Edge")) || (Browser.equalsIgnoreCase("firefox"))){
				SearchMobile.getWebDriver(Browser);
				bol =false;
				sc.close();
			}else {
				System.out.println("Enter Valid Browser name(Chrome/Edge)");		
			}	
		}
		try {
			
			String filePath = System.getProperty("user.dir")+"\\testData\\Details.xlsx";
			int rows = ExcelUtils.getRowCount(filePath, "Amazon");
			
			for(int i=1; i<=rows;i++) {
				//read data from excel
				String link = ExcelUtils.getCellData(filePath, "Amazon", rows, 0);
				String toSearch = ExcelUtils.getCellData(filePath, "Amazon", rows, 1);
				String toSelect = ExcelUtils.getCellData(filePath, "Amazon", rows, 2);
				
				//pass data to the driver
				SearchMobile.LaunchUrl(link);
				SearchMobile.ScreenShot("./snaps/1)Launch.png");

				SearchMobile.MaximizeWindow();
				SearchMobile.ScreenShot("./snaps/2)Maximized.png");

				SearchMobile.toSearch(toSearch);
				SearchMobile.ScreenShot("./snaps/3)ToSearch.png");

				boolean condition =SearchMobile.Validation(toSearch);
				SearchMobile.ScreenShot("./snaps/4)Validation.png");

				SearchMobile.dropSelect(toSelect);
				SearchMobile.ScreenShot("./snaps/5)DropSelect.png");

				SearchMobile.closeBrowser();
				
				//write output in excel
				if(condition)
				{
					System.out.println("Test Results Printed in EXCEL");
					ExcelUtils.setCellData(filePath, "Amazon",i,4,"Passed");					
					ExcelUtils.fillGreenColor(filePath, "Amazon",i,4);
				}
				else
				{
					System.out.println("Test Results Printed in EXCEL");
					ExcelUtils.setCellData(filePath, "Amazon",i,4,"Failed");
					ExcelUtils.fillRedColor(filePath, "Amazon",i,4);
				}
				
			}
		}
		catch(Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
}