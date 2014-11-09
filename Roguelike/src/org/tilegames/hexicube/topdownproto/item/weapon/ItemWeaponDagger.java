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

public class ItemWeaponDagger extends ItemWeapon
{
	public static boolean nameDiscovered = false;
	
	public static Texture tex;
	
	private int durability;
	private ItemModifier mod;
	private boolean modDiscovered;
	
	public ItemWeaponDagger()
	{
		int rand = Game.rand.nextInt(10);
		if(rand < 2) mod = ItemModifier.CURSED;
		else if(rand < 5) mod = ItemModifier.SHODDY;
		else if(rand == 9) mod = ItemModifier.QUICK;
		else if(rand != 8) mod = ItemModifier.NONE;
		else mod = ItemModifier.SHARPENED;
		
		durability = Game.rand.nextInt(101) + 50;
	}
	
	@Override
	public String[] getCustomActions(Item other)
	{
		return new String[0];
	}
	
	@Override
	public void handleCustomAction(String action, Item other) {}
	
	@Override
	public String getWeaponDamageRange()
	{
		if(!nameDiscovered) return "???";
		if(!modDiscovered) return "2d3 SHARP";
		if(mod == ItemModifier.SHODDY) return "1d3 SHARP";
		if(mod == ItemModifier.SHARPENED) return "3d3 SHARP";
		return "2d3 SHARP";
	}
	
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
			String s = null;
			if(mod == ItemModifier.SHODDY) s = "shoddy";
			else if(mod == ItemModifier.QUICK) s = "quick";
			else if(mod == ItemModifier.SHARPENED) s = "pointy";
			else mod = null;
			if(mod != null) Game.message("You realise the Dagger is " + s + "...");
			modDiscovered = true;
		}
		EntityLiving e = (EntityLiving) target;
		if(!e.alive) return false;
		int rolls = 2;
		if(mod == ItemModifier.SHODDY) rolls = 1;
		else if(mod == ItemModifier.SHARPENED) rolls = 3;
		e.hurt(Game.rollDice(3, rolls), DamageType.SHARP);
		durability--;
		if(durability == 0) Game.message("The " + getName() + " broke...");
		return true;
	}
	
	@Override
	public int useDelay()
	{
		return (mod == ItemModifier.QUICK) ? 8 : 12;
	}
	
	@Override
	public int getManaCost()
	{
		return 0;
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
		if(nameDiscovered)
		{
			if(modDiscovered)
			{
				if(mod == null || mod == ItemModifier.NONE) return "Dagger";
				if(mod == ItemModifier.CURSED) return "Cursed Dagger";
				if(mod == ItemModifier.SHODDY) return "Rusted Dagger";
				if(mod == ItemModifier.SHARPENED) return "Pointy Dagger";
				if(mod == ItemModifier.QUICK) return "Quick Dagger";
				System.out.println("Removed bad modifier on dagger: " + mod);
				mod = ItemModifier.NONE;
				return "Dagger";
			}
			else return "Unusual Dagger";
		}
		else
		{
			if(mod == null || mod == ItemModifier.NONE) return "Unknown sword";
			return "Unusual Sword";
		}
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(entity instanceof EntityPlayer)
		{
			if(equipped)
			{
				if(!nameDiscovered)
				{
					nameDiscovered = true;
					Game.message("Discovered weapon: Dagger");
				}
				if(!modDiscovered)
				{
					if(mod == ItemModifier.CURSED)
					{
						modDiscovered = true;
						Game.message("The Dagger is cursed, you can't remove it!");
					}
					else if(mod == null || mod == ItemModifier.NONE) modDiscovered = true;
				}
			}
		}
	}
	
	@Override
	public int getMaxDurability()
	{
		return 150;
	}
	
	@Override
	public int getCurrentDurability()
	{
		return durability;
	}
	
	@Override
	public boolean canMove()
	{
		return !(mod == ItemModifier.CURSED);
	}
	
	@Override
	public boolean purify()
	{
		if(mod == ItemModifier.CURSED)
		{
			mod = ItemModifier.NONE;
			return true;
		}
		return false;
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
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y);
	}
}