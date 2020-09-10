import java.io.*;
import java.net.*;
import java.util.*;

public class ser {
	public static void main(String args[]) {
		try {
			//Strings
			
			
			//Integers
			int length, counter, req;
			counter = 0; 
			req = 0;
			
			//creating server
			
			ServerSocket server = new ServerSocket(5656);
			Socket s = server.accept(); //this show that the client was able to make connection with the port

			//Making a req before data input
			makeRequest(s, req);
			//ensuring that the server receives any incoming traffic from client
			Scanner sc = new Scanner(s.getInputStream());
			
			//finding the length of the input
			readLength(sc);
			length = Integer.parseInt(readLength(sc));
			if (length > 0) {
				String[] info = new String[length];
				info = (sc.next().split("\n"));
				System.out.println("This is the data sent by the client to the server \n");
				System.out.println(info);
				
			}else {
				System.out.println("There was no data sent to the server");
			}
			
			/* //Passing data back to client i.e. The request from server
			PrintStream passData = new PrintStream(s.getOutputStream());
			req = 2; //this shows that no option has been chosen
			passData.println(req);*/
			
			System.out.println("System has enter partial sleep \n");
			for (int i = 0; i < 10; i++) {
				System.out.println(i);	
				try {
					Thread.sleep(1000);//1 second per no. i.e. 10 seconds total
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
			//Confirms end of sleep
			System.out.println("System sleep done \n");
			
			req = 2;//chooses option 2
			while (counter < 1) {
				makeRequest(s, req);
				//update expression
				counter = counter + 1;
				System.out.println("This is a request counter no. "+ counter + "\n");
			}
			
			
			server.close();
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//This method makes the request to the client
	public static void makeRequest(Socket s, int req) {
		try {
		//Passing data back to client i.e. The request from server
		PrintStream passData = new PrintStream(s.getOutputStream());
		passData.println(req);
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//This method reads the length of the data in order to make an array its size
	public static String readLength(Scanner sc) {
		int num = 0;
		String numToString;
		if(sc != null) {
			while(sc.hasNext()) {
			//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sc), "utf-8"), 8);
			num = num + 1;
			}
			System.out.println("There are "+ num + "elements in data \n");
			numToString = String.valueOf(num);
			return numToString;
		}else {
			return "There was an error counting the data";
		}
	}
}
