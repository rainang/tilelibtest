package com.github.rainang.tilelib.properties;

import com.github.rainang.tilelib.tiles.Tile;
import javafx.beans.property.SimpleObjectProperty;

public class TileProperty<T extends Tile> extends SimpleObjectProperty<T>
{
	public TileProperty() {}
	
	public TileProperty(T initialValue)
	{
		super(initialValue);
	}
	
	public TileProperty(Object bean, String name)
	{
		super(bean, name);
	}
	
	public TileProperty(Object bean, String name, T initialValue)
	{
		super(bean, name, initialValue);
	}
}
