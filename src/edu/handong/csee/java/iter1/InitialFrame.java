package edu.handong.csee.java.iter1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class InitialFrame {
	private String username;
	private String password;
	private Connectivity connection;
	InitialFrame(Connectivity mainConnection){
		connection = mainConnection;
	}
	
	public void initiateFrame() {
		//새로운 프레임 생성.
		JFrame mainFrame = new JFrame("Login");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocation(100,100);
		mainFrame.setPreferredSize(new Dimension(400,150));
		
		JPanel mainContainer = new JPanel(new GridLayout(1,2));
		mainContainer.setPreferredSize(new Dimension(400, 150));
		
		//로그인시 아이디와 비밀번호를 입력해넣을 텍스트 필드와 패널.
		JPanel inputContainer = new JPanel(new GridLayout(2,2));
		inputContainer.setPreferredSize(new Dimension(200, 200));
		inputContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel lblUsername = new JLabel("User ID:");
		JTextField txtUser = new JTextField();
		JLabel lblPassword = new JLabel("Password:");
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setEchoChar('?');
		
		inputContainer.add(lblUsername);
		inputContainer.add(txtUser);
		inputContainer.add(lblPassword);
		inputContainer.add(txtPassword);
		
		//입력받은 아이디와 비밀번호를 적용할 버튼과 패널.
		JPanel buttonContainer = new JPanel(new GridLayout(2,1));
		JButton callSignIn = new JButton("Sign In");
		JButton callSignUp = new JButton("Sign Up");
		//액션 리스너 적용
		callSignIn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		callSignIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				username = txtUser.getText().trim();
				password = new String(txtPassword.getPassword()).trim();
				if(username.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(null, "given username or password is empty", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//System.out.println("given username:"+username+", given password:"+password);
					//System.out.println("trySignIn method initiated...");
					trySignIn(mainFrame);
				}
			}
		});
		callSignUp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		callSignUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AccountRegistrationFrame(connection);
				mainFrame.dispose();
			}
		});
		
		buttonContainer.add(callSignIn);
		buttonContainer.add(callSignUp);
		//메인 컨테이너에 패널들 삽입
		mainContainer.add(inputContainer);
		mainContainer.add(buttonContainer);
		//메인 프레임에 메인 컨테이너 삽입
		mainFrame.add(mainContainer, BorderLayout.CENTER);
		
		//프레임을 보이게 바꿈
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	private void trySignIn(JFrame mainFrame) {
		connection.Connect();
		//System.out.println("trySignIn method connected db successfully");
		String dbquery = "SELECT * from account_info;";
		connection.state(dbquery);
		//System.out.println("Query given:"+dbquery);
		if(connection.checkSignIn(username, password)) {
			//System.out.println("Conditions for signing in satisfied.");
			//System.out.println("calling new Frame...");
			if(connection.getElement(username, "super").equals("NO")) {
				//new AccountInfoFrame(username);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new MemoCalendar();
					}
				});
				mainFrame.dispose();
			}
			else if(connection.getElement(username, "super").equals("YES")) {
				new SuperAccountInfoFrame(username, connection);
				mainFrame.dispose();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Sign in failed! Check whether username or password is valid!", "SignInFailiureError", JOptionPane.INFORMATION_MESSAGE);
		}
		//System.out.println("trySignIn method finished.");
		connection.close();
	}
}
