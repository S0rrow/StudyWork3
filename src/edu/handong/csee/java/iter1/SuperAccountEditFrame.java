package edu.handong.csee.java.iter1;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class SuperAccountEditFrame extends JFrame{
	private Connectivity connection;
	private boolean userIsSuperAccount = false;
	SuperAccountEditFrame(String username, String superAccount, Connectivity mainConnection){
		//System.out.println("Initiated new AccountEditFrame.")
		connection = mainConnection;
		connection.Connect();
		setTitle("Account Edit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100,100);
		JPanel mainContainer = new JPanel(new GridLayout(8, 1));
		setPreferredSize(new Dimension(600, 600));
		
		//username
		JPanel usernameContainer = new JPanel(new GridLayout(1,2));
		JLabel lblUsername = new JLabel("Username: ");
		JLabel lbldbUsername = new JLabel(username);
		usernameContainer.add(lblUsername);
		usernameContainer.add(lbldbUsername);
		
		//password
		JPanel passwordContainer = new JPanel(new GridLayout(1,2));
		JLabel lblPassword = new JLabel("New Password: ");
		JPasswordField inputPassword = new JPasswordField();
		inputPassword.setEchoChar('?');
		passwordContainer.add(lblPassword);
		passwordContainer.add(inputPassword);
		
		//gender
		JPanel genderContainer = new JPanel(new GridLayout(1,3));
		JLabel lblGender = new JLabel("Gender:");
		JRadioButton choiceMale = new JRadioButton("Male");
		choiceMale.setActionCommand("Male");
		JRadioButton choiceFemale = new JRadioButton("Female");
		choiceFemale.setActionCommand("Female");
		ButtonGroup choiceGender = new ButtonGroup();
		choiceGender.add(choiceMale);
		choiceGender.add(choiceFemale);
		genderContainer.add(lblGender);
		genderContainer.add(choiceMale);
		genderContainer.add(choiceFemale);
		if(connection.getElement(username, "gender").equals("Male")) {
			choiceMale.setSelected(true);
			choiceFemale.setSelected(false);
		}
		else if(connection.getElement(username, "gender").equals("Female")) {
			choiceMale.setSelected(false);
			choiceFemale.setSelected(true);
		}
		
		//email
		String[] emailChoices = {"gmail.com", "naver.com", "hanmail.net", "manual"};
		JPanel emailContainer = new JPanel(new GridLayout(1,5));
		JLabel lblEmail = new JLabel("Email:");
		JTextField inputEmail = new JTextField(connection.getElement(username, "email").substring(0, connection.getElement(username, "email").indexOf("@")));
		JLabel annotationMark = new JLabel("@");
		String emailSite = connection.getElement(username, "email").substring(connection.getElement(username, "email").indexOf("@")+1,connection.getElement(username, "email").length());
		JTextField manualEmailSite = new JTextField();
		JComboBox<String> emailChoiceBox = new JComboBox<String>(emailChoices);
		emailChoiceBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(emailChoiceBox.getSelectedItem().equals("manual")) {
					manualEmailSite.setVisible(true);
					manualEmailSite.setEnabled(true);
				}
				else {
					manualEmailSite.setVisible(false);
					manualEmailSite.setEnabled(false);
				}
			}
		});
		if(checkExistence(emailChoices, emailSite)) {
			manualEmailSite.setVisible(false);
			manualEmailSite.setEnabled(false);
		}
		else {
			manualEmailSite.setVisible(true);
			manualEmailSite.setEnabled(true);
			manualEmailSite.setText(emailSite);
			emailChoiceBox.setSelectedItem("manual");
		}
		emailContainer.add(lblEmail);
		emailContainer.add(inputEmail);
		emailContainer.add(annotationMark);
		emailContainer.add(manualEmailSite);
		emailContainer.add(emailChoiceBox);
		//department
		JPanel departmentContainer = new JPanel(new GridLayout(1,2));
		JLabel lblDepartment = new JLabel("Department:");
		JTextField inputDepartment = new JTextField(connection.getElement(username, "department"));
		departmentContainer.add(lblDepartment);
		departmentContainer.add(inputDepartment);
		
		//student id
		JPanel studentidContainer = new JPanel(new GridLayout(1,2));
		JLabel lblStudentid = new JLabel("Student ID:");
		JTextField inputStudentid = new JTextField(connection.getElement(username, "studentid"));
		studentidContainer.add(lblStudentid);
		studentidContainer.add(inputStudentid);
		
		//is super account
		JPanel superContainer = new JPanel(new GridLayout(1,2));
		JLabel lblSuper = new JLabel("is Super:");
		JRadioButton choiceYes = new JRadioButton("YES");
		choiceYes.setActionCommand("YES");
		JRadioButton choiceNo = new JRadioButton("NO");
		choiceNo.setActionCommand("NO");
		ButtonGroup choiceYesNo = new ButtonGroup();
		choiceYesNo.add(choiceYes);
		choiceYesNo.add(choiceNo);
		superContainer.add(lblSuper);
		superContainer.add(choiceYes);
		superContainer.add(choiceNo);
		if(connection.getElement(username, "super").equals("YES")) {
			userIsSuperAccount = true;
			choiceYes.setSelected(true);
			choiceNo.setSelected(false);
		}
		else if(connection.getElement(username, "super").equals("NO")) {
			choiceYes.setSelected(false);
			choiceNo.setSelected(true);
		}
		
		//buttons
		JPanel buttonContainer = new JPanel(new GridLayout(1,3));
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!new String(inputPassword.getPassword()).equals("") && new String(inputPassword.getPassword()).trim().length()<8) JOptionPane.showMessageDialog(null, "Length of password should be longer than 7!", "passwordLengthError", JOptionPane.INFORMATION_MESSAGE);
				else if(inputEmail.getText().equals("") || (emailChoiceBox.getSelectedItem().equals("manual") && manualEmailSite.getText().equals(""))) JOptionPane.showMessageDialog(null, "Email can't be empty!", "emailLengthError", JOptionPane.INFORMATION_MESSAGE);
				else if(inputEmail.getText().contains("@") || (emailChoiceBox.getSelectedItem().equals("manual") && manualEmailSite.getText().contains("@"))) JOptionPane.showMessageDialog(null, "Email is invalid!", "emailInvalidError", JOptionPane.INFORMATION_MESSAGE);
				else if(inputDepartment.getText().equals("")) JOptionPane.showMessageDialog(null, "Department can't be empty!", "departmentLengthError", JOptionPane.INFORMATION_MESSAGE);
				else if(!connection.isUnique(inputStudentid.getText(), "studentid") && !connection.getElement(username, "studentid").equals(inputStudentid.getText())) JOptionPane.showMessageDialog(null, "Account with this student id already exists!", "studentIDInvalidError", JOptionPane.INFORMATION_MESSAGE);
				else {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Save?");
					if(dialogResult==JOptionPane.YES_OPTION) {
						String alterpassword = "";
						String emailAddress = "";
						if(new String(inputPassword.getPassword()).equals("")) alterpassword = connection.getElement(username, "password");
						else alterpassword = new String(inputPassword.getPassword()).trim();
						if(emailChoiceBox.getSelectedItem().equals("manual")) emailAddress = inputEmail.getText().trim()+"@"+manualEmailSite.getText().trim();
						else emailAddress = inputEmail.getText().trim()+"@"+emailChoiceBox.getSelectedItem();
						//System.out.println("alterpassword:"+alterpassword);
						//System.out.println("emailAddress:"+emailAddress);
						//public void alterAccountAsSuper(String username, String password, String gender, String email, String department, String studentid, String isSuper)
						connection.alterAccountAsSuper(username, alterpassword, choiceGender.getSelection().getActionCommand(), emailAddress, inputDepartment.getText().trim(), inputStudentid.getText().trim(), choiceYesNo.getSelection().getActionCommand());
						connection.close();
						if(username.equals(superAccount) && userIsSuperAccount && choiceYesNo.getSelection().getActionCommand().equals("NO")) {
							new AccountInfoFrame(superAccount, connection);
						}
						else new SuperAccountInfoFrame(superAccount, connection);
						dispose();
					}
				}
			}
		});
		JButton dropAccount = new JButton("Drop");
		dropAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "DELETE this account from the database?");
				if(dialogResult==JOptionPane.YES_OPTION) {
					//System.out.println("deleting account...");
					connection.stateDataManip("DELETE FROM account_info WHERE username='"+username+"';");
					//System.out.println("account deleted from database!");
					connection.close();
					if(username.equals(superAccount) && userIsSuperAccount && choiceYesNo.getSelection().getActionCommand().equals("NO")) {
						InitialFrame newMainFrame = new InitialFrame(connection);
						newMainFrame.initiateFrame();
					}
					else new SuperAccountInfoFrame(superAccount, connection);
					dispose();
				}
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connection.close();
				new SuperAccountInfoFrame(superAccount, connection);
				dispose();
			}
		});
		buttonContainer.add(save);
		buttonContainer.add(dropAccount);
		buttonContainer.add(cancel);
		
		mainContainer.add(usernameContainer);
		mainContainer.add(passwordContainer);//변경 가능
		mainContainer.add(genderContainer);//변경 가능
		mainContainer.add(emailContainer);//변경 가능
		mainContainer.add(departmentContainer);//변경 가능
		mainContainer.add(studentidContainer);
		mainContainer.add(superContainer);
		mainContainer.add(buttonContainer);
		add(mainContainer);
		pack();
		setVisible(true);
	}
	
	private boolean checkExistence(String[] choices, String tofind) {
		for(String item: choices) {
			if(item.equals(tofind)) return true;
		}
		return false;
	}
}
