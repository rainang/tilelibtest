package com.github.rainang.tilelib.control;

import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.HBox;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CoordinateField extends HBox
{
	private ObjectProperty<Coordinate> coordinate = new SimpleObjectProperty<>();
	
	private final IntField fldX;
	private final IntField fldY;
	
	public CoordinateField(Coordinate coordinate, int minValue, int maxValue)
	{
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
	
	public CoordinateField(Coordinate coordinate)
	{
		this(coordinate, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public CoordinateField()
	{
		this(Coordinate.ORIGIN, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
