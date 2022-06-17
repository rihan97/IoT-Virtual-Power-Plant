package uk.arduino;
// This class is used for reading the CSV file in  line by line and by using the data file it organises the data.
import java.io.BufferedReader; // provides buffering to the Reader for Reading in data
import java.io.FileNotFoundException;// tells the user the file could not be found
import java.io.FileReader; // Reads in the file by reading the characters
import java.io.IOException;// used for general exceptions like file missing, no permission etc.
import java.util.ArrayList; // used for holding values of the same type 
import java.util.List;// used for storing data in

public class CSVDataList {// public class called CSVDataList. 
	//When this method i called it sets the Arraylist to array of data object
	
	private ArrayList<OnlineDataFile> dataList = new ArrayList<OnlineDataFile>();// creating a new called dataList
	private String path;
	
	public CSVDataList(String path) {
		this.path = path;
	}

	public ArrayList<OnlineDataFile> getDataList() {
		return dataList;
	}		

	//using getters and setters
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void readCSV() throws IOException,FileNotFoundException{// creating method called readCSV to read the file and using Exceptions so we know if something went wrong
		//
		@SuppressWarnings("resource")// annotations used for disabling a warning
		BufferedReader in = new BufferedReader(new FileReader(path));	// declaring BufferedReader as "in" and using FileReader and calling it "path"
		
		int count=0;// counter used for reading the lines in so we can read from 0
		
		String line = null;// setting the line number as null
		
		while((line = in.readLine())!=null){// while loop used. bufferedreader used and the filereader used to read all the lines

			//we will have the while line including commas in the line string
			//we will need to split this
	
			
			if(count!=0)// if statement used for splitting the method of the string 
			{
			
			String[] splits = line.split(",");//split and put in array
			
			//to read the csv file there will be 5 elements on splits array starting from index o to 5			
			//create a data temp object
			
			OnlineDataFile d = new OnlineDataFile();// Declaring DataFile as d
			// Parsing the attributes to convert the data type to the correct data type
			
			
			double temp = Double.parseDouble(splits[0]); // this contains the temp reading 
			double humidity = Double.parseDouble(splits[1]); //this contains the humidity reading
			double wind = Double.parseDouble(splits[2]); //this contains the humidity reading
			double baro = Double.parseDouble(splits[3]);

			//setting the objects to the  d values d= data
			d.setTemp(temp);
			d.setHumidity(humidity);
			d.setWind(wind);
			d.setBaro(baro);
						
			dataList.add(d);// adding this object "d" to the dataList
			}
			count++;
		}		
		
	}

}