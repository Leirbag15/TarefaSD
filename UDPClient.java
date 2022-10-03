package ufcqx.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
	
	private DatagramSocket socket;
    private InetAddress address;
    private byte[] buffer = new byte[256];
    private byte[] buf;

    public UDPClient() {
      DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
        try {
			socket = new DatagramSocket();
			address = InetAddress.getByName("localhost");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public String sendMsg(String msg) {
        buf = msg.getBytes();
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, address, 4446);
        try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        packet = new DatagramPacket(buf, buf.length);
        try {
        socket.receive(packet);
        }catch (IOException e){
          e.printStackTrace();
        }
        String received = new String(packet.getData(), 0, packet.getLength());
        
        return received;
    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) {
		UDPClient client = new UDPClient();
		System.out.print(client.sendMsg("Oi, tudo bom!"));
	}
}
