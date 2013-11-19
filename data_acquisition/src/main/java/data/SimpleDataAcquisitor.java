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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

/**
 * Klasa wczytujaca dane z pliku w prostej formie jednokloumnowej
 */
public class SimpleDataAcquisitor {

	/**
	 * Scieżka do pliku
	 */
	String filePath;
	
	/**
	 * Konstruktor klasy
	 */
	public SimpleDataAcquisitor(){
		this.filePath=null;
	}
	
	/**
	 * Konstruktor klasy
	 * @param filePath scieżka do pliku
	 */
	public SimpleDataAcquisitor(String filePath){
		this.filePath=filePath;
	}

	/**
	 * Ustaw sieżkę do pliku
	 * @param path scieżka do pliku
	 */
	public void setFilePath(String path){
		this.filePath=path;
	}
	/**
	 * Pobierz scieżkę do pliku
	 * @return scieżka do pliku
	 */
	public String getFilePath(){
		return this.filePath;
	}
	
	/**
	 * Metoda wczytująca dane z pliku
	 * @return dane z pliku
	 * @throws IOException bład przetwarzania pliku
	 */
	public TimeSeries readData_TimeSeries() throws IOException{
		TimeSeries ts=new TimeSeries(this.filePath);
		FileReader fr=new FileReader(this.filePath);
		BufferedReader bf=new BufferedReader(fr);
		String line;
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		while ((line=bf.readLine())!=null){
			try{
				if (line.contains(",")){
					line=line.replaceAll(",",".");
				}
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				date = calendar.getTime();
				ts.add(new Day(date),Double.valueOf(line));
			}
			catch (Exception e){
				if (!ts.isEmpty()){
					ts.clear();
				}
			}
		}
		if (ts.isEmpty()){
			bf.close();
			throw new IOException();
		}
		bf.close();
		return ts;
	}
}
