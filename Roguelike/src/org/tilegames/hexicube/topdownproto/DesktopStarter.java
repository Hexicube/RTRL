package org.tilegames.hexicube.topdownproto;

import com.badlogic.gdx.backends.jglfw.JglfwApplication;
import com.badlogic.gdx.backends.jglfw.JglfwApplicationConfiguration;

public class DesktopStarter
{
	public static JglfwApplicationConfiguration config;
	
	public static void main(String[] args)
	{
		config = new JglfwApplicationConfiguration();
		config.title = "Game loading...";
		config.width = 800;
		config.height = 600;
		config.foregroundFPS = 60;
		config.backgroundFPS = 6;
		
		new JglfwApplication(new Game(), config);
	}
}