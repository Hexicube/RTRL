package org.tilegames.hexicube.topdownproto;

public class KeyManager
{	
	//TODO: link into controls menu, load key options
	
	public void tick()
	{
		
	}
	
	public int getKeyForID(int ID)
	{
		for(KeyBind bind : KeyBind.values())
		{
			if(bind.ID == ID) return bind.value;
		}
		return -1;
	}
	
	public int getIDForKey(int key)
	{
		for(KeyBind bind : KeyBind.values())
		{
			if(bind.value == key) return bind.ID;
		}
		return -1;
	}
}