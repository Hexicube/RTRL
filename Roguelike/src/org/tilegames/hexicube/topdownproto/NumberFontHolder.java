package org.tilegames.hexicube.topdownproto;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberFontHolder
{
	public static Texture font;
	
	private static final int[] charPairing = new int[256];
	
	public static void prep()
	{
		font = Game.loadImage("font2");
		
		charPairing["a".charAt(0)] = 1;
		charPairing["b".charAt(0)] = 2;
		charPairing["c".charAt(0)] = 3;
		charPairing["d".charAt(0)] = 4;
		charPairing["e".charAt(0)] = 5;
		charPairing["f".charAt(0)] = 6;
		charPairing["g".charAt(0)] = 7;
		charPairing["h".charAt(0)] = 8;
		charPairing["i".charAt(0)] = 9;
		charPairing["j".charAt(0)] = 10;
		charPairing["k".charAt(0)] = 11;
		charPairing["l".charAt(0)] = 12;
		charPairing["m".charAt(0)] = 13;
		charPairing["n".charAt(0)] = 14;
		charPairing["o".charAt(0)] = 256-16+1;
		charPairing["p".charAt(0)] = 256-16+2;
		charPairing["q".charAt(0)] = 256-16+3;
		charPairing["r".charAt(0)] = 256-16+4;
		charPairing["s".charAt(0)] = 256-16+5;
		charPairing["t".charAt(0)] = 256-16+6;
		charPairing["u".charAt(0)] = 256-16+7;
		charPairing["v".charAt(0)] = 256-16+8;
		charPairing["w".charAt(0)] = 256-16+9;
		charPairing["x".charAt(0)] = 256-16+10;
		charPairing["y".charAt(0)] = 256-16+11;
		charPairing["z".charAt(0)] = 256-16+12;
		
		charPairing["A".charAt(0)] = 1;
		charPairing["B".charAt(0)] = 2;
		charPairing["C".charAt(0)] = 3;
		charPairing["D".charAt(0)] = 4;
		charPairing["E".charAt(0)] = 5;
		charPairing["F".charAt(0)] = 6;
		charPairing["G".charAt(0)] = 7;
		charPairing["H".charAt(0)] = 8;
		charPairing["I".charAt(0)] = 9;
		charPairing["J".charAt(0)] = 10;
		charPairing["K".charAt(0)] = 11;
		charPairing["L".charAt(0)] = 12;
		charPairing["M".charAt(0)] = 13;
		charPairing["N".charAt(0)] = 14;
		charPairing["O".charAt(0)] = 256-16+1;
		charPairing["P".charAt(0)] = 256-16+2;
		charPairing["Q".charAt(0)] = 256-16+3;
		charPairing["R".charAt(0)] = 256-16+4;
		charPairing["S".charAt(0)] = 256-16+5;
		charPairing["T".charAt(0)] = 256-16+6;
		charPairing["U".charAt(0)] = 256-16+7;
		charPairing["V".charAt(0)] = 256-16+8;
		charPairing["W".charAt(0)] = 256-16+9;
		charPairing["X".charAt(0)] = 256-16+10;
		charPairing["Y".charAt(0)] = 256-16+11;
		charPairing["Z".charAt(0)] = 256-16+12;
	}
	
	public static int[] encodeString(String s)
	{
		char[] text = s.toCharArray();
		int[] values = new int[text.length];
		for(int a = 0; a < text.length; a++)
		{
			values[a] = charPairing[text[a]];
		}
		return values;
	}
	
	public static int getTextWidth(long val, boolean doubleScale)
	{
		if(val == 0) return doubleScale?10:5;
		int width = 0;
		if(val < 0) val *= -1;
		while(val > 0)
		{
			width += 6;
			val /= 16;
		}
		if(doubleScale) width *= 2;
		return width;
	}
	
	public static int getTextWidth(int[] values, boolean doubleScale)
	{
		int width = 0;
		for(int a = 0; a < values.length; a++)
		{
			int val = values[a];
			if(val == 0)
			{
				width += 9;
				continue;
			}
			if(val < 0) val *= -1;
			while(val > 0)
			{
				width += 6;
				val /= 16;
			}
			if(a+1 < values.length) width += 3;
		}
		if(doubleScale) width *= 2;
		return width;
	}
	
	public static void render(SpriteBatch batch, long val, int x, int y, boolean doubleScale)
	{
		int xPos = 0;
		if(val == 0)
		{
			batch.draw(font, x + xPos, y - (doubleScale ? 18 : 9), (doubleScale ? 10 : 5), doubleScale ? 18 : 9, 0, 0, 5, 9, false, false);
			return;
		}
		if(val < 0) val *= -1;
		while(val > 0)
		{
			int small = (int)(val%16);
			batch.draw(font, x + xPos, y - (doubleScale ? 18 : 9), (doubleScale ? 10 : 5), doubleScale ? 18 : 9, (small%4) * 6, (small/4) * 10, 5, 9, false, false);
			xPos += doubleScale?12:6;
			val /= 16;
		}
	}
	
	public static void render(SpriteBatch batch, int[] values, int x, int y, boolean doubleScale)
	{
		int xPos = 0;
		for(int a = 0; a < values.length; a++)
		{
			int val = values[a];
			if(val == 0)
			{
				batch.draw(font, x + xPos, y - (doubleScale ? 18 : 9), (doubleScale ? 10 : 5), doubleScale ? 18 : 9, 0, 0, 5, 9, false, false);
				xPos += doubleScale?18:9;
				continue;
			}
			if(val < 0) val *= -1;
			while(val > 0)
			{
				int small = (int)(val%16);
				batch.draw(font, x + xPos, y - (doubleScale ? 18 : 9), (doubleScale ? 10 : 5), doubleScale ? 18 : 9, (small%4) * 6, (small/4) * 10, 5, 9, false, false);
				xPos += doubleScale?12:6;
				val /= 16;
			}
			xPos += doubleScale?6:3;
		}
	}
}