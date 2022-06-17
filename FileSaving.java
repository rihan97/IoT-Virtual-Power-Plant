// This class is used for saving the data from GUI into a CSV format file. 
//This class uses the DataFile Class to organise the data so it can save in CSV format
package uk.arduino;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FileSaving {
	
	private List <DataFile> dList = new ArrayList<DataFile>(); //creating an arraylist
	
	//using getters and setters
	public List<DataFile> getrList() {
		return dList;
	}

	public void setrList(List<DataFile> rList) {
		this.dList = rList;
	}

	public void readCSV() throws IOException,FileNotFoundException{ 
		int count = 0; // counter used for skipping the first line as the first line are the headers
		@SuppressWarnings("resource")// using the buffered reader to read the csv file
		BufferedReader in = new BufferedReader(new FileReader("./Data/data.csv"));	
		String line = null; // A string created called line
		while((line = in.readLine())!=null){ // the while loop reads csv file line by line until it hits null when there is no data
			if (count!=0){ // used count so it then reads the data not the header as line 0 are the headers
			

			
			String[] splits = line.split(",");//split and put in array
			
			// there will be 4 elements on splits array, from index 0 to 3
			
			DataFile r = new DataFile();//creating DataFile r object to store the data
			//Taking data from string array and split to the correct variables
			String date = (splits[0]);
			double temp = Double.parseDouble(splits[1]);
			double light = Double.parseDouble(splits[2]);
			double humidity = Double.parseDouble(splits[3]);
			double wind = Double.parseDouble(splits[4]);
			double baro = Double.parseDouble(splits[5]);
			
			//Setting the object r  with values from the variables
			r.setDate(date);
			r.setTemp(temp);
			r.setLight(light);
			r.setHumidity(humidity);
			r.setWind(wind);
			r.setBaro(baro);
			
			
			
			//now add this object on the dataList of type dataFile				
			dList.add(r);
			System.out.println(r.toString());
			}
			count++;
			
		}
	}
	
	public void writeCSV() throws IOException{

		PrintStream file = new PrintStream ("./Data/data.csv");//location of saving file
		for(DataFile r: dList){
			System.out.println(r.toString());
			file.print(r.getDate()+",");
			file.print(r.getTemp()+",");
			file.print(r.getLight()+",");
			file.print(r.getHumidity()+",");
			file.print(r.getWind()+",");
			file.print(r.getBaro()+",");
			file.println();
		}
		file.close();
		
	}
}