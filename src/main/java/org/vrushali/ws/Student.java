
/*******************************************************************************
 * Copyright 2020  Vrushali Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

/**
 * 
 */
package org.vrushali.ws;

import static org.vrushali.ws.WSApplication.identifier;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author spaneos
 *
 */
public class Student {

	/**
	 * @throws InterruptedException
	 * 
	 */

	public static void srcapeTheDataFromVtu(String filePath, String url) throws InterruptedException {

		List<Input> inputs = DataService.loadData(filePath);

		System.out.println("#####################################################################");
		System.out.println("UE Score Scraping is started...");
		System.out.println("Enjoy Scraping 😊😊😊 ");
		System.out.println("#####################################################################");
		System.out.println();
		Thread.sleep(4000);

		List<String> failedUsns = new ArrayList<>();
		List<Output> outputs = new ArrayList<>();

		for (Input input : inputs) {
			WebDriver driver = null;
			try {

				driver = new ChromeDriver();
				driver.manage().window().maximize();

				// driver.get("https://results.vtu.ac.in/_CBCS/index.php");
				driver.get(url);

				driver.findElement(By.id("txtUserIdSchool")).sendKeys("0601MS0647");
				driver.findElement(By.id("txtPasswordSchool")).sendKeys("basava@647");
				driver.findElement(By.id("btnLogInSchool")).click();

				Thread.sleep(3000);

				driver.get("https://kreis.karnataka.gov.in/SMS/WebPages/frmTestSats.aspx");
				int usnsize = input.getUsn().trim().length();
				String usn = input.getUsn();
				if (input.getUsn().trim().length() < 9) {
					StringBuilder zero = new StringBuilder();

					for (int i = 0; i <9-usnsize; i++) {
						
						 zero.append("0");
						 
					}
					usn=zero.append(usn).toString();
				}
				
				driver.findElement(By.id("MainContent_txtStudCd")).sendKeys(usn);
				driver.findElement(By.id("MainContent_btnGetStud")).click();
				Thread.sleep(10000);
				driver.findElement(By.id("MainContent_btnSubmit")).click();
				driver.close();
			} catch (Exception e) {
				e.printStackTrace();
				driver.close();
				failedUsns.add(input.getUsn());
			} finally {
				driver.quit();
			}
		}
		DataService.writeDataToFile(failedUsns, outputs);
	}

}
