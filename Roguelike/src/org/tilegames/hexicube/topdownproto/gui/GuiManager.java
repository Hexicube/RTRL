package org.tilegames.hexicube.topdownproto.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GuiManager
{
	public GuiManager parent;
	
	public abstract void tick();
	public abstract void render(SpriteBatch batch);
	
	public abstract void mousePress(int x, int y, int pointer);
	public void mouseMove(int x, int y) {}
	public abstract boolean keyPress(int key);
	
	public abstract boolean pausesGame();
	public abstract boolean drawBehind();
	public abstract boolean drawAbove();
}