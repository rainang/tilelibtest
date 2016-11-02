package com.github.rainang.tilelib.input;

import com.github.rainang.tilelib.canvas.TileCanvasStack;
import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.properties.CoordinateDProperty;
import com.github.rainang.tilelib.properties.CoordinateProperty;
import com.github.rainang.tilelib.properties.TileProperty;
import com.github.rainang.tilelib.tiles.Tile;

public class SimpleInputAdapter<L extends Layout<C>, C extends Coordinate, T extends Tile> implements InputAdapter<L,
		C, T>
{
	private final TileCanvasStack<L, C, T> canvas;
	
	private final CoordinateDProperty<CoordinateD> mousePos = new CoordinateDProperty<>(this, "mouse_pos");
	
	private final CoordinateProperty<C> mouseover = new CoordinateProperty<>(this, "mouseover");
	
	private final TileProperty<T> mouseTile = new TileProperty<>(this, "mouse_tile");
	
	private final TileProperty<T> focusTile = new TileProperty<>(this, "focus_tile");
	
	public SimpleInputAdapter(TileCanvasStack<L, C, T> canvas)
	{
		this.canvas = canvas;
		init();
	}
	
	@Override
	public TileCanvasStack<L, C, T> getCanvasStack()
	{
		return canvas;
	}
	
	@Override
	public CoordinateDProperty<CoordinateD> mousePosProperty()
	{
		return mousePos;
	}
	
	@Override
	public CoordinateProperty<C> mouseoverProperty()
	{
		return mouseover;
	}
	
	@Override
	public TileProperty<T> mouseTileProperty()
	{
		return mouseTile;
	}
	
	@Override
	public TileProperty<T> focusTileProperty()
	{
		return focusTile;
	}
}
