package org.tilegames.hexicube.topdownproto.map;

import java.util.Random;

import org.tilegames.hexicube.topdownproto.Game;

public class Generator
{
	private int[][] data;
	
	private int width = 128, height = 128;
	
	private final double roomDensity = 0.002D;
	
	private Random r = new Random();
	
	public int[][] gen(int w, int h, int[] firstRoomPos, boolean needsLadder)
	{
		width = w;
		height = h;
		data = new int[width][height];
		int ladderRoom = needsLadder?1:-1;
		for(int a = 0; a < width*height*roomDensity; a++)
		{
			if(firstRoomPos != null)
			{
				genRoom(firstRoomPos[0], firstRoomPos[1], r.nextInt(11)+15, r.nextInt(11)+15);
				data[firstRoomPos[0]][firstRoomPos[1]] = 6;
				firstRoomPos = null;
			}
			else
			{
				int xPos = r.nextInt(width);
				int yPos = r.nextInt(height);
				if(data[xPos][yPos] == 0)
				{
					genRoom(xPos, yPos, r.nextInt(11)+15, r.nextInt(11)+15);
					if(a == ladderRoom)
					{
						if(data[xPos][yPos] == 2) data[xPos][yPos] = 7;
						else ladderRoom++;
					}
				}
			}
		}
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				if(data[x][y] == 2 && isExposed(x, y))
				{
					if(Game.rand.nextInt(10) == 7) data[x][y] = 5;
					else data[x][y] = 1;
				}
			}
		}
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				if(data[x][y] == 3 && doorType(x, y) == 0) data[x][y] = 2;
			}
		}
		return data;
	}
	
	private void genRoom(int x, int y, int maxWidth, int maxHeight)
	{
		if(data[x][y] != 0) return;
		int leftSide = x, rightSide = x, topSide = y, bottomSide = y;
		while(true)
		{
			int widthLeft = maxWidth - Math.abs(leftSide-rightSide) - 1;
			int heightLeft = maxHeight - Math.abs(topSide-bottomSide) - 1;
			if(widthLeft <= 0 && heightLeft <= 0) break;
			boolean hadChange = false;
			if(heightLeft > 0 && topSide+1 < height)
			{
				boolean canMove = true;
				for(x = leftSide; x <= rightSide && canMove; x++)
				{
					if(data[x][topSide+1] != 0) canMove = false;
				}
				if(canMove)
				{
					hadChange = true;
					topSide++;
					heightLeft--;
				}
			}
			if(heightLeft > 0 && bottomSide > 0)
			{
				boolean canMove = true;
				for(x = leftSide; x <= rightSide && canMove; x++)
				{
					if(data[x][bottomSide-1] != 0) canMove = false;
				}
				if(canMove)
				{
					hadChange = true;
					bottomSide--;
				}
			}
			if(widthLeft > 0 && rightSide+1 < width)
			{
				boolean canMove = true;
				for(y = bottomSide; y <= topSide; y++)
				{
					if(data[rightSide+1][y] != 0) canMove = false;
				}
				if(canMove)
				{
					hadChange = true;
					rightSide++;
					widthLeft--;
				}
			}
			if(widthLeft > 0 && leftSide > 0)
			{
				boolean canMove = true;
				for(y = bottomSide; y <= topSide && canMove; y++)
				{
					if(data[leftSide-1][y] != 0) canMove = false;
				}
				if(canMove)
				{
					hadChange = true;
					leftSide--;
				}
			}
			if(!hadChange) break;
		}
		leftSide++;
		rightSide--;
		bottomSide++;
		topSide--;
		if(Math.abs(leftSide-rightSide) < 4 || Math.abs(topSide-bottomSide) < 4) return;
		for(x = leftSide; x <= rightSide; x++)
		{
			for(y = bottomSide; y <= topSide; y++)
			{
				data[x][y] = 2;
			}
		}
		if(Math.abs(leftSide-rightSide) > 16 && Math.abs(topSide-bottomSide) > 16 && r.nextInt(10) > 4)
		{
			int width = rightSide-leftSide;
			int height = topSide-bottomSide;
			int chests = (int)(width*height/100);
			int minX = leftSide+2;
			int maxX = rightSide-2;
			int minY = bottomSide+2;
			int maxY = topSide-2;
			if(minX < maxX && minY < maxY)
			{
				for(int a = 0; a < chests; a++)
				{
					data[r.nextInt(maxX-minX+1)+minX][r.nextInt(maxY-minY+1)+minY] = 4;
				}
			}
		}
		genHallway(leftSide, r.nextInt(topSide-bottomSide-2)+bottomSide+1, -1, 0);
		genHallway(rightSide, r.nextInt(topSide-bottomSide-2)+bottomSide+1, 1, 0);
		genHallway(r.nextInt(rightSide-leftSide-2)+leftSide+1, bottomSide, 0, -1);
		genHallway(r.nextInt(rightSide-leftSide-2)+leftSide+1, topSide, 0, 1);
	}
	
	private void genHallway(int x, int y, int dirX, int dirY)
	{
		if(dirX != 0 && dirY != 0) return;
		if(dirX == 0 && dirY == 0) return;
		int startX = x, endX = x, startY = y, endY = y;
		boolean valid = false;
		while(!valid)
		{
			endX += dirX;
			endY += dirY;
			if(endX < 0 || endX >= width || endY < 0 || endY >= height) break;
			if(data[endX][endY] == 2 && !isExposed(endX, endY))
			{
				valid = true;
				endX -= dirX;
				endY -= dirY;
			}
		}
		if(valid)
		{
			if(endX < startX)
			{
				int temp = endX;
				endX = startX;
				startX = temp;
			}
			if(endY < startY)
			{
				int temp = endY;
				endY = startY;
				startY = temp;
			}
			for(x = startX-1; x <= endX+1; x++)
			{
				for(y = startY-1; y <= endY+1; y++)
				{
					data[x][y] = 2;
				}
			}
			data[startX][startY] = 3;
			data[endX][endY] = 3;
		}
	}
	
	private boolean isExposed(int x, int y)
	{
		if(x == 0 || y == 0 || x == width-1 || y == height-1) return true;
		for(int x1 = x-1; x1 <= x+1; x1++)
		{
			for(int y1 = y-1; y1 <= y+1; y1++)
			{
				if(data[x1][y1] == 0) return true;
			}
		}
		return false;
	}
	
	public int doorType(int x, int y)
	{
		if(x == 0 || y == 0 || x == width-1 || y == height-1) return 0;
		if(data[x][y] != 3) return 0;
		if(data[x][y+1] == 1 && data[x][y-1] == 1 && data[x-1][y] == 2 && data[x+1][y] == 2) return 1;
		if(data[x-1][y] == 1 && data[x+1][y] == 1 && data[x][y-1] == 2 && data[x][y+1] == 2) return 2;
		return 0;
	}
}