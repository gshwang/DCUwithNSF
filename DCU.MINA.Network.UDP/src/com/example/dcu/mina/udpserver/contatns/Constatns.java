package com.example.dcu.mina.udpserver.contatns;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class Constatns {
	public static final byte STX = 2;
	public static final byte ETX = 3;
	
	// charset
	public static CharsetDecoder charsetDecoder = null;
	static{
		Charset charset = Charset.forName("ISO-8859-1");
		charsetDecoder = charset.newDecoder();
	}
}
