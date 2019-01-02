package edu.handong.csee.java.sw3;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class AccountInfoFrame extends JFrame{
	private Connectivity connection = new Connectivity();

	AccountInfoFrame(String username) {
		//System.out.println("Initiated new AccountInfoFrame.");
		setTitle("Signed In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100,100);
		connection.Connect();
		JPanel mainContainer = new JPanel(new GridLayout(7, 1));
		mainContainer.setPreferredSize(new Dimension(600, 600));
		
		//username
		JPanel usernameContainer = new JPanel(new GridLayout(1,2));
		JLabel lblUsername = new JLabel("Username: ");
		JLabel lbldbUsername = new JLabel(username);
		usernameContainer.add(lblUsername);
		usernameContainer.add(lbldbUsername);
		
		//gender
		JPanel genderContainer = new JPanel(new GridLayout(1,2));
		JLabel lblGender = new JLabel("Gender:");
		JLabel lbldbGender = new JLabel(connection.getElement(username, "gender"));
		genderContainer.add(lblGender);
		genderContainer.add(lbldbGender);
		
		//email
		JPanel emailContainer = new JPanel(new GridLayout(1,2));
		JLabel lblEmail = new JLabel("Email: ");
		JLabel lbldbEmail = new JLabel(connection.getElement(username, "email"));
		emailContainer.add(lblEmail);
		emailContainer.add(lbldbEmail);
		
		//department
		JPanel departmentContainer = new JPanel(new GridLayout(1,2));
		JLabel lblDepartment = new JLabel("Department: ");
		JLabel lbldbDepartment = new JLabel(connection.getElement(username, "department"));
		departmentContainer.add(lblDepartment);
		departmentContainer.add(lbldbDepartment);
		
		//student id
		JPanel studentidContainer = new JPanel(new GridLayout(1,2));
		JLabel lblStudentid = new JLabel("Student ID:");
		JLabel lbldbStudentid = new JLabel(connection.getElement(username, "studentid"));
		studentidContainer.add(lblStudentid);
		studentidContainer.add(lbldbStudentid);
		
		//is super account
		JPanel superContainer = new JPanel(new GridLayout(1,2));
		JLabel lblSuper = new JLabel("is Super:");
		JLabel lbldbSuper = new JLabel(connection.getElement(username, "super"));
		superContainer.add(lblSuper);
		superContainer.add(lbldbSuper);

		JPanel buttonContainer = new JPanel(new GridLayout(1,3));
		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AccountEditFrame(username);
				dispose();
			}
		});
		JButton logOut = new JButton("Log out");
		logOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Log out?");
				if(dialogResult==JOptionPane.YES_OPTION) {
					InitialFrame newMainFrame = new InitialFrame();
					newMainFrame.initiateFrame();
					dispose();
				}
			}
		});
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Quit?");
				if(dialogResult==JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		buttonContainer.add(edit);
		buttonContainer.add(logOut);
		buttonContainer.add(quit);
		
		mainContainer.add(usernameContainer);
		mainContainer.add(genderContainer);
		mainContainer.add(emailContainer);
		mainContainer.add(departmentContainer);
		mainContainer.add(studentidContainer);
		mainContainer.add(superContainer);
		mainContainer.add(buttonContainer);
		add(mainContainer);
		connection.close();
		pack();
		setVisible(true);
	}

}
