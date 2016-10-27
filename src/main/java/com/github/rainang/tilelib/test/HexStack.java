package com.github.rainang.tilelib.test;

import com.github.rainang.tilelib.TileCanvas;
import com.github.rainang.tilelib.TileCanvasStack;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import com.github.rainang.tilelib.coordinates.Hex;
import com.github.rainang.tilelib.coordinates.HexCoordinate;
import com.github.rainang.tilelib.input.InputAdapter;
import com.github.rainang.tilelib.input.SimpleInputAdapter;
import com.github.rainang.tilelib.tiles.HexTile;
import com.github.rainang.tilelib.tiles.HexTileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

class HexStack extends TileCanvasStack<HexCoordinate, ColoredTile>
{
	private final InputAdapter<ColoredTile> input = new SimpleInputAdapter<>(this);
	
	private final Map<Integer, List<ColoredTile>> highlightList = new HashMap<>();
	
	HexStack()
	{
		input.mouseTileProperty()
			 .addListener(i -> updateHighlights());
		for (int i = -2; i < 3; i++)
			highlightList.put(i, new ArrayList<>());
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		TileCanvas<HexCoordinate, ColoredTile> canvas = new TileCanvas<>("Tile");
		canvas.setPainter(this::paintTile);
		canvas.setRenderListSupplier(() -> getTiles().values());
		addLayer(canvas);
		
		canvas = new TileCanvas<>("Mouse", 1);
		canvas.setPainter(this::paintHighlight);
		addLayer(canvas);
		
		canvas = new TileCanvas<>("X", 2);
		canvas.setPainter(this::paintHighlight);
		canvas.setVisible(false);
		addLayer(canvas);
		
		canvas = new TileCanvas<>("Y", 3);
		canvas.setPainter(this::paintHighlight);
		canvas.setVisible(false);
		addLayer(canvas);
		
		canvas = new TileCanvas<>("Z", 4);
		canvas.setPainter(this::paintHighlight);
		canvas.setVisible(false);
		addLayer(canvas);
		
		canvas = new TileCanvas<>("High", 5);
		canvas.setPainter(this::paintHighlight);
		addLayer(canvas);
		
		canvas = new TileCanvas<>("Grid", 6);
		canvas.setPainter(this::paintGrid);
		canvas.setVisible(false);
		canvas.setRenderListSupplier(() -> getTiles().values());
		addLayer(canvas);
		
		HexTileMap<ColoredTile> m = new HexTileMap<>();
		TileLibTest.HEX_FINDER.range(HexCoordinate.create(0, 0), 10, c -> m.put(c, new ColoredTile(c)));
		setTiles(m);
		
		setLayout(new Hex.Layout(Hex.Orientation.POINTY, CoordinateD.create(15, 15), CoordinateD.create(getWidth() /
				2D, getHeight() / 2D), 0.3));
	}
	
	private void updateHighlights()
	{
		highlightList.get(-1)
					 .clear();
		highlightList.get(-1)
					 .add(input.getMouseTile());
		getLayer("Mouse").setRenderListSupplier(input.getMouseTile() == null ? () -> highlightList.get(-2) : () ->
				highlightList.get(-1));
		getLayer("X").setRenderListSupplier(() -> getAxisHighlights(2, t -> equalX(input.getMouseTile(), t)));
		getLayer("Y").setRenderListSupplier(() -> getAxisHighlights(1, t -> equalY(input.getMouseTile(), t)));
		getLayer("Z").setRenderListSupplier(() -> getAxisHighlights(0, t -> equalZ(input.getMouseTile(), t)));
		paintHighlights();
	}
	
	private Collection<ColoredTile> getAxisHighlights(int axis, Predicate<HexCoordinate> filter)
	{
		ColoredTile t = input.getMouseTile();
		if (t == null)
			return highlightList.get(-2);
		List<ColoredTile> list = highlightList.get(axis);
		list.clear();
		
		HexCoordinate c;
		for (int i = axis; i <= axis + 3; i += 3)
		{
			c = t.getPos();
			while (true)
			{
				HexCoordinate c1 = TileLibTest.HEX_FINDER.offset(c, i);
				if (!getTiles().containsKey(c1))
					break;
				if (filter.test(c1))
					list.add(getTile(c1));
				c = c1;
			}
		}
		return list;
	}
	
	InputAdapter<ColoredTile> getInputAdapter()
	{
		return input;
	}
	
	private boolean equalX(HexTile t1, HexCoordinate t2)
	{
		if (t1 == null || t2 == null)
			return false;
		HexCoordinate c1 = t1.getPos();
		return c1.x() == t2.x();
	}
	
	private boolean equalY(HexTile t1, HexCoordinate t2)
	{
		if (t1 == null || t2 == null)
			return false;
		HexCoordinate c1 = t1.getPos();
		return c1.y() == t2.y();
	}
	
	private boolean equalZ(HexTile t1, HexCoordinate t2)
	{
		if (t1 == null || t2 == null)
			return false;
		HexCoordinate c1 = t1.getPos();
		return c1.z() == t2.z();
	}
	
	// PAINTING
	
	@Override
	protected void refreshCache()
	{
		if (!canPaint())
			return;
		renderCache.clear();
		getTiles().keySet()
				  .forEach(this::getPoints);
	}
	
	private void paintTile(TileCanvas<HexCoordinate, ColoredTile> c, ColoredTile t)
	{
		paintTile(c.getGraphicsContext2D(), t, t.getTileColor(c.getPriority()));
	}
	
	private void paintHighlight(TileCanvas<HexCoordinate, ColoredTile> c, ColoredTile t)
	{
		paintTile(c.getGraphicsContext2D(), t, t.getTileColor(c.getPriority()));
	}
	
	private void paintTile(GraphicsContext gc, ColoredTile t, Color color)
	{
		double[][] points = renderCache.get(t.getPos());
		gc.setFill(color);
		gc.fillPolygon(points[0], points[1], 6);
	}
	
	private void paintGrid(TileCanvas<HexCoordinate, ColoredTile> c, HexTile t)
	{
		double[][] points = renderCache.get(t.getPos());
		
		for (int i = 0; i < 3; i++)
			c.getGraphicsContext2D()
			 .strokeLine(points[0][i], points[1][i], points[0][i + 1], points[1][i + 1]);
	}
	
	private void getPoints(HexCoordinate hex)
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
	}
}
