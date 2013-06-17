package com.example.dcu.mina.udpserver.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.example.dcu.mina.udpserver.bean.NSFDataBean;

public class IOProtocolCodecFactory extends DemuxingProtocolCodecFactory{
	
	public IOProtocolCodecFactory(){
		super.addMessageDecoder(IODecoder.class);
		super.addMessageEncoder(NSFDataBean.class, IOEncoder.class);
	}

}
