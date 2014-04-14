package org.tilegames.hexicube.topdownproto.item.weapon;

import org.tilegames.hexicube.topdownproto.Game;
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
	private boolean modDiscovered;
	
	private int durability;
	
	private ItemModifier modifier;
	
	public ItemWandLeechLife()
	{
		durability = Game.rand.nextInt(101) + 300;
		int val = Game.rand.nextInt(10);
		if(val < 3) modifier = ItemModifier.CONSERVATIVE;
		else
		{
			modifier = ItemModifier.NONE;
			modDiscovered = true;
		}
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
		int manaCost = 12;
		if(modifier == ItemModifier.CONSERVATIVE) manaCost = 9;
		if(p.mana >= manaCost)
		{
			p.mana -= manaCost;
			p.manaExperience += manaCost;
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
			if(!modDiscovered)
			{
				modDiscovered = true;
				Game.message("Discovered modifier: Conservative");
			}
			return true;
		}
		else
		{
			Game.message("You need "+manaCost+" mana to do that! (You have " + p.mana + ")");
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
		return modifier;
	}
	
	@Override
	public String getName()
	{
		if(!nameDiscovered)
		{
			if(modifier == ItemModifier.CONSERVATIVE) return "Unusual Wand";
			return "Unknown Wand";
		}
		if(modifier == ItemModifier.CONSERVATIVE)
		{
			if(modDiscovered) return "Conservative Wand of Vampirism";
			return "Unusual Wand of Vampirism";
		}
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