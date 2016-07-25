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
    public void ReceiveServerSocketData() {
        DatagramSocket socket;//UDP
        try {
            socket = new DatagramSocket(554);
            byte data[] = new byte[4 * 1024];
            //参数一:要接受的data 参数二：data的长度
            DatagramPacket packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            //把接收到的data转换为String字符串
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


    protected void connectServerWithUDPSocket() {

        DatagramSocket socket;
        try {
            //创建DatagramSocket对象并指定一个端口号，注意，如果客户端需要接收服务器的返回数据,
            //还需要使用这个端口号来receive，所以一定要记住
            socket = new DatagramSocket(554);
            //使用InetAddress(Inet4Address).getByName把IP地址转换为网络地址
            InetAddress serverAddress = InetAddress.getByName("10.0.1.40");
            //Inet4Address serverAddress = (Inet4Address) Inet4Address.getByName("192.168.1.32");
            String str = "[2143213;21343fjks;213]";
            byte data[] = str.getBytes();//把字符串str字符串转换为字节数组
            //创建一个DatagramPacket对象，用于发送数据。
            //参数一：要发送的数据  参数二：数据的长度  参数三：服务端的网络地址  参数四：服务器端端口号
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 10025);
            socket.send(packet);//把数据发送到服务端。
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    protected void connectServerWithTCPSocket() {

        Socket socket = null;
        try {
            try {
                socket = new Socket("192.168.1.32", 554);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = new FileInputStream("filePath");
            // 获取Socket的OutputStream对象用于发送数据。
            OutputStream outputStream = socket.getOutputStream();
            // 创建一个byte类型的buffer字节数组，用于存放读取的本地文件
            byte buffer[] = new byte[4 * 1024];
            int temp = 0;
            // 循环读取文件
            while ((temp = inputStream.read(buffer)) != -1) {

                outputStream.write(buffer, 0, temp);
            }
            // 发送读取的数据到服务端 DataOutputStream writer = new DataOutputStream(socket.getOutputStream());writer.writeUTF("嘿嘿，你好啊，服务器..");  // 写一个UTF-8的信息
            outputStream.flush();

            /** 或创建一个报文，使用BufferedWriter写入,看你的需求 **/
//          String socketData = "[2143213;21343fjks;213]";
//          BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
//                  socket.getOutputStream()));
//          writer.write(socketData.replace("\n", " ") + "\n");
//          writer.flush();
            /************************************************/
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void serverReceviedByTcp() {

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
