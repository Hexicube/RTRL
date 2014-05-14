package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementTextButton extends GuiElementButton
{
	private static final Texture leftSide = Game.loadImage("textfield/left"),
								rightSide = Game.loadImage("textfield/right"),
								mainPiece = Game.loadImage("textfield/center"),
								leftSide2 = Game.loadImage("textfield/left2"),
							   rightSide2 = Game.loadImage("textfield/right2"),
							   mainPiece2 = Game.loadImage("textfield/center2");
	
	private int width;
	public String text;
	public Color colour;
	
	public GuiElementTextButton(int x, int y, float xAlign, float yAlign, int width, String text, Color fontColour)
	{
		super(x, y, xAlign, yAlign);
		if(width < 32) width = 32;
		if(width < FontHolder.getTextWidth(FontHolder.getCharList(text), true)) width = FontHolder.getTextWidth(FontHolder.getCharList(text), true);
		this.width = width;
		this.text = text;
		colour = fontColour;
	}
	
	@Override
	public boolean gotClicked(int x, int y, int pointer)
	{
		int[] pos = getPosition();
		if(x >= pos[0] && x < pos[0]+width && y >= pos[1] && y <= pos[1]+16) return true;
		return false;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		int[] pos = getPosition();
		Color c = batch.getColor();
		batch.setColor(1, 1, 1, c.a);
		batch.draw((timer>0)?leftSide2:leftSide, pos[0], pos[1]-12);
		batch.draw((timer>0)?rightSide2:rightSide, pos[0]+width-8, pos[1]-12);
		batch.draw((timer>0)?mainPiece2:mainPiece, pos[0]+4, pos[1]-12, width-12, 32);
		batch.setColor(colour);
		char[] chars = FontHolder.getCharList(text);
		FontHolder.render(batch, chars, pos[0]+2+width/2-FontHolder.getTextWidth(chars, true)/2, pos[1]+17, true);
		batch.setColor(c);
	}
}