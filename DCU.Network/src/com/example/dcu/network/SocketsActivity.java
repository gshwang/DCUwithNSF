package com.example.dcu.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.dcu.network.thread.CommsThread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SocketsActivity extends Activity {

	static final String NICKNAME = "GSHWANG";
	InetAddress serverAddress;
	Socket socket;
	
	//all the views
	public static TextView txtMessagesReceived;
	EditText txtMessage;
	
	//thread for communicating on the socket
	CommsThread commsThread;
	
	public static Handler UIupdater = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			int numOfByteReceived = msg.arg1;
			byte[] buffer = (byte[])msg.obj;
			
			String strReceived = new String(buffer);
			strReceived = strReceived.substring(0, numOfByteReceived);
			txtMessagesReceived.setText(txtMessagesReceived.getText().toString() 
							+ "\n"
							+ strReceived);
		};
		
	};
	
	private class CreateCommThreadTask extends AsyncTask<Void, Integer, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			try {
				serverAddress = InetAddress.getByName("192.168.0.52");
			
				socket = new Socket(serverAddress, 500);
				commsThread = new CommsThread(socket);
				commsThread.start();
				sendToServer(NICKNAME);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	private class WriteToServerTask extends AsyncTask<byte[], Void, Void>{
		@Override
		protected Void doInBackground(byte[]... data) {
			commsThread.write(data[0]);
			return null;
		}
	}
	
	
	private class CloseSocketTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	
	private void sendToServer(String message) {
			byte[] theByteArray = message.getBytes();
			new WriteToServerTask().execute(theByteArray);
			
		}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtMessage = (EditText)findViewById(R.id.txtMessage);
		txtMessagesReceived = (TextView)findViewById(R.id.txtMessagesReceived);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickSend(View view){
		sendToServer(txtMessage.getText().toString());
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new CreateCommThreadTask().execute();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		new CloseSocketTask().execute();
	}

}
