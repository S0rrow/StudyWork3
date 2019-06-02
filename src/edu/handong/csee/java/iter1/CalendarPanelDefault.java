package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

public class CalendarPanelDefault extends CalendarPanelInstance {

	public CalendarPanelDefault(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame, Mediator md) {
		super(userName, mainConnection, cdm, frame, md);
		curTheme = "default";
		themeBut.setText("inverse");;
	}

}
