package org.tilegames.hexicube.topdownproto;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.tilegames.hexicube.topdownproto.entity.*;
import org.tilegames.hexicube.topdownproto.item.*;
import org.tilegames.hexicube.topdownproto.item.accessory.ItemBraceletCredits;
import org.tilegames.hexicube.topdownproto.item.accessory.ItemNecklaceFeeding;
import org.tilegames.hexicube.topdownproto.item.accessory.ItemNecklaceManaTraining;
import org.tilegames.hexicube.topdownproto.item.accessory.ItemNecklaceScarf;
import org.tilegames.hexicube.topdownproto.item.accessory.ItemNecklaceStrangle;
import org.tilegames.hexicube.topdownproto.item.armour.ItemArmourHoodie;
import org.tilegames.hexicube.topdownproto.item.armour.ItemArmourJeans;
import org.tilegames.hexicube.topdownproto.item.armour.ItemArmourJumper;
import org.tilegames.hexicube.topdownproto.item.armour.ItemArmourTrainers;
import org.tilegames.hexicube.topdownproto.item.armour.ItemArmourWoolCap;
import org.tilegames.hexicube.topdownproto.item.usable.ItemPickaxe;
import org.tilegames.hexicube.topdownproto.item.usable.ItemPotionHealing;
import org.tilegames.hexicube.topdownproto.item.usable.ItemPotionInvisibility;
import org.tilegames.hexicube.topdownproto.item.usable.ItemPotionMana;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;
import org.tilegames.hexicube.topdownproto.item.weapon.ItemArrow;
import org.tilegames.hexicube.topdownproto.item.weapon.ItemWandLeechLife;
import org.tilegames.hexicube.topdownproto.item.weapon.ItemWeapon;
import org.tilegames.hexicube.topdownproto.item.weapon.ItemWeaponBadSword;
import org.tilegames.hexicube.topdownproto.item.weapon.ItemWeaponBone;
import org.tilegames.hexicube.topdownproto.item.weapon.ItemWeaponDagger;
import org.tilegames.hexicube.topdownproto.item.weapon.ItemWeaponShortBow;
import org.tilegames.hexicube.topdownproto.map.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.InputProcessor;

public class Game implements ApplicationListener, InputProcessor
{
	public static int xOffset = 384, yOffset = 284;
	
	public static final String gameName = "Numpad Explorer";
	public static final String versionText = "Alpha 4";
	
	private static SpriteBatch spriteBatch;
	
	public static int volume;
	
	public static boolean gameActive;
	
	private static float currentDeltaPassed;
	
	public static boolean[] keysDown;
	public static boolean[] keyPress;
	
	public static Random rand;
	
	private static int ticks, frameRate, renderPercent, tickPercent;
	private static long time, tickTime, renderTime;
	
	private static boolean paused = false;
	
	public static Texture tileTex, invTex, invHighlightTex, invItemTypeTex, invUsedBar, statusTex, statusBarTex, touchInputTex;
	
	public static Map[] maps;
	public static Map curMap;
	
	public static int camX, camY;
	
	private static ArrayList<Message> messages;
	
	public static EntityPlayer player;
	
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
		
		rand = new Random();
		
		Texture[] images = new Texture[4];
		for(int a = 0; a < images.length; a++)
		{
			images[a] = loadImage("necklace/necklace" + (a + 1));
		}
		images = shuffleTex(images);
		ItemNecklaceFeeding.tex = images[0];
		ItemNecklaceStrangle.tex = images[1];
		ItemNecklaceManaTraining.tex = images[2];
		
		images = new Texture[2];
		for(int a = 0; a < images.length; a++)
		{
			images[a] = loadImage("weapon/sword" + (a + 1));
		}
		images = shuffleTex(images);
		ItemWeaponBadSword.tex = images[0];
		ItemWeaponDagger.tex = images[1];
		
