package com.example.dcu.mina.udpserver;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.example.dcu.mina.udpserver.bean.NSFDataBean;

public class UDPHandler extends IoHandlerAdapter{

	private AtomicInteger nbReceived = new AtomicInteger(0);
	private int MAX_RECEIVED = 100000;
	private static long t0;

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		session.close(true);
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {		
		if(message instanceof NSFDataBean)
		{
			NSFDataBean beandata = (NSFDataBean)message;
			beandata.toString();
		}else{
			throw new Exception("This is no NSFDataBean Object!!!");
		}
		session.write(message);
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("Session closed.....");
		System.out.println("Nb message received : " + nbReceived.get());
		nbReceived.set(0);
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("Session created....");
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("Session idle....");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("Session opened...");
	}
	
	
	
}
