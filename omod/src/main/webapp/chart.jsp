<%@ page import="java.awt.Image" %>
<%@ page import="java.awt.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.awt.BasicStroke"%>
<%@ page import="java.io.*" %>
<%@ page import="java.io.File"%>
<%@ page import="org.jfree.chart.*" %>
<%@ page import="org.jfree.util.Rotation" %>
<%@ page import="org.jfree.ui.ApplicationFrame" %>
<%@ page import="org.jfree.chart.JFreeChart" %>
<%@ page import="org.jfree.chart.ChartFactory" %>
<%@ page import="org.jfree.chart.plot.PiePlot3D" %>
<%@ page import="org.jfree.data.general.PieDataset" %>
<%@ page import="org.jfree.data.general.DefaultPieDataset" %>
<%
try {
	File image = File.createTempFile("image", "tmp");
	
	// Create the chart class instance
	GraphFrame graph = new GraphFrame("Graph");
	
	JFreeChart chart = graph.createChart("Disease Burden", request);
	ChartUtilities.saveChartAsPNG(image, chart, 1000, 500);
	// Get the input stream
	FileInputStream fileInStream = new FileInputStream(image);
	// Output stream for returning the chart as an image
	OutputStream outStream = response.getOutputStream();
	long fileLength;
	byte[] byteStream;
	fileLength = image.length();
	byteStream = new byte[(int)fileLength];
	// Read the chart image
	fileInStream.read(byteStream, 0, (int)fileLength);
	// Returns the chart image whenever called
	response.setContentType("image/png");
	response.setContentLength((int)fileLength);
	fileInStream.close();
	outStream.write(byteStream);
	outStream.flush();
	outStream.close();
}
catch (IOException e) {
	System.err.println("Problem occured creating chart.");
}
%>
<%! public class GraphFrame extends ApplicationFrame {
	
		public GraphFrame(final String title) {
			super(title);
		}
		
		// Create Chart
		public JFreeChart createChart(String title, HttpServletRequest request) {
	    	PieDataset dataset = this.createDataset(request);
	    	JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
	        PiePlot3D plot = (PiePlot3D) chart.getPlot();
	        plot.setStartAngle(290);
	        plot.setDirection(Rotation.CLOCKWISE);
	        plot.setForegroundAlpha(0.5f);
	        return chart;
	    }
		
		// Create Dataset
	    private  PieDataset createDataset(HttpServletRequest request) {
	    	DefaultPieDataset result = new DefaultPieDataset();
			String diseaseData = (String) request.getAttribute("diseases");
			String[] list = diseaseData.split("\n");
			
			for (String s : list) {
				String[] vals = s.split(":");
				result.setValue(vals[0], Integer.valueOf(vals[1]));			
			}
	        return result;
	    }
}
%>