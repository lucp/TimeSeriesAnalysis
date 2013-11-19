/**
 * Copyright (c) 2013,
 * Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package data;

import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

public class SwingTableDataAcquisitor {
	
	/**
	 * Tablica danych
	 */
	JTable jTable;
	/**
	 * Format daty
	 */
	String dateFormat;
	
	/**
	 * Konstruktor klasy
	 * @param table tablica danych
	 * @param dateFormat format daty
	 */
	public SwingTableDataAcquisitor(JTable table,String dateFormat){
		this.jTable=table;
		this.dateFormat=dateFormat;
	}
	
	/**
	 * Metoda pobierająca dane z tabeli
	 * @param name nazwa dla nowej serii danych
	 * @return dane z tabeli
	 * @throws Exception bład parsowania danych
	 */
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
	
	/**
	 * Funkcja wstawiająca dane do tabeli
	 * @param table tablica do wstawienia danych
	 * @param series dane do wstawienia
	 * @param dateFormat format daty
	 * @throws Exception błąd parsowania
	 */
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
	
	/**
	 * Funkcja dzieląca dane na dwie równe części
	 * @param ts dane do podziału
	 * @return dwuelementowa tablica z danymi
	 */
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
