package org.sketchertab.style;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.*;
import android.util.Log;
import org.sketchertab.Style;

class SketchyStyle extends StyleBrush {
	private float prevX;
	private float prevY;

	private ArrayList<PointF> points = new ArrayList<PointF>();

	{
		paint.setAntiAlias(true);
	}

    @Override
    public void setOpacity(int opacity) {
        super.setOpacity((int) (opacity * 0.5f));
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

			if (length < 4000 && Math.random() > (length / 2000)) {
                float ddx = dx * 0.2F;
                float ddy = dy * 0.2F;
                c.drawLine(current.x + ddx, current.y + ddy, point.x - ddx,
                        point.y - ddy, paint);
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

	public void saveState(HashMap<Integer, Object> state) {
		ArrayList<PointF> points = new ArrayList<PointF>();
		points.addAll(this.points);
		state.put(StylesFactory.SKETCHY, points);
	}

	@SuppressWarnings("unchecked")
	public void restoreState(HashMap<Integer, Object> state) {
		this.points.clear();
		ArrayList<PointF> points = (ArrayList<PointF>) state
				.get(StylesFactory.SKETCHY);
		this.points.addAll(points);
	}
}
