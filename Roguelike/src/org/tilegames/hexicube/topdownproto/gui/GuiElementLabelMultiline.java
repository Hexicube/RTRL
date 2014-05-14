package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.FontHolder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementLabelMultiline extends GuiElement
{
	public String[] text;
	public Color colour;
	
	public GuiElementLabelMultiline(int x, int y, float xAlign, float yAlign, String[] text, Color fontColour)
	{
		super(x, y, xAlign, yAlign);
		this.text = text;
		colour = fontColour;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		int[] pos = getPosition();
		Color c = batch.getColor();
		batch.setColor(colour);
		for(int a = 0; a < text.length; a++)
		{
			FontHolder.render(batch, FontHolder.getCharList(text[a]), pos[0]+2, pos[1]+17-a*20, true);
		}
		batch.setColor(c);
	}

	@Override
	public void tick(){}
}