package org.tilegames.hexicube.topdownproto.item.weapon;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityLiving;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemWeaponBone extends ItemWeapon
{
	private int durability;
	private int spriteID;
	
	private static Texture tex = Game.loadImage("weapon/bone");
	
	public ItemWeaponBone(int dur)
	{
		durability = dur;
		spriteID = Game.rand.nextInt(3);
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
		return "1d4 BLUNT";
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
		EntityLiving e = (EntityLiving) target;
		if(!e.alive) return false;
		e.hurt(Game.rollDice(4, 1), DamageType.BLUNT);
		durability--;
		if(durability == 0) Game.message("Your Bone Club breaks.");
		return true;
	}
	
	@Override
	public DamageType getAttackType()
	{
		return DamageType.BLUNT;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	
	@Override
	public String getName()
	{
		return "Bone Club";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{}
	
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
	public boolean canMove()
	{
		return true;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y, 32, 32, spriteID * 32, 0, 32, 32, false, false);
	}
	
	@Override
	public Color getInvBorderCol()
	{
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public int useDelay()
	{
		return 55;
	}
	
	@Override
	public int getManaCost()
	{
		return 0;
	}
}