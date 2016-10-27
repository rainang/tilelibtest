package com.github.rainang.tilelib.control;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LabeledNode<N extends Node> extends HBox
{
	private final Label label;
	
	private final N node;
	
	public LabeledNode(String text, N node)
	{
		getStyleClass().add("labeled-node");
		
		this.label = new Label(text);
		this.node = node;
		
		label.getStyleClass()
			 .add("labeled-node");
		label.getStyleClass()
			 .add("labeled-node-l");
		
		node.getStyleClass()
			.add("labeled-node");
		node.getStyleClass()
			.add("labeled-node-r");
		
		getChildren().addAll(label, node);
	}
	
	public Label getLabel()
	{
		return label;
	}
	
	public N getNode()
	{
		return node;
	}
}
