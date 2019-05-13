package edu.handong.csee.java.iter1;

public class Main {
	private static final Connectivity connection = new Connectivity();// singleton design pattern; sole "connection"
																		// class

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.test(main.getConnection());
	}

	public void run(Connectivity mainConnection) {
		InitialFrame lw = new InitialFrame(mainConnection);
		lw.initiateFrame();
	}

	public void test(Connectivity mainConnection) {
		CalendarDataManager data = new CalendarDataManager();
		new Scheduler(mainConnection, data);
		//new MemoCalendar();
	}

	public Connectivity getConnection() {
		return connection;
	}
}
