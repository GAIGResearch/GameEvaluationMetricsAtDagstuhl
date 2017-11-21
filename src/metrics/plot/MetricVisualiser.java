package metrics.plot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import metrics.LoggableGameState;

public class MetricVisualiser {

	SqueezeGraphPanel scoreGraphPanel = new SqueezeGraphPanel(0f, Color.LIGHT_GRAY);
	
	SqueezeGraphPanel objectCountsGraphPanel = new SqueezeGraphPanel(0f, Color.LIGHT_GRAY);
	
	ArrayList<Double> scores = new ArrayList();
	
	HashMap<String, ArrayList<Double>> objectCounts = new HashMap<String, ArrayList<Double>>();
	
	HashMap<String, Color> coloursHash = new HashMap();
	
	int tickCount = 0;
	
	public MetricVisualiser(){
		coloursHash.put("base", Color.red);
		coloursHash.put("alienBlue", Color.blue);
		coloursHash.put("bomb", Color.black);
		coloursHash.put("sam", Color.yellow);
		
		JFrame frame1 = new JFrame();
		frame1.setSize(960, 200);
		frame1.add(scoreGraphPanel);
		frame1.setVisible(true);		
		frame1.setLocation(0, 400);
		scoreGraphPanel.setVisible(true);
		frame1.setTitle("Score");

		JFrame frame2 = new JFrame();
		frame2.setSize(960, 200);
		frame2.add(objectCountsGraphPanel);
		frame2.setVisible(true);		
		frame2.setLocation(0, 600);
		scoreGraphPanel.setVisible(true);
		frame2.setTitle("Object counts     red=base, blue=alienBlue, black=bomb, yellow=sam");

	}
	
	public void update(LoggableGameState gameState){
		tickCount++;
		showScores(gameState);
		showObjectCounts(gameState);
	}
	
	public void showObjectCounts(LoggableGameState gameState){
		HashMap<String, Integer> objectCountsHash = gameState.getGameObjects();
		for (String key : objectCountsHash.keySet()){
			if (!objectCounts.containsKey(key)){
				ArrayList<Double> counts = new ArrayList();
				objectCounts.put(key, counts);
				for (int pos = 0; pos < tickCount - 1; pos++){
					counts.add(0d);
				}
			}
			objectCounts.get(key).add((double)objectCountsHash.get(key));
		}
		for (String key : objectCounts.keySet()){
			if (!objectCountsHash.containsKey(key)){
				objectCounts.get(key).add(0d);
			}
		}
		boolean clearIt = true;
		for (String key : objectCounts.keySet()){
			System.out.println(key);
			ArrayList<Float> x_values = new ArrayList();
			ArrayList<Float> y_values = new ArrayList();
			x_values.add(0f);
			y_values.add(0f);
			ArrayList<Double> counts = objectCounts.get(key);
			for (int pos = 0; pos < tickCount; pos++){
				x_values.add(new Float(pos + 1));
				y_values.add(new Float(counts.get(pos)));
			}
			objectCountsGraphPanel.showData(x_values, y_values, clearIt, coloursHash.get(key));
			clearIt = false;
		}
	}
	
	public void showScores(LoggableGameState gameState){
		Double score = gameState.getScore();
		scores.add(score);
		
		ArrayList<Float> x_values = new ArrayList();
		ArrayList<Float> y_values = new ArrayList();
		x_values.add(0f);
		y_values.add(0f);
		for (int pos = 0; pos < scores.size(); pos++){
			x_values.add(new Float(pos + 1));
			y_values.add(new Float(scores.get(pos)));
		}
		scoreGraphPanel.showData(x_values, y_values, true, Color.red);
	}
	
}
