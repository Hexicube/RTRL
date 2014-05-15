package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;

public class GuiManagerPauseMenu extends GuiManagerBase
{
	private GuiElementTextButton newGame, soundSettings, controls, exitGame;
	
	public GuiManagerPauseMenu()
	{
		elems.add(new GuiElementLabel(5, -25, 0f, 1f, 100, "Game Paused", false, Color.WHITE));
		newGame = new GuiElementTextButton(5, -60, 0f, 1f, 110, "New Game", Color.RED);
		elems.add(newGame);
		soundSettings = new GuiElementTextButton(5, -90, 0f, 1f, 110, "Sound", Color.RED);
		elems.add(soundSettings);
		controls = new GuiElementTextButton(5, -120, 0f, 1f, 110, "Controls", Color.RED);
		elems.add(controls);
		exitGame = new GuiElementTextButton(5, -150, 0f, 1f, 110, "Exit Game", Color.RED);
		elems.add(exitGame);
		background = new Color(0, 0, 0, 0.6f);
	}
	
	@Override
	public void tick()
	{
		if(newGame.checked())
		{
			Game.newGame();
			Game.setMenu(null);
		}
		else if(soundSettings.checked());
		else if(controls.checked()) Game.setMenu(new GuiManagerControls());
		else if(exitGame.checked()) System.exit(0);
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
		return true;
	}
}