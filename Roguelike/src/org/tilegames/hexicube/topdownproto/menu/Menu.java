package org.tilegames.hexicube.topdownproto.menu;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Menu
{
	private Menu parent;
	
	public void openInventory()
	{
		parent = Game.menu;
		Game.menu = this;
	}
	
	public void closeInventory()
	{
		Game.menu = parent;
	}
	
	public abstract void keyPress(int key);
	public abstract void keyRelease(int key);
	public abstract void keyTyped(char key);
	
	public abstract void mouseDown(int x, int y, boolean rightMouse);
	public abstract void mouseUp(int x, int y, boolean rightMouse);
	public abstract void mouseMove(int x, int y);
	public abstract void mouseDrag(int x, int y, boolean rightMouse);
	
	public abstract boolean pausesGame();
	public abstract void tick();
	public abstract void render(SpriteBatch batch);
}