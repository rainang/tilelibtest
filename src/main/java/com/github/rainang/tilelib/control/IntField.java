package com.github.rainang.tilelib.control;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

public class IntField extends TextField
{
	public static final String INT_PATTERN = "^[+-]$|^[+-]?[0-9]{1,10}$";

//	static
//	{
//		StringBuilder sb = new StringBuilder();
//		sb.append("^[+-]?(")
//		  .append("[0-9]{1,9}$")
//		  .append("|")
//		  .append("1[0-9]{9}$|20[0-9]{8}$")
//		  .append("|")
//		  .append("21[0-3][0-9]{7}$")
//		  .append("|")
//		  .append("214[0-6][0-9]{6}$")
//		  .append("|")
//		  .append("2147[0-3][0-9]{5}$")
//		  .append("|")
//		  .append("21474[0-7][0-9]{4}$")
//		  .append("|")
//		  .append("214748[0-2][0-9]{3}$")
//		  .append("|")
//		  .append("2147483[0-5][0-9]{2}$")
//		  .append("|")
//		  .append("21474836[0-3][0-9]$")
//		  .append("|")
//		  .append("214748364[0-7]$")
//		  .append(")|")
//		  .append("^-2147483648$");
//		INT_PATTERN = sb.toString();
//	}
	
	private IntegerProperty minValue;
	
	private IntegerProperty maxValue;
	
	private IntegerProperty value = new SimpleIntegerProperty();
	
	public IntField()
	{
		this(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
	}
	
	public IntField(int initialValue)
	{
		this(Integer.MIN_VALUE, Integer.MAX_VALUE, initialValue);
	}
	
	public IntField(int minValue, int maxValue)
	{
		this(minValue, maxValue, 0);
	}
	
	public IntField(int minValue, int maxValue, int initialValue)
	{
		super("0");
		getStyleClass().add("int-field");
		if (minValue > maxValue)
			throw new IllegalArgumentException(String.format("minValue %s must be less than or equal to maxValue %s",
					minValue, maxValue));
		
		StringConverter<String> converter = new StringConverter<String>()
		{
			@Override
			public String toString(String string)
			{
				return clamp(string);
			}
			
			@Override
			public String fromString(String string)
			{
				return clamp(string);
			}
			
			private String clamp(String string)
			{
				int i;
				if (string.length() > 11)
					i = string.charAt(0) == '-' ? getMinValue() : getMaxValue();
				else
				{
					long l = Long.parseLong(string);
					i = (int) (l > 0 ? Math.min(getMaxValue(), l) : Math.max(getMinValue(), l));
				}
				value.set(i);
				return Integer.toString(i);
			}
		};
		
		UnaryOperator<TextFormatter.Change> filter = change -> change.getControlNewText()
																	 .matches(INT_PATTERN) ? change : null;
		
		if (minValue != Integer.MIN_VALUE)
			setMinValue(minValue);
		if (maxValue != Integer.MAX_VALUE)
			setMaxValue(maxValue);
		initialValue = Math.min(Math.max(initialValue, getMinValue()), getMaxValue());
		setTextFormatter(new TextFormatter<>(converter, Integer.toString(initialValue), filter));
		setInt(initialValue);
		
		setAlignment(Pos.CENTER_RIGHT);
		
		setOnScroll(e -> setText("" + (e.getDeltaY() > 0 ? getValue() + 1 : getValue() - 1)));
	}
	
	public void setMinValue(int minValue)
	{
		minValueProperty().set(minValue);
		if (getValue() < getMinValue())
			setInt(getMinValue());
	}
	
	public void setMaxValue(int maxValue)
	{
		maxValueProperty().set(maxValue);
		if (getValue() > getMaxValue())
			setInt(getMaxValue());
	}
	
	public int getValue()
	{
		return value.get();
	}
	
	public int getMinValue()
	{
		return minValue == null ? Integer.MIN_VALUE : minValueProperty().get();
	}
	
	public int getMaxValue()
	{
		return maxValue == null ? Integer.MAX_VALUE : maxValueProperty().get();
	}
	
	public IntegerProperty minValueProperty()
	{
		if (minValue == null)
			minValue = new SimpleIntegerProperty();
		return minValue;
	}
	
	public IntegerProperty maxValueProperty()
	{
		if (maxValue == null)
			maxValue = new SimpleIntegerProperty();
		return maxValue;
	}
	
	public IntegerProperty valueProperty()
	{
		return value;
	}
	
	public void setInt(int newValue)
	{
		textProperty().set(Integer.toString(newValue));
	}
	
	@Override
	public void replaceText(int start, int end, String text)
	{
		if (text.equals("-"))
		{
			setText(Integer.toString(getValue() * -1));
			return;
		} else if (text.equals("+"))
		{
			setText(Integer.toString(Math.abs(getValue())));
			return;
		}
		super.replaceText(start, end, text);
		commitValue();
	}
}