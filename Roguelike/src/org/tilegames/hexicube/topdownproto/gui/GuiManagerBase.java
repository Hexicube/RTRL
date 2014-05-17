package org.tilegames.hexicube.topdownproto.gui;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GuiManagerBase extends GuiManager
{
	protected ArrayList<GuiElement> elems;
	protected Color background;
	protected static Texture bgTex;
	static
	{
		Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		p.setColor(1, 1, 1, 1);
		p.drawPixel(0, 0);
		bgTex = new Texture(p);
	}
	
	public GuiManagerBase()
	{
		elems = new ArrayList<GuiElement>();
	}
	
	@Override
	public void tick()
	{
		if(parent != null) parent.tick();
		for(GuiElement e : elems)
		{
			e.tick();
		}
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		if(parent != null && parent.drawAbove()) parent.render(batch);
		else
		{
			batch.setColor(background);
			batch.draw(bgTex, 0, 0, Game.width, Game.height, 0, 0, 1, 1, false, false);
		}
		batch.setColor(1, 1, 1, 1);
		for(GuiElement e : elems)
		{
			e.render(batch);
		}
	}
	
	@Override
	public void mousePress(int x, int y, int pointer)
	{
		int size = elems.size();
		for(int a = size-1; a >= 0; a--) //prioritises front elements
		{
			GuiElement e = elems.get(a);
			if(e instanceof GuiElementClickable)
			{
				if(((GuiElementClickable)e).gotClicked(x, y, pointer))
				{
					((GuiElementClickable)e).handleClick();
					return;
				}
			}
		}
	}
	
	@Override
	public boolean keyPress(int key)
	{
		return false;
	}
}