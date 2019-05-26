package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class NoteEdit extends JFrame implements Note{
	NoteEdit(String filepath){
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
		return null;
	}

	@Override
	public void callback() {
		// TODO Auto-generated method stub
		//new MemoCalendar();
		this.dispose();
	}
	
	

}
