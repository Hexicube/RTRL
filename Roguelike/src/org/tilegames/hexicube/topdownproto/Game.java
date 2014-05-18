package org.tilegames.hexicube.topdownproto;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.tilegames.hexicube.topdownproto.KeyHandler.Key;
import org.tilegames.hexicube.topdownproto.entity.*;
import org.tilegames.hexicube.topdownproto.gui.*;
import org.tilegames.hexicube.topdownproto.item.*;
import org.tilegames.hexicube.topdownproto.item.accessory.*;
import org.tilegames.hexicube.topdownproto.item.armour.*;
import org.tilegames.hexicube.topdownproto.item.usable.*;
import org.tilegames.hexicube.topdownproto.item.weapon.*;
import org.tilegames.hexicube.topdownproto.map.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.InputProcessor;

public class Game implements ApplicationListener, InputProcessor
{
	public static int width = 800, height = 600;
	
	public static final String gameName = "RTRL";
	public static final String versionText = "Alpha V0.1";
	
	private static SpriteBatch spriteBatch;
	
	public static int volume;
	
	public static boolean gameActive;
	
	private static float currentDeltaPassed;
	
	public static Random rand;
	
	private static int ticks, frameRate, renderPercent, tickPercent;
	private static long time, tickTime, renderTime;
	
	private static boolean paused = false;
	
	public static Texture tileTex, invTex, invHighlightTex, invItemTypeTex, invUsedBar, statusTex, statusBarTex, touchInputTex;
	public static Texture[] necklaceTex, swordTex, potionTex, braceletTex, wandTex;
	
	public static Map[] maps;
	public static Map curMap;
	
	public static int camX, camY;
	
	private static ArrayList<Message> messages;
	
	public static EntityPlayer player;
	
	
	private static GuiManager currentMenu;
	
	public static GuiElementTextInput currentlyTyping;
	public static GuiElementDraggable currentlyDragging;
	
	public static KeyHandler keys;
	
	
	
	public static SoundHandler sounds;
	
	
	@Override
	public void create()
	{
		/* Game plot idea:
		 * You just entered a cave for shelter from a blizzard which has
		 * recently calmed down. You try to leave, but an evil force prevents
		 * you from doing so. You have to confront it at some point, but it's
		 * best to find tools in the cave first. */
		
		tileTex = loadImage("tiles");
		invTex = loadImage("inventory");
		invHighlightTex = loadImage("highlight");
		invItemTypeTex = loadImage("itemtype");
		invUsedBar = loadImage("usagebar");
		statusTex = loadImage("status");
		statusBarTex = loadImage("statusbar");
		
		necklaceTex = new Texture[4];
		for(int a = 0; a < necklaceTex.length; a++)
		{
			necklaceTex[a] = loadImage("necklace/necklace" + (a + 1));
		}
		swordTex = new Texture[2];
		for(int a = 0; a < swordTex.length; a++)
		{
			swordTex[a] = loadImage("weapon/sword" + (a + 1));
		}
		potionTex = new Texture[32];
		for(int a = 0; a < potionTex.length; a++)
		{
			potionTex[a] = loadImage("item/potion" + (a + 1));
		}
		braceletTex = new Texture[1];
		for(int a = 0; a < braceletTex.length; a++)
		{
			braceletTex[a] = loadImage("bracelet/bracelet" + (a + 1));
		}
		wandTex = new Texture[1];
		for(int a = 0; a < wandTex.length; a++)
		{
			wandTex[a] = loadImage("weapon/wand" + (a + 1));
		}
		
		spriteBatch = new SpriteBatch();
		volume = 100;
		
		Gdx.input.setInputProcessor(this);
		Gdx.graphics.setVSync(true);
		
		FontHolder.prep();
		
		Gdx.graphics.setTitle(gameName + " - " + versionText);
		
		currentDeltaPassed = 0;
		
		keys = new KeyHandler(new File("keys.txt"));
		sounds = new SoundHandler(new File("sounds.txt"));
		
		time = TimeUtils.nanoTime();
		ticks = 0;
		frameRate = 0;
		
		//Gdx.graphics.setDisplayMode(800, 600, true); //fullscreen
		
		currentMenu = new GuiManagerMainMenu();
	}
	
