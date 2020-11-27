
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
import java.util.Scanner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author spaneos
 *
 */
public class Image {

	public static void scrape(Scanner sc) {
		System.out.println("Enter input file path");
		String filePath = sc.next();
		System.out.println(filePath);

		List<ImgInput> imgLinks = DataService.loadImgLink(filePath);
		RestTemplate restTemplate = new RestTemplate();
		List<ImgOutput> imageOutputs=new ArrayList<>();
		List<String> failedUsns=new ArrayList<>();
		imgLinks.forEach(imgLink -> {
			try {

			ResponseEntity<byte[]> image=restTemplate.getForEntity(imgLink.getLink(), byte[].class);
			imageOutputs.add(new ImgOutput(image.getBody(),imgLink.getUsn()));
			} catch (Exception e) {
				failedUsns.add(imgLink.getUsn());
				e.printStackTrace();
			}
		});
		
		DataService.writeImgData(imageOutputs,failedUsns);
		
		

	}

}
