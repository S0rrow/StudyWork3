package edu.handong.csee.java.iter1;

public class Main {
	private static final Connectivity connection = new Connectivity();//singleton design pattern; sole "connection" class
	
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.run(main.getConnection());
	}
	
	public void run(Connectivity mainConnection) {
		InitialFrame lw = new InitialFrame(mainConnection);
		lw.initiateFrame();
	}
	
	public Connectivity getConnection() {
		return connection;
	}
}
