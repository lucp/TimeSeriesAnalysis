package data_tst;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.data.time.TimeSeries;
import org.junit.Assert;
import org.junit.Test;
import data.DataAcquisitor;

public class DataAcqTst {

	@Test
	public void readingCSVTst(){
		try{
			DataAcquisitor dataAcq=new DataAcquisitor("gielda1.csv", ',', '\"',1,12);
			TimeSeries ts=dataAcq.readData_TimeSeries();
			System.out.print(ts.getItemCount());
			Assert.assertEquals(ts.getItemCount(),212);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void doubleParseTst1() throws Exception{
		Assert.assertEquals(Double.valueOf("32.4"),32.4,0);
	}
	
	@Test
	public void doubleParseTst2() throws Exception{
		Assert.assertEquals(Double.valueOf("32,4"),32.4,0);
	}
	
	@Test
	public void dateTst() throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = formatter.parse("2013-01-23");
		Assert.assertEquals(d1.toString(),"2013-01-23");
	}
}
