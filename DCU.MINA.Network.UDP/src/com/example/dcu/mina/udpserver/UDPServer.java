package com.example.dcu.mina.udpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

import com.example.dcu.mina.udpserver.codec.IOProtocolCodecFactory;

public class UDPServer {


	NioDatagramAcceptor acceptor = null;
	DatagramSessionConfig config = null;
	private int PORT = 9999;
	IOProtocolCodecFactory codecFactory = null;
	
	public UDPServer(IoHandlerAdapter handler) throws IOException
	{
		codecFactory = new IOProtocolCodecFactory();
		acceptor = new NioDatagramAcceptor();
		acceptor.setHandler(handler);
		acceptor.getFilterChain().addLast("protocolFilter"
									, new ProtocolCodecFilter(codecFactory));
		config = acceptor.getSessionConfig();
		acceptor.bind(new InetSocketAddress(PORT ));
		
		System.out.println("Server Start....");
		
	}
	
	
	/**
	 * 
	 */
	public void unbindServer(){
		acceptor.unbind();
	}

	
}
