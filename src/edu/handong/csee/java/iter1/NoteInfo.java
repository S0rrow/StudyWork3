package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class NoteInfo extends JFrame implements Note{
	
	NoteInfo(String filepath){
		super();
		load(filepath);
		
	}

	@Override
	public String load(String filepath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] parse(String filepath) {
		// TODO Auto-generated method stub
		String[] meta = filepath.split(":");
		return meta;
	}

	@Override
	public void callback() {
		// TODO Auto-generated method stub
		new MemoCalendar();
		this.dispose();
	}
}
