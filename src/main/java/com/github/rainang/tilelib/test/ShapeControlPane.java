package com.github.rainang.tilelib.test;

import com.github.rainang.tilelib.control.IntField;
import com.github.rainang.tilelib.control.LabeledNode;
import com.github.rainang.tilelib.coordinates.HexCoordinate;
import com.github.rainang.tilelib.tiles.HexTile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

abstract class ShapeControlPane extends VBox
{
	ChangeListener<HexTile> focusChangeListener;
	
	final IntField range1;
	final IntField range2;
	final IntField orient;
	
	private ShapeControlPane(HexStack canvas, int type)
	{
		getStyleClass().add("control-pane");
		
		if ((type & 4) != 4)
		{
			range1 = new IntField(1, 10, 5);
			this.range1.valueProperty()
					   .addListener(i -> createMap(canvas));
			getChildren().add(new LabeledNode<>("Range 1", range1));
		} else
			range1 = null;
		
		if ((type & 2) == 2)
		{
			range2 = new IntField(1, 10, 5);
			range2.valueProperty()
				  .addListener(i -> createMap(canvas));
			getChildren().add(new LabeledNode<>("Range 2", range2));
		} else
			range2 = null;
		
		if ((type & 1) == 1)
		{
			orient = new IntField(0, 5);
			orient.valueProperty()
				  .addListener(i -> createMap(canvas));
			getChildren().add(new LabeledNode<>("Orientation", orient));
		} else
			orient = null;
		
		this.focusChangeListener = (a, b, c) -> createMap(canvas);
	}
	
	private ShapeControlPane(HexStack canvas, Button type)
	{
		this(canvas, 1);
		getChildren().add(type);
		type.setOnAction(e ->
		{
			type.setText(type.getText()
							 .equals("Ring") ? "Spiral" : "Ring");
			createMap(canvas);
		});
	}
	
	boolean createMap(HexStack canvas)
	{
		HexTile tile = canvas.getInputAdapter()
							 .getFocusTile();
		if (tile == null)
			return false;
		List<HexCoordinate> filter = new ArrayList<>();
		search(tile.getPos(), filter::add);
		List<HexTile> list = filter.stream()
								   .map(canvas::getTile)
								   .filter(t -> t != null)
								   .collect(Collectors.toList());
		canvas.setLayerRenderListSupplier("High", () -> list);
		canvas.paintHighlights();
		return true;
	}
	
	abstract void search(HexCoordinate h, Consumer<HexCoordinate> consumer);
	
	void init(HexStack canvas)
	{
		canvas.getInputAdapter()
			  .focusTileProperty()
			  .addListener(focusChangeListener);
		createMap(canvas);
		canvas.paintHighlights();
	}
	
	void close(HexStack canvas)
	{
		canvas.getInputAdapter()
			  .focusTileProperty()
			  .removeListener(focusChangeListener);
	}
	
	static ShapeControlPane hexagon(HexStack canvas)
	{
		return new ShapeControlPane(canvas, 0)
		{
			@Override
			void search(HexCoordinate h, Consumer<HexCoordinate> consumer)
			{
				TileLibTest.HEX_FINDER.range(h, range1.getValue(), consumer);
			}
		};
	}
	
	static ShapeControlPane triangle(HexStack canvas)
	{
		return new ShapeControlPane(canvas, 1)
		{
			@Override
			void search(HexCoordinate h, Consumer<HexCoordinate> consumer)
			{
				TileLibTest.HEX_FINDER.triangle(h, range1.getValue(), orient.getValue(), consumer);
			}
		};
	}
	
	static ShapeControlPane rectangle(HexStack canvas)
	{
		return new ShapeControlPane(canvas, 3)
		{
			@Override
			void search(HexCoordinate h, Consumer<HexCoordinate> consumer)
			{
				TileLibTest.HEX_FINDER.rectangle(h, range1.getValue(), range2.getValue(), orient.getValue(), consumer);
			}
		};
	}
	
	static ShapeControlPane parallelogram(HexStack canvas)
	{
		return new ShapeControlPane(canvas, 3)
		{
			@Override
			void search(HexCoordinate h, Consumer<HexCoordinate> consumer)
			{
				TileLibTest.HEX_FINDER.parallelogram(h, range1.getValue(), range2.getValue(), orient.getValue(),
						consumer);
			}
		};
	}
	
	static ShapeControlPane spiral(HexStack canvas)
	{
		Button type = new Button("Ring");
		IntegerProperty i = new SimpleIntegerProperty();
		return new ShapeControlPane(canvas, type)
		{
			@Override
			void search(HexCoordinate h, Consumer<HexCoordinate> consumer)
			{
				if (type.getText()
						.equals("Ring"))
					TileLibTest.HEX_FINDER.ring(h, orient.getValue(), range1.getValue(), consumer);
				else
					TileLibTest.HEX_FINDER.spiral(h, orient.getValue(), range1.getValue(), consumer);
			}
		};
	}
	
	private static abstract class TwoPointPane extends ShapeControlPane
	{
		ChangeListener<HexTile> mouseChangeListener;
		
		HexCoordinate h1;
		HexCoordinate h2;
		
		private TwoPointPane(HexStack canvas, int type)
		{
			super(canvas, type);
		}
		
		@Override
		public void init(HexStack canvas)
		{
			super.init(canvas);
			focusChangeListener = (i, o, n) ->
			{
				h1 = n == null ? null : n.getPos();
				createMap(canvas);
			};
			mouseChangeListener = (i, o, n) ->
			{
				h2 = n == null ? null : n.getPos();
				createMap(canvas);
			};
			
			canvas.getInputAdapter()
				  .focusTileProperty()
				  .addListener(focusChangeListener);
			canvas.getInputAdapter()
				  .mouseTileProperty()
				  .addListener(mouseChangeListener);
		}
		
		@Override
		public void close(HexStack canvas)
		{
			super.close(canvas);
			canvas.getInputAdapter()
				  .focusTileProperty()
				  .removeListener(focusChangeListener);
			canvas.getInputAdapter()
				  .mouseTileProperty()
				  .removeListener(mouseChangeListener);
		}
	}
	
	static ShapeControlPane intersection(HexStack canvas)
	{
		return new TwoPointPane(canvas, 2)
		{
			@Override
			void search(HexCoordinate h, Consumer<HexCoordinate> consumer)
			{
				if (h1 != null)
					TileLibTest.HEX_FINDER.range(h1, range1.getValue(), consumer);
				if (h2 != null)
					TileLibTest.HEX_FINDER.range(h2, range2.getValue(), consumer);
				if (h1 != null && h2 != null)
					TileLibTest.HEX_FINDER.rangeIntersection(h1, range1.getValue(), h2, range2.getValue(), consumer);
			}
		};
	}
	
	static ShapeControlPane line(HexStack canvas)
	{
		return new TwoPointPane(canvas, 4)
		{
			@Override
			void search(HexCoordinate h, Consumer<HexCoordinate> consumer)
			{
				if (h1 != null && h2 != null)
					TileLibTest.HEX_FINDER.line(h1, h2, consumer);
			}
		};
	}
}