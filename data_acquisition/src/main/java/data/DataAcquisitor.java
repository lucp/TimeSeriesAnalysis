package data;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class DataAcquisitor 
{
	CSVReader csvreader;
	int[] valueTab;
    
	public DataAcquisitor(String file) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file),',','\"');
		this.valueTab=null;
	}
	
	public DataAcquisitor(String file,char separator, char quotechar) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.valueTab=null;
	}
	
}
