package uk.arduino;


//This class is used for displaying CSV in a GUI
//this class is similar to another class inside the program called �CSVRun�.
import java.io.IOException;// // used for general exceptions like file missing, no permission etc.

import javax.swing.JFrame;// this is FOR the window 
import javax.swing.JPanel; //Inside the window
import javax.swing.border.EmptyBorder;// Border in the window
import javax.swing.JTextArea;// in this area the CSV Data is displayed
import javax.swing.JScrollPane;// used for scrolling up and down for the data
import java.awt.Font; 


public class BBCOnlineDataGUI extends JFrame {

	
	private static final long serialVersionUID = 1L;//  declaring  a static final
	private JPanel contentPane;
	
	public BBCOnlineDataGUI() {  //creates the frame
		setTitle("BBC Display");// Sets the name at the top of the window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//this is used so that all the frames are not closed when you close this windows
		setBounds(100, 100, 865, 534);// sets the size of the window
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));//sets the borders
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea1 = new JTextArea();//creates a new JtextArea
		textArea1.setFont(new Font("Arial", Font.PLAIN, 14));
		textArea1.setBounds(52, 30, 731, 432);// the size of the text Area
		contentPane.add(textArea1);//adds the  textArea to content pane
		
		JScrollPane scrollPane = new JScrollPane(textArea1);// creates a new scrollPane
		scrollPane.setBounds(52, 30, 731, 432);//sets the size of the scrollPane
		contentPane.add(scrollPane);// adds the scrollPane to the contentPane
		
		CSVDataList read = new CSVDataList("./data/BBCOnlineData.csv");// Locates the File and it reads it in
		try {
			read.readCSV();
		} catch (IOException e1) {
			e1.printStackTrace();// helps trace exceptions - shows what happened and where it happened in the code
		}
		for(OnlineDataFile d:read.getDataList()){//Looping through the dataList inside the read object
        	textArea1.append(d+"\n");}// to display the values
	}
}