	@Override
	public void dispose()
	{}
	
	public static void newGame()
	{
		rand = new Random();
		
		necklaceTex = shuffleTex(necklaceTex);
		ItemNecklaceFeeding.tex = necklaceTex[0];
		ItemNecklaceFeeding.nameDiscovered = false;
		ItemNecklaceStrangle.tex = necklaceTex[1];
		ItemNecklaceStrangle.nameDiscovered = false;
		ItemNecklaceManaTraining.tex = necklaceTex[2];
		ItemNecklaceManaTraining.nameDiscovered = false;
		
		swordTex = shuffleTex(swordTex);
		ItemWeaponBadSword.tex = swordTex[0];
		ItemWeaponBadSword.nameDiscovered = false;
		ItemWeaponDagger.tex = swordTex[1];
		ItemWeaponDagger.nameDiscovered = false;
		
		potionTex = shuffleTex(potionTex);
		ItemPotionHealing.tex = potionTex[0];
		ItemPotionHealing.nameDiscovered = false;
		ItemPotionMana.tex = potionTex[1];
		ItemPotionMana.nameDiscovered = false;
		ItemPotionInvisibility.tex = potionTex[2];
		ItemPotionInvisibility.nameDiscovered = false;
		
		braceletTex = shuffleTex(braceletTex);
		ItemBraceletCredits.tex = braceletTex[0];
		
		wandTex = shuffleTex(wandTex);
		ItemWandLeechLife.tex = wandTex[0];
		ItemWandLeechLife.nameDiscovered = false;
		
		maps = new Map[15];
		int[] ladderPos = new int[2];
		for(int a = 0; a < maps.length; a++)
		{
			Map m = new Map(120, 120, new TileWall());
			int[][] data = new Generator().gen(120, 120, (a == 0) ? null : ladderPos, a != 4);
			for(int x = 0; x < 120; x++)
			{
				for(int y = 0; y < 120; y++)
				{
					if(data[x][y] == 0) m.tiles[x][y] = new TileVoid();
					else if(data[x][y] == 1) m.tiles[x][y] = new TileWall();
					else if(data[x][y] == 2) m.tiles[x][y] = new TileFloor();
					else if(data[x][y] == 3)
					{
						if(data[x][y - 1] == 1) m.tiles[x][y] = new TileDoor(false, false);
						else m.tiles[x][y] = new TileDoor(true, true);
					}
					else if(data[x][y] == 4)
					{
						m.tiles[x][y] = new TileFloor();
						ArrayList<Item> items = new ArrayList<Item>();
						insertRandomLoot(items, a);
						addEntity(new EntityChest(x, y, items), m, true);
					}
					else if(data[x][y] == 5)
					{
						m.tiles[x][y] = new TileTorchWall();
						int strength = rand.nextInt(6) + 2;
						//addLight(m, x, y, strength + 3, strength, 0);
						addLight(m, x, y, strength+3, strength+3, strength+3);
					}
					else if(data[x][y] == 6)
					{
						m.tiles[x][y] = new TileStair(false, a);
					}
					else if(data[x][y] == 7)
					{
						m.tiles[x][y] = new TileStair(true, a);
						ladderPos[0] = x;
						ladderPos[1] = y;
					}
					else m.tiles[x][y] = new TileWall();
					m.tiles[x][y].map = m;
				}
			}
			maps[a] = m;
		}
		
		player = new EntityPlayer(0, 0);
		while(true)
		{
			int x = rand.nextInt(maps[0].tiles.length - 1) + 1;
			int y = rand.nextInt(maps[0].tiles[x].length);
			if(maps[0].tiles[x][y].setCurrentEntity(player))
			{
				player.xPos = x;
				player.yPos = y;
				if(rand.nextBoolean())
				{
					player.armour[0] = new ItemArmourWoolCap();
					player.armour[1] = new ItemArmourJumper();
					player.necklace = new ItemNecklaceScarf();
				}
				else player.armour[1] = new ItemArmourHoodie();
				player.armour[2] = new ItemArmourJeans();
				player.armour[3] = new ItemArmourTrainers();
				player.heldItem = new ItemPickaxe();
				addEntity(player, maps[0], true);
				maps[0].updateTexture(x, y);
				ArrayList<Item> items = new ArrayList<Item>();
				items.add(new ItemNecklaceFeeding());
				items.add(new ItemNecklaceStrangle());
				items.add(new ItemNecklaceManaTraining());
				items.add(new ItemPotionHealing());
				items.add(new ItemPotionHealing());
				items.add(new ItemPotionMana());
				items.add(new ItemPotionMana());
				items.add(new ItemPotionInvisibility());
				items.add(new ItemPotionInvisibility());
				items.add(new ItemBraceletCredits());
				items.add(new ItemWeaponShortBow());
				items.add(new ItemArrow(30, DamageType.ACID));
				items.add(new ItemWeaponBadSword());
				items.add(new ItemWeaponDagger());
				items.add(new ItemWandLeechLife());
				shuffleItems(items);
				int size = items.size();
				for(int a = 0; a < size; a++)
				{
					player.inventory[a] = items.get(a);
				}
				break;
			}
		}
		
		messages = new ArrayList<Message>();
		
		curMap = maps[0];
	}
	
