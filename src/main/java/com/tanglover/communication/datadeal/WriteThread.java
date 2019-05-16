package com.tanglover.communication.datadeal;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class WriteThread implements Runnable {
	
	private Socket s;
	
	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}
	

	public WriteThread(Socket s) {
		super();
		this.s = s;
	}



	ObjectOutputStream oos = null;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	
	}

}
