package org.tilegames.hexicube.topdownproto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

public class SoundHandler
{
	public int master, music, effects;
	private HashMap<String, Integer> specificSoundLevels;
	
	private File config;
	
	public SoundHandler(File config)
	{
		this.config = config;
		specificSoundLevels = new HashMap<String, Integer>();
		if(config != null)
		{
			if(!config.exists() || !config.isFile()) saveSounds();
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(config));
				while(reader.ready())
				{
					String line = reader.readLine();
					String[] data = line.split(":");
					if(data.length != 2)
					{
						System.err.println("Invalid sound config line (ignoring it):");
						System.err.println("    "+line);
						continue;
					}
					int val;
					try
					{
						val = Integer.parseInt(data[1]);
					}
					catch(NumberFormatException e)
					{
						System.err.println("Invalid sound config line (ignoring it):");
						System.err.println("    "+line);
						continue;
					}
					if(data[0].equals("Master")) master = val;
					else if(data[0].equals("Music")) music = val;
					else if(data[0].equals("Effects")) effects = val;
					else if(data[0].startsWith("#"))
					{
						specificSoundLevels.put(data[0].substring(1), val);
					}
					else
					{
						System.err.println("Invalid sound config line (ignoring it):");
						System.err.println("    "+line);
					}
				}
				reader.close();
				saveSounds();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void saveSounds()
	{
		if(config != null)
		{
			try
			{
				PrintWriter writer = new PrintWriter(config);
				writer.println("Master:"+master);
				writer.println("Music:"+music);
				writer.println("Effects:"+effects);
				for(Entry<String, Integer> e : specificSoundLevels.entrySet())
				{
					if(e.getValue() != 100) writer.println("#"+e.getKey()+":"+e.getValue());
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
	
	public int getSoundLevel(String soundName, String soundType)
	{
		int val = 1;
		if(soundType.equals("Master")) val *= master;
		else if(soundType.equals("Music")) val *= music;
		else if(soundType.equals("Effects")) val *= effects;
		else
		{
			System.out.println("Unknown sound type: "+soundType);
			val *= 100;
		}
		Integer mod = specificSoundLevels.get(soundName);
		if(mod == null)
		{
			System.out.println("Unknown sound name (will be added): "+soundName);
			specificSoundLevels.put(soundName, 100);
			val *= 100;
		}
		else val *= mod;
		val /= 10000;
		return val;
	}
}