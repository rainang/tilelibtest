package com.github.rainang.tilelib.test;

import com.github.rainang.tilelib.canvas.HexCanvasStack;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import com.github.rainang.tilelib.coordinates.HexCoordinate;
import com.github.rainang.tilelib.input.InputAdapter;
import com.github.rainang.tilelib.input.SimpleInputAdapter;
import com.github.rainang.tilelib.layout.HexOrientation;
import com.github.rainang.tilelib.layout.Layout;
import com.github.rainang.tilelib.tiles.HexTile;
import com.github.rainang.tilelib.tiles.HexTileMap;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

class HexStack extends HexCanvasStack<HexTile>
{
	private final InputAdapter<Layout.Hex, HexCoordinate, HexTile> input = new SimpleInputAdapter<>(this);
	
	private final Map<Integer, List<HexTile>> highlightList = new HashMap<>();
	
	HexStack()
	{
		input.mouseTileProperty()
			 .addListener(i -> updateHighlights());
		
		for (int i = -2; i < 3; i++)
			highlightList.put(i, new ArrayList<>());
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		addLayers("Tile", "Mouse", "X", "Y", "Z", "High", "Grid");
		
		setLayerRenderListSupplier("Tile", () -> getTiles().values());
		setLayerRenderListSupplier("Grid", () -> getTiles().values());
		
		setLayerFill("Mouse", Color.color(0, 0, 0, 0.5));
		setLayerFill("X", Color.color(0, 1, 0, 0.25));
		setLayerFill("Y", Color.color(1, 0, 0, 0.25));
		setLayerFill("Z", Color.color(0, 0, 1, 0.25));
		setLayerFill("High", Color.color(0, 0, 0, 0.25));
		
		setLayerPainter("Grid", defaultGridPainter);
		
		setLayerVisibility("X", false);
		setLayerVisibility("Y", false);
		setLayerVisibility("Z", false);
		setLayerVisibility("Grid", false);
		
		HexTileMap<HexTile> m = new HexTileMap<>();
		TileLibTest.HEX_FINDER.range(HexCoordinate.create(0, 0), 10, c -> m.put(c, new HexTile()
		{
			@Override
			public HexCoordinate getPos()
			{
				return c;
			}
			
			@Override
			public String toString()
			{
				return getPos().toString();
			}
		}));
		setTiles(m);
		
		setLayout(new Layout.Hex(HexOrientation.POINTY, CoordinateD.create(15, 15), CoordinateD.create(getWidth() /
				2D, getHeight() / 2D), 0.3));
	}
	
	BooleanProperty getLayerVisibilityProperty(String layer)
	{
		return getLayer(layer).visibleProperty();
	}
	
	private void updateHighlights()
	{
		highlightList.get(-1)
					 .clear();
		highlightList.get(-1)
					 .add(input.getMouseTile());
		setLayerRenderListSupplier("Mouse", input.getMouseTile() == null ? () -> highlightList.get(-2) : () ->
				highlightList.get(-1));
		setLayerRenderListSupplier("X", () -> getAxisHighlights(2, t -> equalX(input.getMouseTile(), t)));
		setLayerRenderListSupplier("Y", () -> getAxisHighlights(1, t -> equalY(input.getMouseTile(), t)));
		setLayerRenderListSupplier("Z", () -> getAxisHighlights(0, t -> equalZ(input.getMouseTile(), t)));
		paintHighlights();
	}
	
	private Collection<HexTile> getAxisHighlights(int axis, Predicate<HexCoordinate> filter)
	{
		HexTile t = input.getMouseTile();
		if (t == null)
			return highlightList.get(-2);
		List<HexTile> list = highlightList.get(axis);
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
	
	InputAdapter<Layout.Hex, HexCoordinate, HexTile> getInputAdapter()
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
	
	void paintHighlights()
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
