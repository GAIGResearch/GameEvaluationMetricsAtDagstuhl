package metrics.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SqueezeGraphPanel extends JPanel {

	private float y_axis;

	private Color background_colour;

	private Color line_colour;

	private BufferedImage graph_image;

	public SqueezeGraphPanel(float y_axis, Color background_colour, Color line_colour) {
		this.y_axis = y_axis;
		this.background_colour = background_colour;
		this.line_colour = line_colour;
	}

	public void showData(ArrayList<Float> x_values, ArrayList<Float> y_values) {
		repaint();
		int width = getWidth() * 2;
		int height = getHeight() * 2;
		if (graph_image == null)
			graph_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = graph_image.createGraphics();
		if (graphics != null) {
			try {
				graphics.setColor(background_colour);
				graphics.fillRect(0, 0, width, height);
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				Point old_point = new Point();
				graphics.setColor(line_colour);
				float min_x = 100000000;
				float max_x = -100000000;
				float min_y = 100000000;
				float max_y = -100000000;
				float av_y = 0f;
				int up_count = 0;
				int down_count = 0;
				float up = 0f;
				float down = 0f;
				for (int pos = 0; pos < x_values.size(); pos++) {
					float x = x_values.get(pos);
					float y = y_values.get(pos);
					min_x = Math.min(x, min_x);
					max_x = Math.max(x, max_x);
					min_y = Math.min(y, min_y);
					max_y = Math.max(y, max_y);
					av_y += y;
					if (y < y_axis) {
						down_count++;
						down += y;
					} else if (y > y_axis) {
						up_count++;
						up += y;
					}
				}
				av_y = av_y / (float) x_values.size();
				float av_up = up / (float) up_count;
				float av_down = down / (float) down_count;
				Point p = new Point();
				graphics.setStroke(new BasicStroke(2f));
				for (int pos = 0; pos < x_values.size(); pos++) {
					float x = x_values.get(pos);
					float y = y_values.get(pos);
					getPoint(x, y, min_x, max_x, min_y, max_y, width, height, p);
					if (pos > 0)
						graphics.drawLine(old_point.x, old_point.y, p.x, p.y);
					old_point.x = p.x;
					old_point.y = p.y;
				}
				Color blue = new Color(0, 0, 255, 100);
				graphics.setColor(blue);
				float dash[] = { 20.0f };
				graphics.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
				getPoint(0, y_axis, min_x, max_x, min_y, max_y, width, height, p);
				old_point.x = p.x;
				old_point.y = p.y;
				getPoint(max_x, y_axis, min_x, max_x, min_y, max_y, width, height, p);
				graphics.drawLine(old_point.x, old_point.y, p.x, p.y);

				Color red = new Color(255, 0, 0, 100);
				graphics.setColor(red);
				getPoint(0, av_y, min_x, max_x, min_y, max_y, width, height, p);
				old_point.x = p.x;
				old_point.y = p.y;
				getPoint(max_x, av_y, min_x, max_x, min_y, max_y, width, height, p);
				graphics.drawLine(old_point.x, old_point.y, p.x, p.y);
				graphics.setColor(line_colour);
				graphics.setFont(new Font("Arial", Font.PLAIN,
						30));
				String av_ups = Float.toString(av_up);
				av_ups = av_ups.substring(0, Math.min(av_ups.length(), 5));
				String av_downs = Float.toString(av_down);
				av_downs = av_downs.substring(0, Math.min(av_downs.length(), 5));
				String max_down = Float.toString(min_y);
				max_down = max_down.substring(0, Math.min(max_down.length(), 5));
				String max_up = Float.toString(max_y);
				max_up = max_up.substring(0, Math.min(max_up.length(), 5));
				graphics.drawString("Upper: " + up_count + " (" + av_ups + ", " + max_up + ")", 10,
						40);
				graphics.drawString("Downer: " + down_count + " (" + av_downs + ", " + max_down
						+ ")", 10, 80);
			} finally {
				graphics.dispose();
			}
		}
		repaint();
	}

	private void getPoint(float x, float y, float min_x, float max_x, float min_y, float max_y,
			int width, int height, Point p) {
		float x_range = max_x - min_x;
		float y_range = max_y - min_y;

		y = 0 - y;

		p.x = Math.round(((x - min_x) / x_range) * (float) width);
		if (y_range == 0)
			p.y = Math.round((float) height / 2f);
		else
			p.y = Math.round((y + max_y) / y_range * (float) height);
		if (p.y > height || p.y < 0){
			float diff = y-min_y;
//			System.out.println(min_y + " " + max_y + " " + y_range + " " + y + " " + diff);
//			System.out.println(p.y + " " + height);
		}
	}

	public void paintComponent(Graphics g) {
		if (g != null) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setTransform(AffineTransform.getScaleInstance(0.5d, 0.5d));
			if (graph_image != null)
				g2.drawImage(graph_image, 0, 0, null);
		}
	}

}
