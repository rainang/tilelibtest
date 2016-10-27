package com.github.rainang.tilelib.test;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

class ViewControl extends GridPane
{
	ViewControl(HexStack canvas)
	{
		getStyleClass().add("control-pane");
		getStyleClass().add("view-pane");
		
		CheckBox cbL = new CheckBox("Grid Lines");
		CheckBox cbX = new CheckBox("X");
		CheckBox cbY = new CheckBox("Y");
		CheckBox cbZ = new CheckBox("Z");
		
		cbL.selectedProperty()
		   .bindBidirectional(canvas.getLayerVisibilityProperty("Grid"));
		cbX.selectedProperty()
		   .bindBidirectional(canvas.getLayerVisibilityProperty("X"));
		cbY.selectedProperty()
		   .bindBidirectional(canvas.getLayerVisibilityProperty("Y"));
		cbZ.selectedProperty()
		   .bindBidirectional(canvas.getLayerVisibilityProperty("Z"));
		
		add(cbL, 1, 0);
		add(cbX, 0, 0);
		add(cbY, 0, 1);
		add(cbZ, 0, 2);
	}
}
