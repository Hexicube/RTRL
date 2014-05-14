package org.tilegames.hexicube.topdownproto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class KeyHandler
{
	public enum Key
	{
		LEFT("Left", 21), RIGHT("Right", 22), UP("Up", 19), DOWN("Down", 20),
		
		LOOK("Look", 0), INV("Inventory", 0), USE("Use", 0), USE_SELF("UseSelf", 0),
		
		PAUSE("Pause", 131);
		
		private int keyID, defaultValue;
		private String name;
		private static int numKeys;
		
		private Key(String name, int defaultVal)
		{
			this.keyID = Key.nextID();
			this.name = name;
			defaultValue = defaultVal;
		}
		
		private static int nextID()
		{
			return numKeys++;
		}
		
		public int getID()
		{
			return keyID;
		}
		
		public int getDefaultValue()
		{
			return defaultValue;
		}
		
		public String getName()
		{
			return name;
		}
		
		public static int numKeys()
		{
			return numKeys;
		}
	}
	
	private int[] keyValues;
	private boolean[] keyPressed = new boolean[256];
	private boolean[] keyHeld = new boolean[256];
	
	private File config;
	private boolean needsSaving;
	
	public KeyHandler(File config)
	{
		this.config = config;
		keyValues = new int[Key.numKeys()];
		for(Key k : Key.values())
		{
			keyValues[k.getID()] = k.getDefaultValue();
		}
		if(config != null)
		{
			if(!config.exists() || !config.isFile())
			{
				needsSaving = true;
				saveKeys();
			}
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(config));
				while(reader.ready())
				{
					String line = reader.readLine();
					String[] data = line.split(":");
					if(data.length != 2)
					{
						System.err.println("Invalid key config line (ignoring it):");
						System.err.println("    "+line);
						continue;
					}
					for(Key k : Key.values())
					{
						if(k.getName().equalsIgnoreCase(data[0]))
						{
							try
							{
								keyValues[k.getID()] = Integer.parseInt(data[1]);
							}
							catch(NumberFormatException e)
							{
								System.err.println("Invalid key config line (ignoring it):");
								System.err.println("    "+line);
							}
						}
					}
				}
				reader.close();
				needsSaving = true;
				saveKeys();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void setKeyBind(Key key, int ID)
	{
		keyValues[key.getID()] = ID;
		needsSaving = true;
	}
	
	public int getKeyBind(Key key)
	{
		return keyValues[key.getID()];
	}
	
	public void saveKeys()
	{
		if(needsSaving)
		{
			needsSaving = false;
			if(config != null)
			{
				try
				{
					PrintWriter writer = new PrintWriter(config);
					for(Key k : Key.values())
					{
						writer.println(k.getName()+":"+keyValues[k.getID()]);
					}
					writer.flush();
					writer.close();
				}
				catch(FileNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void keyPress(int key)
	{
		if(key == 0) return;
		try
		{
			keyPressed[key] = true;
			keyHeld[key] = true;
		}
		catch(IndexOutOfBoundsException e) {}
	}
	
	public void keyRelease(int key)
	{
		try
		{
			keyHeld[key] = false;
		}
		catch(IndexOutOfBoundsException e) {}
	}
	
	public boolean isKeyPressed(int key)
	{
		try
		{
			return keyPressed[keyValues[key]];
		}
		catch(IndexOutOfBoundsException e)
		{
			return false;
		}
	}
	
	public boolean isKeyHeld(int key)
	{
		try
		{
			return keyHeld[keyValues[key]];
		}
		catch(IndexOutOfBoundsException e)
		{
			return false;
		}
	}
	
	public boolean isKeyPressed(Key key)
	{
		try
		{
			return keyPressed[keyValues[key.getID()]];
		}
		catch(IndexOutOfBoundsException e)
		{
			return false;
		}
	}
	
	public boolean isKeyHeld(Key key)
	{
		try
		{
			return keyHeld[keyValues[key.getID()]];
		}
		catch(IndexOutOfBoundsException e)
		{
			return false;
		}
	}
	
	public static String getProperName(int key)
	{
		try
		{
			String s = keyNames[key];
			if(s.equals("???")) return "#"+key;
			return s;
		}
		catch(IndexOutOfBoundsException e)
		{
			return "#"+key;
		}
	}
	
	public void tick()
	{
		for(int a = 0; a < keyPressed.length; a++)
		{
			keyPressed[a] = false;
		}
	}
	
	private static final String[] keyNames = new String[]{
		"UNKNOWN", "???", "???", "Home", "???", "???", "???", "0", "1", "2",
		"3", "4", "5", "6", "7", "8", "9", "Asterisk", "Pound", "Up",
		"Down", "Left", "Right", "???", "???", "???", "???", "???", "???", "A",
		"B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
		"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
		
		"V", "W", "X", "Y", "Z", "Comma", "Period", "Left Alt", "Right Alt", "Left Shift",
		"Right Shift", "Tab", "Space", "???", "???", "Envelope", "Enter", "Backspace", "Grave", "Minus",
		"???", "???", "???", "Backslash", "Semicolon", "Hash", "Forward Slash", "At Sign", "Number", "???",
		"???", "Plus", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "Delete", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "Left Control",
		"Right Control", "Escape", "End", "Insert", "???", "???", "???", "???", "???", "???",
		"???", "???", "Page Up", "Page Down", "Numpad 0", "Numpad 1", "Numpad 2", "Numpad 3", "Numpad 4", "Numpad 5",
		
		"Numpad 6", "Numpad 7", "Numpad 8", "Numpad 9", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "???", "???", "???", "???", "???", "???", "???",
		"???", "???", "???", "Colon", "F1", "F2", "F3", "F4", "F5", "F6",
		
		"F7", "F8", "F9", "F10", "F11", "F12"
	};
}