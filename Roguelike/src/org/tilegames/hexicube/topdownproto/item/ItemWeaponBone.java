package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityLiving;

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
		spriteID = Game.rand.nextInt(4);
	}
	
	@Override
	public String getWeaponDamageRange()
	{
		return "1d4";
	}
	@Override
	public boolean use(Entity source, Direction dir)
	{
		Entity target = null;
		if(dir == Direction.NONE) return false;
		else if(dir == Direction.UP) target = source.map.tiles[source.xPos][source.yPos+1].getCurrentEntity();
		else if(dir == Direction.DOWN) target = source.map.tiles[source.xPos][source.yPos-1].getCurrentEntity();
		else if(dir == Direction.LEFT) target = source.map.tiles[source.xPos-1][source.yPos].getCurrentEntity();
		else if(dir == Direction.RIGHT) target = source.map.tiles[source.xPos+1][source.yPos].getCurrentEntity();
		else return false;
		if(target == null) return false;
		if(!(target instanceof EntityLiving)) return false;
		if(durability <= 0) return false;
		EntityLiving e = (EntityLiving)target;
		if(!e.alive) return false;
		e.hurt(Game.rollDice(4, 1), DamageType.BLUNT);
		durability--;
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
		return "Bone";
	}
	@Override
	public void tick(Entity entity, boolean equipped) {}
	@Override
	public int getMaxDurability()
	{
		return 7200;
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
		batch.draw(tex, x, y, 32, 32, spriteID*32, 0, 32, 32, false, false);
	}
}