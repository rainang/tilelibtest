package com.github.rainang.tilelib.canvas;

import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.tiles.Tile;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.BiConsumer;

public class QuadCanvasStack<T extends Tile> extends TileCanvasStack<Layout.Quad, Coordinate, T>
{
	
	protected final BiConsumer<GraphicsContext, T> defaultTilePainter = (gc, t) ->
	{
		CoordinateD s = getLayout().size;
		CoordinateD c = getLayout().toPixel(t.getPos());
		gc.fillRect(c.x() - s.x(), c.y() - s.y(), c.x() + s.x(), c.y() + s.y());
	};
	
	protected final BiConsumer<GraphicsContext, T> defaultGridPainter = (gc, t) ->
	{
		CoordinateD s = getLayout().size;
		CoordinateD c = getLayout().toPixel(t.getPos());
		gc.strokeRect(c.x() - s.x(), c.y() - s.y(), c.x() + s.x(), c.y() + s.y());
	};
	
	public QuadCanvasStack()
	{
		this(500, 500);
	}
	
	public QuadCanvasStack(int width, int height)
	{
		super(width, height);
	}
	
	@Override
	protected BiConsumer<GraphicsContext, T> getDefaultPainter()
	{
		return defaultTilePainter;
	}
}
