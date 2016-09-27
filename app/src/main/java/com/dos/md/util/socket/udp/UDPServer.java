
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPServer {
	public static void main(String[] args) throws IOException {
		
		DatagramSocket socket=new DatagramSocket(8800);
		
		byte[] data =new byte[1024];
		DatagramPacket packet=new DatagramPacket(data, data.length);
		
		socket.receive(packet);//此方法在接收到数据报之前会一直阻塞
		
		String info=new String(data, 0, packet.getLength());
		System.out.println("我是服务器，客户端说："+info);
		
		
		
		InetAddress address=packet.getAddress();
		int port=packet.getPort();
		byte[] data2="欢迎!".getBytes();
		
		DatagramPacket packet2=new DatagramPacket(data2, data2.length, address, port);
		
		
		socket.send(packet2);
		
		socket.close();
	}
}
