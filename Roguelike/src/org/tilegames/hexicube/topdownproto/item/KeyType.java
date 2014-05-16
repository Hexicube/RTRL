package org.tilegames.hexicube.topdownproto.item;

public enum KeyType
{
	NONE(""), RED("Red"), YELLOW("Yellow"), GREEN("Green"),
	BLUE("Blue"), ORANGE("Orange"), VIOLET("Violet"), ARCADE("Arcade");
	
	public String name;
	
	private KeyType(String name)
	{
		this.name = name;
	}
}