import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	public static void main(String[] args) {
		try {
			
			Socket socket=new Socket("localhost", 8888);
			
			OutputStream os=socket.getOutputStream
				
			PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
			pw.write("用户名：alice;密码：789");
			pw.flush();
			socket.shutdownOutput();
			//3.获取输入流，并读取服务器端的响应信息
			InputStream is=socket.getInputStream();
			
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String info=null;
			while((info=br.readLine())!=null){
				System.out.println("服务器说："+info);
			}
			
			br.close();
			is.close();
			pw.close();
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
