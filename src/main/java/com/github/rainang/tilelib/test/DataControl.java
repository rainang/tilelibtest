package com.github.rainang.tilelib.test;

import com.github.rainang.tilelib.control.LabeledNode;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

class DataControl extends VBox
{
	DataControl(HexStack canvas)
	{
		getStyleClass().add("control-pane");
		
		Label lblMS = new Label();
		Label lblMO = new Label();
		Label lblMT = new Label();
		Label lblFT = new Label();
		
		LabeledNode<Label> lbl0 = new LabeledNode<>("Mouse", lblMS);
		LabeledNode<Label> lbl1 = new LabeledNode<>("Hex", lblMO);
		LabeledNode<Label> lbl2 = new LabeledNode<>("Tile", lblMT);
		LabeledNode<Label> lbl3 = new LabeledNode<>("Focus", lblFT);
		
		lbl0.getLabel()
			.setId("data");
		lbl1.getLabel()
			.setId("data");
		lbl2.getLabel()
			.setId("data");
		lbl3.getLabel()
			.setId("data");
		
		canvas.getInputAdapter()
			  .mousePosProperty()
			  .addListener((a, b, c) -> setLabelText(lblMS, c + ""));
		canvas.getInputAdapter()
			  .mouseoverProperty()
			  .addListener((a, b, c) -> setLabelText(lblMO, c + ""));
		canvas.getInputAdapter()
			  .mouseTileProperty()
			  .addListener((a, b, c) -> setLabelText(lblMT, (c != null) + ""));
		canvas.getInputAdapter()
			  .focusTileProperty()
			  .addListener((a, b, c) -> setLabelText(lblFT, c.getPos() + ""));
		
		getChildren().addAll(lbl0, lbl1, lbl2, lbl3);
	}
	
	private final void setLabelText(Label lbl, String text)
	{
		if (lbl == null)
			return;
		lbl.setText(text);
	}
}
