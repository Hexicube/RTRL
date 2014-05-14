package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.gui.GuiManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiManagerInventory extends GuiManager
{
	private EntityPlayer player;
	
	public GuiManagerInventory(EntityPlayer player)
	{
		this.player = player;
		//TODO
		//1. Render inventory code, with a custom gui element class extending clickable
		//2. Restore basic item swap functionality with mouse control
		//3. Make clicking an item show a menu of actions that can be performed
		//4. Add methods to allow items to specify custom actions to perform based on other item (i.e. grindstone with sword)
	}
	
	@Override
	public void tick()
	{
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
	}
	
	@Override
	public void mousePress(int x, int y, int pointer)
	{
	}
	
	@Override
	public boolean keyPress(int key)
	{
		return true; //block input from controling character
	}
	
	@Override
	public boolean pausesGame()
	{
		return false;
	}
	
	@Override
	public boolean drawBehind()
	{
		return true;
	}
}