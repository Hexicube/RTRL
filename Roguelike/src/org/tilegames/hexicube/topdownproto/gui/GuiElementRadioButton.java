package org.tilegames.hexicube.topdownproto.gui;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementRadioButton extends GuiElementClickable
{
	private static final Texture tex1 = Game.loadImage("radiobutton/inactive");
	private static final Texture tex2 = Game.loadImage("radiobutton/active");
	
	private boolean active;
	
	private ArrayList<GuiElementRadioButton> otherButtons;
	
	public GuiElementRadioButton(int x, int y, float xAlign, float yAlign, boolean startValue, ArrayList<GuiElementRadioButton> otherButtons)
	{
		super(x, y, xAlign, yAlign);
		if(startValue)
		{
			for(GuiElementRadioButton e : otherButtons)
			{
				e.active = false;
			}
		}
		active = startValue;
		this.otherButtons = otherButtons;
	}
	
	@Override
	public boolean gotClicked(int x, int y, int pointer)
	{
		int[] pos = getPosition();
		if(x >= pos[0] && x < pos[0]+16 && y >= pos[1] && y <= pos[1]+16) return true;
		return false;
	}
	
	@Override
	public void handleClick()
	{
		for(GuiElementRadioButton e : otherButtons)
		{
			e.active = false;
		}
		active = true;
	}
	
	@Override
	public void tick() {}
	
	@Override
	public void render(SpriteBatch batch)
	{
		Color c = batch.getColor();
		batch.setColor(1, 1, 1, c.a);
		int[] pos = getPosition();
		batch.draw(active?tex2:tex1, pos[0], pos[1]);
		batch.setColor(c);
	}
	
	public boolean checked()
	{
		return active;
	}
}