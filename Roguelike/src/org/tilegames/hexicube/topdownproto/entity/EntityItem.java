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
	public void tick()
	{}
	
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		curItem.render(batch, Game.width/2 + x * 32 - 16, Game.height/2 + y * 32 - 16, false);
	}
	
	@Override
	public void collide(Entity entity)
	{}
	
	@Override
	public boolean visible(Entity looker)
	{
		return true;
	}
}