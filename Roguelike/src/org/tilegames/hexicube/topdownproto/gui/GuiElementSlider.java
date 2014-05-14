package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementSlider extends GuiElementDraggable
{
	private static final Texture leftSide = Game.loadImage("slider/left"),
								rightSide = Game.loadImage("slider/right"),
								mainPiece = Game.loadImage("slider/center"),
								sliderBar = Game.loadImage("slider/bar");
	
	private int width, min, max, pos;
	public Color colour;
	
	public GuiElementSlider(int x, int y, float xAlign, float yAlign, int width, int min, int max, Color fontColour)
	{
		super(x, y, xAlign, yAlign);
		if(width < 32) width = 32;
		this.width = width;
		this.min = min;
		this.max = max;
		pos = 0;
		colour = fontColour;
	}
	
	@Override
	public void handleDrag(int x, int y, int pointer)
	{
		int[] pos = getPosition();
		int relativePosX = x-pos[0];
		if(relativePosX < 0) relativePosX = 0;
		if(relativePosX > width) relativePosX = width;
		this.pos = relativePosX;
	}
	
	@Override
	public void handleRelease()
	{
		Game.currentlyDragging = null;
	}
	
	@Override
	public boolean gotClicked(int x, int y, int pointer)
	{
		int[] pos = getPosition();
		if(x >= pos[0] && x < pos[0]+width && y >= pos[1] && y <= pos[1]+16) return true;
		return false;
	}
	
	@Override
	public void handleClick()
	{
		Game.currentlyDragging = this;
	}
	
	@Override
	public void tick()
	{
		
	}
	
	public int getValue()
	{
		return (min*(width-pos)+max*(pos))/width;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		Color c = batch.getColor();
		batch.setColor(1, 1, 1, c.a);
		int[] pos = getPosition();
		batch.draw(leftSide, pos[0], pos[1]);
		batch.draw(rightSide, pos[0]+width-8, pos[1]);
		batch.draw(mainPiece, pos[0]+8, pos[1], width-16, 16);
		batch.draw(sliderBar, pos[0]+this.pos-4, pos[1]);
		batch.setColor(colour);
		FontHolder.render(batch, FontHolder.getCharList(""+getValue()), pos[0]+width+6, pos[1]+15, true);
		batch.setColor(c);
	}
}