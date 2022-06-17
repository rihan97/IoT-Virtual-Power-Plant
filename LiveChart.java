//This class is used for displaying the live Chart for the three sensors temperature, motion and ldr
package uk.arduino;


import java.awt.Dimension;
import java.util.ArrayList;
//import java.util.List;


import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;


public class LiveChart extends JFrame {
	
	//Creating a list for each data type to load data from the files
	private static ArrayList<Double> Temperature;// creating array List for Temperature 
	private static ArrayList<Double> LDR;
    private static ArrayList<Double> Humidity;
    private static ArrayList<Double> Wind;
    private static ArrayList<Double> Baro;
	private static final long serialVersionUID = 1L;
	
	public LiveChart(String s,ArrayList<Double> temp,ArrayList<Double> light,ArrayList<Double> Ht,ArrayList<Double> Wnd,ArrayList<Double> Pressure) // Inserting the lists of the data for chart
	{
		super(s);
		Temperature = temp;
		LDR = light;
		Humidity = Ht;
		Wind = Wnd;
		Baro = Pressure;
		
	}
	
	public void create() //Creating window to display the live chart
	{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //this is used so that all the frames are not closed when you close this windows
		JPanel jpanel = createPanel(); // creates the window
		jpanel.setPreferredSize(new Dimension(650, 400)); //sets the size of the window
		setContentPane(jpanel);
	}
	
		
	//Creating the dataset
	private XYDataset createDataset()
	{
				XYSeriesCollection xyseriescollection = new XYSeriesCollection();
				XYSeries xyseries = new XYSeries("Celcius"); //Create the reading for temperature
				XYSeries xyseries1 = new XYSeries("Light"); //Create the reading for the light
				XYSeries xyseries2 = new XYSeries("Humidity"); //Create the reading for the Humidity
				XYSeries xyseries3 = new XYSeries("Wind"); //Create the reading for the Wind
				XYSeries xyseries4 = new XYSeries("Pressure"); //Create the reading for the Pressure
				int i=1;
				
				for(Double temp:Temperature){ // reading the lists and plots the values for Temperature 
					xyseries.add(i,temp);
					++i;
				}
				i=1;
				
				for(Double light:LDR){ //reading the lists and plots the values for Light
					xyseries1.add(i,light);
					++i;
				}
				i=1;
				
				for(Double ht:Humidity){ ////reading the lists and plots the values for Humidity
					xyseries2.add(i,ht);
					++i;
				}
				i=1;
				
				for(Double wnd:Wind){ ////reading the lists and plots the values for Wind
					xyseries3.add(i,wnd);
					++i;
				}
				i=1;
				
				for(Double pressure:Baro){ ////reading the lists and plots the values for Pressure
					xyseries4.add(i,pressure);
					++i;
				}
				xyseriescollection.addSeries(xyseries);//Add's the line to the collection
				xyseriescollection.addSeries(xyseries1);//Add's the line to the collection
				xyseriescollection.addSeries(xyseries2);//Add's the line to the collection
				xyseriescollection.addSeries(xyseries3);//Add's the line to the collection
				xyseriescollection.addSeries(xyseries4);//Add's the line to the collection
				return xyseriescollection; //Returns the collection
				
	}
	
		
	private JFreeChart createChart(XYDataset xydataset, String str)// //Method for creating and displaying the Live Chart 
	{
		//Set the headers for the panels
		JFreeChart jfreechart = ChartFactory.createXYLineChart(str , "Number Of Readings", "Value", xydataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot(); //Creating  object 
		ValueAxis axis = xyplot.getDomainAxis();
		axis.setAutoRange(true);
        axis.setFixedAutoRange(10.0);
		xyplot.setDomainPannable(true);
		xyplot.setRangePannable(true);
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
		xylineandshaperenderer.setBaseShapesVisible(true);
		xylineandshaperenderer.setBaseShapesFilled(true);
		NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return jfreechart;
	}
	

	public JPanel createPanel() //creating panel inside the window
	{
		JFreeChart jfreechart = createChart(createDataset(),"Live Chart for  Temp, LDR, Humidity, Wind, Pressure");
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setMouseWheelEnabled(true);
		return chartpanel;
	}
}
