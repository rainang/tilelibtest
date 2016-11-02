package com.github.rainang.tilelib.canvas;

import com.github.rainang.tilelib.coordinates.HexCoordinate;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.tiles.HexTile;
import com.github.rainang.tilelib.tiles.TileMapBase;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class HexCanvasStack<T extends HexTile> extends TileCanvasStack<Layout.Hex, HexCoordinate, T>
{
	protected final Map<HexCoordinate, double[][]> renderCache = new HashMap<>();
	
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
	public void setTiles(TileMapBase<HexCoordinate, T> tiles)
	{
		this.tiles = tiles;
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
		getTiles().keySet()
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
