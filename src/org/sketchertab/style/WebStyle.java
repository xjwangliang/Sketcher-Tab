package org.sketchertab.style;

import java.util.ArrayList;
import java.util.HashMap;

import org.sketchertab.Style;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

class WebStyle extends StyleBrush {
	private float prevX;
	private float prevY;

	private ArrayList<PointF> points = new ArrayList<PointF>();

	{
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
	}

	public void stroke(Canvas c, float x, float y) {
		PointF current = new PointF(x, y);
		points.add(current);

		c.drawLine(prevX, prevY, x, y, paint);

		float dx;
		float dy;
		float length;

		for (int i = 0, max = points.size(); i < max; i++) {
			PointF point = points.get(i);

			dx = point.x - current.x;
			dy = point.y - current.y;

			length = dx * dx + dy * dy;

			if (length < 2500 && Math.random() > 0.9) {
				c.drawLine(current.x, current.y, point.x, point.y, paint);
			}
		}

		prevX = x;
		prevY = y;
	}

	public void strokeStart(float x, float y) {
		prevX = x;
		prevY = y;
	}

	public void draw(Canvas c) {
	}

	public void setColor(int color) {
		paint.setColor(color);
	}

	public void saveState(HashMap<Integer, Object> state) {
		ArrayList<PointF> points = new ArrayList<PointF>();
		points.addAll(this.points);
		state.put(StylesFactory.WEB, points);
	}

	@SuppressWarnings("unchecked")
	public void restoreState(HashMap<Integer, Object> state) {
		this.points.clear();
		ArrayList<PointF> points = (ArrayList<PointF>) state.get(StylesFactory.WEB);
		this.points.addAll(points);
	}
}
