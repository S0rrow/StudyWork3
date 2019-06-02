package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

public class CalendarPanelDefault extends CalendarPanelInstance {

<<<<<<< HEAD
	public CalendarPanelDefault(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame,Reactor ed) {
		super(userName, mainConnection, cdm, frame, ed);
=======
	public CalendarPanelDefault(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame, Mediator md) {
		super(userName, mainConnection, cdm, frame, md);
>>>>>>> c7bb816ed74ca17ec4e5eda5ffdc73c339fb3aad
		curTheme = "default";
		themeBut.setText("inverse");;
	}

}
