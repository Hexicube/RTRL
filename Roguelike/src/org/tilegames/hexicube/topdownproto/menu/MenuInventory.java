package org.tilegames.hexicube.topdownproto.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuInventory extends Menu
{

	private int currentItem;
	private boolean itemSelected;
	
	private int getItemPos(int x, int y)
	{
		
	}
	
	@Override
	public void keyPress(int key)
	{
		//TODO: inventory controls, i.e. close inv
	}
	
	@Override
	public void keyRelease(int key) {}
	
	@Override
	public void keyTyped(char key) {}
	
	@Override
	public void mouseDown(int x, int y, boolean rightMouse)
	{
		if(rightMouse)
		{
			//TODO: context menu
		}
		else
		{
			int pos = getItemPos(x, y);
			if(currentItem == -1)
			{
				currentItem = pos;
				itemSelected = false;
			}
			else
			{
				if(pos != currentItem)
				{
					//TODO: item swap
				}
				currentItem = -1;
			}
		}
	}
	
	@Override
	public void mouseUp(int x, int y, boolean rightMouse)
	{
		if(rightMouse)
		{
			//TOO: context menu
		}
		else
		{
			if(currentItem != -1)
			{
				int pos = getItemPos(x, y);
				if(pos == currentItem) itemSelected = true;
				else
				{
					//TODO: item swap
					currentItem = -1;
				}
			}
		}
	}
	
	@Override
	public void mouseMove(int x, int y) {}
	
	@Override
	public void mouseDrag(int x, int y, boolean rightMouse)
	{
		//TODO: drag visuals?
	}
	
	@Override
	public boolean pausesGame()
	{
		return false;
	}
	
	@Override
	public void tick() {}
	
	@Override
	public void render(SpriteBatch batch)
	{
		//TODO: render inventory
	}
}