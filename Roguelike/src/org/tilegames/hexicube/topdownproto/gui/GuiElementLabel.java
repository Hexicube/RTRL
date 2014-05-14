package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementLabel extends GuiElement
{
	private static final Texture leftSide = Game.loadImage("textfield/left"),
								rightSide = Game.loadImage("textfield/right"),
								mainPiece = Game.loadImage("textfield/center");
	
	private int width;
	private boolean background;
	public String text;
	public Color colour;
	
	public GuiElementLabel(int x, int y, float xAlign, float yAlign, int width, String text, boolean background, Color fontColour)
	{
		super(x, y, xAlign, yAlign);
		if(width < 32) width = 32;
		if(width < FontHolder.getTextWidth(FontHolder.getCharList(text), true)) width = FontHolder.getTextWidth(FontHolder.getCharList(text), true);
		this.width = width;
		this.text = text;
		this.background = background;
		colour = fontColour;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		int[] pos = getPosition();
		Color c = batch.getColor();
		if(background)
		{
			batch.setColor(1, 1, 1, c.a);
			batch.draw(leftSide, pos[0], pos[1]-12);
			batch.draw(rightSide, pos[0]+width-8, pos[1]-12);
			batch.draw(mainPiece, pos[0]+4, pos[1]-12, width-12, 32);
		}
		batch.setColor(colour);
		FontHolder.render(batch, FontHolder.getCharList(text), pos[0]+2, pos[1]+17, true);
		batch.setColor(c);
	}

	@Override
	public void tick(){}
}