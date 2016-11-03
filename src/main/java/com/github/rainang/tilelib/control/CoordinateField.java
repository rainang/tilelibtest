package com.github.rainang.tilelib.control;

import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.PointD;
import com.github.rainang.tilelib.properties.CoordinateProperty;
import javafx.scene.layout.HBox;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CoordinateField extends HBox
{
	private CoordinateProperty coordinate = new CoordinateProperty();
	
	private final IntField fldX;
	private final IntField fldY;
	
	public CoordinateField(Point point, int minValue, int maxValue)
	{
		getStyleClass().add("point-field");
		
		this.coordinate.set(point);
		this.fldX = new IntField(min(point.x(), minValue), max(point.x(), maxValue), point.x());
		this.fldY = new IntField(min(point.y(), minValue), max(point.y(), maxValue), point.y());
		
		fldX.valueProperty()
			.addListener(j -> updateCoordinates());
		fldY.valueProperty()
			.addListener(j -> updateCoordinates());
		
		getChildren().addAll(fldX, fldY);
	}
	
	public CoordinateField(Point point)
	{
		this(point, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public CoordinateField()
	{
		this(Point.ORIGIN, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	private void updateCoordinates()
	{
		coordinate.set(Point.create(fldX.getValue(), fldY.getValue()));
	}
	
	public CoordinateProperty coordinateProperty()
	{
		return coordinate;
	}
	
	public Point getCoordinate()
	{
		return coordinate.get();
	}
	
	public PointD getCoordinateD()
	{
		return getCoordinate().asDouble();
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
