package org.tilegames.hexicube.topdownproto.gui;

public abstract class GuiElementClickable extends GuiElement
{
	public GuiElementClickable(int x, int y, float xAlign, float yAlign)
	{
		super(x, y, xAlign, yAlign);
	}
	
	public abstract boolean gotClicked(int x, int y, int pointer);
	public abstract void handleClick();

	public boolean gotClicked(int x, int y)
	{
		return gotClicked(x, y, -1);
	}
}