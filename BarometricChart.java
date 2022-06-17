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


public class BarometricChart extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static ArrayList<Double> bmp; //making an ArrayList called
	public BarometricChart(String s,ArrayList<Double> Bmp)
	{	
		super(s);		
		bmp = Bmp;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //frames are not closed when you close this windows
		JPanel jpanel = createDemoPanel(); // creates the window
		jpanel.setPreferredSize(new Dimension(500, 270)); //sets the size of the window
		setContentPane(jpanel);
	}

	private static XYDataset createDataset() //method for getting LDR data to display
	{
		//creating data set
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		XYSeries xyseries = new XYSeries("BMP");
		int i=1; //declaring variables
		for(Double bmp:bmp){
			//System.out.println(i+" "+temp);
			xyseries.add(i,bmp);
			++i;
		}
		
		xyseriescollection.addSeries(xyseries);
		return xyseriescollection;
		
		
	}

	private static JFreeChart createChart(XYDataset xydataset) //Method for displaying the LDR Chart 
	{
		JFreeChart jfreechart = ChartFactory.createXYLineChart(" Barometric Pressure Chart", "Number Of Readings", "Value", xydataset, PlotOrientation.VERTICAL, true, true, false);
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