package com.github.rainang.tilelib.input;

import com.github.rainang.tilelib.board.Board;
import com.github.rainang.tilelib.board.tile.Tile;
import com.github.rainang.tilelib.canvas.TileCanvasStack;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.PointD;
import com.github.rainang.tilelib.properties.CoordinateDProperty;
import com.github.rainang.tilelib.properties.CoordinateProperty;
import com.github.rainang.tilelib.properties.TileProperty;

public interface InputAdapter<L extends Layout, T extends Tile, B extends Board<T>>
{
	TileCanvasStack<L, T, B> getCanvasStack();
	
	default void init()
	{
		getCanvasStack().setOnMouseMoved(e -> mousePosProperty().set(PointD.create(e.getX(), e.getY())));
		mousePosProperty().addListener((a, b, c) -> mouseoverProperty().set(getLayout().fromPixel(c)));
		mouseoverProperty().addListener((a, b, c) -> mouseTileProperty().set(getCanvasStack().getTile(c)));
		
		getCanvasStack().setOnMouseClicked(e -> focusTileProperty().set(getMouseTile()));
	}
	
	default L getLayout()
	{
		return getCanvasStack().getLayout();
	}
	
	// PROPERTIES
	
	CoordinateDProperty mousePosProperty();
	
	CoordinateProperty mouseoverProperty();
	
	TileProperty<T> mouseTileProperty();
	
	TileProperty<T> focusTileProperty();
	
	default PointD getMousePos()
	{
		return mousePosProperty().get();
	}
	
	default Point getMouseover()
	{
		return mouseoverProperty().get();
	}
	
	default T getMouseTile()
	{
		return mouseTileProperty().get();
	}
	
	default T getFocusTile()
	{
		return focusTileProperty().get();
	}
	
	default boolean hasMouseTile()
	{
		return getMouseTile() != null;
	}
	
	default boolean hasFocuTile()
	{
		return getFocusTile() != null;
	}
}
