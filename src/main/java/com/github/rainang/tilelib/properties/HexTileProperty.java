package com.github.rainang.tilelib.properties;

import com.github.rainang.tilelib.tiles.HexTile;

public class HexTileProperty<T extends HexTile> extends TileProperty<T>
{
	public HexTileProperty() {}
	
	public HexTileProperty(T initialValue)
	{
		super(initialValue);
	}
	
	public HexTileProperty(Object bean, String name)
	{
		super(bean, name);
	}
	
	public HexTileProperty(Object bean, String name, T initialValue)
	{
		super(bean, name, initialValue);
	}
}
