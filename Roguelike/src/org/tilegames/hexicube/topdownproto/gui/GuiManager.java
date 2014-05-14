package org.tilegames.hexicube.topdownproto.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GuiManager
{
	public abstract void tick();
	public abstract void render(SpriteBatch batch);
	
	public abstract void mousePress(int x, int y, int pointer);
	public abstract boolean keyPress(int key);
	
	public abstract boolean pausesGame();
	public abstract boolean drawBehind();
}