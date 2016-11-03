package com.github.rainang.tilelib.input;

import com.github.rainang.tilelib.board.Board;
import com.github.rainang.tilelib.board.tile.Tile;
import com.github.rainang.tilelib.canvas.TileCanvasStack;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.properties.CoordinateDProperty;
import com.github.rainang.tilelib.properties.CoordinateProperty;
import com.github.rainang.tilelib.properties.TileProperty;

public class SimpleInputAdapter<L extends Layout, T extends Tile, B extends Board<T>> implements InputAdapter<L, T, B>
{
	private final TileCanvasStack<L, T, B> canvas;
	
	private final CoordinateDProperty mousePos = new CoordinateDProperty(this, "mouse_pos");
	
	private final CoordinateProperty mouseover = new CoordinateProperty(this, "mouseover");
	
	private final TileProperty<T> mouseTile = new TileProperty<>(this, "mouse_tile");
	
	private final TileProperty<T> focusTile = new TileProperty<>(this, "focus_tile");
	
	public SimpleInputAdapter(TileCanvasStack<L, T, B> canvas)
	{
		this.canvas = canvas;
		init();
	}
	
	@Override
	public TileCanvasStack<L, T, B> getCanvasStack()
	{
		return canvas;
	}
	
	@Override
	public CoordinateDProperty mousePosProperty()
	{
		return mousePos;
	}
	
	@Override
	public CoordinateProperty mouseoverProperty()
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
