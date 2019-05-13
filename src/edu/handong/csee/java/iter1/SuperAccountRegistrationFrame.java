package edu.handong.csee.java.iter1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class SuperAccountRegistrationFrame extends JFrame{
	private boolean usableUsername;
	private String usernameCache;
	private Connectivity connection;
	private CalendarDataManager data;
	SuperAccountRegistrationFrame(String superAccount, Connectivity mainConnection, CalendarDataManager cdm){
		setTitle("Account Registration");
		usableUsername = false;
		connection = mainConnection;
		data = cdm;
		connection.Connect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100,100);
		JPanel mainContainer = new JPanel();
		setPreferredSize(new Dimension(600, 600));
		
		JPanel inputContainer = new JPanel(new GridLayout(7,1));
		///username
		JPanel usernameContainer = new JPanel(new GridLayout(1, 3));
		JLabel lblUsername = new JLabel("Username:");
		usernameCache = "";
		JTextField inputUsername = new JTextField();
		inputUsername.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				usableUsername = false;
			}
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		JButton checkUsername = new JButton("check username");
		usernameContainer.add(lblUsername);
		usernameContainer.add(inputUsername);
		usernameContainer.add(checkUsername);
		checkUsername.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!usernameCache.equals(inputUsername.getText())) {
					//System.out.println("checking the validity of username input...");
					if(connection.isUnique(inputUsername.getText(), "username") && !inputUsername.getText().equals("")) {
						//System.out.println("username isUnique? check result:"+connection.isUnique(inputUsername.getText(), "username"));
						//System.out.println("usernameValidInform");
						JOptionPane.showMessageDialog(null, "username valid!", "usernameValidInform", JOptionPane.PLAIN_MESSAGE);
						usernameCache = inputUsername.getText();
						usableUsername = true;
					}
					else if(!connection.isUnique(inputUsername.getText(), "username")){
						//System.out.println("usernameInvalidError");
						JOptionPane.showMessageDialog(null, "username already exists!", "usernameInvalidError", JOptionPane.INFORMATION_MESSAGE);
						usableUsername = false;
					}
					else if(inputUsername.getText().equals("")) {
						//System.out.println("usernameLengthError");
						JOptionPane.showMessageDialog(null, "username can't be empty!", "usernameLengthError", JOptionPane.INFORMATION_MESSAGE);
						usableUsername = false;
					}
				}
			}
		});
		
		//password
		JPanel passwordContainer = new JPanel(new GridLayout(1,2));
		JLabel lblPassword = new JLabel("Password:");
		JPasswordField inputPassword = new JPasswordField();
		inputPassword.setEchoChar('?');
		passwordContainer.add(lblPassword);
		passwordContainer.add(inputPassword);
		
		//gender
		JPanel genderContainer = new JPanel(new GridLayout(1,2));
		JLabel lblGender = new JLabel("Gender:");
		JRadioButton choiceMale = new JRadioButton("Male", true);
		choiceMale.setActionCommand("Male");
		JRadioButton choiceFemale = new JRadioButton("Female");
		choiceFemale.setActionCommand("Female");
		ButtonGroup choiceGender = new ButtonGroup();
		choiceGender.add(choiceMale);
		choiceGender.add(choiceFemale);
		genderContainer.add(lblGender);
		genderContainer.add(choiceMale);
		genderContainer.add(choiceFemale);
		
		//email
		String[] emailChoices = {"gmail.com", "naver.com", "hanmail.net", "manual"};
		JPanel emailContainer = new JPanel(new GridLayout(1,5));
		JLabel lblEmail = new JLabel("Email:");
		JTextField inputEmail = new JTextField();
		JLabel annotationMark = new JLabel("@");
		JTextField manualEmailSite = new JTextField();
		JComboBox<String> emailChoiceBox = new JComboBox<String>(emailChoices);
		emailChoiceBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		emailContainer.add(lblEmail);
		emailContainer.add(inputEmail);
		emailContainer.add(annotationMark);
		emailContainer.add(manualEmailSite);
		emailContainer.add(emailChoiceBox);
		manualEmailSite.setEnabled(false);
		
		//department
		JPanel departmentContainer = new JPanel(new GridLayout(1,2));
		JLabel lblDepartment = new JLabel("Department:");
		JTextField inputDepartment = new JTextField();
		departmentContainer.add(lblDepartment);
		departmentContainer.add(inputDepartment);
		
		//student id
		JPanel studentidContainer = new JPanel(new GridLayout(1,2));
		JLabel lblStudentid = new JLabel("Student ID:");
		JTextField inputStudentid = new JTextField();
		studentidContainer.add(lblStudentid);
		studentidContainer.add(inputStudentid);
		
		//super account
		JPanel superContainer = new JPanel(new GridLayout(1,2));
		JLabel lblSuper = new JLabel("is Super:");
		JRadioButton choiceYes = new JRadioButton("YES");
		choiceYes.setActionCommand("YES");
		JRadioButton choiceNo = new JRadioButton("NO", true);
		choiceNo.setActionCommand("NO");
		ButtonGroup choiceYesNo = new ButtonGroup();
		choiceYesNo.add(choiceYes);
		choiceYesNo.add(choiceNo);
		superContainer.add(lblSuper);
		superContainer.add(choiceYes);
		superContainer.add(choiceNo);
		
		//inputContainer에 전부 삽입.
		inputContainer.add(usernameContainer);
		inputContainer.add(passwordContainer);
		inputContainer.add(genderContainer);
		inputContainer.add(emailContainer);
		inputContainer.add(departmentContainer);
		inputContainer.add(studentidContainer);
		inputContainer.add(superContainer);
		mainContainer.add(inputContainer, BorderLayout.CENTER);
		
		//버튼
		JPanel buttonContainer = new JPanel(new GridLayout(1, 2));
		JButton signUp = new JButton("Insert");
		signUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!usableUsername) JOptionPane.showMessageDialog(null,"Check if the username is valid!", "usernameInvalidError",JOptionPane.INFORMATION_MESSAGE);
				else if(inputUsername.getText().equals("")) JOptionPane.showMessageDialog(null, "Username is invalid: string should be longer than 0", "usernameLengthError", JOptionPane.INFORMATION_MESSAGE);
				else if(new String(inputPassword.getPassword()).toString().length()<8) JOptionPane.showMessageDialog(null, "Length of password should be longer than 7!", "passwordLengthError", JOptionPane.INFORMATION_MESSAGE);
				else if(inputEmail.getText().equals("") || (emailChoiceBox.getSelectedItem().equals("manual") && manualEmailSite.getText().equals(""))) JOptionPane.showMessageDialog(null, "Email can't be empty!", "emailLengthError", JOptionPane.INFORMATION_MESSAGE);
				else if(inputEmail.getText().contains("@") || (emailChoiceBox.getSelectedItem().equals("manual") && manualEmailSite.getText().contains("@"))) JOptionPane.showMessageDialog(null, "Email is invalid!", "emailInvalidError", JOptionPane.INFORMATION_MESSAGE);
				else if(inputDepartment.getText().equals("")) JOptionPane.showMessageDialog(null, "Department can't be empty!", "departmentLengthError", JOptionPane.INFORMATION_MESSAGE);
				else if(!connection.isUnique(inputStudentid.getText(), "studentid")) JOptionPane.showMessageDialog(null, "Account with this student id already exists!", "studentIDInvalidError", JOptionPane.INFORMATION_MESSAGE);
				else {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Sign up new account with this information?");
					if(dialogResult==JOptionPane.YES_OPTION) {
						String emailAddress = "";
						if(emailChoiceBox.getSelectedItem().equals("manual")) emailAddress = inputEmail.getText().trim()+"@"+manualEmailSite.getText().trim();
						else emailAddress = inputEmail.getText().trim()+"@"+emailChoiceBox.getSelectedItem();
						connection.insertAccount(inputUsername.getText(), new String(inputPassword.getPassword()), choiceGender.getSelection().getActionCommand(),emailAddress, inputDepartment.getText(), inputStudentid.getText(), choiceYesNo.getSelection().getActionCommand());
						JOptionPane.showMessageDialog(null, "Insertion successful!", "Inserted", JOptionPane.PLAIN_MESSAGE);
						connection.close();
						new SuperAccountInfoFrame(superAccount, connection, data);
						dispose();
					}
				}
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connection.close();
				new SuperAccountInfoFrame(superAccount, connection, data);
				dispose();
			}
		});
		buttonContainer.add(signUp);
		buttonContainer.add(cancel);
		
		add(buttonContainer, BorderLayout.SOUTH);
		
		add(mainContainer, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
}
