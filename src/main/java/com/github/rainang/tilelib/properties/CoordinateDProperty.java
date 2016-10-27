package com.github.rainang.tilelib.properties;

import com.github.rainang.tilelib.coordinates.CoordinateD;
import javafx.beans.property.SimpleObjectProperty;

public class CoordinateDProperty<C extends CoordinateD> extends SimpleObjectProperty<C>
{
	public CoordinateDProperty() {}
	
	public CoordinateDProperty(C initialValue)
	{
		super(initialValue);
	}
	
	public CoordinateDProperty(Object bean, String name)
	{
		super(bean, name);
	}
	
	public CoordinateDProperty(Object bean, String name, C initialValue)
	{
		super(bean, name, initialValue);
	}
}
