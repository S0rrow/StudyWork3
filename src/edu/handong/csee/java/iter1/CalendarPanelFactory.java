package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

public class CalendarPanelFactory {
	private Connectivity connection;
	private String username;
	private CalendarDataManager data;
	private JFrame frame;
<<<<<<< HEAD
	private Reactor ed;
	public CalendarPanelFactory(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame, Reactor ed) {
=======
	private Mediator md;
	public CalendarPanelFactory(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame, Mediator mainMD) {
>>>>>>> c7bb816ed74ca17ec4e5eda5ffdc73c339fb3aad
		super();
		this.ed= ed;
		connection = mainConnection;
		data = cdm;
		this.frame = frame;
		username = userName;
		md = mainMD;
	}
	
	public CalendarPanelInstance makePanel(String theme) {
		if(theme.equals("inverse")) {
<<<<<<< HEAD
			return new CalendarPanelInverse(username, connection, data, frame,ed);
		}
		else {
			return new CalendarPanelDefault(username, connection, data, frame,ed);
=======
			return new CalendarPanelInverse(username, connection, data, frame, md);
		}
		else {
			return new CalendarPanelDefault(username, connection, data, frame, md);
>>>>>>> c7bb816ed74ca17ec4e5eda5ffdc73c339fb3aad
		}
	}
}
