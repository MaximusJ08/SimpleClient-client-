public class Main_Client {

	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 23);
		Thread chatClientThread = new Thread(client);
		chatClientThread.start();
	}
}