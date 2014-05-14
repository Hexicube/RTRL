package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementPasswordInput extends GuiElementTextInput
{
	public GuiElementPasswordInput(int x, int y, float xAlign, float yAlign, int maxWidth, Color fontColour)
	{
		super(x, y, xAlign, yAlign, maxWidth, fontColour);
	}
	
	@Override
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
				if(FontHolder.getTextWidth(FontHolder.getCharList((text+key).replaceAll(".", "*")), true) <= maxWidth-2) text += key;
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
		FontHolder.render(batch, FontHolder.getCharList(getValue().replaceAll(".", "*")+(Game.currentlyTyping==this?((ticker<50)?"|":""):"")), pos[0]+2, pos[1]+17, true);
		batch.setColor(c);
	}
}