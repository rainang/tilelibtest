package com.github.rainang.tilelib.canvas;

import com.github.rainang.tilelib.board.Board;
import com.github.rainang.tilelib.board.tile.Tile;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.point.Point;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HexCanvasStack<T extends Tile, B extends Board<T>> extends TileCanvasStack<Layout.Hex, T, B>
{
	protected final Map<Point, double[][]> renderCache = new HashMap<>();
	
	protected final BiConsumer<GraphicsContext, T> defaultTilePainter = (gc, t) ->
	{
		double[][] points = renderCache.get(t.getPos());
		gc.fillPolygon(points[0], points[1], 6);
	};
	
	protected final BiConsumer<GraphicsContext, T> defaultGridPainter = (gc, t) ->
	{
		double[][] points = renderCache.get(t.getPos());
		for (int i = 0; i < 3; i++)
			gc.strokeLine(points[0][i], points[1][i], points[0][i + 1], points[1][i + 1]);
	};
	
	@Override
	protected BiConsumer<GraphicsContext, T> getDefaultPainter()
	{
		return defaultTilePainter;
	}
	
	@Override
	public void setBoard(B board)
	{
		this.board = board;
		refreshCache();
		paint();
	}
	
	@Override
	public void setLayout(Layout.Hex layout)
	{
		if (this.layout == layout)
			return;
		
		this.layout = layout;
		refreshCache();
		paint();
	}
	
	private void refreshCache()
	{
		if (!canPaint())
			return;
		renderCache.clear();
		getBoard().getCoordinates()
				  .forEach(hex ->
				  {
					  double xPoints[] = new double[7];
					  double yPoints[] = new double[7];
					  getLayout().polygonCorners(hex, (c, i) ->
					  {
						  xPoints[i] = c.x();
						  yPoints[i] = c.y();
					  });
			
					  double[][] points;
					  points = new double[][] {
							  xPoints,
							  yPoints
					  };
					  renderCache.put(hex, points);
				  });
	}
}
