package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementTextInput extends GuiElementClickable
{
	protected static final Texture leftSide = Game.loadImage("textfield/left"),
									rightSide = Game.loadImage("textfield/right"),
									mainPiece = Game.loadImage("textfield/center");
	
	protected int maxWidth, ticker;
	public Color colour;
	public String text;
	
	public GuiElementTextInput(int x, int y, float xAlign, float yAlign, int maxWidth, Color fontColour)
	{
		super(x, y, xAlign, yAlign);
		if(maxWidth < 32) maxWidth = 32;
		this.maxWidth = maxWidth;
		text = "";
		colour = fontColour;
	}
	
	@Override
	public boolean gotClicked(int x, int y, int pointer)
	{
		int[] pos = getPosition();
		if(x >= pos[0] && x < pos[0]+maxWidth && y >= pos[1] && y <= pos[1]+16) return true;
		return false;
	}
	
	@Override
	public void handleClick()
	{
		Game.currentlyTyping = this;
		ticker = 0;
	}
	
	@Override
	public void tick()
	{
		ticker++;
		if(ticker >= 100) ticker = 0;
	}
	
	public String getValue()
	{
		return text;
	}
	
	public void keyType(char key)
	{
		if(key == 8)
		{
			if(text.length() > 0) text = text.substring(0, text.length()-1);
		}
		else if(key == 27 || key == 13) Game.currentlyTyping = null;
		else
		{
			if(FontHolder.isValid(key))
			{
				if(FontHolder.getTextWidth(FontHolder.getCharList(text+key), true) <= maxWidth-2) text += key;
			}
		}
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		int[] pos = getPosition();
		Color c = batch.getColor();
		batch.setColor(1, 1, 1, c.a);
		batch.draw(leftSide, pos[0], pos[1]-12);
		batch.draw(rightSide, pos[0]+maxWidth-8, pos[1]-12);
		batch.draw(mainPiece, pos[0]+4, pos[1]-12, maxWidth-12, 32);
		batch.setColor(colour);
		FontHolder.render(batch, FontHolder.getCharList(getValue()+(Game.currentlyTyping==this?((ticker<50)?"|":""):"")), pos[0]+2, pos[1]+17, true);
		batch.setColor(c);
	}
}