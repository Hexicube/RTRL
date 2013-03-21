package org.tilegames.hexicube.topdownproto;

public class Message
{
	public Message(String t, int l)
	{
		text = t;
		timeLeft = l;
	}
	
	public String text;
	public int timeLeft;
}