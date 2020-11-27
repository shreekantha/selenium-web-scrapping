
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

import static org.vrushali.ws.WSApplication.fileName;
import static org.vrushali.ws.WSApplication.os;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * @author spaneos
 *
 */
public class DataService {

	public static List<Input> loadData(String filePath) {
		System.out.println("#####################################################################");
		System.out.println("Uploading the Data from the file '" + filePath + "'");
		System.out.println("#####################################################################");
		System.out.println();
		File file = new File(filePath);

		if (file.getName() != null && !file.getName().isEmpty())
			fileName = file.getName().trim().split("\\.")[0];
		FileReader fileReader = null;
		List<Input> beans = null;
		try {

			fileReader = new FileReader(file);
			beans = new CsvToBeanBuilder<Input>(fileReader).withType(Input.class).build().parse();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return beans;
	}

	/**
	 * @param failedUsns
	 * @param outputs
	 */
	public static void writeDataToFile(List<String> failedUsns, List<Output> outputs) {
		if (!outputs.isEmpty()) {

			writeDataToFile(outputs);
		}
		if (!failedUsns.isEmpty()) {

			writeFailedUsnToFile(failedUsns);
		}
	}

	/**
	 * @param outputs
	 */
	private static void writeDataToFile(List<Output> outputs) {
		Writer writer = null;

		try {
			File outputDataFilePath = null;
			System.out.println("OS in write data to file: " + os);
			if ("Linux".equalsIgnoreCase(os)) {
				outputDataFilePath = new File("output/" + fileName + "_score.csv");
			} else {
				outputDataFilePath = new File("output\\" + fileName + "_score.csv");
			}
			System.out.println("#####################################################################");
			System.out.println("Writting the scraped data to " + outputDataFilePath.getName() + " file.");
			System.out.println("#####################################################################");
			System.out.println();
			// Create Mapping Strategy to arrange the
			// column name in order
//			ColumnPositionMappingStrategy<Output> mappingStrategy = new ColumnPositionMappingStrategy<Output>();
//			mappingStrategy.setType(Output.class);
//
//			// Arrange column name as provided in below array.
//
//			String[] columns = new String[] { "usn", "term", "courseCode", "courseName", "internal", "external",
//					"total", "result" };
//			mappingStrategy.setColumnMapping(columns);
//			StatefulBeanToCsv<Output> beanToCsv = new StatefulBeanToCsvBuilder<Output>(writer)
//					.withMappingStrategy(mappingStrategy).build();

			writer = new FileWriter(outputDataFilePath);
			StatefulBeanToCsv<Output> beanToCsv = new StatefulBeanToCsvBuilder<Output>(writer).build();
			beanToCsv.write(outputs);

			System.out.println("#####################################################################");
			System.out.println("Writting the scraped data to " + outputDataFilePath.getName() + " file is done");
			System.out.println("🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 ");
			System.out.println("You can find the scraped data file in the below location :\n "
					+ outputDataFilePath.getAbsolutePath());
			System.out.println("#####################################################################");
			System.out.println();
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} finally {

			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param failedUsns
	 * @param term
	 */
	private static void writeFailedUsnToFile(List<String> failedUsns) {
		Writer writer = null;

		try {
			File outputDataFilePath = null;
			if ("Linux".equalsIgnoreCase(os)) {
				outputDataFilePath = new File("output/" + fileName + "_failedusn.csv");
			} else {
				outputDataFilePath = new File("output\\" + fileName + "_failedusn.csv");
			}
			System.out.println("#####################################################################");
			System.out.println("Writting the failed USN(s) to " + outputDataFilePath.getName() + " file");
			System.out.println("#####################################################################");
			System.out.println();
			writer = new FileWriter(outputDataFilePath);
			CSVWriter csvWriter = new CSVWriter(writer);
			Object[] objs = failedUsns.toArray();
			String[] usns = Arrays.copyOf(objs, objs.length, String[].class);
			csvWriter.writeNext(usns);
			csvWriter.close();
			System.out.println("#####################################################################");
			System.out
					.println("Writting the failed USN(s) to " + outputDataFilePath.getAbsolutePath() + " file is done");
			System.out.println("You can find the failed USN(s) file in the below location \n : " + outputDataFilePath);
			System.out.println("#####################################################################");
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static List<ImgInput> loadImgLink(String filePath) {
		System.out.println("#####################################################################");
		System.out.println("Uploading the Data from the file '" + filePath + "'");
		System.out.println("#####################################################################");
		System.out.println();
		File file = new File(filePath);

		if (file.getName() != null && !file.getName().isEmpty())
			fileName = file.getName().trim().split("\\.")[0];
		FileReader fileReader = null;
		List<ImgInput> beans = null;
		try {

			fileReader = new FileReader(file);
			beans = new CsvToBeanBuilder<ImgInput>(fileReader).withType(ImgInput.class).build().parse();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return beans;
	}

	/**
	 * @param outputs
	 */
	public static void writeImgData(List<ImgOutput> outputs, List<String> failedUsns) {

		if (!outputs.isEmpty())
			writeImgToFile(outputs);
		if (!failedUsns.isEmpty())
			writeFailedUsnToFile(failedUsns);
	}

	/**
	 * @param outputs
	 */
	private static void writeImgToFile(List<ImgOutput> outputs) {

		try {
			outputs.forEach(output -> {
				File outputDataFilePath = null;
				System.out.println("OS in write data to file: " + os);
				if ("Linux".equalsIgnoreCase(os)) {
					outputDataFilePath = new File("output/images/" + output.getUsn() + ".jpg");
				} else {
					outputDataFilePath = new File("output\\images\\" + output.getUsn() + ".jpg");
				}
				System.out.println("#####################################################################");
				System.out.println("Writting the scraped data to " + outputDataFilePath.getName() + " file.");
				System.out.println("#####################################################################");
				System.out.println();
				try (FileOutputStream fos = new FileOutputStream(outputDataFilePath.getAbsolutePath())) {
					fos.write(output.getImage());

				} catch (IOException e) {
					System.out.println(e.getMessage());
				}

				System.out.println("#####################################################################");
				System.out.println("Writting the scraped data to " + outputDataFilePath.getName() + " file is done");
				System.out.println("🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 ");
				System.out.println("You can find the scraped data file in the below location :\n "
						+ outputDataFilePath.getAbsolutePath());
				System.out.println("#####################################################################");
				System.out.println();
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.exit(0);
		}
	}

}
