package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;

public class GuiManagerSound extends GuiManagerBase
{
	private GuiElementTextButton back;
	private GuiElementSlider master, music, effects;
	
	public GuiManagerSound()
	{
		elems.add(new GuiElementLabel(5, -25, 0f, 1f, 100, "Sound", false, Color.WHITE));
		
		elems.add(new GuiElementLabel(5, -60, 0f, 1f, 100, "Master", false, Color.WHITE));
		master = new GuiElementSlider(5, -80, 0f, 1f, 200, 0, 100, Color.WHITE);
		elems.add(master);
		elems.add(new GuiElementLabel(5, -105, 0f, 1f, 100, "Music", false, Color.WHITE));
		music = new GuiElementSlider(5, -125, 0f, 1f, 200, 0, 100, Color.WHITE);
		elems.add(music);
		elems.add(new GuiElementLabel(5, -150, 0f, 1f, 100, "Effects", false, Color.WHITE));
		effects = new GuiElementSlider(5, -170, 0f, 1f, 200, 0, 100, Color.WHITE);
		elems.add(effects);
		
		back = new GuiElementTextButton(5, 5, 0f, 0f, 110, "Back", Color.RED);
		elems.add(back);
		
		background = GuiManagerMainMenu.mainMenuCol;
	}
	
	@Override
	public void tick()
	{
		Game.sounds.master = master.getValue();
		Game.sounds.music = music.getValue();
		Game.sounds.effects = effects.getValue();
		if(back.checked())
		{
			Game.setMenu(null);
			Game.sounds.saveSounds();
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
	public boolean keyPress(int key)
	{
		return true;
	}
}