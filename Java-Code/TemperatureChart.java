//This class is used for displaying the Temperature reading
package uk.arduino;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;


public class TemperatureChart extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static ArrayList<Double> temp; //making an ArrayList called temp
	public TemperatureChart(String s,ArrayList<Double> tempC)
	{	
		super(s);		
		temp = tempC;
	}
	
	public void create()
	{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //this is used so that all the frames are not closed when you close this windows
		JPanel jpanel = createDemoPanel(); // creates the window
		jpanel.setPreferredSize(new Dimension(500, 270)); //sets the size of the window
		setContentPane(jpanel);
	}

	private static XYDataset createDataset() //method for getting Temperature data to display
	{
		//creating data set
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		XYSeries xyseries = new XYSeries("Celcius");
		int i=1;// declaring variables 
		for(Double temp:temp){ 
			xyseries.add(i,temp);
			++i;
		}
		
		xyseriescollection.addSeries(xyseries);
		return xyseriescollection;
		
		
	}

	private static JFreeChart createChart(XYDataset xydataset) //Method for displaying the Temperature Chart 
	{
		JFreeChart jfreechart = ChartFactory.createXYLineChart("Temperature Chart", "Number of Readings", "Temperature C", xydataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
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

	public static JPanel createDemoPanel()// creating panel inside the window
	{
		JFreeChart jfreechart = createChart(createDataset());
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setMouseWheelEnabled(true);
		return chartpanel;
	}

}