package org.tilegames.hexicube.topdownproto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LanguageHandler
{
	private HashMap<String, String> stringTable;
	private File languageFile;
	
	public LanguageHandler(File config)
	{
		languageFile = config;
	}
	
	public String getEntry(String name)
	{
		if(stringTable == null)
		{
			if(languageFile == null) return "Missing language file!";
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(languageFile));
				while(reader.ready())
				{
					String line = reader.readLine();
					String[] data = line.split(":");
					if(data.length < 2)
					{
						System.err.println("Invalid language line (ignoring it):");
						System.err.println("    "+line);
						continue;
					}
					String val = data[1];
					for(int a = 2; a < data.length; a++)
					{
						val += ":"+data[a];
					}
					stringTable.put(data[0], val);
				}
				reader.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		String str = stringTable.get(name);
		if(str == null) return "Missing \""+name+"\" in language file!";
		return str;
	}
}