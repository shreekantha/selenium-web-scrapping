
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
public class Vtu4u {

	/**
	 * @param usns
	 * @param url
	 */
	public static void srcapeTheDataFromVtu4u(String filePath, String url) {

		List<Input> inputs = DataService.loadData(filePath);

		List<String> failedUsns = new ArrayList<>();
		List<Output> outputs = new ArrayList<>();
		for (Input input : inputs) {
			WebDriver driver = null;
			try {

				driver = new ChromeDriver();
				driver.manage().window().maximize();
				String targetUrl = new StringBuilder(url).append("/result/").append(input.getUsn()).append("/sem-")
						.append(input.getTerm()).append("/rs-26?cbse=1").toString();
				driver.get(targetUrl);

//				driver.findElement(By.id("usn")).sendKeys(usn);
//				driver.findElement(By.id("syl3")).click();
				Thread.sleep(3000);

				WSApplication.identifier = driver
						.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/div[2]/div[1]/div[3]/div[3]/div[1]"))
						.getText();

				List<WebElement> divTableBodies = driver.findElements(By.tagName("tbody"));
				for (WebElement tableBody : divTableBodies) {
					List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
					rows.remove(0);
					rows.remove(rows.size() - 1);
					for (WebElement e : rows) {
						List<WebElement> cells = e.findElements(By.tagName("td"));
						List<String> cellValues = new ArrayList<>();
						for (WebElement c : cells) {
							cellValues.add(c.getText());
						}
						int cellsCount = cellValues.size();
						System.out.println("size : " + cellsCount);
						if (cellValues.size() <= 6) {
							System.out.println("");
							outputs.add(new Output(input.getUsn(), input.getTerm(),
									cellValues.get(cellValues.size() - cellsCount--), cellValues.get(1),
									cellValues.get(2), cellValues.get(3), cellValues.get(4), cellValues.get(5),
									WSApplication.identifier));
						} else {

							outputs.add(new Output(input.getUsn(), input.getTerm(), cellValues.get(0),
									cellValues.get(1), cellValues.get(2), cellValues.get(3), cellValues.get(4),
									cellValues.get(5), cellValues.get(6), cellValues.get(7), cellValues.get(8),
									WSApplication.identifier));
						}
					}
				}
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
