package com.github.rainang.tilelib.canvas;

import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.tiles.Tile;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Collection;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TileCanvas<C extends Coordinate, T extends Tile> extends Canvas
{
	private final String name;
	
	private Supplier<Collection<T>> renderListSupplier = () -> Collections.EMPTY_LIST;
	
	private BiConsumer<GraphicsContext, T> painter = (gc, t) ->
	{
	};
	
	private boolean clearOnPaint = true;
	
	// CONSTRUCTORS
	
	TileCanvas(String name, double width, double height)
	{
		this.name = name;
		
		setWidth(width);
		setHeight(height);
		
		GraphicsContext gc = getGraphicsContext2D();
		gc.setLineWidth(1);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFill(Color.LIGHTGRAY);
		gc.setStroke(Color.BLACK);
	}
	
	// GETTERS & SETTERS
	
	public String getName()
	{
		return name;
	}
	
	public BiConsumer<GraphicsContext, T> getPainter()
	{
		return painter;
	}
	
	public void setPainter(BiConsumer<GraphicsContext, T> painter)
	{
		this.painter = painter;
	}
	
	public boolean isClearOnPaint()
	{
		return clearOnPaint;
	}
	
	public void setClearOnPaint(boolean clearOnPaint)
	{
		this.clearOnPaint = clearOnPaint;
	}
	
	void setRenderListSupplier(Supplier<Collection<T>> renderListSupplier)
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
			painter.accept(getGraphicsContext2D(), t);
	}
}
