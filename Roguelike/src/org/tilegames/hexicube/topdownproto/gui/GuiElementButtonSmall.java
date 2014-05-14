package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementButtonSmall extends GuiElementButton
{
	private static final Texture tex1 = Game.loadImage("button/small/inactive");
	private static final Texture tex2 = Game.loadImage("button/small/active");
	
	public GuiElementButtonSmall(int x, int y, float xAlign, float yAlign)
	{
		super(x, y, xAlign, yAlign);
	}
	
	@Override
	public boolean gotClicked(int x, int y, int pointer)
	{
		int[] pos = getPosition();
		if(x >=  pos[0] && x < pos[0]+16 && y >= pos[1] && y <= pos[1]+16) return true;
		return false;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		Color c = batch.getColor();
		batch.setColor(1, 1, 1, c.a);
		int[] pos = getPosition();
		batch.draw((timer>0)?tex2:tex1, pos[0], pos[1]);
		batch.setColor(c);
	}
}