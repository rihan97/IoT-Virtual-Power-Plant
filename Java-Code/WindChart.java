//This class is used for displaying the LDR reading
package uk.arduino;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;


public class WindChart extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static ArrayList<Double> wind; //making an ArrayList called
	public WindChart(String s,ArrayList<Double> Wind)
	{	
		super(s);		
		wind = Wind;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //frames are not closed when you close this windows
		JPanel jpanel = createDemoPanel(); // creates the window
		jpanel.setPreferredSize(new Dimension(500, 270)); //sets the size of the window
		setContentPane(jpanel);
	}

	private static XYDataset createDataset() //method for getting LDR data to display
	{
		//creating data set
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		XYSeries xyseries = new XYSeries("WIND");
		int i=1; //declaring variables
		for(Double wind:wind){
			//System.out.println(i+" "+temp);
			xyseries.add(i,wind);
			++i;
		}
		
		xyseriescollection.addSeries(xyseries);
		return xyseriescollection;
		
		
	}

	private static JFreeChart createChart(XYDataset xydataset) //Method for displaying the LDR Chart 
	{
		JFreeChart jfreechart = ChartFactory.createXYLineChart(" Wind Speed Chart", "Number Of Readings", "Value", xydataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		xyplot.setDomainPannable(true);
		xyplot.setRangePannable(true);
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
		xylineandshaperenderer.setBaseShapesVisible(true);
		xylineandshaperenderer.setBaseShapesFilled(true);
		NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return jfreechart;
	}

	public static JPanel createDemoPanel() // creating panel inside the window
	{
		JFreeChart jfreechart = createChart(createDataset());
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setMouseWheelEnabled(true);
		return chartpanel;
	}

}