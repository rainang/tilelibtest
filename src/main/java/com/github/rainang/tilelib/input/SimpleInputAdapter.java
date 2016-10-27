package com.github.rainang.tilelib.input;

import com.github.rainang.tilelib.TileCanvasStack;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import com.github.rainang.tilelib.coordinates.HexCoordinate;
import com.github.rainang.tilelib.properties.CoordinateDProperty;
import com.github.rainang.tilelib.properties.CoordinateProperty;
import com.github.rainang.tilelib.properties.HexTileProperty;
import com.github.rainang.tilelib.tiles.HexTile;

public class SimpleInputAdapter<T extends HexTile> implements InputAdapter<T>
{
	private final TileCanvasStack<HexCoordinate, T> canvas;
	
	private final CoordinateDProperty<CoordinateD> mousePos = new CoordinateDProperty<>(this, "mouse_pos");
	
	private final CoordinateProperty<HexCoordinate> mouseover = new CoordinateProperty<>(this, "mouseover");
	
	private final HexTileProperty<T> mouseTile = new HexTileProperty<>(this, "mouse_tile");
	
	private final HexTileProperty<T> focusTile = new HexTileProperty<>(this, "focus_tile");
	
	public SimpleInputAdapter(TileCanvasStack<HexCoordinate, T> canvas)
	{
		this.canvas = canvas;
		init();
	}
	
	@Override
	public TileCanvasStack<HexCoordinate, T> getCanvasStack()
	{
		return canvas;
	}
	
	@Override
	public CoordinateDProperty<CoordinateD> mousePosProperty()
	{
		return mousePos;
	}
	
	@Override
	public CoordinateProperty<HexCoordinate> mouseoverProperty()
	{
		return mouseover;
	}
	
	@Override
	public HexTileProperty<T> mouseTileProperty()
	{
		return mouseTile;
	}
	
	@Override
	public HexTileProperty<T> focusTileProperty()
	{
		return focusTile;
	}
}
