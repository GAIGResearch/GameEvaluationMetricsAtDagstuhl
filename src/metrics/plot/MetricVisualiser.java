package metrics.plot;

import java.awt.Color;
import java.util.*;

import javax.swing.JFrame;

import metrics.LoggableGameState;

public class MetricVisualiser {

	HashMap<Integer, SqueezeGraphPanel> panels = new HashMap<Integer, SqueezeGraphPanel>();
	HashMap<Integer, String> panelNames = new HashMap<Integer, String>();

	HashMap<Integer, SqueezeGraphPanel> multiPanels = new HashMap<Integer, SqueezeGraphPanel>();
	HashMap<Integer, String> multiPanelNames = new HashMap<Integer, String>();

	HashMap<String, ArrayList<Double>> objectCounts = new HashMap<String, ArrayList<Double>>();

	ArrayList<Color> colours = new ArrayList();
	
	int tickCount = 0;
	
	public MetricVisualiser(){
		colours.add(Color.red);
		colours.add(Color.blue);
		colours.add(Color.black);
		colours.add(Color.yellow);
		colours.add(Color.green);
		colours.add(Color.orange);
		colours.add(Color.cyan);
		colours.add(Color.magenta);
		colours.add(Color.white);
		colours.add(Color.pink);
	}
	
	public void update(Map<String,ArrayList<Double>> measures, Map<String,Integer> groupsMap){
		tickCount++;
		showSinglePlots(measures);
		showMultiPlots(measures, groupsMap);
	}

	private void showSinglePlots(Map<String,ArrayList<Double>> measures) {
		Iterator<Map.Entry<Integer, SqueezeGraphPanel>> itPanels = panels.entrySet().iterator();

		while (itPanels.hasNext())
		{
			Map.Entry<Integer, SqueezeGraphPanel> next = itPanels.next();
			int panelID = next.getKey();
			SqueezeGraphPanel panel = next.getValue();
			String panelName = panelNames.get(panelID);

			ArrayList<Double> data = measures.get(panelName);
			plotSingleTrend(data, panel);
		}
	}

	private void plotSingleTrend(ArrayList<Double> data, SqueezeGraphPanel panel)
	{
		ArrayList<Float> x_values = new ArrayList();
		ArrayList<Float> y_values = new ArrayList();
		x_values.add(0f);
		y_values.add(0f);
		for (int pos = 0; pos < data.size(); pos++){
			x_values.add(new Float(pos + 1));
			y_values.add(new Float(data.get(pos)));
		}

		panel.showData(x_values, y_values, true, Color.red);
	}

	private void showMultiPlots(Map<String,ArrayList<Double>> measures, Map<String,Integer> groupsMap)
	{
		Iterator<Map.Entry<Integer, SqueezeGraphPanel>> itPanels = multiPanels.entrySet().iterator();

		while (itPanels.hasNext())
		{
			Map.Entry<Integer, SqueezeGraphPanel> next = itPanels.next();
			int panelID = next.getKey();
			SqueezeGraphPanel panel = next.getValue();

			ArrayList<ArrayList<Double>> allData = new ArrayList<>();
			Iterator<Map.Entry<String, Integer>> itGroups = groupsMap.entrySet().iterator();
			while (itGroups.hasNext())
			{
				Map.Entry<String, Integer> nextGroup = itGroups.next();
				String dataName = nextGroup.getKey();
				int dataIntId = nextGroup.getValue();

				if(dataIntId == panelID)
					allData.add(measures.get(dataName));
			}

			plotMultiTrend(allData, panel);
		}
	}

	private void plotMultiTrend(ArrayList<ArrayList<Double>> data, SqueezeGraphPanel panel)
	{
		Iterator<Color> colsIt = colours.iterator();
		boolean clearIt = true;
		for(ArrayList<Double> list : data)
		{
			ArrayList<Float> x_values = new ArrayList();
			ArrayList<Float> y_values = new ArrayList();
			x_values.add(0f);
			y_values.add(0f);

			for (int pos = 0; pos < tickCount; pos++){
				x_values.add(new Float(pos + 1));
				y_values.add(new Float(list.get(pos)));
			}

			Color nextColor = colsIt.hasNext() ? colsIt.next() : null;
			if(nextColor == null)
			{
				colsIt = colours.iterator();
				nextColor = colsIt.next();
			}

			panel.showData(x_values, y_values, clearIt, nextColor);
			clearIt = false;
		}

	}

	public void addSinglePlots (int []plotIDs, String []plotNames)
	{
		for(int idx = 0; idx < plotIDs.length; ++idx)
		{
			int id = plotIDs[idx];
			String name = plotNames[idx];

			SqueezeGraphPanel panel = new SqueezeGraphPanel(0f, Color.LIGHT_GRAY);
			panels.put(id, panel);
			panelNames.put(id, name);

			JFrame frame1 = new JFrame();
			frame1.setSize(960, 200);
			frame1.add(panel);
			frame1.setVisible(true);
			frame1.setLocation(0, 400);
			panel.setVisible(true);
			frame1.setTitle(name);
		}
	}

	public void addMultiPlots (int []plotIDs, String []plotNames)
	{
		for(int idx = 0; idx < plotIDs.length; ++idx)
		{
			int id = plotIDs[idx];
			String name = plotNames[idx];

			SqueezeGraphPanel panel = new SqueezeGraphPanel(0f, Color.LIGHT_GRAY);
			multiPanels.put(id, panel);
			multiPanelNames.put(id, name);

			JFrame frame1 = new JFrame();
			frame1.setSize(960, 200);
			frame1.add(panel);
			frame1.setVisible(true);
			frame1.setLocation(0, 400);
			panel.setVisible(true);
			frame1.setTitle(name);
		}
	}
	
}
