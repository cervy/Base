

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * TCP 

 */
public class Server {
	public static void main(String[] args) {
		try {
			
			ServerSocket serverSocket=new ServerSocket(8888);
			Socket socket=null;
			
			int count=0;
			
			//循环监听等待客户端的连接
			while(true){
				
				socket=serverSocket.accept();
				
				ServerThread serverThread=new ServerThread(socket);
				serverThread.start();//客户端接收与回复
				
				count++;//统计客户端的数量
				
				InetAddress address=socket.getInetAddress();
				//System.out.println("当前客户端的IP："+address.getHostAddress());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
