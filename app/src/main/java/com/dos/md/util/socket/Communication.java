package com.dos.md.util.socket;

import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by DOS on 2016/7/20.
 * TCP使用的是流的方式发送，UDP是以包的形式发送
 */
public class Communication {
    
    protected void connectUDP() {

        DatagramSocket socket;
        try {
            socket = new DatagramSocket(554);
            //使用InetAddress(Inet4Address).getByName把IP地址转换为网络地址
            InetAddress serverAddress = InetAddress.getByName("10.0.1.40");
            String str = "[2143213;21343fjks;213]";
            byte data[] = str.getBytes();
           
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 10025);
            
            socket.send(packet);
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void receiveUDPData() {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(554);
            
            byte data[] = new byte[4 * 1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            
            socket.receive(packet);
            
            String result = new String(packet.getData(), packet.getOffset(),
                    packet.getLength());
            socket.close();
            Log.e("result", result);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    protected void connectTCP() {

        Socket socket = null;
        try {
            try {
                socket = new Socket("192.168.1.32", 554);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = new FileInputStream("filePath");//各类型流
            
            OutputStream outputStream = socket.getOutputStream();
            
            byte buffer[] = new byte[4 * 1024];
            int temp = 0;
            while ((temp = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, temp);
            }
            // 发送读取的数据到服务端 DataOutputStream writer = new DataOutputStream(socket.getOutputStream());writer.writeUTF(".");  // 写一个UTF-8的信息
            outputStream.flush();

            /** 或创建一个报文，使用BufferedWriter写入 **/
//          String socketData = "[2143213;21343fjks;213]";
//          BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
//                  socket.getOutputStream()));
//          writer.write(socketData.replace("\n", " ") + "\n");
//          writer.flush();
        
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void recevieTCP() {

        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(554);
            // 调用ServerSocket的accept()方法，接受客户端所发送的请求，
            // 如果客户端没有发送数据，那么该线程就停滞不继续
            Socket socket = serverSocket.accept();
            
            InputStream inputStream = socket.getInputStream();
            
            byte buffer[] = new byte[1024 * 4];
            int temp = 0;
            StringBuilder builder = new StringBuilder();
            // 从InputStream当中读取客户端所发送的数据
            while ((temp = inputStream.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, temp));
            }

            serverSocket.close();
            Log.e("datareceivedbyTcp", builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
