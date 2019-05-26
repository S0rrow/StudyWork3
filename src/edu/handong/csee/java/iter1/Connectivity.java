package edu.handong.csee.java.iter1;

import java.sql.*;
import java.util.Vector;

public class Connectivity {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8&serverTimezone=UTC";//&serverTimezone=UTC
	static final String USER = "root";
	static final String PASS = "password";
	public Connection connect;
	public Statement statement;
	public PreparedStatement preparedStatement;
	public ResultSet resultSet;
	
	Connectivity(){
		connect = null;
		statement = null;
		preparedStatement = null;
		resultSet = null;
	}
	
	// jdbc �뱶�씪�씠踰꾨줈 db�뿉 �뿰寃고븯湲� �쐞�븳 �븿�닔.
	public void Connect() {
		try {
			Class.forName(JDBC_DRIVER);
			//System.out.println("Connecting to selected database...");
			connect = DriverManager.getConnection(DB_URL, USER, PASS);
			//System.out.println("database [testdb] connected.");
		}
		catch(Exception e) {
			System.out.println("unable to connect to database due to"+e.getMessage());
			e.printStackTrace();
		}
	}
	//�대━瑜� 二쇰㈃ �떎�뻾�븯湲� �쐞�븳 �븿�닔.
	public void state(String query) {
		try {
			//System.out.println("creating statement...");
			statement = connect.createStatement();
			//System.out.println("statement created!");
			//System.out.println("executing query...");
			//System.out.println("saving result to resultSet...");
			resultSet = statement.executeQuery(query);
			//System.out.println("resultSet saved!");
		} catch (SQLException e) {
			System.out.println("given query unable to execute due to "+e.getMessage());
			e.printStackTrace();
		}
	}
	//荑쇰━瑜� 二쇰㈃ preparedStatement濡� �떎�뻾�븯湲� �쐞�븳 �븿�닔.
	//�빐�떦 preparedStatement�뿉 ���븳 executeUpdate �씠�썑 resultSet�쓽 媛믪쓣 executeQuery瑜� �넻�빐 諛쏆븘���빞 �븿.
	public void prepareState(String query) {
		try {
			preparedStatement = connect.prepareStatement(query);
		} catch(SQLException e) {
			System.out.println("given query unable to execute due to "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	//�뜲�씠�꽣 愿�由щ�� �쐞�븳 荑쇰━瑜� 諛쏆븘 �떎�뻾�븯湲� �쐞�븳 �븿�닔
	public void stateDataManip(String query) {
		try {
			//System.out.println("creating statement...");
			statement = connect.createStatement();
			//System.out.println("statement created!");
			//System.out.println("executing update...");
			statement.executeUpdate(query);
			//System.out.println("update executed.");
		} catch (SQLException e) {
			System.out.println("given query unable to execute due to "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	//�뿰寃�, resultSet, 荑쇰━臾몃뱾�쓣 紐⑤몢 close�븯湲� �쐞�븳 �븿�닔.
	public void close() {
		//System.out.println("closing...");
		try {
			if(resultSet != null) {
				resultSet.close();
				//System.out.println("resultSet closed.");
			}
			if(statement != null) {
				statement.close();
				//System.out.println("statement closed.");
			}
			if(preparedStatement != null) {
				preparedStatement.close();
				//System.out.println("preparedStatement closed.");
			}
			if(connect != null) {
				connect.close();
				//System.out.println("connect closed.");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkSignIn(String username, String password) {
		try {
			reinitResultSet();
			while(resultSet.next()) {
				String dbUsername = resultSet.getString("username");
				//System.out.println("current dbusername:"+dbUsername);
				String dbPassword = resultSet.getString("password");
				//System.out.println("current dbpassword:"+dbPassword);
				if(username.equals(dbUsername)) {
					if(password.equals(dbPassword)) {
						return true;
					}
				}
			}
		} catch(SQLException e) {
			System.out.println("retrieving resultSet unable due to "+e.getMessage());
		}
		return false;
	}
	
	private void reinitResultSet() {
		try {
			//System.out.println("reinitiating resultSet cursor...");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * from account_info");
			//System.out.println("reinitiated.");
		} catch (SQLException e) {
			System.out.println("unable to reinitiate result set due to "+e.getMessage());
		}
	}
	//username, password, gender, email, department, studentid
	public void insertAccount(String username, String password, String gender, String email, String department, String studentid, String isSuper) {
		try {
			preparedStatement = connect.prepareStatement("INSERT account_info VALUES(?,?,?,?,?,?,?)");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, gender);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, department);
			preparedStatement.setString(6, studentid);
			preparedStatement.setString(7, isSuper);
			//System.out.println(preparedStatement);
			//System.out.println("executing update for preparedStatement...");
			preparedStatement.executeUpdate();
			//System.out.println("insertion finished.");
		} catch(SQLException e) {
			System.out.println("unable to insert new account due to "+e.getMessage());
		}
	}
	//�씠誘� 議댁옱�븯�뒗 怨꾩젙�쓽 �젙蹂대�� �닔�젙�븯湲� �쐞�븳 硫붿꽌�뱶
	public void alterAccount(String username, String password, String gender, String email, String department) {
		try {
			preparedStatement = connect.prepareStatement("UPDATE account_info SET password = ?, gender = ?, email = ?, department = ? WHERE username = ?");
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, gender);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, department);
			preparedStatement.setString(5, username);
			//System.out.println(preparedStatement);
			//System.out.println("executing update for preparedStatement...");
			preparedStatement.executeUpdate();
			//System.out.println("alter finished.");
		} catch(SQLException e) {
			System.out.println("unable to alter account due to "+e.getMessage());
		}
	}
	
	public void alterAccountAsSuper(String username, String password, String gender, String email, String department, String studentid, String isSuper) {
		try {
			preparedStatement = connect.prepareStatement("UPDATE account_info SET password = ?, gender = ?, email = ?, department = ?, studentid = ?, super = ? WHERE username = ?");
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, gender);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, department);
			preparedStatement.setString(5, studentid);
			preparedStatement.setString(6, isSuper);
			preparedStatement.setString(7, username);
			//System.out.println(preparedStatement);
			//System.out.println("EXECUTING UPDATE...");
			preparedStatement.executeUpdate();
			//System.out.println("ALTER FINISHED.");
		} catch(SQLException e) {
			System.out.println("unable to alter account due to "+e.getMessage());
		}
	}
	
	public String getElement(String username, String type) {
		try {
			reinitResultSet();
			while(resultSet.next()) {
				String dbusername = resultSet.getString("username");
				String dbelement = resultSet.getString(type);
				if(username.equals(dbusername)) {
					return dbelement;
				}
			}
		} catch(SQLException e) {
			System.out.println("Exception occured in getElement method.");
			System.out.println("retrieving resultSet unable due to "+e.getMessage());
		} 
		return null;
	}
	
	public boolean isUnique(String element, String type) {
		try {
			reinitResultSet();
			while(resultSet.next()) {
				//System.out.println("Checking whether "+element+" is unique in type:"+type);
				String dbElement = resultSet.getString(type);
				//System.out.println("checking if '"+element+"' equals to '"+dbElement+"'.");
				//System.out.println("result:"+element.equals(dbElement));
				if(element.equals(dbElement)) return false;
			}
		} catch(SQLException e) {
			System.out.println("Exception occured in isUnique method.");
			System.out.println("retrieving resultSet unable due to "+e.getMessage());
		}
		return true;
	}
	
	public int getCount() {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM account_info");
			while(resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("unable to execute getCount method due to "+e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	public Vector<String> getAccountInfo(String username) {
		Vector<String> accountData = new Vector<String>();
		try {
			reinitResultSet();
			while(resultSet.next()) {
				if(resultSet.getString("username").equals(username)) {
					accountData.add(username);
					accountData.add(resultSet.getString("password"));
					accountData.add(resultSet.getString("gender"));
					accountData.add(resultSet.getString("email"));
					accountData.add(resultSet.getString("department"));
					accountData.add(resultSet.getString("studentid"));
					accountData.add(resultSet.getString("super"));
					return accountData;
				}
			}
		} catch(SQLException e){
			System.out.println("getting account info unable due to "+e.getMessage())
			;
		}
		return null;
	}
}
