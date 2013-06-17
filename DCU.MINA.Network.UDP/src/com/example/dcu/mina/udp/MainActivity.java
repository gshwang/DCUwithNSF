package com.example.dcu.mina.udp;

import java.io.IOException;

import org.apache.mina.core.service.IoHandlerAdapter;

import com.example.dcu.mina.udp.R;
import com.example.dcu.mina.udpserver.UDPHandler;
import com.example.dcu.mina.udpserver.UDPServer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	
	UDPServer server = null;
	IoHandlerAdapter handler = null;

	private class CreateUDPServerdTask extends AsyncTask<Void, Integer, Void>{
		@Override
		protected Void doInBackground(Void... params) {
	        handler = new UDPHandler();
	        try {
				server = new UDPServer(handler);
			} catch (IOException e) {
				//e.printStackTrace();
			}			
			return null;
		}
	}
	
	private class StopUDPServerTask extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			server.unbindServer();
			return null;
		}
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		new CreateUDPServerdTask().execute();
	}

	@Override
	protected void onPause() {
		super.onPause();
		new StopUDPServerTask().execute();
	}
}
