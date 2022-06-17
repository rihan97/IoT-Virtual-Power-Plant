//This class is used for reading the data in from the serial port as well as displaying it 
// This class is where the actual GUI is built with all the buttons 
package uk.arduino;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.JTextArea;

import org.jfree.ui.RefineryUtilities;

import java.awt.TextArea;

import java.util.Date;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class VirtualPowerPlantGUI extends JFrame implements SerialPortEventListener { //class called 

	/**
	 * 
	 * 
	 */
	
	private static final long serialVersionUID = -5164047937305246047L;
	private JPanel contentPane; //the panel 
	private ArrayList<Double> tempList = new ArrayList<Double>(); // creating a list for Temperature to store the data
	private ArrayList<Double> ldrList = new ArrayList<Double>(); //creating a list for ldrList to store the data 
	private ArrayList<Double>humidityList = new ArrayList <Double>(); //creating a list for humidity to store the data
	private ArrayList<Double> windList = new ArrayList<Double>(); // creating a list for wind to store the data
	private ArrayList<Double> baroList = new ArrayList<Double>(); //creating a list for pressureList to store the data 
	private ArrayList<DataFile> dataList = new ArrayList<DataFile>(); //saving the csv data
	LiveChart combined = new LiveChart("Virtual Power Plant",tempList,ldrList,humidityList,windList, baroList);
	int live=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { // main method
		EventQueue.invokeLater(new Runnable() {
			public void run() { //method run for running  the serial port
				try {
					VirtualPowerPlantGUI frame = new VirtualPowerPlantGUI();
					frame.initialize();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	//Creating the Frame for  Graphical User Interface
	public VirtualPowerPlantGUI() { // The Graphical User Interface Window
		setBackground(new Color(255, 153, 102)); // setting the background colour
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //this is used so that all the frames are not closed when you close this windows
		setBounds(100, 100, 1032, 670); // sets the size of the window
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE); // Setting background colour
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); //sets the borders
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textDisplay = new TextArea(); // creating text area for displaying the data coming in through the serial port
		textDisplay.setForeground(Color.RED);
		textDisplay.setFont(new Font("Arial", Font.PLAIN, 19)); //setting the font size and font style
		textDisplay.setBounds(261, 116, 544, 207); //set the size of the text area
		contentPane.add(textDisplay); //adding textArea to the contentPane
		
		
	// Code for opening the Temperature Chart	
		JButton btnTemperatureChart = new JButton("Temperature Chart"); // A button for Temperature Chart
		btnTemperatureChart.setFont(new Font("Arial", Font.BOLD, 19)); //setting the font and size
		btnTemperatureChart.setBackground(Color.CYAN); //setting the background colour for the  button
		btnTemperatureChart.setToolTipText("Click to Open Temperature Chart");//This code is created for hover animation

		
		btnTemperatureChart.addActionListener(new ActionListener() { //adding action to the button so that it does something when the button is pressed	
			public void actionPerformed(ActionEvent arg0) { //allows the Temperature Chart class to run so that the Temperature can be displayed 
				TemperatureChart chartTemp = new TemperatureChart("Temperature Chart",tempList);//Calling the Temperature Chart class and giving name as chartTemp
				chartTemp.create();
				chartTemp.pack();
				RefineryUtilities.centerFrameOnScreen(chartTemp);
				chartTemp.setVisible(true);
			}
		});
		btnTemperatureChart.setBounds(12, 149, 233, 38); //setting the size of the button and the place
		contentPane.add(btnTemperatureChart); //adding the button to the content pane inside the window
	
	// Code for opening the LDR Chart
		JButton btnLdrChart = new JButton("LDR Chart"); //A button for LDR Chart
		btnLdrChart.setFont(new Font("Arial", Font.BOLD, 19));//setting the font and size
		btnLdrChart.setBackground(new Color(255, 153, 102)); //setting the background colour for the  button
		btnLdrChart.setToolTipText("Click to Open LDR Chart"); //This code is created for hover animation

		btnLdrChart.addActionListener(new ActionListener() { //adding action to the button so that it does something when the button is pressed
			public void actionPerformed(ActionEvent arg0) { //allows the LDR Chart class to run so that the LDR can be displayed 
				
				LdrChart chart = new LdrChart("LDR Chart",ldrList); //Calling the LDRChart class and giving name as chartLDR
				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);
				
			}
		});
		btnLdrChart.setBounds(20, 200, 206, 38); //setting the size of the buttons 
		contentPane.add(btnLdrChart); //adding the button to the gui window
		
		// Code for opening the Humidity Chart
		JButton btnHumidityChart = new JButton("Humidity Chart"); // A button for Motion Chart
		btnHumidityChart.setFont(new Font("Arial", Font.BOLD, 19)); //Setting the size and font style for the button 
		btnHumidityChart.setBackground(Color.MAGENTA); //Setting the background colour for the button
		btnHumidityChart.setToolTipText("Click to Open Humidity Chart"); //This code is created for hover animation

		btnHumidityChart.addActionListener(new ActionListener() { //adding action to the button so that it does something when the button is pressed
			public void actionPerformed(ActionEvent arg0) { //allows the Motion Chart class to run so that the Motion data can be displayed 
					HumidityChart chart = new HumidityChart("Humidity Chart",humidityList);//Calling the LDRChart class and giving name as chartLDR
					chart.pack();
					RefineryUtilities.centerFrameOnScreen(chart);
					chart.setVisible(true);

				}
			
		});
		btnHumidityChart.setBounds(20, 251, 206, 38); //setting the size of the motion chart button and placing it
		contentPane.add(btnHumidityChart); //adding it to the content pane the GUI window
		
		// Code for opening the Wind Chart
		JButton btnWindChart = new JButton("Wind Chart"); // A button for Wind Chart
		btnWindChart.setFont(new Font("Arial", Font.BOLD, 19)); //Setting the size and font style for the button 
		btnWindChart.setBackground(Color.MAGENTA); //Setting the background colour for the button
		btnWindChart.setToolTipText("Click to Open Humidity Chart"); //This code is created for hover animation

		btnWindChart.addActionListener(new ActionListener() { //adding action to the button so that it does something when the button is pressed
			public void actionPerformed(ActionEvent arg0) { //allows the wind Chart class to run so that the wind data can be displayed 
					WindChart chart = new WindChart("Wind Chart",windList);//Calling the windChart class and giving name as windLDR
					chart.pack();
					RefineryUtilities.centerFrameOnScreen(chart);
					chart.setVisible(true);

				}
			
		});
		btnWindChart.setBounds(20, 353, 206, 38); //setting the size of the wind chart button and placing it
		contentPane.add(btnWindChart); //adding it to the content pane the GUI window
		//
					
						
		// Code for opening the Barometric Chart
		JButton btnBarometricChart = new JButton("Barometric Chart");// A button for Baro Chart
		btnBarometricChart.setFont(new Font("Arial", Font.BOLD, 19)); //Setting the size and font style for the button 
		btnBarometricChart.setBackground(Color.CYAN); //Setting the background colour for the button
		btnBarometricChart.setToolTipText("Click to Open Barometric Chart"); //This code is created for hover animation

				btnBarometricChart.addActionListener(new ActionListener() { //adding action to the button so that it does something when the button is pressed
					public void actionPerformed(ActionEvent arg0) { //allows the Barometric Chart class to run so that the wind data can be displayed 
						BarometricChart chart = new BarometricChart("Barometric Chart",baroList);//Calling the BarometricChart class and giving name as windLDR
							chart.pack();
							RefineryUtilities.centerFrameOnScreen(chart);
							chart.setVisible(true);

						}
					
				});
				btnBarometricChart.setBounds(20, 302, 206, 38); //setting the size of the wind chart button and placing it
				contentPane.add(btnBarometricChart); //adding it to the content pane the GUI window
				
				
		
		//Code for turning the Live Chart on
		JButton btnLiveChartOn = new JButton(" Live Chart On"); // For turning the live chart on 
		btnLiveChartOn.setBackground(Color.YELLOW);
		btnLiveChartOn.setFont(new Font("Arial", Font.BOLD, 19));
		btnLiveChartOn.setToolTipText("Click to Turn On Live Chart");//This code is created for hover animation

		btnLiveChartOn.addActionListener(new ActionListener() { //adding action to the button so that it does something when the button is pressed
			public void actionPerformed(ActionEvent arg0) {
				live=1; //1 = live chart is on 
			}
		});
		btnLiveChartOn.setBounds(20, 427, 206, 35); //setting the size of the button
		contentPane.add(btnLiveChartOn); //adding the button to the content pane
		
		
		//Code for turning the Live Chart off
		JButton btnLiveChartOff = new JButton("Live Chart Off"); // for Turning the live chart off
		btnLiveChartOff.setFont(new Font("Arial", Font.BOLD, 19));
		btnLiveChartOff.setBackground(Color.RED);
		btnLiveChartOff.setToolTipText("Click to Turn off Live Chart"); //This code is created for hover animation
		
		btnLiveChartOff.addActionListener(new ActionListener() { //adding action to the button so that it does something when the button is pressed
			public void actionPerformed(ActionEvent e) {
				live=0;//0= live chart off
			}
		});
		btnLiveChartOff.setBounds(20, 475, 206, 37); //setting the size of the button
		contentPane.add(btnLiveChartOff); //adding the button to the content pane
		
		//Code for Saving Data in CSV format
		JButton btnSaveData = new JButton("Save Data"); //declaring and creating a button for saving data in csv format
		btnSaveData.setBackground(Color.GREEN);
		btnSaveData.setFont(new Font("Arial", Font.BOLD, 19));
		btnSaveData.setToolTipText("Click to Save the Data"); //This code is created for hover animation

		btnSaveData.addActionListener(new ActionListener() { //adding action to the button so that it does something when the button is pressed
			public void actionPerformed(ActionEvent e) {
				FileSaving file = new FileSaving(); //Using the Class FileSaving Class and using the rList Array list from FileSaving 
				file.setrList(dataList); //setting the name file to rList from the class FIleSaving
				try {///try catch used if the file not saved or no permission
					file.writeCSV(); // writing in the format of CSV
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSaveData.setBounds(20, 549, 206, 38); //Setting the size and place of the button	
		contentPane.add(btnSaveData); //Adding the button to gui pane
		
		//Code for Giving the GUI a title
		JTextArea txtrSmartHouse = new JTextArea(); //Using Text Area for the Title
		txtrSmartHouse.setForeground(Color.GREEN);
		txtrSmartHouse.setBackground(Color.BLACK); // setting the colour of the text area to Red
		txtrSmartHouse.setFont(new Font("Arial", Font.BOLD, 36));//Setting the font style and font size 
		txtrSmartHouse.setText("Virtual Power Plant"); //Giving a title
		txtrSmartHouse.setBounds(175, 25, 335, 44);//Giving the Text Area a size
		contentPane.add(txtrSmartHouse); //adding the Text Area to the content pane to Windows Builder
		
		//Text Area for showing if energy will be produced enough for tommorrow
		Energy = new JTextField(); //naming the Text fIELD
		Energy.setForeground(Color.BLUE);
		Energy.setFont(new Font("Arial Black", Font.PLAIN, 21));//Setting the font style and size
		Energy.setBounds(522, 31, 478, 44); //setting the size of the text field
		contentPane.add(Energy); //adding the text field to content pane
		Energy.setColumns(10);

		// Code for Inserting Logo Image 
		JLabel Logo = new JLabel((new ImageIcon(".\\Images\\Logo.jpg")));//Locating where the image is
		Logo.setBounds(12,13,121,120); //Setting the area and size of the image
		contentPane.add(Logo);
				contentPane.setLayout(null);
				
				
				//Loading BBC online data using button
				JButton btnLoadcsv = new JButton("Load: BBC Online Data");
				btnLoadcsv.setBounds(636, 549, 247, 50);
				btnLoadcsv.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));//The font and font size used to display the text
				btnLoadcsv.setToolTipText("Click to Load CSV");// This code is created for hover animation
				btnLoadcsv.addActionListener(new ActionListener() {//added an action to the button so it does something when the button is clicked
					public void actionPerformed(ActionEvent e) {
						BBCOnlineDataGUI first = new BBCOnlineDataGUI();//just creates a new object of the JFrame class CSVDisplayGUI.
						first.setVisible(true);// allows the CSVDisplayGUI to display when the button is clicked.
						
					}
				});
				contentPane.setLayout(null);
				contentPane.add(btnLoadcsv);//adding the button to the content pane inside the window
				
				//Loading openweather online data using button
				JButton btnLoadOpenweatherOnline = new JButton("Load: OpenWeather Online Data");
				btnLoadOpenweatherOnline.setToolTipText("Click to Load CSV");
				btnLoadOpenweatherOnline.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
				btnLoadOpenweatherOnline.setBounds(261, 549, 335, 50);
				btnLoadOpenweatherOnline.addActionListener(new ActionListener() {//added an action to the button so it does something when the button is clicked
					public void actionPerformed(ActionEvent e) {
						OpenWeatherOnlineDataGUI second = new OpenWeatherOnlineDataGUI();//just creates a new object of the JFrame class CSVDisplayGUI.
						second.setVisible(true);// allows the CSVDisplayGUI to display when the button is clicked.
						
					}
				});
				contentPane.setLayout(null);
				contentPane.add(btnLoadOpenweatherOnline);
				
				final TextArea textArea3 = new TextArea();
				textArea3.setForeground(Color.MAGENTA);
				textArea3.setFont(new Font("Arial", Font.PLAIN, 19));
				textArea3.setBounds(261, 342, 544, 190);
				contentPane.add(textArea3);
				
				JButton btnLoadAverages = new JButton("Load: Averages");
				btnLoadAverages.setToolTipText("Click to Load CSV");
				btnLoadAverages.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
				btnLoadAverages.setBounds(824, 402, 176, 50);
				btnLoadAverages.addActionListener(new ActionListener() {//added an action to the button so it does something when the button is clicked
					public void actionPerformed(ActionEvent e) {
						CSVDataList read = new CSVDataList("./data/AveragesbetweenOnlinesources.csv");// Locates the File and it reads it in
						try {
							read.readCSV();
						} catch (IOException e1) {
							e1.printStackTrace();// helps trace exceptions - shows what happened and where it happened in the code
						}
						for(OnlineDataFile d:read.getDataList()){//Looping through the dataList inside the read object
					    	textArea3.append(d+"\n");}// to display the values
						
					}
				});
				contentPane.setLayout(null);
				contentPane.add(btnLoadAverages);
				
				
		
	
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Serial Communication code 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	SerialPort serialPort;
	//Identifying the most popular ports which get used
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyUSB0", // Linux
			"COM12", // Windows //
	};

	//A buffered reader declared as an input which will get data from InputStreamReader
	//Done by converting bytes into character
	private BufferedReader input;	
	private static final int TIME_OUT = 2000; //Time used in milliseconds to block while waiting for the port to open
	private static final int DATA_RATE = 9600; //Declared the data rate as the default data rate per second for COM ports
	
	private TextArea textDisplay; //For displaying the actual data
	private JTextField Energy;//For Displaying if heating is on or off in GUI
	
	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

		// Finding an instance of serial port from the  PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) { //if the port couldn't be found then do this 
			System.out.println("Could not find COM port.");
			return;
		}
		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// setting port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams so that the data can be read in 
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			serialPort.getOutputStream();

			// adding event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	// This is used when you stop using the port
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	//This is where reading in the data and printing occurs by handling the event on the serial port
	public synchronized void serialPlot(SerialPortEvent oEvent) {
		
	}
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) { // Asking if data is available
			try {
				Date d = new Date(); //getting the current date and time
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //Displays the date
				String datetime = dt.format(d);
				String inputLine=input.readLine(); //in CSV format
				
				String[] data = inputLine.split(","); // using to split the data
				double temp = Double.parseDouble(data[0]); // this contains the temp reading 
				double ldr = Double.parseDouble(data[1]); // this contains the ldr reading 
				double humidity = Double.parseDouble(data[2]); //this contains the humidity reading
				double wind = Double.parseDouble(data[3]); //this contains the humidity reading
				double baro = Double.parseDouble(data[4]); //this contains the humidity reading
				tempList.add(temp); //this is for future use
				ldrList.add(ldr); //this is for future use
				humidityList.add(humidity); //this is for future use
				windList.add(wind); //this is for future use
				baroList.add(baro); //this is for future use
				
				if (live==1){
					combined.create();
					combined.pack();
					RefineryUtilities.centerFrameOnScreen(combined);
					combined.setVisible(true);
				}
				
				//plotting the data in Live
				System.out.println(datetime+" > "+inputLine); //print on the console 
				textDisplay.append(datetime+" > "+inputLine+"\n"); //displaying the value on to the textArea
				
				
				if( (ldr ==1) && (humidity>=1) && (wind>=2.50)){// enough energy time heating off 
					Energy.setText("Enough Energy Produced For Tomz");//above 18 and this works
				}
				else if ( (ldr ==0) && (wind<=0.50) && (humidity==0)){// not enough energy 
					Energy.setText("Not Enough Energy Will be Produced Tomz");
				}
				else if (wind>=3.00 && (ldr==1)){//good electric
					Energy.setText("Use All Appliances In House");
				}
				
				else if (wind>=0.50 && (ldr==0)){//good electric
					Energy.setText("Dont Use The Iron Tomz");
				}
				
				DataFile r = new DataFile(); //Using the class Record 
				r.setDate(datetime); //using setters to set 
				r.setTemp(temp);//using setters to set
				r.setLight(ldr);//using setters to set
				r.setHumidity(humidity);//using setters to set
				r.setWind(wind);//using setters to set
				r.setBaro(baro);//using setters to set
				
				
				dataList.add(r);
				
			} catch (Exception e) {
				System.err.println(e.toString());
			}
	}
	}
}