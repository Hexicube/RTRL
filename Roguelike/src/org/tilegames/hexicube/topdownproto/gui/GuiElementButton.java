package org.tilegames.hexicube.topdownproto.gui;

public abstract class GuiElementButton extends GuiElementClickable
{
	public GuiElementButton(int x, int y, float xAlign, float yAlign)
	{
		super(x, y, xAlign, yAlign);
	}
	
	protected int timer;
	
	@Override
	public void handleClick()
	{
		timer = 8;
	}
	
	@Override
	public void tick()
	{
		if(timer > 0) timer--;
	}
	
	public boolean checked()
	{
		return (timer == 8);
	}
}