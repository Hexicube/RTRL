package org.tilegames.hexicube.topdownproto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.tilegames.hexicube.topdownproto.entity.*;
import org.tilegames.hexicube.topdownproto.map.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputProcessor;

public class Game implements ApplicationListener, InputProcessor
{
	public static final int xOffset = 384, yOffset = 284;
	
	public static final String gameName = "Roguelike";
	public static final String versionText = "Prototype";
	
	private static SpriteBatch spriteBatch;
	
	public static File optionsFile;
	public static int volume;
	
	public static boolean gameActive;
	
	private static float currentDeltaPassed;
	
	public static boolean[] keysDown;
	public static boolean[] keyPress;
	
	public static Random rand;
	
	private static int ticks, frameRate;
	private static long time;
	
	private static boolean paused = false;
	
	public static Texture tileTex;
	
	public static Map[] maps;
	public static int mapID;
	
	public static int camX, camY;
	
	@Override
	public void create()
	{
		rand = new Random();
		
		tileTex = loadImage("tiles");
		
		maps = new Map[1];
		for(int a = 0; a < 1; a++)
		{
			Map m = new Map(120, 120);
			int[][] data = new Generator().gen(120, 120);
			for(int x = 0; x < 120; x++)
			{
				for(int y = 0; y < 120; y++)
				{
					if(data[x][y] == 0) m.tiles[x][y] = new TileWall();
					else if(data[x][y] == 1) m.tiles[x][y] = new TileWall();
					else if(data[x][y] == 2) m.tiles[x][y] = new TileFloor();
					else if(data[x][y] == 3) m.tiles[x][y] = new TileFloor(); //TODO: doors
					else if(data[x][y] == 4) m.tiles[x][y] = new TileFloor(); //TODO: chests
					else if(data[x][y] == 5)
					{
						m.tiles[x][y] = new TileFloor(); //TODO: torch
						addLight(m, x, y, 10, 7, 0);
					}
					else m.tiles[x][y] = new TileWall();
				}
			}
			maps[a] = m;
		}
		
		spriteBatch = new SpriteBatch();
		volume = 100;
		
		Gdx.input.setInputProcessor(this);
		Gdx.graphics.setVSync(true);
		
		FontHolder.prep();
		
		Gdx.graphics.setTitle(gameName+" - "+versionText);
		
		currentDeltaPassed = 0;
		
		keysDown = new boolean[512];
		keyPress = new boolean[512];
		
		time = System.nanoTime();
		ticks = 0;
		frameRate = 0;
		
		addEntity(new EntityPlayer(2, 5, 20), maps[0]);
		
		//Gdx.graphics.setDisplayMode(800, 600, true); //fullscreen
	}
	
	@Override
	public void dispose()
	{
	}
	
	@Override
	public void pause()
	{
	}
	
	@Override
	public void render()
	{
		currentDeltaPassed += Gdx.graphics.getDeltaTime();
		if(currentDeltaPassed > .1f) currentDeltaPassed = .1f; //anti mega lag, makes it do 6 ticks after large lag
		while(currentDeltaPassed >= .01667f) //about 60tps
		{
			currentDeltaPassed -= .01667f;
			tick();
		}
		ticks++;
		if(System.nanoTime() - time >= 1000000000)
		{
			frameRate = ticks;
			ticks = 0;
			time = System.nanoTime();
		}
		spriteBatch.begin();
		Gdx.graphics.getGL10().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		for(int x = 0; x < maps[mapID].tiles.length; x++)
		{
			for(int y = 0; y < maps[mapID].tiles[x].length; y++)
			{
				spriteBatch.setColor((float)(maps[mapID].tiles[x][y].lightLevel[0]+3)/18f, (float)(maps[mapID].tiles[x][y].lightLevel[1]+3)/18f, (float)(maps[mapID].tiles[x][y].lightLevel[2]+3)/18f, 1);
				maps[mapID].tiles[x][y].render(spriteBatch, x-camX, y-camY);
			}
		}
		int size = maps[mapID].entities.size();
		for(int a = 0; a < size; a++)
		{
			Entity e = maps[mapID].entities.get(a);
			Tile t = maps[mapID].tiles[e.xPos][e.yPos];
			spriteBatch.setColor((float)(t.lightLevel[0]+3)/18f, (float)(t.lightLevel[1]+3)/18f, (float)(t.lightLevel[2]+3)/18f, 1);
			maps[mapID].entities.get(a).render(spriteBatch, camX, camY);
		}
		
		spriteBatch.setColor(1, 1, 1, 1);
		char[] tickRateText = FontHolder.getCharList(String.valueOf(frameRate));
		FontHolder.render(spriteBatch, tickRateText, 796-FontHolder.getTextWidth(tickRateText, true), 20, true);
		spriteBatch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
		if(width != 800 || height != 600)
		{
			Gdx.graphics.setDisplayMode(800, 600, false);
		}
		else spriteBatch = new SpriteBatch();
	}
	
