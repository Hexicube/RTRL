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
	public void tick()
	{}
	
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		if(contents.size() == 1)
		{
			Item i = contents.get(0);
			i.render(batch, Game.width/2 + (xPos - camX) * 32 - 16, Game.height/2 + (yPos - camY) * 32 - 16, false);
		}
		else batch.draw(tex, Game.width/2 + (xPos - camX) * 32 - 16, Game.height/2 + (yPos - camY) * 32 - 16);
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