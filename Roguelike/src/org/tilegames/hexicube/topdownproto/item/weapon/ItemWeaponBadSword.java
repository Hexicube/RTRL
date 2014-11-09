package org.tilegames.hexicube.topdownproto.item.weapon;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityLiving;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemWeaponBadSword extends ItemWeapon
{
	public static boolean nameDiscovered = false;
	private boolean modDiscovered;
	
	public static Texture tex;
	
	private int durability;
	private ItemModifier mod;
	
	public ItemWeaponBadSword()
	{
		int rand = Game.rand.nextInt(10);
		if(rand < 2) mod = ItemModifier.SHODDY;
		else if(rand < 6) mod = ItemModifier.NONE;
		else if(rand < 9) mod = ItemModifier.CURSED;
		else mod = ItemModifier.SHARPENED;
		
		durability = getMaxDurability();
		
		nameDiscovered = false;
		modDiscovered = false;
	}
	
	@Override
	public String[] getCustomActions(Item other)
	{
		return new String[0];
	}
	
	@Override
	public void handleCustomAction(String action, Item other) {}
	
	@Override
	public boolean use(Entity source, Direction dir)
	{
		Entity target = null;
		if(dir == Direction.NONE) return false;
		else if(dir == Direction.UP) target = source.map.tiles[source.xPos][source.yPos + 1].getCurrentEntity();
		else if(dir == Direction.DOWN) target = source.map.tiles[source.xPos][source.yPos - 1].getCurrentEntity();
		else if(dir == Direction.LEFT) target = source.map.tiles[source.xPos - 1][source.yPos].getCurrentEntity();
		else if(dir == Direction.RIGHT) target = source.map.tiles[source.xPos + 1][source.yPos].getCurrentEntity();
		else return false;
		if(target == null) return false;
		if(!(target instanceof EntityLiving)) return false;
		if(durability <= 0) return false;
		if(!modDiscovered)
		{
			Game.message("You realise the Badsword is " + (mod == ItemModifier.SHODDY ? "shoddy" : "sharp") + "...");
			modDiscovered = true;
		}
		EntityLiving e = (EntityLiving) target;
		if(!e.alive) return false;
		e.hurt((mod == ItemModifier.SHARPENED ? Game.rollDice(2, 4) : (Game.rollDice(4, 1) - (mod == ItemModifier.SHODDY ? 1 : 0))), DamageType.SHARP);
		durability--;
		if(durability == 0) Game.message("The " + getName() + " broke...");
		return true;
	}
	
	@Override
	public String getWeaponDamageRange()
	{
		if(!nameDiscovered) return "???";
		if(!modDiscovered) return "1d4 SHARP";
		if(mod == ItemModifier.SHARPENED) return "2d4 SHARP";
		if(mod == ItemModifier.SHODDY) return "1d4-1 SHARP";
		return "1d4 SHARP";
	}
	
	@Override
	public int getMaxDurability()
	{
		return 200;
	}
	
	@Override
	public int getCurrentDurability()
	{
		return durability;
	}
	
	@Override
	public DamageType getAttackType()
	{
		return DamageType.SHARP;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return mod;
	}
	
	@Override
	public String getName()
	{
		if(mod == null || mod == ItemModifier.NONE) modDiscovered = true;
		if(!nameDiscovered)
		{
			if(modDiscovered) return "Unknown Sword";
			return "Unusual Sword";
		}
		if(!modDiscovered) return "Unusual Badsword";
		if(mod == null || mod == ItemModifier.NONE) return "Badsword";
		else if(mod == ItemModifier.CURSED) return "Cursed Badsword";
		else if(mod == ItemModifier.SHARPENED) return "Sharpened Badsword";
		else if(mod == ItemModifier.SHODDY) return "Really Badsword";
		else
		{
			System.out.println("Removed bad modifier on badsword: " + mod);
			mod = ItemModifier.NONE;
		}
		return "Badsword";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(entity instanceof EntityPlayer && equipped)
		{
			if(!nameDiscovered) Game.message("Discovered weapon: Badsword");
			nameDiscovered = true;
			if(!modDiscovered)
			{
				if(mod == ItemModifier.CURSED)
				{
					Game.message("The Badsword is cursed, you can't remove it!");
					modDiscovered = true;
				}
				if(mod == null || mod == ItemModifier.NONE) modDiscovered = true;
			}
		}
	}
	
	@Override
	public boolean canMove()
	{
		return !(mod == ItemModifier.CURSED);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y);
	}
	
	@Override
	public Color getInvBorderCol()
	{
		if(!nameDiscovered) return Color.ORANGE;
		if(mod != ItemModifier.NONE)
		{
			if(modDiscovered && mod == ItemModifier.CURSED) return Color.RED;
			return Color.ORANGE;
		}
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public int useDelay()
	{
		return 35;
	}
	
	@Override
	public int getManaCost()
	{
		return 0;
	}
}