		images = new Texture[6];
		for(int a = 0; a < images.length; a++)
		{
			images[a] = loadImage("item/potion" + (a + 1));
		}
		images = shuffleTex(images);
		ItemPotionHealing.tex = images[0];
		ItemPotionMana.tex = images[1];
		ItemPotionInvisibility.tex = images[2];
		
		images = new Texture[1];
		for(int a = 0; a < images.length; a++)
		{
			images[a] = loadImage("bracelet/bracelet" + (a + 1));
		}
		images = shuffleTex(images);
		ItemBraceletCredits.tex = images[0];
		
		images = new Texture[1];
		for(int a = 0; a < images.length; a++)
		{
			images[a] = loadImage("weapon/wand" + (a + 1));
		}
		images = shuffleTex(images);
		ItemWandLeechLife.tex = images[0];
		
		maps = new Map[15];
		int[] ladderPos = new int[2];
		for(int a = 0; a < maps.length; a++)
		{
			Map m = new Map(120, 120);
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
						addLight(m, x, y, strength + 3, strength, 0);
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
		
		spriteBatch = new SpriteBatch();
		volume = 100;
		
		Gdx.input.setInputProcessor(this);
		Gdx.graphics.setVSync(true);
		
		FontHolder.prep();
		
		Gdx.graphics.setTitle(gameName + " - " + versionText);
		
		currentDeltaPassed = 0;
		
		keysDown = new boolean[512];
		keyPress = new boolean[512];
		
		time = TimeUtils.nanoTime();
		ticks = 0;
		frameRate = 0;
		
		while(true)
		{
			int x = rand.nextInt(maps[0].tiles.length - 1) + 1;
			int y = rand.nextInt(maps[0].tiles[x].length);
			if(maps[0].tiles[x][y] instanceof TileFloor && maps[0].tiles[x - 1][y] instanceof TileFloor)
			{
				player = new EntityPlayer(x, y);
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
				addEntity(new EntityChest(x - 1, y, items), maps[0], true);
				break;
			}
		}
		
		messages = new ArrayList<Message>();
		
		curMap = maps[0];
		
		// Gdx.graphics.setDisplayMode(800, 600, true); //fullscreen
	}
	
	@Override
	public void dispose()
	{}
	
	@Override
	public void pause()
	{}
	
