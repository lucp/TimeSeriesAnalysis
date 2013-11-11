package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

public class SimpleDataAcquisitor {

	String filePath;
	
	public SimpleDataAcquisitor(){
		this.filePath=null;
	}
	
	public SimpleDataAcquisitor(String filePath){
		this.filePath=filePath;
	}
	
	public void setFilePath(String path){
		this.filePath=path;
	}
	public String getFilePath(){
		return this.filePath;
	}
	
	public TimeSeries readData_TimeSeries() throws IOException{
		TimeSeries ts=new TimeSeries(this.filePath);
		FileReader fr=new FileReader(this.filePath);
		BufferedReader bf=new BufferedReader(fr);
		String line;
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		while ((line=bf.readLine())!=null){
			try{
				if (line.contains(",")){
					line=line.replaceAll(",",".");
				}
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				date = calendar.getTime();
				ts.add(new Day(date),Double.valueOf(line));
			}
			catch (Exception e){
				if (!ts.isEmpty()){
					ts.clear();
				}
			}
		}
		if (ts.isEmpty()){
			bf.close();
			throw new IOException();
		}
		bf.close();
		return ts;
	}
}
