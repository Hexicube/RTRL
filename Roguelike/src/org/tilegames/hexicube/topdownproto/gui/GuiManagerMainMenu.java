package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Color;

public class GuiManagerMainMenu extends GuiManagerBase
{
	public static Color mainMenuCol = Color.DARK_GRAY;
	
	private GuiElementTextButton startGame, soundSettings, controls, exitGame;
	
	public GuiManagerMainMenu()
	{
		elems.add(new GuiElementLabel(5, -25, 0f, 1f, 100, Game.gameName+" - "+Game.versionText, false, Color.WHITE));
		startGame = new GuiElementTextButton(5, -60, 0f, 1f, 110, "Start Game", Color.RED);
		elems.add(startGame);
		soundSettings = new GuiElementTextButton(5, -90, 0f, 1f, 110, "Sound", Color.RED);
		elems.add(soundSettings);
		controls = new GuiElementTextButton(5, -120, 0f, 1f, 110, "Controls", Color.RED);
		elems.add(controls);
		exitGame = new GuiElementTextButton(5, -150, 0f, 1f, 110, "Exit Game", Color.RED);
		elems.add(exitGame);
		background = mainMenuCol;
	}
	
	@Override
	public void tick()
	{
		if(startGame.checked()) Game.setMenu(null);
		else if(soundSettings.checked());
		else if(controls.checked()) Game.setMenu(new GuiManagerControls(true));
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
		return false;
	}
}