	@Override
	public void render()
	{
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
		
		for(int x = 0; x < curMap.tiles.length; x++)
		{
			for(int y = 0; y < curMap.tiles[x].length; y++)
			{
				int tX = x - camX;
				int tY = y - camY;
				int tileX = tX * 32;
				int tileY = tY * 32;
				if(tileX + xOffset > screenW || tileY + yOffset > screenH || tileX + xOffset + 32 < 0 || tileY + yOffset + 32 < 0) continue;
				spriteBatch.setColor((float) (curMap.tiles[x][y].lightLevel[0] + 3) / 18f, (float) (curMap.tiles[x][y].lightLevel[1] + 3) / 18f, (float) (curMap.tiles[x][y].lightLevel[2] + 3) / 18f, 1);
				curMap.tiles[x][y].render(spriteBatch, tX, tY);
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
			if(entX + xOffset > screenW || entY + yOffset > screenH || entX + xOffset + 32 < 0 || entY + yOffset + 32 < 0) continue;
			Tile t = curMap.tiles[e.xPos][e.yPos];
			boolean invis = !curMap.entities.get(a).visible(player);
			spriteBatch.setColor((float) (t.lightLevel[0] + 3) / 18f, (float) (t.lightLevel[1] + 3) / 18f, (float) (t.lightLevel[2] + 3) / 18f, invis ? ((curMap.entities.get(a) == player) ? 0.5f : 0) : 1);
			curMap.entities.get(a).render(spriteBatch, camX, camY);
		}
		
		spriteBatch.setColor(1, 1, 1, 1);
		if(player.viewingInventory)
		{
			int xPos = screenW / 2 - 200;
			int yPos = screenH / 2 - 240 - 32;
			spriteBatch.draw(invTex, xPos, yPos);
			int harmTimer = -1, harmStrength = 0, slowTimer = -1, slowStrength = 0, healTimer = -1, healStrength = 0, invisTimer = -1, invulTimer = -1, ghostTimer = -1;
			size = player.effects.size();
			for(int a = 0; a < size; a++)
			{
				Effect e = player.effects.get(a);
				int str = e.getEffectStrength();
				int time = e.timeRemaining();
				if(e.getEffectType() == EffectType.HARM)
				{
					if(str > harmStrength)
					{
						harmStrength = str;
						harmTimer = time;
					}
					else if(str == harmStrength && time > harmTimer)
					{
						harmTimer = time;
					}
				}
				else if(e.getEffectType() == EffectType.SLOW)
				{
					if(str > slowStrength)
					{
						slowStrength = str;
						slowTimer = time;
					}
					else if(str == slowStrength && time > slowTimer)
					{
						slowTimer = time;
					}
				}
				else if(e.getEffectType() == EffectType.HEAL)
				{
					if(str > healStrength)
					{
						healStrength = str;
						healTimer = time;
					}
					else if(str == healStrength && time > healTimer)
					{
						healTimer = time;
					}
				}
				else if(e.getEffectType() == EffectType.INVISIBLE)
				{
					if(str > 0 && time > invisTimer)
					{
						invisTimer = time;
					}
				}
				else if(e.getEffectType() == EffectType.INVULNERABLE)
				{
					if(str > 0 && time > invulTimer)
					{
						invulTimer = time;
					}
				}
				else if(e.getEffectType() == EffectType.GHOSTLY)
				{
					if(str > 0 && time > ghostTimer)
					{
						ghostTimer = time;
					}
				}
			}
			int posY = yPos + 508;
			if(harmStrength > 0)
			{
				FontHolder.render(spriteBatch, FontHolder.getCharList("Harm " + romanNumerals(harmStrength)), xPos + 402, posY, false);
				FontHolder.render(spriteBatch, FontHolder.getCharList((harmTimer / 60) + "s"), xPos + 422, posY - 9, false);
				posY -= 18;
			}
			if(slowStrength > 0)
			{
				FontHolder.render(spriteBatch, FontHolder.getCharList("Slow " + romanNumerals(slowStrength)), xPos + 402, posY, false);
				FontHolder.render(spriteBatch, FontHolder.getCharList((slowTimer / 60) + "s"), xPos + 422, posY - 9, false);
				posY -= 18;
			}
			if(healStrength > 0)
			{
				FontHolder.render(spriteBatch, FontHolder.getCharList("Heal " + romanNumerals(healStrength)), xPos + 402, posY, false);
				FontHolder.render(spriteBatch, FontHolder.getCharList((healTimer / 60) + "s"), xPos + 422, posY - 9, false);
				posY -= 18;
			}
			if(invisTimer > -1)
			{
				FontHolder.render(spriteBatch, FontHolder.getCharList("Invisibility"), xPos + 402, posY, false);
				FontHolder.render(spriteBatch, FontHolder.getCharList((invisTimer / 60) + "s"), xPos + 422, posY - 9, false);
				posY -= 18;
			}
			if(invulTimer > -1)
			{
				FontHolder.render(spriteBatch, FontHolder.getCharList("Invulnerability"), xPos + 402, posY, false);
				FontHolder.render(spriteBatch, FontHolder.getCharList((invisTimer / 60) + "s"), xPos + 422, posY - 9, false);
				posY -= 18;
			}
			if(ghostTimer > -1)
			{
				FontHolder.render(spriteBatch, FontHolder.getCharList("Ghostly"), xPos + 402, posY, false);
				FontHolder.render(spriteBatch, FontHolder.getCharList((ghostTimer / 60) + "s"), xPos + 422, posY - 9, false);
				posY -= 18;
			}
			for(int x = 0; x < 10; x++)
			{
				for(int y = 0; y < 11; y++)
				{
					Item i = player.getItemInSlot(x, y);
					if(i != null)
					{
						i.render(spriteBatch, xPos + 4 + x * 40, 436 + yPos - y * 40, y == 0);
						if(i.getMaxDurability() > 1)
						{
							int stage = i.getCurrentDurability() * 16 / i.getMaxDurability();
							if(stage == 16) stage = 15;
							spriteBatch.setColor(i.getInvBorderCol());
							spriteBatch.draw(invItemTypeTex, xPos + 3 + x * 40, 405 + yPos - y * 40);
							spriteBatch.setColor(1, 1, 1, 1);
							spriteBatch.draw(invUsedBar, xPos + 4 + x * 40, 436 + yPos - y * 40, 32, 2, 0, 30 - stage * 2, 32, 2, false, false);
						}
					}
				}
			}
			spriteBatch.draw(invHighlightTex, xPos + 4 + player.invX * 40, 436 + yPos - player.invY * 40, 32, 32, 32, 0, 32, 32, false, false);
			Item curItem = player.getItemInSlot(player.invX, player.invY);
			String itemName = curItem == null ? player.getSlotName(player.invX, player.invY) : curItem.getName();
			if(curItem instanceof ItemStack)
			{
				int size1 = ((ItemStack) curItem).getStackSize();
				if(size1 != 1) itemName += " x" + size1;
			}
			if(curItem != null && curItem.getMaxDurability() > 1) itemName += " (" + (curItem.getCurrentDurability() * 100 / curItem.getMaxDurability()) + "%)";
			if(curItem != null && curItem instanceof ItemWeapon) itemName = "[" + ((ItemWeapon) curItem).getWeaponDamageRange() + "] " + itemName;
			if(player.invSelectY != -1)
			{
				Item otherItem = player.getItemInSlot(player.invSelectX, player.invSelectY);
				String itemName2 = otherItem == null ? player.getSlotName(player.invSelectX, player.invSelectY) : otherItem.getName();
				if(otherItem instanceof ItemStack)
				{
					int size1 = ((ItemStack) otherItem).getStackSize();
					if(size1 != 1) itemName2 += " x" + size1;
				}
				if(otherItem != null && otherItem.getMaxDurability() > 1) itemName2 += " (" + (otherItem.getCurrentDurability() * 100 / otherItem.getMaxDurability()) + "%)";
				if(otherItem != null && otherItem instanceof ItemWeapon) itemName2 = "[" + ((ItemWeapon) otherItem).getWeaponDamageRange() + "] " + itemName2;
				spriteBatch.draw(invHighlightTex, xPos + 4 + player.invSelectX * 40, 436 + yPos - player.invSelectY * 40, 32, 32, 0, 0, 32, 32, false, false);
				FontHolder.render(spriteBatch, FontHolder.getCharList(itemName2 + " <---> " + itemName), xPos + 4, 508 + yPos, false);
			}
			else FontHolder.render(spriteBatch, FontHolder.getCharList(itemName), xPos + 4, 508 + yPos, false);
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
				spriteBatch.setColor(1, 1, 1, (m.timeLeft < 300) ? ((float) m.timeLeft / 300f) : 1);
				FontHolder.render(spriteBatch, FontHolder.getCharList(m.text), 4, screenH + 6 - (size - a) * 10, false);
			}
		}
		spriteBatch.setColor(1, 1, 1, 1);
		spriteBatch.draw(statusTex, screenW - 256, 0);
		int healthAmount = (int) Math.ceil((double) player.health * 200 / (double) player.healthMax);
		spriteBatch.setColor(0, 1, 0, 1);
		spriteBatch.draw(statusBarTex, screenW - 205, 31, healthAmount, 8, 0, 0, healthAmount, 8, false, false);
		int manaAmount = (int) Math.ceil((double) player.mana * 200 / (double) player.manaMax);
		spriteBatch.setColor(0, 0.5f, 1, 1);
		spriteBatch.draw(statusBarTex, screenW - 205, 18, manaAmount, 8, 0, 0, manaAmount, 8, false, false);
		int foodAmount = (int) Math.ceil((double) player.hungerLevel * 200 / 25200);
		spriteBatch.setColor(1, 0.5f, 0, 1);
		spriteBatch.draw(statusBarTex, screenW - 205, 5, foodAmount, 8, 0, 0, foodAmount, 8, false, false);
		
		spriteBatch.setColor(1, 1, 1, 1);
		spriteBatch.draw(curMap.mapTex, 0, curMap.tiles[0].length - nextPowerTwo(curMap.tiles[0].length));
		
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
		xOffset = width / 2 - 16;
		yOffset = height / 2 - 16;
		spriteBatch = new SpriteBatch();
	}
	
