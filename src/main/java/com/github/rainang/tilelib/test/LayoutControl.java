package com.github.rainang.tilelib.test;

import com.github.rainang.tilelib.control.CoordinateField;
import com.github.rainang.tilelib.control.LabeledNode;
import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.layout.HexOrientation;
import com.github.rainang.tilelib.layout.Layout;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import static com.github.rainang.tilelib.layout.HexOrientation.FLAT;
import static com.github.rainang.tilelib.layout.HexOrientation.POINTY;

class LayoutControl extends VBox
{
	private final Button btnOrient = new Button(POINTY.name());
	
	private final CoordinateField size = new CoordinateField(Coordinate.create(15, 15), -64, 64);
	private final CoordinateField offset = new CoordinateField(Coordinate.create(250, 250), -500, 500);
	
	LayoutControl(HexStack canvas)
	{
		getStyleClass().add("control-pane");
		
		LabeledNode<Button> lbl1 = new LabeledNode<>("Layout", btnOrient);
		LabeledNode<CoordinateField> lbl2 = new LabeledNode<>("Size", size);
		LabeledNode<CoordinateField> lbl3 = new LabeledNode<>("Offset", offset);
		
		btnOrient.getStyleClass()
				 .add("layout-button");
		btnOrient.setOnAction(e ->
		{
			HexOrientation orientation;
			if (btnOrient.getText()
						 .equals(FLAT.name()))
			{
				btnOrient.setText(POINTY.name());
				orientation = POINTY;
			} else
			{
				btnOrient.setText(FLAT.name());
				orientation = FLAT;
			}
			canvas.setLayout(new Layout.Hex(orientation, canvas.getLayout().size, canvas.getLayout().origin, 0.3));
		});
		
		size.coordinateProperty()
			.addListener(i -> onLayoutChanged(canvas));
		offset.coordinateProperty()
			  .addListener(i -> onLayoutChanged(canvas));
		btnOrient.setPrefWidth(size.getPrefWidth());
		
		getChildren().addAll(lbl1, lbl2, lbl3);
	}
	
	private void onLayoutChanged(HexStack canvas)
	{
		Layout.Hex layout = canvas.getLayout();
		canvas.setLayout(new Layout.Hex(layout.orientation, size.getCoordinateD(), offset.getCoordinateD(), 0.3));
	}
}
