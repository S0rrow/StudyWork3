package edu.handong.csee.java.iter1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LockCommand implements ActionListener{
	private Scheduler Sc;
	public LockCommand(Scheduler Sc) {
		this.Sc=Sc;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Sc.lock();
		Sc.ed.bottomInfo.setText("LOCK");
		
		
	}

	
}
