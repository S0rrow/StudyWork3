package edu.handong.csee.java.iter1;

public class Mediator {
	String username;
	String theme;
	Connectivity connection;
	CalendarDataManager data;
	Mediator(String username, String theme, Connectivity connection, CalendarDataManager data){
		if(username != null) this.username = username;
		if(theme != null) this.theme = theme;
		this.connection = connection;
		this.data = data;
	}
	
	public void registrate() {
		new AccountRegistrationFrame(this);
	}
	
	public void signin() {
		new Scheduler(this);
	}
	
	public void superAccount() {
		new SuperAccountInfoFrame(this);
	}
	
	public void callBack() {
		InitialFrame frame = new InitialFrame(connection, data);
		frame.initiateFrame();
	}
}
