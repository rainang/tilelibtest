package com.github.rainang.tilelib.properties;

import com.github.rainang.tilelib.coordinates.Coordinate;
import javafx.beans.property.SimpleObjectProperty;

public class CoordinateProperty<C extends Coordinate> extends SimpleObjectProperty<C>
{
	public CoordinateProperty() {}
	
	public CoordinateProperty(C initialValue)
	{
		super(initialValue);
	}
	
	public CoordinateProperty(Object bean, String name)
	{
		super(bean, name);
	}
	
	public CoordinateProperty(Object bean, String name, C initialValue)
	{
		super(bean, name, initialValue);
	}
}
