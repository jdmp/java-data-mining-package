package org.jdmp.gui.util;

import java.awt.Color;

public abstract class ColorUtil {
	public static final Color[] TRACECOLORS = { Color.blue, Color.green, Color.red, Color.black, Color.yellow,
			Color.cyan };

	public static final Color contrastBW(Color c) {
		if ((c.getRed() + c.getGreen() + c.getBlue()) > 200.0) {
			return (Color.black);
		} else {
			return (Color.white);
		}
	}

	public static final Color fromDouble(double v) {
		// inf = 255 255 0 yellow
		// 1 = 0 255 0 green
		// 0 = 0 0 0 black
		// -1 = 255 0 0 red
		// -inf = 255 0 255 magenta
		// nan = 0 255 255 cyan
		if (v == Double.MIN_VALUE || Double.isNaN(v))
			return (Color.MAGENTA);
		else if (Double.isInfinite(v))
			return (Color.CYAN);
		else if (v > 1.0)
			return (ColorMap.colorGreenToYellow[(int) (255.0 * Math.tanh((v - 1.0) / 10.0))]);
		else if (v > 0.0)
			return (ColorMap.colorBlackToGreen[(int) (255.0 * v)]);
		else if (v > -1.0)
			return (ColorMap.colorRedToBlack[(int) (255.0 * (v + 1.0))]);
		else
			return (ColorMap.colorRedToMagenta[(int) (255.0 * Math.tanh((-v - 1.0) / 10.0))]);
	}

	private static Color fromString(String s) {
		if (s == null)
			return Color.black;
		int hc = Math.abs(hash(s.hashCode()));
		int r = 192 + (hc % 256) / 4;
		hc = hc / 256;
		int g = 192 + (hc % 256) / 4;
		hc = hc / 256;
		int b = 192 + (hc % 256) / 4;
		return new Color(r > 255 ? 255 : r, g > 255 ? 255 : g, b > 255 ? 255 : b);
	}

	public static Color fromObject(Object v) {
		if (v == null) {
			return Color.black;
		}
		if (v instanceof Double) {
			return fromDouble((Double) v);
		}
		try {
			double d = Double.parseDouble(v.toString());
			return fromDouble(d);
		} catch (Exception e) {
		}
		return fromString(v.toString());
	}

	private static int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

}
