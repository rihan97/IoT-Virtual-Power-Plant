//This class as well as the File saving class is used for storing the data in a CSV format.
//This class mainly focuses on the format of the data
package uk.arduino;

public class DataFile {// name of class is DataFile which is used by FileSaving class to call this class
	
	//Below is defining attributes for the data received by the application
	private String date;
	private double temp;
	private double light;
	private double humidity;
	private double wind;
	private double baro;
	
	//Getters and Setters used for getting data and assigning the data so that it can be saved
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public double getLight() {
		return light;
	}
	public void setLight(double light) {
		this.light = light;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getWind() {
		return wind;
	}
	public void setWind(double wind) {
		this.wind = wind;
	}
	public double getBaro() {
		return baro;
	}
	public void setBaro(double baro) {
		this.baro = baro;
	}
	@Override
	public String toString() {
		return "DataFile [date=" + date + ", temp=" + temp + ", light=" + light + ", humidity=" + humidity + ", wind="
				+ wind + ", baro=" + baro + "]";
	}
	
	
	
	
}
