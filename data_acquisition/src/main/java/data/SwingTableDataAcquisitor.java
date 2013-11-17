package data;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
				if (time.contains("\"")){
					time=time.replaceAll("\"", "");
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
	
	public static void updateJTable(JTable table,TimeSeries series,String dateFormat) throws Exception{
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		while (model.getRowCount()!=0){
			model.removeRow(0);
		}
		if (!series.isEmpty()){
			SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
			for (int i=0;i<series.getItemCount();i++){
				model.addRow(new Object[]{(String)dateFormatter.format(series.getTimePeriod(i).getStart()),Double.toString(series.getValue(i).doubleValue())});
			}
		}
	}
	
	public static TimeSeries[] splitTimeSeriesOnHalf(TimeSeries ts){
		TimeSeries[] out=new TimeSeries[2];
		out[0]=new TimeSeries("firstHalf");
		out[1]=new TimeSeries("secondHalf");
		for (int i=0;i<ts.getItemCount();i++){
			if (i<(ts.getItemCount()/2)){
				out[0].add(ts.getDataItem(i));
			}
			else{
				out[1].add(ts.getDataItem(i));
			}
		}
		return out;
	}
	
}