	@Override
	public void pause()
	{}
	
	@Override
	public void render()
	{
		boolean drawBehind = true;
		if(currentMenu != null && !currentMenu.drawBehind()) drawBehind = false;
		
		currentDeltaPassed += Gdx.graphics.getDeltaTime();
		if(currentDeltaPassed > .1f) currentDeltaPassed = .1f; // anti mega lag,
																// makes it do 6
																// ticks after
																// large lag
		long start = TimeUtils.nanoTime();
		while(currentDeltaPassed >= .01667f) // about 60tps
		{
			currentDeltaPassed -= .01667f;
			tick();
		}
		long end = TimeUtils.nanoTime();
		tickTime += (end - start);
		start = TimeUtils.nanoTime();
		spriteBatch.begin();
		
		Gdx.graphics.getGLCommon().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGLCommon().glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		int screenW = Gdx.graphics.getWidth();
		int screenH = Gdx.graphics.getHeight();
		
		if(drawBehind)
		{
			for(int x = 0; x < curMap.tiles.length; x++)
			{
				for(int y = 0; y < curMap.tiles[x].length; y++)
				{
					int tX = x - camX;
					int tY = y - camY;
					int tileX = tX * 32;
					int tileY = tY * 32;
					if(tileX + width/2 > screenW || tileY + height/2 > screenH || tileX + width/2 + 32 < 0 || tileY + height/2 + 32 < 0) continue;
					Tile t = curMap.tiles[x][y];
					if(t instanceof TileVoid)
					{
						spriteBatch.setColor(3f/18f, 3f/18f, 3f/18f, 1);
						curMap.wallTile.render(spriteBatch, tX, tY);
					}
					else
					{
						spriteBatch.setColor((float) (curMap.tiles[x][y].lightLevel[0] + 3) / 18f, (float) (curMap.tiles[x][y].lightLevel[1] + 3) / 18f, (float) (curMap.tiles[x][y].lightLevel[2] + 3) / 18f, 1);
						t.render(spriteBatch, tX, tY);
					}
				}
			}
			int size = curMap.entities.size();
			for(int a = 0; a < size; a++)
			{
				Entity e = curMap.entities.get(a);
				int eX = e.xPos - camX;
				int eY = e.yPos - camY;
				int entX = eX * 32;
				int entY = eY * 32;
				if(entX + width/2 > screenW || entX + height/2 > screenH || entX + width/2 + 32 < 0 || entY + height/2 + 32 < 0) continue;
				Tile t = curMap.tiles[e.xPos][e.yPos];
				boolean invis = !curMap.entities.get(a).visible(player);
				spriteBatch.setColor((float) (t.lightLevel[0] + 3) / 18f, (float) (t.lightLevel[1] + 3) / 18f, (float) (t.lightLevel[2] + 3) / 18f, invis ? ((curMap.entities.get(a) == player) ? 0.5f : 0) : 1);
				e.render(spriteBatch, camX, camY);
			}
			size = curMap.damageEntities.size();
			for(int a = 0; a < size; a++)
			{
				Entity e = curMap.damageEntities.get(a);
				int eX = e.xPos - camX;
				int eY = e.yPos - camY;
				int entX = eX * 32;
				int entY = eY * 32;
				if(entX + width/2 > screenW || entY + height/2 > screenH || entX + width/2 + 32 < 0 || entY + height/2 + 32 < 0) continue;
				e.render(spriteBatch, camX, camY);
			}
		}
		
		if(frameRate < 30) spriteBatch.setColor(1, 0, 0, 1);
		else if(frameRate < 55) spriteBatch.setColor(1, 1, 0, 1);
		else spriteBatch.setColor(0, 1, 0, 1);
		char[] tickRateText = FontHolder.getCharList(String.valueOf(frameRate) + "fps");
		FontHolder.render(spriteBatch, tickRateText, screenW - 6 - FontHolder.getTextWidth(tickRateText, true), screenH - 6, true);
		spriteBatch.setColor(1, 1, 1, 1);
		char[] tickTimeText = FontHolder.getCharList(String.valueOf(tickPercent) + "% tick");
		FontHolder.render(spriteBatch, tickTimeText, screenW - 6 - FontHolder.getTextWidth(tickTimeText, true), screenH - 26, true);
		char[] renderTimeText = FontHolder.getCharList(String.valueOf(renderPercent) + "% render");
		FontHolder.render(spriteBatch, renderTimeText, screenW - 6 - FontHolder.getTextWidth(renderTimeText, true), screenH - 46, true);
		char[] idleTimeText = FontHolder.getCharList(String.valueOf(100 - renderPercent - tickPercent) + "% idle");
		FontHolder.render(spriteBatch, idleTimeText, screenW - 6 - FontHolder.getTextWidth(idleTimeText, true), screenH - 66, true);
		
		if(drawBehind)
		{
			int size = messages.size();
			for(int a = 0; a < size; a++)
			{
				Message m = messages.get(a);
				m.timeLeft--;
				if(m.timeLeft <= 30)
				{
					messages.remove(a);
					size--;
					a--;
				}
				else
				{
					spriteBatch.setColor(1, 1, 1, (m.timeLeft < 300) ? ((float) m.timeLeft / 300f) : 1);
					//FontHolder.render(spriteBatch, FontHolder.getCharList(m.text), 4, screenH + 6 - (size - a) * 10, false);
					FontHolder.render(spriteBatch, FontHolder.getCharList(m.text), 4, screenH - 3 - a * 10, false);
				}
			}
			spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.draw(statusTex, screenW - 256, 0);
			int healthAmount = (int) Math.ceil((double) player.health * 200 / (double) player.healthMax);
			spriteBatch.setColor(0, 1, 0, 1);
			spriteBatch.draw(statusBarTex, screenW - 205, 31, healthAmount, 8, 0, 0, healthAmount, 8, false, false);
			if(player.heldItem != null)
			{
				int manaRequiredAmount = (int) Math.ceil((double) player.heldItem.getManaCost() * 200 / (double) player.manaMax);
				spriteBatch.setColor(0, 0, 1, 1);
				spriteBatch.draw(statusBarTex, screenW - 205, 18, manaRequiredAmount, 8, 0, 0, manaRequiredAmount, 8, false, false);
			}
			int manaAmount = (int) Math.ceil((double) player.mana * 200 / (double) player.manaMax);
			spriteBatch.setColor(0, 0.5f, 1, 1);
			spriteBatch.draw(statusBarTex, screenW - 205, 18, manaAmount, 8, 0, 0, manaAmount, 8, false, false);
			int foodAmount = (int) Math.ceil((double) player.hungerLevel * 200 / player.hungerLevelMax);
			spriteBatch.setColor(1, 0.5f, 0, 1);
			spriteBatch.draw(statusBarTex, screenW - 205, 5, foodAmount, 8, 0, 0, foodAmount, 8, false, false);
			
			spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.draw(curMap.mapTex, 0, curMap.tiles[0].length - nextPowerTwo(curMap.tiles[0].length));
		}
		
		if(currentMenu != null) currentMenu.render(spriteBatch);
		
		spriteBatch.end();
		end = TimeUtils.nanoTime();
		renderTime += (end - start);
		
		ticks++;
		if(TimeUtils.nanoTime() - time >= 1000000000)
		{
			frameRate = ticks;
			ticks = 0;
			renderPercent = (int) (renderTime / 10000000);
			tickPercent = (int) (tickTime / 10000000);
			time = TimeUtils.nanoTime();
			renderTime = 0;
			tickTime = 0;
		}
	}
	
