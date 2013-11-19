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

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * Klasa pozwalająca na czytanie plików w formacie CSV i konwersję
 */
public class CSVDataAcquisitor 
{
	/**
	 * Klasa czytająca i interpretująca dane z pliku CSV względem wzorca
	 */
	CSVReader csvreader;
	/**
	 * Ścieżka do pliku
	 */
	String filePath;
	/**
	 * Format względem którego przetwarzana jest data
	 */
	String dateFormat;
	/**
	 * Numer kolumny pliku w której jest data
	 */
	int timeTab;
	/**
	 * Numer kolumny z wartością
	 */
	int valueTab;
    
	/**
	 * Konstruktor klasy przyjmujący domyślnie wartości:
	 * ',' - przedział pól
	 * '\"' - oznaczenie danej
	 * "yyyy-MM-dd" - format daty
	 * 0 - numer kolumny z datą
	 * 1 - numer kolumny z wartością
	 * @param file scieżka do pliku CSV
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file),',','\"');
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=0;
		this.valueTab=1;
	}
	
	/**
	 * Konstruktor klasy
	 * @param file scieżka do pliku CSV
	 * @param separator znak oddzielający pola danych
	 * @param quotechar znak wyznaczający daną
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,char separator, char quotechar) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=0;
		this.valueTab=1;
	}
	
	/**
	 * Konstruktor klasy
	 * @param file scieżka do pliku CSV
	 * @param separator znak oddzielający pola danych
	 * @param quotechar znak wyznaczający daną
	 * @param tTab numer kolumny z datą
	 * @param vTab numer kolumny z wartością
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	/**
	 * Konstruktor klasy
	 * @param file scieżka do pliku CSV
	 * @param separator znak oddzielający pola danych
	 * @param quotechar znak wyznaczający daną
	 * @param tTab numer kolumny z datą
	 * @param vTab numer kolumny z wartością
	 * @param dateFormat format daty
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab,String dateFormat) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat=dateFormat;
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	/**
	 * Konstruktor klasy
	 * @param file scieżka do pliku CSV
	 * @param tTab numer kolumny z datą
	 * @param vTab numer kolumny z wartością
	 * @param dateFormat format daty
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,int tTab,int vTab,String dateFormat) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), ',', '\"');
		this.filePath=file;
		this.dateFormat=dateFormat;
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	/**
	 * Konstruktor klasy
	 * @param file scieżka do pliku CSV
	 * @param tTab numer kolumny z datą
	 * @param vTab numer kolumny z wartością
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,int tTab,int vTab) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file));
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	/**
	 * Funkcja ustawiająca numer kolumny z datą
	 * @param tTab numer kolumny z datą
	 */
	public void setTimeTab(int tTab){
		this.timeTab=tTab;
	}
	/**
	 * Funkcja ustawiająca numer kolumny z wartością
	 * @param vTab numer kolumny z wartością
	 */
	public void setValueTab(int vTab){
		this.valueTab=vTab;
	}
	/**
	 * Funkcja ustawiająca format daty
	 * @param format format daty
	 */
	public void setDateFormat(String format){
		this.dateFormat=format;
	}
	
	/**
	 * Metoda czytająca dane z pliku, wyznacza wyniki i przekazuje je na wyjście
	 * @return dane wybrane z odpowiednich kolumn
	 * @throws IOException błąd przetwarzania - brak przetworzonych danych
	 */
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
	
	/**
	 * Metoda czytająca dane z pliku, wyznacza wyniki i przekazuje je na wyjście
	 * @return dane wybrane z odpowiednich kolumn
	 * @throws IOException błąd przetwarzania - brak przetworzonych danych
	 */
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
