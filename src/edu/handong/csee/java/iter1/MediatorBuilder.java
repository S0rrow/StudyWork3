package edu.handong.csee.java.iter1;

public class MediatorBuilder {
	String username;
	String theme;
	Connectivity connection;
	CalendarDataManager data;
	
	public MediatorBuilder setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public MediatorBuilder setTheme(String theme) {
		this.theme = theme;
		return this;
	}
	
	
	public MediatorBuilder setConnectivity(Connectivity connection) {
		this.connection = connection;
		return this;
	}
	
	public MediatorBuilder setCDM(CalendarDataManager data) {
		this.data = data;
		return this;
	}
	
	public Mediator build() {
		return new Mediator(username, theme, connection, data);
	}
}
