package data;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class CSVDataAcquisitor 
{
	CSVReader csvreader;
	String filePath;
	String dateFormat;
	int timeTab;
	int valueTab;
    
	public CSVDataAcquisitor(String file) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file),',','\"');
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=0;
		this.valueTab=1;
	}
	
	public CSVDataAcquisitor(String file,char separator, char quotechar) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=0;
		this.valueTab=1;
	}
	
	public CSVDataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	public CSVDataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab,String dateFormat) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat=dateFormat;
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	public CSVDataAcquisitor(String file,int tTab,int vTab,String dateFormat) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), ',', '\"');
		this.filePath=file;
		this.dateFormat=dateFormat;
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	public CSVDataAcquisitor(String file,int tTab,int vTab) throws IOException{
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
		String name=this.filePath.substring(this.filePath.lastIndexOf("\\")+1,this.filePath.length());
		TimeSeries timeSeries=new TimeSeries(name);
		String[] nextLine;
		SimpleDateFormat dateFormatter = new SimpleDateFormat(this.dateFormat);
		while ((nextLine=this.csvreader.readNext())!=null){
			int added=timeSeries.getItemCount();
			try{
				if (nextLine[this.valueTab].contains(",")){
					nextLine[this.valueTab]=nextLine[this.valueTab].replaceAll(",",".");
				}
				if (nextLine[this.timeTab].contains("\"")){
					nextLine[this.timeTab]=nextLine[this.timeTab].replaceAll("\"", "");
				}
				timeSeries.add(new Day(dateFormatter.parse(nextLine[this.timeTab])),Double.valueOf(nextLine[this.valueTab]));
			}
			catch (Exception e){
				if (!timeSeries.isEmpty()){
					if (timeSeries.getItemCount()!=added){
						timeSeries.clear();
					}
				}
			}
		}
		if (timeSeries.isEmpty()){
			throw new IOException();
		}
		return timeSeries;
	}
	
	public double[] readData_DoubleValueTable() throws IOException{
		LinkedList<Double> list=new LinkedList<Double>();
		String[] nextLine;
		while ((nextLine=this.csvreader.readNext())!=null){
			int added=list.size();
			try{
				if (nextLine[this.valueTab].contains(",")){
					nextLine[this.valueTab]=nextLine[this.valueTab].replaceAll(",",".");
				}
				if (nextLine[this.timeTab].contains("\"")){
					nextLine[this.timeTab]=nextLine[this.timeTab].replaceAll("\"", "");
				}
				list.add(Double.parseDouble(nextLine[this.valueTab]));
			}
			catch (Exception e){
				if (!list.isEmpty()){
					if (list.size()!=added){
						list.clear();
					}
				}
			}
		}
		if (list.isEmpty()){
			throw new IOException();
		}
		double[] out=new double[list.size()];
		for (int i=0;i<list.size();i++){
			out[i]=list.get(i);
		}
		return out;
	}
}
