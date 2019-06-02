package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

public class CalendarPanelDefault extends CalendarPanelInstance {

	public CalendarPanelDefault(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame,Reactor ed) {
		super(userName, mainConnection, cdm, frame, ed);
		curTheme = "default";
		themeBut.setText("inverse");;
	}

}
