package org.tilegames.hexicube.topdownproto.item.weapon;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityLeechBolt;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemWandLeechLife extends ItemWeapon
{
	public static Texture tex;
	
	private static boolean nameDiscovered = false;
	
	private int durability;
	
	public ItemWandLeechLife()
	{
		durability = Game.rand.nextInt(101) + 300;
	}
	
	@Override
	public String getWeaponDamageRange()
	{
		if(nameDiscovered) return "2d10 GENERIC";
		return "???";
	}
	
	@Override
	public boolean use(Entity source, Direction dir)
	{
		if(!(source instanceof EntityPlayer)) return false;
		EntityPlayer p = (EntityPlayer) source;
		if(p.mana >= 12)
		{
			p.mana -= 12;
			p.manaExperience += 12;
			EntityLeechBolt e = new EntityLeechBolt(p.facingDir, p, p.xPos, p.yPos);
			Game.addEntity(e, p.map, false);
			e.move(p.facingDir);
			durability--;
			if(durability == 0) Game.message("The " + getName() + " broke...");
			if(!nameDiscovered)
			{
				nameDiscovered = true;
				Game.message("Discovered wand: Vampirism");
			}
			return true;
		}
		else
		{
			Game.message("You need 12 mana to do that! (You have " + p.mana + ")");
			return false;
		}
	}
	
	@Override
	public int useDelay()
	{
		return 120;
	}
	
	@Override
	public DamageType getAttackType()
	{
		return DamageType.GENERIC;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return ItemModifier.NONE;
	}
	
	@Override
	public String getName()
	{
		if(!nameDiscovered) return "Unknown Wand";
		return "Wand of Vampirism";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{}
	
	@Override
	public int getMaxDurability()
	{
		return 400;
	}
	
	@Override
	public int getCurrentDurability()
	{
		return durability;
	}
	
	@Override
	public boolean canMove()
	{
		return true;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y);
	}
}