package com.github.rainang.tilelib.properties;

import com.github.rainang.tilelib.point.Point;
import javafx.beans.property.SimpleObjectProperty;

public class CoordinateProperty extends SimpleObjectProperty<Point>
{
	public CoordinateProperty() {}
	
	public CoordinateProperty(Point initialValue)
	{
		super(initialValue);
	}
	
	public CoordinateProperty(Object bean, String name)
	{
		super(bean, name);
	}
	
	public CoordinateProperty(Object bean, String name, Point initialValue)
	{
		super(bean, name, initialValue);
	}
}
