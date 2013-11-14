package data;

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
			String time=(String)model.getValueAt(i,0);
			String value=(String)model.getValueAt(i,1);
			if (value.contains(",")){
				value=value.replaceAll(",",".");
			}
			timeSeries.add(new Day(dateFormatter.parse(time)),Double.valueOf(value));
		}
		return timeSeries;
	}
}
