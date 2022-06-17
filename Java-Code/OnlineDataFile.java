package uk.arduino;

public class OnlineDataFile {

	//Below is defining attributes for the data received by the application
		
		private double temp;
		private double humidity;
		private double wind;
		private double baro;
		
		//Getters and Setters used for getting data and assigning the data so that it can be saved
		
		
		public double getTemp() {
			return temp;
		}
		public void setTemp(double temp) {
			this.temp = temp;
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
			return "DataFile [temp=" + temp + ", humidity=" + humidity + ", wind="
					+ wind + ", baro=" + baro + "]";
		}
		
		
		
		
	}

	
	

