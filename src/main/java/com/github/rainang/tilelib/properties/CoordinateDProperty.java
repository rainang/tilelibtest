package com.github.rainang.tilelib.properties;

import com.github.rainang.tilelib.point.PointD;
import javafx.beans.property.SimpleObjectProperty;

public class CoordinateDProperty extends SimpleObjectProperty<PointD>
{
	public CoordinateDProperty() {}
	
	public CoordinateDProperty(PointD initialValue)
	{
		super(initialValue);
	}
	
	public CoordinateDProperty(Object bean, String name)
	{
		super(bean, name);
	}
	
	public CoordinateDProperty(Object bean, String name, PointD initialValue)
	{
		super(bean, name, initialValue);
	}
}
