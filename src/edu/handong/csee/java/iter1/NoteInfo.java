package edu.handong.csee.java.iter1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class NoteInfo extends JFrame implements Note {

	private Connectivity connection;
	private CalendarDataManager data;

	NoteInfo(String username, Connectivity mainConnection, CalendarDataManager cdm, String filepath) {
		super();
		connection = mainConnection;
		data = cdm;
		load(filepath);

	}

	@Override
	public String load(String filepath) {
		String content = "";
		try {
			File file = new File(("ListData/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "")
					+ (data.calMonth + 1) + (data.calDayOfMon < 10 ? "0" : "") + data.calDayOfMon + ".txt"));
			if (file.exists()) {
				BufferedReader in = new BufferedReader(new FileReader(
						"ListData/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "") + (data.calMonth + 1)
								+ (data.calDayOfMon < 10 ? "0" : "") + data.calDayOfMon + ".txt"));

				while(true) {
					String tempStr = in.readLine();
					if(tempStr == null) break;
					content = content + tempStr + System.getProperty("line.seperator");
				}
				in.close();
				
			}
			else {
				content = "";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public String[] parse(String filepath) {
		// TODO Auto-generated method stub
		String[] meta = filepath.split(":");
		return meta;
	}

	@Override
	public void callback() {
		// TODO Auto-generated method stub
		// new MemoCalendar();
		this.dispose();
	}
}
