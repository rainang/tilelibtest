package com.github.rainang.tilelib.canvas;

import com.github.rainang.tilelib.board.Board;
import com.github.rainang.tilelib.board.tile.Tile;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.point.PointD;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.BiConsumer;

public class QuadCanvasStack<T extends Tile, B extends Board<T>> extends TileCanvasStack<Layout.Quad, T, B>
{
	protected final BiConsumer<GraphicsContext, T> defaultTilePainter = (gc, t) ->
	{
		PointD s = getLayout().size;
		PointD c = getLayout().toPixel(t.getPos());
		gc.fillRect(c.x() - s.x(), c.y() - s.y(), c.x() + s.x(), c.y() + s.y());
	};
	
	protected final BiConsumer<GraphicsContext, T> defaultGridPainter = (gc, t) ->
	{
		PointD s = getLayout().size;
		PointD c = getLayout().toPixel(t.getPos());
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
