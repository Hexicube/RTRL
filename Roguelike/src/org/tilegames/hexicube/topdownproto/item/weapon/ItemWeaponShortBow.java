package org.tilegames.hexicube.topdownproto.item.weapon;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityArrow;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemWeaponShortBow extends ItemWeapon
{
	public static boolean nameDiscovered = false;
	private static Texture tex = Game.loadImage("weapon/shortbow");
	
	private ItemModifier mod;
	private boolean modDiscovered;
	private int durability;
	
	public ItemWeaponShortBow()
	{
		int rand = Game.rand.nextInt(10);
		if(rand == 0) mod = ItemModifier.CURSED;
		else if(rand < 5) mod = ItemModifier.SHODDY;
		else mod = ItemModifier.NONE;
		durability = Game.rand.nextInt(61) + 20;
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
		if(mod == ItemModifier.SHODDY) return "1d4 ARROW";
		return "2d4 ARROW";
	}
	
	@Override
	public boolean use(Entity source, Direction dir)
	{
		if(dir == Direction.NONE) return false; 
		if(source instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)source;
			for(int x = 0; x < 10; x++)
			{
				for(int y = 1; y < 11; y++)
				{
					Item i = p.getItemInSlot(x, y);
					if(i instanceof ItemArrow)
					{
						ItemArrow arrow = (ItemArrow) i;
						if(arrow.getStackSize() <= 1) p.setItemInSlot(x, y, null);
						else arrow.setStackSize(arrow.getStackSize() - 1);
						EntityArrow entityArrow = new EntityArrow(p.xPos, p.yPos, 4, 1 + ((mod == ItemModifier.SHODDY) ? 0 : 1), dir, arrow.getAttackType());
						Game.addEntity(entityArrow, p.map, false);
						entityArrow.move(dir);
						if(!nameDiscovered)
						{
							nameDiscovered = true;
							Game.message("Discovered weapon: Shortbow");
						}
						if(!modDiscovered && mod == ItemModifier.SHODDY)
						{
							modDiscovered = true;
							Game.message("You realise the Shortbow is shoddy...");
						}
						durability--;
						if(durability == 0) Game.message("The " + getName() + " broke...");
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public int useDelay()
	{
		return 30;
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
			if(mod == null || mod == ItemModifier.NONE)
			{
				modDiscovered = true;
				return "Shortbow";
			}
			if(!modDiscovered) return "Unusual Shortbow";
			if(mod == ItemModifier.CURSED) return "Cursed Shortbow";
			if(mod == ItemModifier.SHODDY) return "Shoddy Shortbow";
			System.out.println("Removed bad modifier on bow: " + mod);
			mod = ItemModifier.NONE;
			return "Shortbow";
		}
		else
		{
			if(mod == null || mod == ItemModifier.NONE) return "Unknown Bow";
			if(mod == ItemModifier.CURSED || mod == ItemModifier.SHODDY) return "Unusual Bow";
			System.out.println("Removed bad modifier on bow: " + mod);
			mod = ItemModifier.NONE;
			return "Unknown Bow";
		}
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(entity instanceof EntityPlayer)
		{
			if(equipped)
			{
				if(!modDiscovered && mod == ItemModifier.CURSED)
				{
					modDiscovered = true;
					if(nameDiscovered) Game.message("The Shortbow is cursed, you can't remove it!");
					else Game.message("The bow is cursed, you can't remove it!");
				}
			}
		}
	}
	
	@Override
	public int getMaxDurability()
	{
		return 80;
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