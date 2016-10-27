package com.github.rainang.tilelib;

import com.github.rainang.tilelib.control.IntField;
import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CoordinateField extends FlowPane
{
	private ObjectProperty<Coordinate> coordinate = new SimpleObjectProperty<>();
	
	private final IntField fldX;
	private final IntField fldY;
	
	public CoordinateField(Orientation orientation, Coordinate coordinate, int minValue, int maxValue)
	{
		super(orientation);
		getStyleClass().add("coordinate-field");
		
		this.coordinate.set(coordinate);
		this.fldX = new IntField(min(coordinate.x(), minValue), max(coordinate.x(), maxValue), coordinate.x());
		this.fldY = new IntField(min(coordinate.y(), minValue), max(coordinate.y(), maxValue), coordinate.y());
		
		fldX.valueProperty()
			.addListener(j -> updateCoordinates());
		fldY.valueProperty()
			.addListener(j -> updateCoordinates());
		
		getChildren().addAll(fldX, fldY);
	}
	
	public CoordinateField(Coordinate coordinate, int minValue, int maxValue)
	{
		this(Orientation.HORIZONTAL, coordinate, minValue, maxValue);
	}
	
	public CoordinateField(Orientation orientation, Coordinate coordinate)
	{
		this(orientation, coordinate, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public CoordinateField(Coordinate coordinate)
	{
		this(Orientation.HORIZONTAL, coordinate, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public CoordinateField()
	{
		this(Orientation.HORIZONTAL, Coordinate.ORIGIN, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	private void updateCoordinates()
	{
		coordinate.set(Coordinate.create(fldX.getValue(), fldY.getValue()));
	}
	
	public ObjectProperty<Coordinate> coordinateProperty()
	{
		return coordinate;
	}
	
	public Coordinate getCoordinate()
	{
		return coordinate.get();
	}
	
	public CoordinateD getCoordinateD()
	{
		Coordinate c = coordinate.get();
		return CoordinateD.create(c.x(), c.y());
	}
	
	public int getValueX()
	{
		return getCoordinate().x();
	}
	
	public int getValueY()
	{
		return getCoordinate().y();
	}
}
