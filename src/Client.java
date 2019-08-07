
import java.io.*;
import java.net.*;

//Tasks tommrow het these two to talk to ech other on docker 

public class Client implements Runnable {

	String sentence;
	String modifiedSentence;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	DataInputStream inFromServer2;
	Socket clientSocket;
	Boolean closing = false;

	public Client(String address, int port) {
		try {
			clientSocket = new Socket(address, port);
			System.out.println("Connected to server success");
			inFromServer2 = new DataInputStream(clientSocket.getInputStream());
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			outToServer = new DataOutputStream(clientSocket.getOutputStream());

			outToServer.writeUTF("Hello");
			outToServer.flush();
			System.out.println("writeBytes sent");
			modifiedSentence = inFromServer2.readUTF();
			System.out.println("FROM SERVER: " + modifiedSentence);
			System.out.println("Asking sever if it can count");
			outToServer.writeUTF("hello from the client can you count to 10");
			
			while (closing == false) {
				
				if (modifiedSentence.contains("easy")) {
					outToServer.writeUTF("bye");
					System.out.println("saying bye to server");
					closing = true;
				} 
				else {
					modifiedSentence = inFromServer2.readUTF();
					System.out.println("FROM SERVER: " + modifiedSentence);
				}
			}
			System.out.print("closing socket");
			clientSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
