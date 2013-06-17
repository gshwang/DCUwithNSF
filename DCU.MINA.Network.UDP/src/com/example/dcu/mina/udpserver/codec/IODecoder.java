package com.example.dcu.mina.udpserver.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.example.dcu.mina.udpserver.bean.NSFDataBean;
import com.example.dcu.mina.udpserver.contatns.Constatns;

public class IODecoder extends MessageDecoderAdapter{

	private int stxPos;
	private Object dataLen ;

	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		boolean ok = this.messageComplete(session, in);
		if(ok) 
			return MessageDecoderResult.OK;
		
		return null;
	}

	private boolean messageComplete(IoSession session, IoBuffer in) {
		boolean h1OK = false;
		byte rVal;
		while(in.position() < in.limit()){
			if(!h1OK){
				rVal = in.get();
				if(rVal == Constatns.STX){
					h1OK = true;
					this.stxPos = in.position() - 1;
				}
				continue;
			}
		
		
			if(in.remaining() < 23) continue;
		
			// 메타정보로 실데이터 수신여부확인
			in.skip(4); 
			in.skip(14);
			in.skip(1);
		
			int size = in.getInt(); //get data size
		
			if(in.remaining() >= (size +1)){
				in.skip(size);
				rVal = in.get();
				if(rVal == Constatns.ETX){
					this.dataLen = size + 24;
					return true;
				}
				h1OK = false;
				in.position(this.stxPos);
			}else{
				return false;
			}
		
		}
		
		return false;
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		
		NSFDataBean nsfDataBean = new NSFDataBean();
		in.skip(1);
		nsfDataBean.setEventId(in.getString(4,Constatns.charsetDecoder));
		nsfDataBean.setSendDTime(in.getString(14,Constatns.charsetDecoder));
		nsfDataBean.setDataType(in.getChar());
		nsfDataBean.setDataLength(in.getInt());
		if(nsfDataBean.getDataType() == 'J')
		{
			nsfDataBean.setNsfJSONData(in.getString(nsfDataBean.getDataLength()
							, Constatns.charsetDecoder));
		}
		
		out.write(nsfDataBean);
		
		return MessageDecoderResult.OK;
	}



}
