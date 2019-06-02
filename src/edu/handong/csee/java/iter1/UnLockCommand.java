package edu.handong.csee.java.iter1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnLockCommand implements ActionListener {
	private Scheduler Sc;
	public UnLockCommand(Scheduler Sc) {
		this.Sc=Sc;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Sc.unlock();	
		Sc.ed.bottomInfo.setText("UNLOCK");
	}
	
}
