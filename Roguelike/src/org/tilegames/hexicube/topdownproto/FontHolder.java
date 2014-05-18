package org.tilegames.hexicube.topdownproto;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FontHolder
{
	public static Texture font;
	public static FontChar[] characterList;
	
	public static void prep()
	{
		font = Game.loadImage("font");
		characterList = new FontChar[95];
		characterList[0] = new FontChar("A".charAt(0), 0, 0, 5);
		characterList[1] = new FontChar("B".charAt(0), 1, 0, 5);
		characterList[2] = new FontChar("C".charAt(0), 2, 0, 5);
		characterList[3] = new FontChar("D".charAt(0), 3, 0, 5);
		characterList[4] = new FontChar("E".charAt(0), 4, 0, 5);
		characterList[5] = new FontChar("F".charAt(0), 5, 0, 5);
		characterList[6] = new FontChar("G".charAt(0), 6, 0, 5);
		characterList[7] = new FontChar("H".charAt(0), 7, 0, 5);
		characterList[8] = new FontChar("I".charAt(0), 8, 0, 5);
		characterList[9] = new FontChar("J".charAt(0), 9, 0, 5);
		characterList[10] = new FontChar("K".charAt(0), 10, 0, 5);
		characterList[11] = new FontChar("L".charAt(0), 11, 0, 5);
		characterList[12] = new FontChar("M".charAt(0), 12, 0, 5);
		characterList[13] = new FontChar("N".charAt(0), 13, 0, 5);
		characterList[14] = new FontChar("O".charAt(0), 14, 0, 5);
		characterList[15] = new FontChar("P".charAt(0), 15, 0, 5);
		characterList[16] = new FontChar("Q".charAt(0), 16, 0, 5);
		characterList[17] = new FontChar("R".charAt(0), 17, 0, 5);
		characterList[18] = new FontChar("S".charAt(0), 18, 0, 5);
		characterList[19] = new FontChar("T".charAt(0), 19, 0, 5);
		characterList[20] = new FontChar("U".charAt(0), 20, 0, 5);
		characterList[21] = new FontChar("V".charAt(0), 0, 1, 5);
		characterList[22] = new FontChar("W".charAt(0), 1, 1, 5);
		characterList[23] = new FontChar("X".charAt(0), 2, 1, 5);
		characterList[24] = new FontChar("Y".charAt(0), 3, 1, 5);
		characterList[25] = new FontChar("Z".charAt(0), 4, 1, 5);
		characterList[26] = new FontChar("a".charAt(0), 5, 1, 5);
		characterList[27] = new FontChar("b".charAt(0), 6, 1, 5);
		characterList[28] = new FontChar("c".charAt(0), 7, 1, 5);
		characterList[29] = new FontChar("d".charAt(0), 8, 1, 5);
		characterList[30] = new FontChar("e".charAt(0), 9, 1, 5);
		characterList[31] = new FontChar("f".charAt(0), 10, 1, 3);
		characterList[32] = new FontChar("g".charAt(0), 11, 1, 5);
		characterList[33] = new FontChar("h".charAt(0), 12, 1, 5);
		characterList[34] = new FontChar("i".charAt(0), 13, 1, 1);
		characterList[35] = new FontChar("j".charAt(0), 14, 1, 2);
		characterList[36] = new FontChar("k".charAt(0), 15, 1, 4);
		characterList[37] = new FontChar("l".charAt(0), 16, 1, 1);
		characterList[38] = new FontChar("m".charAt(0), 17, 1, 5);
		characterList[39] = new FontChar("n".charAt(0), 18, 1, 5);
		characterList[40] = new FontChar("o".charAt(0), 19, 1, 5);
		characterList[41] = new FontChar("p".charAt(0), 20, 1, 5);
		characterList[42] = new FontChar("q".charAt(0), 0, 2, 5);
		characterList[43] = new FontChar("r".charAt(0), 1, 2, 4);
		characterList[44] = new FontChar("s".charAt(0), 2, 2, 5);
		characterList[45] = new FontChar("t".charAt(0), 3, 2, 3);
		characterList[46] = new FontChar("u".charAt(0), 4, 2, 5);
		characterList[47] = new FontChar("v".charAt(0), 5, 2, 5);
		characterList[48] = new FontChar("w".charAt(0), 6, 2, 5);
		characterList[49] = new FontChar("x".charAt(0), 7, 2, 5);
		characterList[50] = new FontChar("y".charAt(0), 8, 2, 5);
		characterList[51] = new FontChar("z".charAt(0), 9, 2, 5);
		characterList[52] = new FontChar("0".charAt(0), 10, 2, 5);
		characterList[53] = new FontChar("1".charAt(0), 11, 2, 5);
		characterList[54] = new FontChar("2".charAt(0), 12, 2, 5);
		characterList[55] = new FontChar("3".charAt(0), 13, 2, 5);
		characterList[56] = new FontChar("4".charAt(0), 14, 2, 5);
		characterList[57] = new FontChar("5".charAt(0), 15, 2, 5);
		characterList[58] = new FontChar("6".charAt(0), 16, 2, 5);
		characterList[59] = new FontChar("7".charAt(0), 17, 2, 5);
		characterList[60] = new FontChar("8".charAt(0), 18, 2, 5);
		characterList[61] = new FontChar("9".charAt(0), 19, 2, 5);
		characterList[62] = new FontChar("!".charAt(0), 20, 2, 1);
		characterList[63] = new FontChar("\"".charAt(0), 0, 3, 3);
		characterList[64] = new FontChar("£".charAt(0), 1, 3, 5);
		characterList[65] = new FontChar("$".charAt(0), 2, 3, 5);
		characterList[66] = new FontChar("%".charAt(0), 3, 3, 5);
		characterList[67] = new FontChar("^".charAt(0), 4, 3, 5);
		characterList[68] = new FontChar("&".charAt(0), 5, 3, 5);
		characterList[69] = new FontChar("*".charAt(0), 6, 3, 3);
		characterList[70] = new FontChar("(".charAt(0), 7, 3, 2);
		characterList[71] = new FontChar(")".charAt(0), 8, 3, 2);
		characterList[72] = new FontChar("_".charAt(0), 9, 3, 5);
		characterList[73] = new FontChar("-".charAt(0), 10, 3, 5);
		characterList[74] = new FontChar("+".charAt(0), 11, 3, 5);
		characterList[75] = new FontChar("=".charAt(0), 12, 3, 5);
		characterList[76] = new FontChar(".".charAt(0), 13, 3, 1);
		characterList[77] = new FontChar(",".charAt(0), 14, 3, 1);
		characterList[78] = new FontChar("<".charAt(0), 15, 3, 4);
		characterList[79] = new FontChar(">".charAt(0), 16, 3, 4);
		characterList[80] = new FontChar("/".charAt(0), 17, 3, 5);
		characterList[81] = new FontChar("\\".charAt(0), 18, 3, 5);
		characterList[82] = new FontChar(":".charAt(0), 19, 3, 1);
		characterList[83] = new FontChar(";".charAt(0), 20, 3, 1);
		characterList[84] = new FontChar("'".charAt(0), 0, 4, 1);
		characterList[85] = new FontChar("#".charAt(0), 1, 4, 5);
		characterList[86] = new FontChar("~".charAt(0), 2, 4, 5);
		characterList[87] = new FontChar("[".charAt(0), 3, 4, 2);
		characterList[88] = new FontChar("]".charAt(0), 4, 4, 2);
		characterList[89] = new FontChar("{".charAt(0), 5, 4, 3);
		characterList[90] = new FontChar("}".charAt(0), 6, 4, 3);
		characterList[91] = new FontChar("|".charAt(0), 7, 4, 1);
		characterList[92] = new FontChar("|".charAt(0), 8, 4, 1);
		characterList[93] = new FontChar("@".charAt(0), 9, 4, 5);
		characterList[94] = new FontChar("?".charAt(0), 10, 4, 5);
	}
	
	public static char[] getCharList(String str)
	{
		char[] list = new char[str.length()];
		for(char a = 0; a < list.length; a++)
		{
			if(str.charAt(a) == " ".charAt(0)) list[a] = 255;
			else
			{
				boolean found = false;
				for(char b = 0; b < characterList.length; b++)
				{
					if(characterList[b].character == str.charAt(a))
					{
						found = true;
						list[a] = b;
						break;
					}
				}
				if(!found) list[a] = 254;
			}
		}
		return list;
	}
	
	public static int getTextWidth(char[] charList, boolean doubleScale)
	{
		int xPos = 0;
		for(int a = 0; a < charList.length; a++)
		{
			if(charList[a] == 255) xPos += 3;
			else if(charList[a] == 254)
			;
			else
			{
				xPos += characterList[charList[a]].width + 1;
			}
		}
		if(doubleScale) xPos *= 2;
		return xPos;
	}
	
	public static boolean isValid(char key)
	{
		if(key == " ".charAt(0)) return true;
		for(int a = 0; a < characterList.length; a++)
		{
			if(characterList[a].character == key) return true;
		}
		return false;
	}
	
	public static void render(SpriteBatch batch, char[] charList, int x, int y, boolean doubleScale)
	{
		int xPos = 0;
		for(int a = 0; a < charList.length; a++)
		{
			if(charList[a] == 255) xPos += doubleScale ? 6 : 3;
			else if(charList[a] == 254)
			;
			else
			{
				batch.draw(font, x + xPos, y - (doubleScale ? 18 : 9), (doubleScale ? 10 : 5), doubleScale ? 18 : 9, characterList[charList[a]].x * 6, characterList[charList[a]].y * 10, 5, 9, false, false);
				xPos += (characterList[charList[a]].width + 1) * (doubleScale ? 2 : 1);
			}
		}
	}
}