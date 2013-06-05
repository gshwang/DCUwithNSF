package com.example.dcu.network.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.example.dcu.network.SocketsActivity;

public class CommsThread extends Thread{
	
	private final Socket socket;
	private final InputStream inputStream;
	private final OutputStream outputStream;
	
	public CommsThread(Socket sock){
		socket = sock;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		
		try {
			tmpIn = socket.getInputStream();
			tmpOut = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputStream = tmpIn;
		outputStream = tmpOut;
	}

	@Override
	public void run() {
		//buffer store for the stream
		byte[] buffer = new byte[1024];
		
		//bytes returned form read()
		int bytes;
		
		//keep listening to the InputStream until an
		//exception occurs
		while(true){
			try{				
				bytes = inputStream.read(buffer);
				SocketsActivity.UIupdater.obtainMessage(0, bytes, -1, buffer).sendToTarget();
			}catch(IOException e){
				break;
			}
		}
		
	}
	
	public void write(byte[] bytes){
		try {
			outputStream.write(bytes);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void cancel(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}





