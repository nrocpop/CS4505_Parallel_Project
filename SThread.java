import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SThread extends Thread 
{
	private Object [][] RTable; // routing table
	private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
   private BufferedReader in; // reader (for reading from the machine connected to)
	private String inputLine, outputLine, destination, addr; // communication strings
	private Socket ClientSocket; // socket for communicating with a destination
	private int ind; // index t in the routing table
	String Delimiter = "::";



	// Constructor
	SThread(Object [][] Table, Socket toClient, int index) throws IOException
	{
			out = new PrintWriter(toClient.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
			RTable = Table;
			ClientSocket = toClient;
			ind = index;

	}
	
	// Run method (will run for each machine that connects to the ServerRouter)
	public void run()
	{
		System.out.print("Running Sthread");
		try
		{
			System.out.print("Getting s Array");
		String[] s = in.readLine().split(Delimiter);//check user type. 0 is client, 1 is server
			System.out.print("S Resolved");

			if(Integer.parseInt(s[0]) == 0){
				for (int i = 0; i < RTable.length; i++) {
					if(RTable[i][0].equals(s[1])){
						System.out.println("Found Server at " + RTable[i][0]);
						out.println(RTable[i][1]);
					}
				}
			}
			else{
				System.out.println("Writing server to router table" + s[1] +"::" + s[2]);
				RTable[ind][0] = s[1];//server ip address
				RTable[ind][1] = s[2];//server port
				System.out.println(RTable[ind][0].toString());
				System.out.println(RTable[ind][1].toString());
			}
         }catch (IOException e){
			e.printStackTrace();
		}


	}
}