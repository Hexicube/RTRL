package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityChest;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemTheBox extends ItemBox
{
	@Override
	protected Item[] open()
	{
		return new Item[]{new ItemTheBox()};
	}
	
	@Override
	public void check()
	{
		Game.message("I'm a special box, I contain myself!");
	}

	@Override
	public String getName()
	{
		return "THE BOX";
	}

	@Override
	public void tick(Entity entity, boolean equipped)
	{}

	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(EntityChest.tex, x, y);
	}
}