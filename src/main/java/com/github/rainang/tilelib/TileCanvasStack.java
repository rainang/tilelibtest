package com.github.rainang.tilelib;

import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.coordinates.Hex;
import com.github.rainang.tilelib.tiles.Tile;
import com.github.rainang.tilelib.tiles.TileMapBase;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileCanvasStack<C extends Coordinate, T extends Tile> extends StackPane
{
	private Map<String, TileCanvas<C, T>> layers = new HashMap<>();
	
	protected final Map<C, double[][]> renderCache = new HashMap<>();
	
	private TileMapBase<C, T> tiles;
	
	private Hex.Layout layout;
	
	public TileCanvasStack()
	{
		this(500, 500);
	}
	
	public TileCanvasStack(int width, int height)
	{
		setWidth(width);
		setHeight(height);
	}
	
	// TILES
	
	public T getTile(C c)
	{
		return tiles.get(c);
	}
	
	// LAYERING
	
	public void addLayer(TileCanvas<C, T> layer)
	{
		layers.put(layer.getName(), layer);
		getChildren().clear();
		getChildren().addAll(sorted());
	}
	
	public TileCanvas<C, T> removeLayer(String name)
	{
		TileCanvas<C, T> result = layers.remove(name);
		getChildren().remove(result);
		return result;
	}
	
	public TileCanvas<C, T> getLayer(String name)
	{
		return layers.get(name);
	}
	
	private List<TileCanvas<C, T>> sorted()
	{
		List<TileCanvas<C, T>> list = new ArrayList<>(layers.values());
		list.sort(TileCanvas::compareTo);
		return list;
	}
	
	public BooleanProperty getLayerVisibilityProperty(String layer)
	{
		return getLayer(layer).visibleProperty();
	}
	
	// GETTERS & SETTERS
	
	public TileMapBase<C, T> getTiles()
	{
		return tiles;
	}
	
	public void setTiles(TileMapBase<C, T> tiles)
	{
		this.tiles = tiles;
		refreshCache();
		paint();
	}
	
	public Hex.Layout getLayout()
	{
		return layout;
	}
	
	public void setLayout(Hex.Layout layout)
	{
		if (this.layout == layout)
			return;
		
		this.layout = layout;
		refreshCache();
		paint();
	}
	
	// PAINTING
	
	protected void refreshCache() {}
	
	public boolean canPaint()
	{
		return tiles != null && layout != null;
	}
	
	public void paint()
	{
		if (!canPaint())
			return;
		List<TileCanvas<C, T>> list = new ArrayList<>(layers.values());
		list.sort(TileCanvas::compareTo);
		list.forEach(TileCanvas::paint);
	}
	
	public void clearLayer(String name)
	{
		getLayer(name).clear();
	}
	
	public void paintLayer(String name)
	{
		if (!canPaint())
			return;
		getLayer(name).paint();
	}
	
	public void paintHighlights()
	{
		if (!canPaint())
			return;
		getLayer("Mouse").paint();
		getLayer("X").paint();
		getLayer("Y").paint();
		getLayer("Z").paint();
		getLayer("High").paint();
	}
}
