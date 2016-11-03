package com.github.rainang.tilelib.canvas;

import com.github.rainang.tilelib.board.Board;
import com.github.rainang.tilelib.board.tile.Tile;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.point.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TileCanvasStack<L extends Layout, T extends Tile, B extends Board<T>> extends StackPane
{
	private Map<String, TileCanvas<T>> layers = new HashMap<>();
	
	B board;
	
	L layout;
	
	TileCanvasStack()
	{
		this(500, 500);
	}
	
	TileCanvasStack(int width, int height)
	{
		setWidth(width);
		setHeight(height);
	}
	
	// TILES
	
	public T getTile(Point c)
	{
		return board.getTile(c);
	}
	
	// LAYERING
	
	public void addLayers(String... names)
	{
		for (String name : names)
			addLayer(name);
	}
	
	public void addLayer(String name)
	{
		TileCanvas<T> layer = new TileCanvas<>(name, getWidth(), getHeight());
		layer.setPainter(getDefaultPainter());
		layers.put(layer.getName(), layer);
		getChildren().add(layer);
	}
	
	public void removeLayers(String... names)
	{
		for (String name : names)
			removeLayer(name);
	}
	
	public void removeLayer(String name)
	{
		TileCanvas<T> layer = layers.remove(name);
		if (layer != null)
			getChildren().remove(layer);
	}
	
	public TileCanvas<T> getLayer(String name)
	{
		return layers.get(name);
	}
	
	// DELEGATION
	
	public void setLayerPainter(String layer, BiConsumer<GraphicsContext, T> painter)
	{
		if (!layers.containsKey(layer))
			return;
		getLayer(layer).setPainter(painter);
	}
	
	public void setLayerRenderListSupplier(String layer, Supplier<Collection<T>> supplier)
	{
		if (!layers.containsKey(layer))
			return;
		layers.get(layer)
			  .setRenderListSupplier(supplier);
	}
	
	public void setLayerVisibility(String layer, boolean b)
	{
		if (!layers.containsKey(layer))
			return;
		layers.get(layer)
			  .setVisible(b);
	}
	
	public void setLayerFill(String layer, Color color)
	{
		if (!layers.containsKey(layer))
			return;
		layers.get(layer)
			  .getGraphicsContext2D()
			  .setFill(color);
	}
	
	// GETTERS & SETTERS
	
	public B getBoard()
	{
		return board;
	}
	
	public void setBoard(B board)
	{
		this.board = board;
		paint();
	}
	
	public L getLayout()
	{
		return layout;
	}
	
	public void setLayout(L layout)
	{
		if (this.layout == layout)
			return;
		
		this.layout = layout;
		paint();
	}
	
	protected BiConsumer<GraphicsContext, T> getDefaultPainter()
	{
		return null;
	}
	
	// PAINTING
	
	public boolean canPaint()
	{
		return board != null && layout != null;
	}
	
	public void paint()
	{
		if (!canPaint())
			return;
		getChildren().stream()
					 .filter(node -> node instanceof TileCanvas)
					 .forEach(node -> ((TileCanvas) node).paint());
	}
	
	public void clearLayer(String name)
	{
		if (!canPaint() || !layers.containsKey(name))
			return;
		getLayer(name).clear();
	}
	
	public void paintLayer(String name)
	{
		if (!canPaint() || !layers.containsKey(name))
			return;
		getLayer(name).paint();
	}
}
