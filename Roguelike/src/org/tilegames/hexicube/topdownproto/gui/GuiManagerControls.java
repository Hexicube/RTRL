package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.KeyHandler.Key;

import com.badlogic.gdx.graphics.Color;

public class GuiManagerControls extends GuiManagerBase
{
	private GuiElementTextButton back;
	private GuiElementTextButton[] controls;
	private int controlEditing;
	
	public GuiManagerControls()
	{
		elems.add(new GuiElementLabel(5, -25, 0f, 1f, 100, "Controls", false, Color.WHITE));
		
		Key[] keys = Key.values();
		controls = new GuiElementTextButton[keys.length];
		for(int a = 0; a < keys.length; a++)
		{
			Key k = keys[a];
			controls[a] = new GuiElementTextButton(5, -50-a*30, 0f, 1f, 250, k.getName()+": "+Game.keys.getProperName(k), Color.RED);
			elems.add(controls[a]);
		}
		
		back = new GuiElementTextButton(5, 5, 0f, 0f, 110, "Back", Color.RED);
		elems.add(back);
		
		background = GuiManagerMainMenu.mainMenuCol;
	}
	
	@Override
	public void tick()
	{
		if(back.checked())
		{
			Game.setMenu(null);
			Game.keys.saveKeys();
		}
		else
		{
			for(int a = 0; a < controls.length; a++)
			{
				if(controls[a].checked())
				{
					if(controlEditing != -1)
					{
						Key k = Key.values()[controlEditing];
						controls[controlEditing].text = k.getName()+": "+Game.keys.getProperName(k);
					}
					controlEditing = a;
					Key k = Key.values()[a];
					controls[a].text = k.getName()+": PRESS NEW KEY";
				}
			}
		}
		super.tick();
	}
	
	@Override
	public boolean pausesGame()
	{
		return true;
	}
	
	@Override
	public boolean drawBehind()
	{
		return false;
	}
	
	@Override
	public boolean drawAbove()
	{
		return false;
	}
	
	@Override
	public void mousePress(int x, int y, int pointer)
	{
		for(GuiElement e : elems)
		{
			if(e instanceof GuiElementClickable)
			{
				if(((GuiElementClickable)e).gotClicked(x, y, pointer))
				{
					((GuiElementClickable)e).handleClick();
					return;
				}
			}
		}
		if(controlEditing != -1)
		{
			Key k = Key.values()[controlEditing];
			controls[controlEditing].text = k.getName()+": "+Game.keys.getProperName(k);
			controlEditing = -1;
		}
	}
	
	@Override
	public boolean keyPress(int key)
	{
		if(controlEditing == -1) return false;
		Key k = Key.values()[controlEditing];
		Game.keys.setKeyBind(k, key);
		controls[controlEditing].text = k.getName()+": "+Game.keys.getProperName(k);
		controlEditing = -1;
		return true;
	}
}