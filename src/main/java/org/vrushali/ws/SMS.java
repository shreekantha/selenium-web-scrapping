
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

import java.util.Scanner;

/**
 * @author spaneos
 *
 */
public class SMS {

	public static void scrape(Scanner sc) throws InterruptedException {
		System.out.println("#####################################################################");
		System.out.println("Setting the web driver for the OS " + WSApplication.os);
		System.out.println("#####################################################################");
		System.out.println();

		// setting the web driver to the system property based on the OS
		if ("Linux".equalsIgnoreCase(WSApplication.os)) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		}

		System.out.println("Enter USNs file path:");
		String inputDataFilePath = sc.next();
		System.out.println("Enter Website URL:");
		String url = sc.next();
		Student.srcapeTheDataFromVtu(inputDataFilePath, url);

	}

}
