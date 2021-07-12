public class SocketController {
    private Socket socket = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    public static void create(String ip, int port) {
        socket = new Socket(ip, port);
        System.out.println("Connected to server ...");

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public static void send() {
        char type = 's'; // s for string
        int length = 29;
        String data = "This is a string of length 29";
        byte[] dataInBytes = data.getBytes(StandardCharsets.UTF_8);         
        //Sending data in TLV format        
        out.writeChar(type);
        out.writeInt(length);
        out.write(dataInBytes);
    }
}
