package data;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;

import java.util.Date;

public class DataAcquisitor 
{
	CSVReader csvreader;
	String filePath;
	int timeTab;
	int valueTab;
    
	public DataAcquisitor(String file) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file),',','\"');
		this.filePath=file;
		this.timeTab=1;
		this.valueTab=2;
	}
	
	public DataAcquisitor(String file,char separator, char quotechar) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.timeTab=1;
		this.valueTab=2;
	}
	
	public DataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	public void setTimeTab(int tTab){
		this.timeTab=tTab;
	}
	public void setValueTab(int vTab){
		this.valueTab=vTab;
	}
	
	public TimeSeries readData_TimeSeries() throws IOException{
		TimeSeries timeSeries=new TimeSeries(this.filePath);
		String[] nextLine;
		while ((nextLine=this.csvreader.readNext())!=null){
			timeSeries.add(new Day(new Date(nextLine[this.timeTab])),Double.valueOf(nextLine[this.valueTab]));
		}
		return timeSeries;
	}
}
