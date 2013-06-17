package com.example.dcu.mina.udpserver.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.example.dcu.mina.udpserver.bean.NSFDataBean;

public class IOEncoder implements MessageEncoder<NSFDataBean>{

	@Override
	public void encode(IoSession arg0, NSFDataBean arg1,
			ProtocolEncoderOutput arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
