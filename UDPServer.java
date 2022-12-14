package ufcqx.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.io.PrintStream;
public class UDPServer extends Thread{
	
	private DatagramSocket socket;
	private boolean running = true;
	private byte[] buffer = new byte[256];
	private byte[] buf;
	private PrintStream out;
	private String msg;
	public UDPServer() {
		try {
			this.socket = new DatagramSocket(4446);
			System.out.println("Servidor UDP criado!");
		}catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(this.running) {
			DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
			
			try {
				this.socket.receive(packet);
				InetAddress address = packet.getAddress();
	            int port = packet.getPort();
	            packet = new DatagramPacket(this.buffer, this.buffer.length, address, port);
	            String received = new String(packet.getData(), 0, packet.getLength());
	            System.out.println("SEVER: " + received);
				sendPacketBack(socket,address,port,"Ola");
	            
	            if (received.startsWith("end")) {
	                this.running = false;
	                continue;
	            }
	            
	            //this.socket.send(packet);
	            
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.socket.close();
	}
	
	private void sendPacketBack(DatagramSocket socket, InetAddress address,int port2, String msg) {
		buf = msg.getBytes();
		DatagramPacket packet2 = new DatagramPacket(buf, buf.length, address, port2);
		try {
			socket.send(packet2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		new UDPServer().start();
	}
}
