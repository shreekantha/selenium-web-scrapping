
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
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author spaneos
 *
 */
public class Vtu {

    private static final Logger log = LoggerFactory.getLogger(Vtu.class);

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

				driver.findElement(By.xpath("//*[@id=\"raj\"]/div[1]/div/input")).sendKeys(input.getUsn());
				driver.findElement(By.xpath("//*[@id=\"raj\"]/div[2]/div[1]/input")).sendKeys("");
				Thread.sleep(10000);
                try {
                    driver.findElement(By.id("submit")).click();
                    // 2. Check for the "Invalid USN" alert immediately
                    WebDriverWait wait = new WebDriverWait(driver, 2);
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());

                    System.out.println("Error from VTU: " + alert.getText());
                    failedUsns.add(input.getUsn());
                    driver.close();
                    alert.accept(); // Close the popup
                    // Stop processing this USN and move to the next one
                    return;

                }catch (TimeoutException f) {
                    // Code to switch to and handle the alert

                    log.info("no alert present");


                }

                identifier = driver.findElement(By.xpath("//*[@id=\"dataPrint\"]/div[2]/div/div/div[2]/div/div/div[2]/div/div[1]/b"))
                    .getText();
                //*[@id="dataPrint"]/div[2]/div/div/div[2]/div/div/div[2]/div/div[1]/b

//				System.out.println("identifier:" + identifier);

				List<WebElement> termElements = driver.findElements(By.tagName("b"));
				List<String> terms = new ArrayList<>();
//                System.out.println("termelements:"+termElements);
				for (WebElement termElement : termElements) {
					String rawTerm = termElement.getText();
//                    System.out.println("rawTerm:"+rawTerm);

                    if (rawTerm.contains("Semester")) {
//                        System.out.println("contains Semester:"+rawTerm);
//                        System.out.println("split: "+ Arrays.toString(rawTerm.split(":")));

                        terms.add(rawTerm.split(":")[1].trim());
					}
				}

				int termCount = 0;
                boolean isResultExtracted=false;
				List<WebElement> divTableBodies = driver.findElements(By.className("divTableBody"));
//                System.out.println("divTableBodies:"+divTableBodies.size());
				for (WebElement tableBody : divTableBodies) {
//                    System.out.println("termcount:"+termCount);
					List<WebElement> rows = tableBody.findElements(By.className("divTableRow"));
//                    System.out.println("rows before remove:"+rows);
					rows.remove(0);
//                    System.out.println("rows after remove:"+rows);

                    for (WebElement e : rows) {
//                        System.out.println("rows:"+e);
						List<WebElement> cells = e.findElements(By.className("divTableCell"));
						List<String> cellValues = new ArrayList<>();
						for (WebElement c : cells) {
							cellValues.add(c.getText());
						}
						if (cellValues != null && cellValues.size() >= 6) {
//                            System.out.println("terms.get(termCount):"+terms.get(termCount));
//							System.out.println("cell values:" + cellValues);

							outputs.add(new Output(input.getUsn(), terms.get(termCount), cellValues.get(0),
									cellValues.get(1), cellValues.get(2), cellValues.get(3), cellValues.get(4),
									cellValues.get(5), identifier));
                            isResultExtracted=true;
						}
					}
                    if(isResultExtracted)
					    termCount++;
				}
				driver.close();
			} catch (Exception e) {
				e.printStackTrace();
//				System.out.println("failed usn:" + input.getUsn());
				failedUsns.add(input.getUsn());
				driver.close();
			} finally {
                assert driver!= null;
                driver.quit();
			}
		}
		DataService.writeDataToFile(failedUsns, outputs);
	}

}
