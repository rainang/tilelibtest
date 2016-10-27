package com.github.rainang.tilelib.test;

import com.github.rainang.tilelib.coordinates.HexCoordinate;
import com.github.rainang.tilelib.coordinates.HexFinder;
import com.github.rainang.tilelib.tiles.HexTileMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class TileLibTest extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	static final HexFinder HEX_FINDER = new HexFinder();
	
	private static HexStack canvas = new HexStack();
	
	private Map<String, ShapeControlPane> map = new HashMap<>();
	
	@Override
	public void start(Stage stage) throws Exception
	{
		map.put("Hexagon", ShapeControlPane.hexagon(canvas));
		map.put("Intersection", ShapeControlPane.intersection(canvas));
		map.put("Parallelogram", ShapeControlPane.parallelogram(canvas));
		map.put("Rectangle", ShapeControlPane.rectangle(canvas));
		map.put("Spiral", ShapeControlPane.spiral(canvas));
		map.put("Triangle", ShapeControlPane.triangle(canvas));
		map.put("Line", ShapeControlPane.line(canvas));
		
		DataControl data = new DataControl(canvas);
		ViewControl view = new ViewControl(canvas);
		ComboBox<String> cmb = new ComboBox<>(FXCollections.observableArrayList(map.keySet()));
		
		VBox flow = new VBox(data, view, new LayoutControl(canvas), cmb);
		flow.getStyleClass()
			.add("control-panel");
		
		HBox flow2 = new HBox(flow, canvas);
		flow2.getStyleClass()
			 .add("main");
		
		cmb.getSelectionModel()
		   .selectedItemProperty()
		   .addListener((i, o, n) ->
		   {
			   ShapeControlPane pane = map.get(o);
			   if (pane != null)
			   {
				   pane.close(canvas);
				   flow.getChildren()
					   .remove(pane);
			   }
			   pane = map.get(n);
			   if (pane != null)
			   {
				   pane.init(canvas);
				   flow.getChildren()
					   .add(pane);
			   }
		   });
		
		HexTileMap<ColoredTile> m = new HexTileMap<>();
		HEX_FINDER.range(HexCoordinate.create(0, 0), 10, c -> m.put(c, new ColoredTile(c)));
		canvas.setTiles(m);
		
		cmb.getSelectionModel()
		   .select(1);
		
		stage.setScene(new Scene(flow2));
		stage.setTitle("TileLib Test");
		stage.getScene()
			 .getStylesheets()
			 .add("stylesheet.css");
		stage.sizeToScene();
		stage.setResizable(false);
		stage.show();
		
		canvas.setOnMouseEntered(e -> stage.getScene()
										   .setCursor(Cursor.CROSSHAIR));
		canvas.setOnMouseExited(e -> stage.getScene()
										  .setCursor(Cursor.DEFAULT));
		
		canvas.getInputAdapter()
			  .focusTileProperty()
			  .set(canvas.getTile(HexCoordinate.ORIGIN));
	}
}
