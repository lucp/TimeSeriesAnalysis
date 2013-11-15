package data;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

public class SwingTableDataAcquisitor {
	
	JTable jTable;
	String dateFormat;
	
	public SwingTableDataAcquisitor(JTable table,String dateFormat){
		this.jTable=table;
		this.dateFormat=dateFormat;
	}
	
	public TimeSeries readData_TimeSeries(String name) throws Exception{
		TimeSeries timeSeries=new TimeSeries(name);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(this.dateFormat);
		DefaultTableModel model=(DefaultTableModel)this.jTable.getModel();
		for (int i=0;i<this.jTable.getRowCount();i++){
			int added=timeSeries.getItemCount();
			try{
				String time=(String)model.getValueAt(i,0);
				String value=(String)model.getValueAt(i,1);
				if (value.contains(",")){
					value=value.replaceAll(",",".");
				}
				timeSeries.add(new Day(dateFormatter.parse(time)),Double.valueOf(value));
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
			throw new Exception();
		}
		return timeSeries;
	}
	
	//public 
}
