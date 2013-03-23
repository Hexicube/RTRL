package org.tilegames.hexicube.topdownproto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.tilegames.hexicube.topdownproto.entity.*;
import org.tilegames.hexicube.topdownproto.item.*;
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
	
	public static final String gameName = "RTRL";
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
	
	public static Texture tileTex, invTex;
	
	public static Map[] maps;
	public static Map curMap;
	
	public static int camX, camY;
	
	private static ArrayList<Message> messages;
	
	private static EntityPlayer player;
	
	@Override
	public void create()
	{
		rand = new Random();
		
		tileTex = loadImage("tiles");
		invTex = loadImage("inventory");
		
		maps = new Map[1];
		for(int a = 0; a < 1; a++)
		{
			Map m = new Map(120, 120);
			int[][] data = new Generator().gen(120, 120);
			for(int x = 0; x < 120; x++)
			{
				for(int y = 0; y < 120; y++)
				{
					if(data[x][y] == 0) m.tiles[x][y] = new TileVoid();
					else if(data[x][y] == 1) m.tiles[x][y] = new TileWall();
					else if(data[x][y] == 2) m.tiles[x][y] = new TileFloor();
					else if(data[x][y] == 3)
					{
						if(data[x][y-1] == 1) m.tiles[x][y] = new TileDoor(false, false);
						else m.tiles[x][y] = new TileDoor(true, true);
					}
					else if(data[x][y] == 4) m.tiles[x][y] = new TileFloor(); //TODO: chests
					else if(data[x][y] == 5)
					{
						m.tiles[x][y] = new TileFloor(); //TODO: torch
						int strength = rand.nextInt(8)+5;
						addLight(m, x, y, strength+3, strength, 0);
					}
					else m.tiles[x][y] = new TileWall();
					m.tiles[x][y].map = m;
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
		
		while(true)
		{
			int x = rand.nextInt(maps[0].tiles.length);
			int y = rand.nextInt(maps[0].tiles[x].length);
			if(maps[0].tiles[x][y] instanceof TileFloor)
			{
				player = new EntityPlayer(x, y, 20);
				player.heldItem = new ItemWeaponTestSword();
				player.necklace = new ItemNecklaceFeeding();
				addEntity(player, maps[0]);
				break;
			}
		}
		
		while(true)
		{
			int x = rand.nextInt(maps[0].tiles.length);
			int y = rand.nextInt(maps[0].tiles[x].length);
			if(maps[0].tiles[x][y] instanceof TileFloor)
			{
				addEntity(new EntityItem(x, y, new ItemWeaponTestSword()), maps[0]);
				break;
			}
		}
		
		messages = new ArrayList<Message>();
		
		curMap = maps[0];
		
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
		
		for(int x = 0; x < curMap.tiles.length; x++)
		{
			for(int y = 0; y < curMap.tiles[x].length; y++)
			{
				spriteBatch.setColor((float)(curMap.tiles[x][y].lightLevel[0]+3)/18f, (float)(curMap.tiles[x][y].lightLevel[1]+3)/18f, (float)(curMap.tiles[x][y].lightLevel[2]+3)/18f, 1);
				curMap.tiles[x][y].render(spriteBatch, x-camX, y-camY);
			}
		}
		int size = curMap.entities.size();
		for(int a = 0; a < size; a++)
		{
			Entity e = curMap.entities.get(a);
			Tile t = curMap.tiles[e.xPos][e.yPos];
			spriteBatch.setColor((float)(t.lightLevel[0]+3)/18f, (float)(t.lightLevel[1]+3)/18f, (float)(t.lightLevel[2]+3)/18f, 1);
			curMap.entities.get(a).render(spriteBatch, camX, camY);
		}
		
		spriteBatch.setColor(1, 1, 1, 1);
		if(player.viewingInventory)
		{
			spriteBatch.draw(invTex, 200, 28);
			for(int a = 0; a < 30; a++)
			{
				Item i = player.inventory[a];
				if(i != null)
				{
					int ID = i.getItemID();
					int xPos = 364 + (a%6)*40;
					int yPos = 504 - (a/6)*40;
					int x = ID%16;
					int y = ID/16;
					spriteBatch.draw(EntityItem.tex, xPos, yPos, 32, 32, x*32, y*32, 32, 32, false, false);
				}
			}
			for(int a = 30; a < 100; a++)
			{
				Item i = player.inventory[a];
				if(i != null)
				{
					int ID = i.getItemID();
					int xPos = 204 + ((a-30)%10)*40;
					int yPos = 304 - ((a-30)/10)*40;
					int x = ID%16;
					int y = ID/16;
					spriteBatch.draw(EntityItem.tex, xPos, yPos, 32, 32, x*32, y*32, 32, 32, false, false);
				}
			}
			//40x40
			Item i = player.heldItem;
			if(i != null)
			{
				int ID = i.getItemID();
				int x = ID%16;
				int y = ID/16;
				spriteBatch.draw(EntityItem.tex, 223, 365, 32, 32, x*32, y*32, 32, 32, false, false);
			}
			i = player.necklace;
			if(i != null)
			{
				int ID = i.getItemID();
				int x = ID%16;
				int y = ID/16;
				spriteBatch.draw(EntityItem.tex, 303, 485, 32, 32, x*32, y*32, 32, 32, false, false);
			}
			i = player.ring1;
			if(i != null)
			{
				int ID = i.getItemID();
				int x = ID%16;
				int y = ID/16;
				spriteBatch.draw(EntityItem.tex, 223, 405, 32, 32, x*32, y*32, 32, 32, false, false);
			}
			i = player.ring2;
			if(i != null)
			{
				int ID = i.getItemID();
				int x = ID%16;
				int y = ID/16;
				spriteBatch.draw(EntityItem.tex, 303, 405, 32, 32, x*32, y*32, 32, 32, false, false);
			}
			i = player.bracelet1;
			if(i != null)
			{
				int ID = i.getItemID();
				int x = ID%16;
				int y = ID/16;
				spriteBatch.draw(EntityItem.tex, 223, 445, 32, 32, x*32, y*32, 32, 32, false, false);
			}
			i = player.bracelet2;
			if(i != null)
			{
				int ID = i.getItemID();
				int x = ID%16;
				int y = ID/16;
				spriteBatch.draw(EntityItem.tex, 303, 445, 32, 32, x*32, y*32, 32, 32, false, false);
			}
			//TODO: render armour in inventory
		}
		
		char[] tickRateText = FontHolder.getCharList(String.valueOf(frameRate));
		FontHolder.render(spriteBatch, tickRateText, 796-FontHolder.getTextWidth(tickRateText, true), 20, true);
		
		size = messages.size();
		for(int a = 0; a < size; a++)
		{
			Message m = messages.get(a);
			m.timeLeft--;
			if(m.timeLeft == 0)
			{
				messages.remove(a);
				size--;
				a--;
			}
			else
			{
				spriteBatch.setColor(1, 1, 1, (m.timeLeft<300)?((float)m.timeLeft/300f):1);
				FontHolder.render(spriteBatch, FontHolder.getCharList(m.text), 4, 606-(size-a)*10, false);
			}
		}
		
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
		//System.out.println(key);
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
			if(maps[z].needsLighting) updateLighting(maps[z]);
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
		map.tiles[e.xPos][e.yPos].setCurrentEntity(e);
		e.map = map;
	}
	
	public static void removeEntity(Entity e)
	{
		if(e.map != null)
		{
			e.map.entities.remove(e);
			e.map.tiles[e.xPos][e.yPos].setCurrentEntity(null);
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
		messages.add(new Message(message, 600));
	}
}