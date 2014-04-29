package org.tilegames.hexicube.topdownproto;

public enum KeyBind
{	
	UP(0, 0), DOWN(1, 0), LEFT(2, 0), RIGHT(3, 0);
	
	public int ID, value;
	public boolean isPressed, isHeld;
	
	private KeyBind(int keyID, int defaultKey)
	{
		ID = keyID;
		value = defaultKey;
	}
}