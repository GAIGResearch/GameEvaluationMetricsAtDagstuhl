package metrics.plot;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MetricVisualiser {

	SqueezeGraphPanel graphPanel = new SqueezeGraphPanel(0f, Color.LIGHT_GRAY);
	
	public MetricVisualiser(){
		JFrame frame = new JFrame();
		frame.setSize(1000, 200);
		frame.add(graphPanel);
		frame.setVisible(true);		
		frame.setLocation(0, 500);
		graphPanel.setVisible(true);
	}
	
	public void update(ArrayList<Double> scores){
		ArrayList<Float> x_values = new ArrayList();
		ArrayList<Float> y_values = new ArrayList();
		x_values.add(0f);
		y_values.add(0f);
		for (int pos = 0; pos < scores.size(); pos++){
			x_values.add(new Float(pos + 1));
			y_values.add(new Float(scores.get(pos)));
		}
		graphPanel.showData(x_values, y_values, true, Color.red);
		x_values.clear();
		y_values.clear();
		x_values.add(0f);
		y_values.add(0f);
		for (int pos = 0; pos < scores.size(); pos++){
			x_values.add(new Float(pos + 1));
			int rpos = (int)Math.floor(Math.random() * scores.size());
			y_values.add(new Float(scores.get(rpos)));
		}
		graphPanel.showData(x_values, y_values, false, Color.green);		
	}
	
}
