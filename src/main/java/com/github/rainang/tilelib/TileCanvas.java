package com.github.rainang.tilelib;

import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.tiles.Tile;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

import java.util.Collection;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TileCanvas<C extends Coordinate, T extends Tile> extends Canvas implements Comparable<TileCanvas<C, T>>
{
	private final String name;
	
	private Supplier<Collection<T>> renderListSupplier = () -> Collections.EMPTY_LIST;
	
	private BiConsumer<TileCanvas<C, T>, T> painter = (gc, t) ->
	{
	};
	
	private int priority;
	
	private boolean clearOnPaint = true;
	
	// CONSTRUCTORS
	
	public TileCanvas(String name)
	{
		this(name, 500, 500);
	}
	
	public TileCanvas(String name, int width, int height)
	{
		this(name, 0, width, height);
	}
	
	public TileCanvas(String name, int priority)
	{
		this(name, priority, 500, 500);
	}
	
	public TileCanvas(String name, int priority, int width, int height)
	{
		this.name = name;
		this.priority = priority;
		
		setWidth(width);
		setHeight(height);
		
		GraphicsContext gc = getGraphicsContext2D();
		gc.setLineWidth(1);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
	}
	
	// IMPLEMENTATIONS
	
	@Override
	public int compareTo(TileCanvas<C, T> o)
	{
		return priority == o.priority ? 0 : priority > o.priority ? 1 : -1;
	}
	
	// GETTERS & SETTERS
	
	public String getName()
	{
		return name;
	}
	
	public BiConsumer<TileCanvas<C, T>, T> getPainter()
	{
		return painter;
	}
	
	public void setPainter(BiConsumer<TileCanvas<C, T>, T> painter)
	{
		this.painter = painter;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	
	public boolean isClearOnPaint()
	{
		return clearOnPaint;
	}
	
	public void setClearOnPaint(boolean clearOnPaint)
	{
		this.clearOnPaint = clearOnPaint;
	}
	
	public void setRenderListSupplier(Supplier<Collection<T>> renderListSupplier)
	{
		this.renderListSupplier = renderListSupplier;
	}
	
	// PAINTING
	
	public void clear()
	{
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
	}
	
	public void paint()
	{
		if (clearOnPaint)
			clear();
		for (T t : renderListSupplier.get())
		{
			painter.accept(this, t);
		}
	}
}
