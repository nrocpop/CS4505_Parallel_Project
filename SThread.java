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
		try
		{
		String[] s = in.readLine().split(Delimiter);//check user type. 0 is client, 1 is server
			if(Integer.parseInt(s[0]) == 0){
				for (int i = 0; i < RTable.length; i++) {
					if(RTable[i][0].equals(s[1])){
						out.write((int)RTable[i][1]);
					}
				}
			}
			else{
				RTable[ind][0] = ClientSocket.getInetAddress().getHostAddress();
			}
         }catch (IOException e){
			e.printStackTrace();
		}
	}
}