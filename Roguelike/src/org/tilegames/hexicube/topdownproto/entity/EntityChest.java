package org.tilegames.hexicube.topdownproto.entity;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.Item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityChest extends Entity
{
	public static Texture tex = Game.loadImage("chest");
	
	public ArrayList<Item> contents;
	
	public EntityChest(int x, int y, ArrayList<Item> items)
	{
		xPos = x;
		yPos = y;
		contents = items;
	}
	
	@Override
	public void tick() {}
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		batch.draw(tex, Game.xOffset+(xPos-camX)*32, Game.yOffset+(yPos-camY)*32);
	}
	@Override
	public void collide(Entity entity) {}
	@Override
	public boolean visible(Entity looker)
	{
		return true;
	}
}