	@Override
	public void resume()
	{
	}
	
	@Override
	public boolean keyDown(int key)
	{
		keysDown[key] = true;
		keyPress[key] = true;
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		//TODO: use for input fields
		return false;
	}

	@Override
	public boolean keyUp(int key)
	{
		keysDown[key] = false;
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int poniter, int button)
	{
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int poniter, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		return false;
	}
	
	@Override
	public boolean touchMoved(int x, int y)
	{
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
	
	public static Texture loadImage(String name)
	{
		return new Texture(Gdx.files.internal("images" + File.separator + name + ".png"));
	}
	
	public static Sound loadSound(String name)
	{
		return Gdx.audio.newSound(Gdx.files.internal("sounds" + File.separator + name + ".mp3"));
	}
	
	public static Music loadMusic(String name)
	{
		return Gdx.audio.newMusic(Gdx.files.internal("sounds" + File.separator + name + ".mp3"));
	}
	
	public static BufferedReader loadTextFile(String name, String folder)
	{
		return getFileReader(loadFile(name, folder));
	}
	
	public static File loadFile(String name, String folder)
	{
		File f = new File(folder+File.separator+name);
		if(!f.exists())
		{
			System.out.println(name+" doesn't exist, creating...");
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	public static BufferedReader getFileReader(File f)
	{
		try {
			return new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void tick()
	{
		if(paused) return;
		for(int z = 0; z < maps.length; z++)
		{
			if(maps[z].needsLighting) updateLighting(maps[z]);
			Object[] list = maps[z].entities.toArray();
			for(int a = 0; a < list.length; a++)
			{
				((Entity)list[a]).tick();
			}
		}
		for(int a = 0; a < keyPress.length; a++)
		{
			keyPress[a] = false;
		}
	}
	
	public static void addEntity(Entity e, Map map)
	{
		removeEntity(e);
		map.entities.add(e);
		e.map = map;
	}
	
	public static void removeEntity(Entity e)
	{
		if(e.map != null)
		{
			e.map.entities.remove(e);
			e.map = null;
		}
	}
	
	public static void addLight(Map map, int x, int y, int R, int G, int B)
	{
		Tile t = map.tiles[x][y];
		t.lightSource[0] = R;
		t.lightSource[1] = G;
		t.lightSource[2] = B;
		map.needsLighting = true;
	}
	
	public static void removeLight(Map map, int x, int y)
	{
		Tile t = map.tiles[x][y];
		t.lightSource[0] = 0;
		t.lightSource[1] = 0;
		t.lightSource[2] = 0;
		map.needsLighting = true;
	}
	
	public static void updateLighting(Map map)
	{
		while(map.needsLighting)
		{
			map.needsLighting = false;
			for(int x = 0; x < map.tiles.length; x++)
			{
				for(int y = 0; y < map.tiles[x].length; y++)
				{
					updateLightingSubfunc(map, x, y);
				}
			}
		}
	}
	
	public static void updateLightingSubfunc(Map map, int x, int y)
	{
		Tile t = map.tiles[x][y];
		if(!t.lightable())
		{
			t.lightLevel[0] = 15;
			t.lightLevel[1] = 15;
			t.lightLevel[2] = 15;
			return;
		}
		int maxR = t.lightSource[0];
		int maxG = t.lightSource[1];
		int maxB = t.lightSource[2];
		if(x > 0)
		{
			Tile t2 = map.tiles[x-1][y];
			if(t2.lightable())
			{
				if(t2.lightLevel[0]-1 > maxR) maxR = t2.lightLevel[0]-1;
				if(t2.lightLevel[0]+1 < maxR) t2.lightLevel[0] = maxR-1;
				if(t2.lightLevel[1]-1 > maxG) maxG = t2.lightLevel[1]-1;
				if(t2.lightLevel[1]+1 < maxG) t2.lightLevel[1] = maxG-1;
				if(t2.lightLevel[2]-1 > maxB) maxB = t2.lightLevel[2]-1;
				if(t2.lightLevel[2]+1 < maxB) t2.lightLevel[2] = maxB-1;
			}
		}
		if(x < map.tiles.length-1)
		{
			Tile t2 = map.tiles[x+1][y];
			if(t2.lightable())
			{
				if(t2.lightLevel[0]-1 > maxR) maxR = t2.lightLevel[0]-1;
				if(t2.lightLevel[0]+1 < maxR) t2.lightLevel[0] = maxR-1;
				if(t2.lightLevel[1]-1 > maxG) maxG = t2.lightLevel[1]-1;
				if(t2.lightLevel[1]+1 < maxG) t2.lightLevel[1] = maxG-1;
				if(t2.lightLevel[2]-1 > maxB) maxB = t2.lightLevel[2]-1;
				if(t2.lightLevel[2]+1 < maxB) t2.lightLevel[2] = maxB-1;
			}
		}
		if(y > 0)
		{
			Tile t2 = map.tiles[x][y-1];
			if(t2.lightable())
			{
				if(t2.lightLevel[0]-1 > maxR) maxR = t2.lightLevel[0]-1;
				if(t2.lightLevel[0]+1 < maxR) t2.lightLevel[0] = maxR-1;
				if(t2.lightLevel[1]-1 > maxG) maxG = t2.lightLevel[1]-1;
				if(t2.lightLevel[1]+1 < maxG) t2.lightLevel[1] = maxG-1;
				if(t2.lightLevel[2]-1 > maxB) maxB = t2.lightLevel[2]-1;
				if(t2.lightLevel[2]+1 < maxB) t2.lightLevel[2] = maxB-1;
			}
		}
		if(y < map.tiles[x].length-1)
		{
			Tile t2 = map.tiles[x][y+1];
			if(t2.lightable())
			{
				if(t2.lightLevel[0]-1 > maxR) maxR = t2.lightLevel[0]-1;
				if(t2.lightLevel[0]+1 < maxR) t2.lightLevel[0] = maxR-1;
				if(t2.lightLevel[1]-1 > maxG) maxG = t2.lightLevel[1]-1;
				if(t2.lightLevel[1]+1 < maxG) t2.lightLevel[1] = maxG-1;
				if(t2.lightLevel[2]-1 > maxB) maxB = t2.lightLevel[2]-1;
				if(t2.lightLevel[2]+1 < maxB) t2.lightLevel[2] = maxB-1;
			}
		}
		if(t.lightLevel[0] != maxR || t.lightLevel[1] != maxG || t.lightLevel[2] != maxB) map.needsLighting = true;
		t.lightLevel[0] = maxR;
		t.lightLevel[1] = maxG;
		t.lightLevel[2] = maxB;
	}
	
	public static String numToStr(int num, int len, String filler)
	{
		String temp = String.valueOf(num);
		while(temp.length() < len) temp = filler + temp;
		String[] digits = temp.split("");
		String result = "";
		int mod = digits.length % 3;
		for(int a = 1; a < digits.length;)
		{
			result += digits[a];
			a++;
			if(a%3 == mod && a < digits.length) result += ",";
		}
		return result;
	}
	
	public static void message(String message)
	{
		//TODO
	}
}