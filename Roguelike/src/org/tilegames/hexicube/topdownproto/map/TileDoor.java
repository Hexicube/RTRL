package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.KeyType;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileDoor extends Tile
{
	private static Texture tex = Game.loadImage("door");
	
	private boolean opened, horizontal;
	
	private KeyType requiredKey;
	
	private Entity curEnt;
	
	public TileDoor(boolean horizontal, boolean forceBasic)
	{
		this.horizontal = horizontal;
		if(forceBasic) requiredKey = KeyType.NONE;
		else
		{
			int rand = Game.rand.nextInt(6);
			if(rand == 0) requiredKey = KeyType.RED;
			else if(rand == 1) requiredKey = KeyType.ORANGE;
			else if(rand == 2) requiredKey = KeyType.YELLOW;
			else if(rand == 3) requiredKey = KeyType.GREEN;
			else if(rand == 4) requiredKey = KeyType.BLUE;
			else if(rand == 5) requiredKey = KeyType.VIOLET;
		}
	}
	
	@Override
	public boolean onWalkAttempt(Entity entity)
	{
		if(entity instanceof EntityPlayer && !opened) Game.message("You need to open the door first!");
		return opened;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		//batch.setColor((float)(lightLevel[0]+3)/18f, (float)(lightLevel[1]+3)/18f, (float)(lightLevel[2]+3)/18f, 1);
		boolean doorHorizontal = (horizontal ^ opened);
		int tileX = x+Game.camX;
		int tileY = y+Game.camY;
		if(!opened)
		{
			Tile t2 = map.tiles[doorHorizontal?tileX:(tileX+1)][doorHorizontal?(tileY-1):tileY];
			int[] light = new int[]{t2.lightLevel[0], t2.lightLevel[1], t2.lightLevel[2]};
			if(light[0] == 0) light[0] = 1;
			if(light[1] == 0) light[1] = 1;
			if(light[2] == 0) light[2] = 1;
			batch.setColor((float)(light[0]+2)/18f, (float)(light[1]+2)/18f, (float)(light[2]+2)/18f, 1);
		}
		batch.draw(Game.tileTex, Game.xOffset+x*32, Game.yOffset+y*32, 32, 32, 0, 0, 32, 32, false, false);
		if(!opened)
		{
			Tile t2 = map.tiles[doorHorizontal?tileX:(tileX+1)][doorHorizontal?(tileY-1):tileY];
			Tile t3 = map.tiles[doorHorizontal?tileX:(tileX-1)][doorHorizontal?(tileY+1):tileY];
			int[] light = new int[]{t2.lightLevel[0], t2.lightLevel[1], t2.lightLevel[2]};
			if(t3.lightLevel[0] > light[0]) light[0] = t3.lightLevel[0];
			if(t3.lightLevel[1] > light[0]) light[1] = t3.lightLevel[1];
			if(t3.lightLevel[2] > light[0]) light[2] = t3.lightLevel[2];
			if(light[0] == 0) light[0] = 1;
			if(light[1] == 0) light[1] = 1;
			if(light[2] == 0) light[2] = 1;
			batch.setColor((float)(light[0]+2)/18f, (float)(light[1]+2)/18f, (float)(light[2]+2)/18f, 1);
		}
		int texX = doorHorizontal?0:32;
		batch.draw(tex, Game.xOffset+x*32, Game.yOffset+y*32, 32, 32, texX, 0, 32, 32, false, false);
	}
	
	@Override
	public void setCurrentEntity(Entity entity)
	{
		curEnt = entity;
	}
	
	@Override
	public Entity getCurrentEntity()
	{
		return curEnt;
	}
	
	@Override
	public boolean lightable()
	{
		return opened;
	}
	
	@Override
	public void use(Entity entity)
	{
		if(requiredKey == KeyType.NONE)
		{
			if(opened)
			{
				if(curEnt == null)
				{
					opened = false;
					entity.map.needsLighting = true;
				}
				else Game.message("Something is blocking the door!");
			}
			else
			{
				opened = true;
				entity.map.needsLighting = true;
			}
		}
		else
		{
			//TODO: inv checks
			Game.message("You need the "+requiredKey+" key or a skeleton key to open that!");
		}
	}
}