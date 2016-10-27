package com.github.rainang.tilelib.test;

import com.github.rainang.tilelib.coordinates.HexCoordinate;
import javafx.scene.paint.Color;

public class ColoredTile implements com.github.rainang.tilelib.tiles.HexTile
{
	private final HexCoordinate pos;
	
	public ColoredTile(HexCoordinate pos)
	{
		if (pos == null)
			throw new NullPointerException();
		this.pos = pos;
	}
	
	@Override
	public HexCoordinate getPos()
	{
		return pos;
	}
	
	public Color getTileColor(int pass)
	{
		switch (pass)
		{
		default:
			return Color.LIGHTGRAY;
		case 1:
			return Color.color(0, 0, 0, 0.25);
		case 2:
			return Color.color(0, 1, 0, 0.25);
		case 3:
			return Color.color(1, 0, 0, 0.25);
		case 4:
			return Color.color(0, 0, 1, 0.25);
		case 5:
			return Color.color(0, 0, 0, 0.5);
		}
	}
}