	@Override
	public void resume()
	{}
	
	@Override
	public boolean keyDown(int key)
	{
		// System.out.println(key);
		if(key == 254)
		{
			if(Gdx.graphics.isFullscreen()) Gdx.graphics.setDisplayMode(800, 600, false);
			else
			{
				DisplayMode d = Gdx.graphics.getDesktopDisplayMode();
				Gdx.graphics.setDisplayMode(d.width, d.height, true);
			}
		}
		keysDown[key] = true;
		keyPress[key] = true;
		return false;
	}
	
	@Override
	public boolean keyTyped(char character)
	{
		// TODO: use for input fields
		return false;
	}
	
	@Override
	public boolean keyUp(int key)
	{
		keysDown[key] = false;
		return false;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		return false;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer)
	{
		return false;
	}
	
	@Override
	public boolean mouseMoved(int x, int y)
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
		if(spawnTimer > 0) spawnTimer--;
		if(paused) return;
		for(int z = 0; z < maps.length; z++)
		{
			spawnEntities(maps[z], z);
			if(maps[z].needsLighting) updateLighting(maps[z]);
			Object[] list = maps[z].entities.toArray();
			for(int a = 0; a < list.length; a++)
			{
				((Entity) list[a]).tick();
			}
			if(maps[z].needsLighting) updateLighting(maps[z]);
		}
		for(int a = 0; a < keyPress.length; a++)
		{
			keyPress[a] = false;
		}
	}
	
	public static boolean addEntity(Entity e, Map map, boolean needsTile)
	{
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
	
	public static String numToStr(int num, int len, String filler)
	{
		String temp = String.valueOf(num);
		while(temp.length() < len)
			temp = filler + temp;
		String[] digits = temp.split("");
		String result = "";
		int mod = digits.length % 3;
		for(int a = 1; a < digits.length;)
		{
			result += digits[a];
			a++;
			if(a % 3 == mod && a < digits.length) result += ",";
		}
		return result;
	}
	
	public static void message(String message)
	{
		messages.add(new Message(message, 600));
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
		else type = KeyType.SKELETON;
		list.add(new ItemKey(type));
		// TODO: make this actually do something
	}
	
	public static void insertRandomLoot(ArrayList<Item> list, EntityLiving source)
	{
		if(source instanceof EntitySkeleton)
		{
			if(rand.nextInt(150) == 27) list.add(new ItemKey(KeyType.SKELETON));
			int count = rollDice(2, 3);
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
			for(int b = 0; b <= pos; b++)
			{
				if(images[b] == null) pos++;
			}
			images2[a] = images[pos];
			images[pos] = null;
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
			items.add(newList.remove(0));
		}
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
		if(val >= 500)
		{
			str += "D";
			val -= 500;
		}
		while(val > 100)
		{
			str += "C";
			val -= 100;
		}
		while(val > 10)
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