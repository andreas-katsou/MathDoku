package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Block extends VBox
{
    private Label label;
    private TextField textField;
    private int value;
    private UndoRedo undoRedo;

    public Block(int dimensions, Button undoButton, Button redoButton)
    {
        undoRedo = new UndoRedo(undoButton, redoButton);

        label = new Label();
        label.setFont(new Font("Calibri", 17));
        label.setStyle("-fx-alignment: top-left");
        textField = new TextField();
        textField.setFont(new Font("Calibri", 17));
        textField.setStyle("-fx-text-box-border: transparent; -fx-alignment: center");
        this.getChildren().addAll(label, textField);

        this.getTextField().textProperty().addListener((observableValue, oldValue, newValue1) ->
        {
            undoRedo.push(new UndoRedoHelper(this, oldValue));

            try
            {
                int newValue2 = Integer.parseInt(newValue1);
                if (newValue2 <= 0 || newValue2 > dimensions) 
				{
				    this.getTextField().setStyle("-fx-background-color:red;");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Be careful you can only enter a number between 1 and " + dimensions);
                    alert.show();
                }

                else 
				{
				    this.getTextField().setStyle("-fx-text-box-border:transparent; -fx-alignment: center");
                }
            } 
			
			catch (NumberFormatException e) 
			{
			    if(newValue1.equals(""))
                {
                    this.getTextField().setStyle("-fx-background-color: red; -fx-alignment: center");
                }

			    else 
				{
                    this.getTextField().setStyle("-fx-background-color:red; -fx-alignment: center");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Be careful you can only enter a number between 1 and " + dimensions);
                    alert.show();
                    System.out.println("Wrong number");
				}
			}
        });
    }

    public TextField getTextField()
    {
        return textField;
    }

    public Label getLabel()
    {
        return label;
    }


    public int getValue()
    {
        return value;
    }

    public UndoRedo getUndoRedo()
    {
        return undoRedo;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

}