	@Override
	public void resize(int width, int height)
	{
		Game.width = width;
		Game.height = height;
		spriteBatch = new SpriteBatch();
	}
	
	@Override
	public void resume()
	{}
	
	@Override
	public boolean keyDown(int key)
	{
		Key k = Key.getKey(key);
		if(k == Key.PAUSE)
		{
			if(currentMenu == null || !currentMenu.pausesGame()) setMenu(new GuiManagerPauseMenu());
			else setMenu(null);
		}
		else if(currentMenu == null || !currentMenu.keyPress(key)) keys.keyPress(key);
		return false;
	}
	
	@Override
	public boolean keyUp(int key)
	{
		keys.keyRelease(key);
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		if(currentlyTyping != null) currentlyTyping.keyType(character);
		return false;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		if(currentMenu != null)
		{
			Game.currentlyDragging = null;
			Game.currentlyTyping = null;
			currentMenu.mousePress(x, height-y-1, pointer);
		}
		return false;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		if(currentlyDragging != null) currentlyDragging.handleRelease();
		return false;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		if(currentlyDragging != null) currentlyDragging.handleDrag(x, height-y-1, pointer);
		return false;
	}
	
	@Override
	public boolean mouseMoved(int x, int y)
	{
		if(currentMenu != null) currentMenu.mouseMove(x, height-y-1);
		return false;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
	
	public static Texture loadImage(String name)
	{
		name = "images/" + name;
		if(!File.separator.equals("/")) name.replace("/", File.separator);
		return new Texture(Gdx.files.internal(name + ".png"));
	}
	
	public static Sound loadSound(String name)
	{
		name = "sounds/" + name;
		if(!File.separator.equals("/")) name.replace("/", File.separator);
		return Gdx.audio.newSound(Gdx.files.internal(name + ".ogg"));
	}
	
	public static Music loadMusic(String name)
	{
		name = "sounds/" + name;
		if(!File.separator.equals("/")) name.replace("/", File.separator);
		return Gdx.audio.newMusic(Gdx.files.internal(name + ".ogg"));
	}
	
	public static void tick()
	{
		if(currentMenu != null)
		{
			currentMenu.tick();
			if(currentMenu != null && currentMenu.pausesGame())
			{
				keys.tick();
				return;
			}
		}
		if(spawnTimer > 0) spawnTimer--;
		if(paused) return;
		for(int z = 0; z < maps.length; z++)
		{
			spawnEntities(maps[z], z);
			if(maps[z].needsLighting) updateLighting(maps[z]);
			Object[] list = maps[z].entities.toArray();
			for(Object e : list)
			{
				((Entity)e).tick();
			}
			list = maps[z].damageEntities.toArray();
			for(Object e : list)
			{
				((EntityDamageHealthDisplay)e).tick();
			}
			if(maps[z].needsLighting) updateLighting(maps[z]);
		}
		keys.tick();
	}
	
	public static boolean addEntity(Entity e, Map map, boolean needsTile)
	{
		if(e instanceof EntityDamageHealthDisplay)
		{
			map.damageEntities.add((EntityDamageHealthDisplay)e);
			return true;
		}
		if(!needsTile)
		{
			map.entities.add(e);
			e.map = map;
			return true;
		}
		if(map.tiles[e.xPos][e.yPos].setCurrentEntity(e))
		{
			removeEntity(e);
			map.entities.add(e);
			e.map = map;
			return true;
		}
		return false;
	}
	
	public static void removeEntity(Entity e)
	{
		if(e.map != null)
		{
			if(e instanceof EntityDamageHealthDisplay)
			{
				e.map.damageEntities.remove((EntityDamageHealthDisplay)e);
				return;
			}
			e.map.entities.remove(e);
			if(e.map.tiles[e.xPos][e.yPos].getCurrentEntity() == e) e.map.tiles[e.xPos][e.yPos].setCurrentEntity(null);
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
		if(!t.takesLight())
		{
			t.lightLevel[0] = t.lightSource[0];
			t.lightLevel[1] = t.lightSource[1];
			t.lightLevel[2] = t.lightSource[2];
			return;
		}
		int maxR = t.lightSource[0];
		int maxG = t.lightSource[1];
		int maxB = t.lightSource[2];
		if(x > 0)
		{
			Tile t2 = map.tiles[x - 1][y];
			if(t2.givesLight())
			{
				if(t2.lightLevel[0] - 1 > maxR) maxR = t2.lightLevel[0] - 1;
				if(t2.lightLevel[0] + 1 < maxR) t2.lightLevel[0] = maxR - 1;
				if(t2.lightLevel[1] - 1 > maxG) maxG = t2.lightLevel[1] - 1;
				if(t2.lightLevel[1] + 1 < maxG) t2.lightLevel[1] = maxG - 1;
				if(t2.lightLevel[2] - 1 > maxB) maxB = t2.lightLevel[2] - 1;
				if(t2.lightLevel[2] + 1 < maxB) t2.lightLevel[2] = maxB - 1;
			}
		}
		if(x < map.tiles.length - 1)
		{
			Tile t2 = map.tiles[x + 1][y];
			if(t2.givesLight())
			{
				if(t2.lightLevel[0] - 1 > maxR) maxR = t2.lightLevel[0] - 1;
				if(t2.lightLevel[0] + 1 < maxR) t2.lightLevel[0] = maxR - 1;
				if(t2.lightLevel[1] - 1 > maxG) maxG = t2.lightLevel[1] - 1;
				if(t2.lightLevel[1] + 1 < maxG) t2.lightLevel[1] = maxG - 1;
				if(t2.lightLevel[2] - 1 > maxB) maxB = t2.lightLevel[2] - 1;
				if(t2.lightLevel[2] + 1 < maxB) t2.lightLevel[2] = maxB - 1;
			}
		}
		if(y > 0)
		{
			Tile t2 = map.tiles[x][y - 1];
			if(t2.givesLight())
			{
				if(t2.lightLevel[0] - 1 > maxR) maxR = t2.lightLevel[0] - 1;
				if(t2.lightLevel[0] + 1 < maxR) t2.lightLevel[0] = maxR - 1;
				if(t2.lightLevel[1] - 1 > maxG) maxG = t2.lightLevel[1] - 1;
				if(t2.lightLevel[1] + 1 < maxG) t2.lightLevel[1] = maxG - 1;
				if(t2.lightLevel[2] - 1 > maxB) maxB = t2.lightLevel[2] - 1;
				if(t2.lightLevel[2] + 1 < maxB) t2.lightLevel[2] = maxB - 1;
			}
		}
		if(y < map.tiles[x].length - 1)
		{
			Tile t2 = map.tiles[x][y + 1];
			if(t2.givesLight())
			{
				if(t2.lightLevel[0] - 1 > maxR) maxR = t2.lightLevel[0] - 1;
				if(t2.lightLevel[0] + 1 < maxR) t2.lightLevel[0] = maxR - 1;
				if(t2.lightLevel[1] - 1 > maxG) maxG = t2.lightLevel[1] - 1;
				if(t2.lightLevel[1] + 1 < maxG) t2.lightLevel[1] = maxG - 1;
				if(t2.lightLevel[2] - 1 > maxB) maxB = t2.lightLevel[2] - 1;
				if(t2.lightLevel[2] + 1 < maxB) t2.lightLevel[2] = maxB - 1;
			}
		}
		if(t.lightLevel[0] != maxR || t.lightLevel[1] != maxG || t.lightLevel[2] != maxB) map.needsLighting = true;
		t.lightLevel[0] = maxR;
		t.lightLevel[1] = maxG;
		t.lightLevel[2] = maxB;
	}
	
	public static String numToStr(double val)
	{
		int whole = (int)val;
		double dec = (double)Math.round((val - whole) * 1000) / 1000;
		String temp = String.valueOf(whole);
		String[] digits = temp.split("");
		String result = "";
		int mod = digits.length % 3;
		for(int a = 1; a < digits.length;)
		{
			result += digits[a];
			a++;
			if(a % 3 == mod && a < digits.length) result += ",";
		}
		if(dec > 0) result += "."+String.valueOf(dec).substring(2);
		return result;
	}
	
	public static void message(String message)
	{
		messages.add(new Message(message, 450));
	}
	
	public static int rollDice(int sides, int amount)
	{
		int count = 0;
		for(int a = 0; a < amount; a++)
		{
			count += rand.nextInt(sides) + 1;
		}
		return count;
	}
	
	public static void insertRandomLoot(ArrayList<Item> list, int lootLevel)
	{
		int r = rand.nextInt(13);
		KeyType type;
		if(r < 2) type = KeyType.RED;
		else if(r < 4) type = KeyType.ORANGE;
		else if(r < 6) type = KeyType.YELLOW;
		else if(r < 8) type = KeyType.GREEN;
		else if(r < 10) type = KeyType.BLUE;
		else if(r < 12) type = KeyType.VIOLET;
		else type = KeyType.ARCADE;
		list.add(new ItemKey(type));
		// TODO: make this actually do something interesting
	}
	
	public static void insertRandomLoot(ArrayList<Item> list, EntityLiving source)
	{
		if(source instanceof EntitySkeleton)
		{
			int count = rollDice(1, 3)-1;
			for(int a = 0; a < count; a++)
			{
				list.add(new ItemWeaponBone(rand.nextInt(101) + 100));
			}
			// TODO: rare bonesword?
		}
		// TODO: make this include loot for harder things
	}
	
	public static Texture[] shuffleTex(Texture[] images)
	{
		Texture[] images2 = new Texture[images.length];
		for(int a = 0; a < images.length; a++)
		{
			int pos = rand.nextInt(images.length - a);
			int pos2 = 0;
			for(int b = 0; b <= images.length; b++)
			{
				if(pos == pos2) break;
				if(images[b] != null) pos2++;
			}
			while(images[pos2] == null) pos2++;
			images2[a] = images[pos2];
			images[pos2] = null;
		}
		return images2;
	}
	
	public static void shuffleItems(ArrayList<Item> items)
	{
		ArrayList<Item> newList = new ArrayList<Item>();
		while(items.size() > 0)
		{
			newList.add(items.remove(rand.nextInt(items.size())));
		}
		while(newList.size() > 0)
		{
			items.add(newList.remove(rand.nextInt(newList.size())));
		}
	}
	
	public static void setMenu(GuiManager menu)
	{
		if(menu == null)
		{
			if(currentMenu != null) currentMenu = currentMenu.parent;
		}
		else
		{
			menu.parent = currentMenu;
			currentMenu = menu;
		}
	}
	
	public static GuiManager getMenu()
	{
		return currentMenu;
	}
	
	private static int spawnTimer = 120;
	
	private static void spawnEntities(Map map, int floor)
	{
		int spawned = 0;
		int entCount = map.entities.size();
		if(entCount >= 100) return;
		if(floor < 5)
		{
			int x = rand.nextInt(map.tiles.length);
			int y = rand.nextInt(map.tiles[x].length);
			if(Math.abs(x - player.xPos) > 15 && Math.abs(y - player.yPos) > 9)
			{
				if(map.tiles[x][y] instanceof TileFloor)
				{
					EntitySkeleton e = new EntitySkeleton(x, y);
					addEntity(e, map, true);
					spawned++;
				}
			}
		}
		if(floor > 1 && floor < 11) // plain snakes
		{
			int x = rand.nextInt(map.tiles.length);
			int y = rand.nextInt(map.tiles[x].length);
			if(Math.abs(x - player.xPos) > 15 && Math.abs(y - player.yPos) > 9)
			{}
		}
		if(floor < 6) // rats
		{
			int x = rand.nextInt(map.tiles.length);
			int y = rand.nextInt(map.tiles[x].length);
			if(Math.abs(x - player.xPos) > 15 && Math.abs(y - player.yPos) > 9)
			{}
		}
		if(floor > 7) // slimes
		{
			int x = rand.nextInt(map.tiles.length);
			int y = rand.nextInt(map.tiles[x].length);
			if(Math.abs(x - player.xPos) > 15 && Math.abs(y - player.yPos) > 9)
			{}
		}
		if(floor > 13) // venomous snakes
		{
			int x = rand.nextInt(map.tiles.length);
			int y = rand.nextInt(map.tiles[x].length);
			if(Math.abs(x - player.xPos) > 15 && Math.abs(y - player.yPos) > 9)
			{}
		}
		spawnTimer = spawned * 30;
	}
	
	public static int nextPowerTwo(int val)
	{
		return (int) Math.pow(2, Math.ceil(Math.log(val) / Math.log(2)));
	}
	
	public static String romanNumerals(int val)
	{
		String str = "";
		while(val >= 1000)
		{
			str += "M";
			val -= 1000;
		}
		if(val >= 990)
		{
			str += "XM";
			val -= 990;
		}
		if(val >= 900)
		{
			str += "CM";
			val -= 900;
		}
		if(val >= 500)
		{
			str += "D";
			val -= 500;
		}
		if(val >= 490)
		{
			str += "XD";
			val -= 490;
		}
		while(val >= 100)
		{
			str += "C";
			val -= 100;
		}
		if(val >= 90)
		{
			str += "XC";
			val -= 90;
		}
		while(val >= 10)
		{
			str += "X";
			val -= 10;
		}
		if(val == 1) str += "I";
		else if(val == 2) str += "II";
		else if(val == 3) str += "III";
		else if(val == 4) str += "IV";
		else if(val == 5) str += "V";
		else if(val == 6) str += "VI";
		else if(val == 7) str += "VII";
		else if(val == 8) str += "VIII";
		else if(val == 9) str += "IX";
		return str;
	}
}