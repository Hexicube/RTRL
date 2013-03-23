package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.Item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityItem extends Entity
{
	public static Texture tex = Game.loadImage("items");
	
	public Item curItem;
	
	public EntityItem(int x, int y, Item item)
	{
		xPos = x;
		yPos = y;
		curItem = item;
	}
	
	@Override
	public void tick() {}
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		int ID = curItem.getItemID();
		int x = ID%16;
		int y = ID/16;
		batch.draw(tex, Game.xOffset+(xPos-camX)*32, Game.yOffset+(yPos-camY)*32, 32, 32, x*32, y*32, 32, 32, false, false);
		//TODO: this
	}
	@Override
	public void collide(Entity entity) {}
}