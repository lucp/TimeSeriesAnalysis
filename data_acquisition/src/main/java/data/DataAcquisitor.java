package data;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataAcquisitor 
{
	CSVReader csvreader;
	String filePath;
	String dateFormat;
	int timeTab;
	int valueTab;
    
	public DataAcquisitor(String file) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file),',','\"');
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=0;
		this.valueTab=1;
	}
	
	public DataAcquisitor(String file,char separator, char quotechar) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=0;
		this.valueTab=1;
	}
	
	public DataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	public DataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab,String dateFormat) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat=dateFormat;
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	public DataAcquisitor(String file,int tTab,int vTab) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file));
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	public void setTimeTab(int tTab){
		this.timeTab=tTab;
	}
	public void setValueTab(int vTab){
		this.valueTab=vTab;
	}
	public void setDateFormat(String format){
		this.dateFormat=format;
	}
	
	public TimeSeries readData_TimeSeries() throws IOException{
		TimeSeries timeSeries=new TimeSeries(this.filePath);
		String[] nextLine;
		while ((nextLine=this.csvreader.readNext())!=null){
			try{
				if (nextLine[this.valueTab].contains(",")){
					nextLine[this.valueTab]=nextLine[this.valueTab].replaceAll(",",".");
				}
				SimpleDateFormat dateFormatter = new SimpleDateFormat(this.dateFormat);
				timeSeries.add(new Day(dateFormatter.parse(nextLine[this.timeTab])),Double.valueOf(nextLine[this.valueTab]));
			}
			catch (Exception e){
				if (!timeSeries.isEmpty()){
					timeSeries.clear();
				}
			}
		}
		return timeSeries;
	}
}
