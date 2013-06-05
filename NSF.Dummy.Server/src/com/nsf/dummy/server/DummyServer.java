package com.nsf.dummy.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DummyServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket server =null;
		OutputStream out =null;
		InputStream in = null;
		Socket sock = null;
		try {
			server = new ServerSocket(500);
		
			System.out.println("Wating Connect .....");
		
			sock = server.accept();
		
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress()+"로 부터 접속했습니다.");
			
			out = sock.getOutputStream();
			in = sock.getInputStream();
			
			//PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			//BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			
			byte[] buffer = new byte[1024];
			int bytes;
			String rcvMsg = null;
			char c;
			StringBuffer strBuffer =  null;
			while(true){
				//strBuffer = new StringBuffer();
				//rcvMsg = "";
				//while((bytes=in.read())!=-1){
				//    c = (char)bytes;
				//    strBuffer.append(c);
				//    rcvMsg = strBuffer.toString();
				//}
				
				
				
				while((bytes=in.read(buffer, 0, buffer.length))!=-1){
					rcvMsg = new String(buffer);
					rcvMsg = rcvMsg.substring(0, bytes);
					System.out.println("Received Message: "+ rcvMsg);
					out.write(rcvMsg.getBytes());	
				}
				
				
				//out.flush();
			}
			//while((line=br.readLine())!=null){
			//	pw.println(line);
			//	pw.flush();
			//	System.out.println("Received Message: " + line);
			//
			//}  
			
			//pw.close();
			//br.close();
			//sock.close();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			in.close();
			out.close();
			sock.close();
			System.out.println("Closed Dummy Server");
		}

	}

}
