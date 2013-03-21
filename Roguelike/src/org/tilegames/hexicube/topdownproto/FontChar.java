package org.tilegames.hexicube.topdownproto;

public class FontChar {
	public FontChar(int character, int x, int y, int width)
	{
		this.character = character;
		this.x = x;
		this.y = y;
		this.width = width;
	}
	
	int x, y, width, character;
}