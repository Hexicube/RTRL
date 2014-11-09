package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiElementInvItem extends GuiElementClickable
{
	private EntityPlayer player;
	private int invX, invY;
	
	private boolean clicked;
	
	public GuiElementInvItem(int x, int y, float xAlign, float yAlign, EntityPlayer player, int invX, int invY)
	{
		super(x, y, xAlign, yAlign);
		this.player = player;
		this.invX = invX;
		this.invY = invY;
	}
	
	@Override
	public boolean gotClicked(int x, int y, int pointer)
	{
		int[] pos = getPosition();
		if(x >=  pos[0] && x < pos[0]+34 && y >= pos[1] && y <= pos[1]+34) return true;
		return false;
	}
	
	@Override
	public void handleClick()
	{
		clicked = true;
	}
	
	@Override
	public void tick()
	{
		clicked = false;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		Item item = player.getItemInSlot(invX, invY);
		if(item != null)
		{
			int[] pos = getPosition();
			Color c = batch.getColor();
			batch.setColor(item.getInvBorderCol());
			batch.draw(Game.invItemTypeTex, pos[0] - 1, pos[1] - 31);
			batch.setColor(1, 1, 1, 1);
			item.render(batch, pos[0], pos[1], invY==0);
			if(item.getMaxDurability() > 1)
			{
				int stage = item.getCurrentDurability() * 16 / item.getMaxDurability();
				if(stage == 16) stage = 15;
				batch.setColor(1, 1, 1, 1);
				batch.draw(Game.invUsedBar, pos[0], pos[1] - 3, 32, 2, 0, 30 - stage * 2, 32, 2, false, false);
			}
			batch.setColor(c);
		}
	}
	
	public boolean checked()
	{
		return clicked;
	}
}