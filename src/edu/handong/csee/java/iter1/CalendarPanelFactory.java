package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

public class CalendarPanelFactory {
	private Connectivity connection;
	private String username;
	private CalendarDataManager data;
	private JFrame frame;
	private Mediator md;
	private Reactor ed;
	public CalendarPanelFactory(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame, Mediator mainMD, Reactor ed) {
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
			return new CalendarPanelInverse(username, connection, data, frame, md, ed);
		}
		else {
			return new CalendarPanelDefault(username, connection, data, frame, md, ed);
		}
	}
}
