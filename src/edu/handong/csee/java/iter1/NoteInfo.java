package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

public class NoteInfo implements Note{
	
	private String filepath;
	
	NoteInfo(String filepath){
		super();
		this.filepath = filepath;
	}

	@Override
	public String load(String filepath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] parse(String filepath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void callback(JFrame scheduler) {
		// TODO Auto-generated method stub
		
	}
}
