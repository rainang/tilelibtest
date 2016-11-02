package com.github.rainang.tilelib.input;

import com.github.rainang.tilelib.canvas.TileCanvasStack;
import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.properties.CoordinateDProperty;
import com.github.rainang.tilelib.properties.CoordinateProperty;
import com.github.rainang.tilelib.properties.TileProperty;
import com.github.rainang.tilelib.tiles.Tile;

public interface InputAdapter<L extends Layout<C>, C extends Coordinate, T extends Tile>
{
	TileCanvasStack<L, C, T> getCanvasStack();
	
	default void init()
	{
		getCanvasStack().setOnMouseMoved(e -> mousePosProperty().set(CoordinateD.create(e.getX(), e.getY())));
		mousePosProperty().addListener((a, b, c) -> mouseoverProperty().set(getLayout().fromPixel(c)));
		mouseoverProperty().addListener((a, b, c) -> mouseTileProperty().set(getCanvasStack().getTile(c)));
		
		getCanvasStack().setOnMouseClicked(e -> focusTileProperty().set(getMouseTile()));
	}
	
	default L getLayout()
	{
		return getCanvasStack().getLayout();
	}
	
	// PROPERTIES
	
	CoordinateDProperty<CoordinateD> mousePosProperty();
	
	CoordinateProperty<C> mouseoverProperty();
	
	TileProperty<T> mouseTileProperty();
	
	TileProperty<T> focusTileProperty();
	
	default CoordinateD getMousePos()
	{
		return mousePosProperty().get();
	}
	
	default Coordinate getMouseover()
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
