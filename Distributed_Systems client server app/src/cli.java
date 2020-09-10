import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.time.LocalTime;

public class cli {
	public static void main(String[] args) {
		
		try {
			//generating random UUIDs
			UUID ranId = UUID.randomUUID();
			String thanks, server_req_words;
			thanks = "Thank you for sending you message ";
			
			
			//Integers
			int number, server_req;
			
			//creating the client
			Socket s = new Socket("localhost", 5656);
			
			/*	//Read data or information from server
			Scanner server_int = new Scanner(s.getInputStream());//scans the data sent by the server
			server_req = server_int.nextInt();*/
			
			OUTER:
					//loopingConstructOne
					{
					//Read data or information from server
					Scanner server_int = new Scanner(s.getInputStream());//scans the data sent by the server
					server_req = server_int.nextInt();
					
					if (server_req >= 1 && server_req <= 4) {
						number = server_req;
						server_req_words = server_option_recognition(server_req);
						System.out.println("The server is requesting for "+ server_req_words +" \n");
						
						//Figures out which option to send to server
						option_recognition(s, number, thanks, ranId);
						}
					else {
						System.out.println("no requst from server");
						break OUTER; //begins the inner zone
					}
					
					INNER:
							//loopingConstructTwo
							{
								//User options specified
								System.out.println("Use the info on the list below to select a feature: \n");
								System.out.println("\t 1. Send toy identification details \n");
								System.out.println("\t 2. Send toy information \n");
								System.out.println("\t 3. Send toy manufacturer details \n");
								System.out.println("\t 4. Send all toy information \n");
								
								//User selects an option
								Scanner sc_user = new Scanner(System.in);
								number = sc_user.nextInt();
								
								//Figures out which option to send to server
								option_recognition(s, number, thanks, ranId);
								
								//Goes back to the top of the outer zone i.e. restarts outer zone
								continue OUTER;	
								}
					}
			
			
			s.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	//1st option
	public static String[] toyIdentification() {
		//This gives toy code and toy name
		//Strings
		String toy_code, toy_name;
		
		System.out.println("What is the toy code \n");
		Scanner code = new Scanner(System.in);
		toy_code = code.next();
		
		
		System.out.println("What is the toy name \n");
		Scanner name = new Scanner(System.in);
		toy_name = name.next();
		
		String ar[] = new String[2];
		ar[0] = toy_code;
		ar[1] = toy_name;
		
		return ar;
		//The end point
	}
	
	//2nd option
	public static String[] toyInformation() {
		//This gives the name, description, price, DOM and batch number
		//Strings
		String descript_info, toyName_info, timeString;
		
		//Integers
		int price_info, batch_info;
		
		System.out.println("What is the name of the toy \n");
		Scanner name_info = new Scanner(System.in);
		toyName_info = name_info.next();
		
		System.out.println("What is the decription of the toy \n");
		Scanner descript = new Scanner(System.in);
		descript_info = descript.next();
		
		System.out.println("What is the price of the toy \n");
		Scanner price = new Scanner(System.in);
		price_info = price.nextInt();
		
		System.out.println("What is the manufacture date in dd/mm/yyyy \n");
		//String dateFormat = "dd/MM/yyyy";
		Scanner date_info = new Scanner(System.in);
		//setDate(new SimpleDateFormat(dateFormat).parse(scanner.nextLine()));
		timeString = date_info.next();
		
		//convert date to string
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		//Date date = dateFormat.parse(timeString);
		
		System.out.println("What is the batch number \n");
		Scanner b_no = new Scanner(System.in);
		batch_info = b_no.nextInt();
		
		//convert date to string
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-mm-dd");
		
		String ti[] = new String[5];
		ti[0] = toyName_info;
		ti[1] = descript_info;
		ti[2] = Integer.toString(price_info);
		ti[3] = timeString;
		ti[4] = Integer.toString(batch_info);
		
		return ti;
		//The end point
	}
	
	//3rd option
	public static String[] toyManufacturer() {
		//company : name, street address, zip-code and country
		//Strings
		String compName_info, streetAdd_info, country_info;
		//Integers
		int zip_info;
		
		System.out.println("What is the company name \n");
		Scanner comp_name = new Scanner(System.in);
		compName_info = comp_name.next();
				
		System.out.println("What is the company street address \n");
		Scanner street_add = new Scanner(System.in);
		streetAdd_info = street_add.next();
		
		System.out.println("What is the company zip-code \n");
		Scanner zip = new Scanner(System.in);
		zip_info = zip.nextInt();
		
		System.out.println("What is the company's country \n");
		Scanner country = new Scanner(System.in);
		country_info = country.next();
		
		String tm[] = new String[4];
		tm[0] = compName_info;
		tm[1] = streetAdd_info;
		tm[2] = Integer.toString(zip_info);
		tm[3] = country_info;
		
		return tm;
		
		//The end point
	}
	
	public static void option_recognition(Socket s, int number, String thanks, UUID ranId) {
		try {
			//Program recognizes the option
			if (number == 1) {
				//toyIdentification();
				String[] ar = toyIdentification();
				//sending the data to the channel for the server
				PrintStream toyId = new PrintStream(s.getOutputStream());
				toyId.println(ar + " \n" + thanks + " \t" + ranId);
				//toyId.println(thanks + ranId);
				
				System.out.println("Toy identification details sent to the server. \n");
			}
			
			else if(number == 2) {
				//toyInformation();
				String[] ti = toyInformation();
				//sending the data to the channel for the server
				PrintStream toyInf = new PrintStream(s.getOutputStream());
				toyInf.println(ti + " \n" + thanks + " \t" + ranId);
				//toyInf.println(thanks + ranId);
				
				System.out.println("Toy information details sent to the server. \n");
			}
			
			else if(number == 3) {
				toyManufacturer();
				String[] tm = toyManufacturer();
				//sending the data to the channel for the server
				PrintStream toyMan = new PrintStream(s.getOutputStream());
				toyMan.println(tm + " \n" + thanks + " \t" + ranId);
				//toyMan.println(thanks + ranId);
				
				System.out.println("Toy manufacturer details sent to the server. \n");
			}
			
			else {
				//All the options executed at once
				//toyIdentification();
				String[] ar = toyIdentification();
				//sending the data to the channel for the server
				PrintStream toyId = new PrintStream(s.getOutputStream());
				toyId.println(ar + " \n" + thanks + " \t" + ranId);
				//toyId.println(thanks + ranId);
				System.out.println("Toy identification details sent to the server. \n");
				
				//toyInformation();
				String[] ti = toyInformation();
				//sending the data to the channel for the server
				PrintStream toyInf = new PrintStream(s.getOutputStream());
				toyInf.println(ti + " \n" + thanks + " \t" + ranId);
				//toyInf.println(thanks + ranId);
				System.out.println("Toy information details sent to the server. \n");
				
				//toyManufacturer();
				String[] tm = toyManufacturer();
				//sending the data to the channel for the server
				PrintStream toyMan = new PrintStream(s.getOutputStream());
				toyMan.println(tm + " \n" + thanks + " \t" + ranId);
				//toyId.println(thanks + ranId);
				System.out.println("Toy manufacturer details sent to the server. \n");
				
				System.out.println("Toy information sent as a whole to the server \n");
				
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	//A switch that allow the client to figure out which option the server has chosen
	public static String server_option_recognition(int server_req) {
		String req;
		switch (server_req) {
		case 1:
			req = "toy identification details";
			return req;
			
		case 2:
			req = "toy information";
			return req;

		case 3:
			req = "toy manufacturer details";
			return req;
			
		case 4:
			req = "all toy information";
			return req;
			
		default:
			req = "'The is an error knowing what the server wants'";
			return req;
			
		}
	}
}
