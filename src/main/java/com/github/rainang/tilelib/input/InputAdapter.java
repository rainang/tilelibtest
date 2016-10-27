package com.github.rainang.tilelib.input;

import com.github.rainang.tilelib.TileCanvasStack;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import com.github.rainang.tilelib.coordinates.Hex;
import com.github.rainang.tilelib.coordinates.HexCoordinate;
import com.github.rainang.tilelib.properties.CoordinateDProperty;
import com.github.rainang.tilelib.properties.CoordinateProperty;
import com.github.rainang.tilelib.properties.HexTileProperty;
import com.github.rainang.tilelib.tiles.HexTile;

public interface InputAdapter<T extends HexTile>
{
	TileCanvasStack<HexCoordinate, T> getCanvasStack();
	
	default void init()
	{
		getCanvasStack().setOnMouseMoved(e -> mousePosProperty().set(CoordinateD.create(e.getX(), e.getY())));
		mousePosProperty().addListener((a, b, c) -> mouseoverProperty().set(getLayout().pixelToHex(c)));
		mouseoverProperty().addListener((a, b, c) -> mouseTileProperty().set(getCanvasStack().getTile(c)));
		
		getCanvasStack().setOnMouseClicked(e -> focusTileProperty().set(getMouseTile()));
	}
	
	default Hex.Layout getLayout()
	{
		return getCanvasStack().getLayout();
	}
	
	// PROPERTIES
	
	CoordinateDProperty<CoordinateD> mousePosProperty();
	
	CoordinateProperty<HexCoordinate> mouseoverProperty();
	
	HexTileProperty<T> mouseTileProperty();
	
	HexTileProperty<T> focusTileProperty();
	
	default CoordinateD getMousePos()
	{
		return mousePosProperty().get();
	}
	
	default HexCoordinate getMouseover()
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
