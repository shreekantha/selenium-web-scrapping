package org.vrushali.ws;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WSApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WSApplication.class, args);
	}

	public static String fileName = "";
	public static String identifier = "";
	public static final String os = System.getProperty("os.name");

	@Override
	public void run(String... args) throws Exception {

		setupToScrape(args);

	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 */
	private void setupToScrape(String... args) throws FileNotFoundException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("#####################################################################");
			System.out.println(" Your running the script in " + os + " operating system");
			System.out.println("#####################################################################");
			System.out.println();
			System.out.println("Please select the below options");
			System.out.println("1. Image Scraping");
			System.out.println("2. VTU Score Scraping");
			System.out.println("3. Others");
			String firstOption = sc.next();
			switch (firstOption) {

			case "1":

				Image.scrape(sc);
				break;
			case "2":

				Score.scrape(sc);
				break;

			case "3":
				SMS.scrape(sc);
				break;
				
			case "4":
				System.out.println("Hope you are doing good.");
				System.out.println("Goodbye....");
				System.exit(0);
				
			default:
				System.out.println("Soooory, You have chosen the wrong option");
				System.out.println("Goodbye....");
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			sc.close();
		}

	}
	
	
	private void ScrapeUniversities(String... args) {
		String url=args[0];
		if ("Linux".equalsIgnoreCase(WSApplication.os)) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		}
		
		
		
	}

}
