package org.vrushali.ws;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@SpringBootApplication
public class WSApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WSApplication.class, args);

	}

	private static String fileName = "";
	private static String identifier = "";
	private static final String os = System.getProperty("os.name");

	@Override
	public void run(String... args) throws Exception {

		System.out.println("#####################################################################");
		System.out.println(" Your running the script in " + os + " operating system");
		System.out.println("#####################################################################");
		System.out.println();
		System.out.println("#####################################################################");
		System.out.println("Setting the web driver for the OS " + os);
		System.out.println("#####################################################################");
		System.out.println();

		// setting the web driver to the system property based on the OS
		if ("Linux".equalsIgnoreCase(os)) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		}

		// Loading the USNs from the csv file
		if (args != null && args.length > 0) {

			final String inputDataFilePath = args[0].trim();
			List<String> usns = loadUsns(inputDataFilePath);
			srcapeTheData(usns);
		} else {
			System.err.println("Iput Data file is missing ...............");
			System.exit(1);
		}

	}

	/**
	 * @return
	 * @throws FileNotFoundException
	 */
	private List<String> loadUsns(String filePath) throws FileNotFoundException {
		System.out.println("#####################################################################");
		System.out.println("Uploading the USNs from the file '" + filePath + "'");
		System.out.println("#####################################################################");
		System.out.println();
		List<String> usnList = new ArrayList<>();
		File file = new File(filePath);

		if (file.getName() != null && !file.getName().isEmpty())
			fileName = file.getName().trim().split("\\.")[0];
		FileReader fileReader = new FileReader(file);

		CSVReader csvReader = new CSVReaderBuilder(fileReader).build();
		try {
			List<String[]> all = csvReader.readAll();
			for (String[] row : all) {
				for (String c : row) {
					usnList.add(c);
				}
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
		return usnList;
	}
	
	/**
	 * @throws InterruptedException
	 * 
	 */
	private void srcapeTheData(List<String> usns) throws InterruptedException {
		System.out.println("#####################################################################");
		System.out.println("UE Score Scraping is started...");
		System.out.println("Enjoy Scraping 😊😊😊 ");
		System.out.println("#####################################################################");
		System.out.println();
		Thread.sleep(4000);
		
		List<String> failedUsns = new ArrayList<>();
		List<Output> outputs = new ArrayList<>();
		
		for (String usn : usns) {
			WebDriver driver = null;
			try {

				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get("https://results.vtu.ac.in/_CBCS/index.php");
				driver.findElement(By.xpath("//*[@id=\"raj\"]/div[1]/div/input")).sendKeys(usn);
				driver.findElement(By.xpath("//*[@id=\"raj\"]/div[2]/div[1]/input")).sendKeys("");
				Thread.sleep(10000);
				driver.findElement(By.id("submit")).click();

				identifier = driver.findElement(By.xpath("//*[@id=\"dataPrint\"]/div[2]/div/div/div[1]/b[2]"))
						.getText();

				List<WebElement> termElements = driver.findElements(By.tagName("b"));
				List<String> terms = new ArrayList<>();

				for (WebElement termElement : termElements) {
					String rawTerm = termElement.getText();
					if (rawTerm.contains("Semester")) {
						terms.add(rawTerm.split(":")[1].trim());
					}
				}

				int termCount = 0;
				List<WebElement> divTableBodies = driver.findElements(By.className("divTableBody"));
				for (WebElement tableBody : divTableBodies) {
					List<WebElement> rows = tableBody.findElements(By.className("divTableRow"));
					rows.remove(0);
					for (WebElement e : rows) {
						List<WebElement> cells = e.findElements(By.className("divTableCell"));
						List<String> cellValues = new ArrayList<>();
						for (WebElement c : cells) {
							cellValues.add(c.getText());
						}
						outputs.add(new Output(usn, terms.get(termCount), cellValues.get(0), cellValues.get(1),
								cellValues.get(2), cellValues.get(3), cellValues.get(4), cellValues.get(5),
								identifier));
					}
					termCount++;
				}
				driver.close();
			} catch (Exception e) {
				e.printStackTrace();
				driver.close();
				failedUsns.add(usn);
			} finally {
				driver.quit();
			}
		}
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
	private void writeDataToFile(List<Output> outputs) {
		Writer writer = null;

		try {
			File outputDataFilePath = null;

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
	private void writeFailedUsnToFile(List<String> failedUsns) {
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
			System.out.println("usns:" + usns);
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
	
	

}
