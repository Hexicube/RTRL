package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityArrow;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemWeaponShortBow extends ItemWeapon
{
	private static boolean nameDiscovered = false;
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
		durability = Game.rand.nextInt(61)+20;
	}
	
	@Override
	public String getWeaponDamageRange()
	{
		if(!nameDiscovered) return "???";
		if(mod == ItemModifier.SHODDY) return "1d4 SHARP";
		return "2d4 SHARP";
	}
	@Override
	public boolean use(Entity source, Direction dir)
	{
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
						ItemArrow arrow = (ItemArrow)i;
						if(arrow.getStackSize() <= 1) p.setItemInSlot(x, y, null);
						else arrow.setStackSize(arrow.getStackSize()-1);
						EntityArrow entityArrow = new EntityArrow(p.xPos, p.yPos, 4, 1+((mod==ItemModifier.SHODDY)?0:1), p.facingDir, arrow.getType());
						Game.addEntity(entityArrow, p.map, false);
						entityArrow.move(p.facingDir);
						if(!modDiscovered && mod == ItemModifier.SHODDY)
						{
							modDiscovered = true;
							Game.message("You realise the Shortbow is shoddy...");
						}
						durability--;
						if(durability == 0) Game.message("The "+getName()+" broke...");
					}
				}
			}
		}
		return false;
	}
	@Override
	public int useDelay()
	{
		return 35;
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
			System.out.println("Removed bad modifier on bow: "+mod);
			mod = ItemModifier.NONE;
			return "Shortbow";
		}
		else
		{
			if(mod == null || mod == ItemModifier.NONE) return "Unknown Bow";
			if(mod == ItemModifier.CURSED || mod == ItemModifier.SHODDY) return "Unusual Bow";
			System.out.println("Removed bad modifier on bow: "+mod);
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
				if(!nameDiscovered)
				{
					nameDiscovered = true;
					Game.message("Discovered weapon: Shortbow");
				}
				if(!modDiscovered && mod == ItemModifier.CURSED)
				{
					modDiscovered = true;
					Game.message("The Shortbow is cursed, you can't remove it!");
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
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y);
	}
}