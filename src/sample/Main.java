package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class Main extends Application
{
    int dimensions = 6;
    BorderPane pane = new BorderPane();
    GridPane grid = new GridPane();
    VBox vBox1 = new VBox();
    VBox vBox2 = new VBox();
    HBox hBox1 = new HBox();
    HBox hBox2 = new HBox();
    ArrayList<BlockGroups> groups = new ArrayList<>();
    Table table = new Table(dimensions, groups);
    FileChooser fileChooser = new FileChooser();
    TextArea textArea = new TextArea();
    Stage openingStage = new Stage();
    BorderPane openingPane = new BorderPane();
    VBox openingVBox = new VBox();
    Stage primaryStage = new Stage();
    Stage stage = new Stage();
    File selectedFile;
    int counter = 1;

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;

        openingStage.setTitle("Mathdoku Launcher");

        Button playButton = new Button("Play");
        playButton.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-font-size:30;  -fx-border-color: #008CBA; -fx-border-width: 2");
        playButton.setOnMouseEntered(e-> playButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-size:30;"));
        playButton.setOnMouseExited(e->playButton.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-font-size:30; -fx-border-color: #008CBA; -fx-border-width: 2"));
        playButton.setPrefSize(300,100);

        Button howToPlayButton = new Button("How to play the game");
        howToPlayButton.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-font-size:22; -fx-border-color: #555555; -fx-border-width: 2");
        howToPlayButton.setOnMouseEntered(e->howToPlayButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size:22"));
        howToPlayButton.setOnMouseExited(e->howToPlayButton.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-font-size:22; -fx-border-color: #555555; -fx-border-width: 2"));
        howToPlayButton.setPrefSize(250,75);

        playButton.setOnAction(e->
                {
                    primaryStage.show();
                    openingStage.close();
                });

        howToPlayButton.setOnAction(e->
        {
            TextArea informationTextArea = new TextArea();

            informationTextArea.setText("A player needs to fill the cells in an NxN square grid with the numbers 1 to" +
                    " N (one number per cell), while adhering to the following constraints:\n" +
                    "\n" +
                    "•  Each number must appear exactly once in each row.\n" +
                    "•  Each number must appear exactly once in each column.\n" +
                    "Furthermore, there are groups of adjacent cells called cages, which are highlighted on the grid " +
                    "by thicker boundaries.\n" +
                    "Within each cage is a label showing a target number followed by an " +
                    "arithmetic operator (+, -, x, ÷). There is an additional constraint associated with these " +
                    "cages:\n" +
                    "•  It must be possible to obtain the target by applying the arithmetic operator to the numbers in " +
                    "that cage. For - and ÷, this can be done in any order.\n" +
                    "Note: If a cage consists of a single cell, then no arithmetic operator is shown. The label " +
                    "simply shows the number that must be in that cell.\n" +
                    "\n"+
                    "To load a pre-defined puzzle it must be given in the following format:\n" +
                    "• Each line defines one cage of the puzzle.\n" +
                    "• The line starts with the target followed immediately by the arithmetic operator (or none if it's" +
                    " a single cell) for that cage.\n" +
                    "• This is followed by a space and a sequence of cell IDs that belong to the cage (consecutive cell" +
                    " IDs are separated by a comma).\n   Here cells are numbered from 1 to (NxN), where 1 to N are the " +
                    "cells in the top row (left to right), N+1 to 2N are the cells in the second row from the top, " +
                    "and so on.");
            informationTextArea.setEditable(false);
            informationTextArea.setWrapText(false);
            informationTextArea.setMaxWidth(Double.MAX_VALUE);
            informationTextArea.setMaxHeight(Double.MAX_VALUE);
            informationTextArea.setFont(new Font("Calibri",18));

            GridPane gridPane = new GridPane();
            GridPane.setHgrow(informationTextArea,Priority.ALWAYS);
            GridPane.setVgrow(informationTextArea, Priority.ALWAYS);
            gridPane.setMaxWidth(Double.MAX_VALUE);
            gridPane.add(informationTextArea,0,0);

            Stage informationStage = new Stage();
            informationStage.setTitle("How to play Mathdoku");
            informationStage.setScene(new Scene(gridPane,1250,400));
            informationStage.show();
        });

        openingVBox.getChildren().addAll(playButton,howToPlayButton);
        openingVBox.setSpacing(40);
        openingVBox.setAlignment(Pos.CENTER);
        openingPane.setCenter(openingVBox);
        openingStage.setMaximized(true);

        Scene openingScene = new Scene(openingPane,1000,1000);
        openingStage.setScene(openingScene);
        openingStage.show();

        primaryStage.setTitle("Mathdoku");

        Label label = new Label("Mathdoku");
        label.setFont(new Font("Calibri" , 34));
        label.setStyle("-fx-text-fill: #555555");
        hBox2.getChildren().add(label);
        hBox2.setAlignment(Pos.CENTER);

        table.setGrid(grid);
        table.setPane(pane);
        table.setvBox1(vBox1);
        table.sethBox1(hBox1);
        table.createUndoRedoButtons();
        createTable();
        table.createNumberButtons();
        createButtons();

        pane.setTop(hBox2);
        pane.setRight(vBox1);
        pane.setLeft(vBox2);
        pane.setBottom(hBox1);
        pane.setStyle("-fx-background-color: #e7e7e7");

        grid.setStyle("-fx-background-color: white");

        Scene scene = new Scene(pane,1000,1000);

        createDimensionButtons();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
    }

    public void createTable()
    {
        table.setDimensions(dimensions);
        table.createTable();
    }

    public void gridSize(Button button)
    {
        String buttonText = button.getText();

        for(int number = 2; number <= 8; number++)
        {
            if(buttonText.equals(number +"×" +number))
            {
                setDimensions(number);
            }
        }

        createTable();
        table.createNumberButtons();
    }

    public void createDimensionButtons()
    {
        vBox2.setAlignment(Pos.CENTER);

        for(int number = 2; number <= 8; number++)
        {
            Button button = new Button(number +"×" +number);
            vBox2.getChildren().addAll(button);
            button.setMinSize(50, 50);
            button.setOnAction(e-> gridSize(button));
            if(button.getText().equals(6 +"×" + 6))
            {
                button.requestFocus();
            }
        }
    }

    public void createButtons()
    {
        Button clearCellButton = new Button("Clear Cell");
        clearCellButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");
        clearCellButton.setOnAction(e-> table.getLastBlock().getTextField().setText(""));

        Button clearBoardButton = new Button("Clear");
        clearBoardButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");
        clearBoardButton.setOnAction(e-> clearBoard());

        Button showMistakesButton = new Button("Show Mistakes");
        showMistakesButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");
        showMistakesButton.setOnAction(e -> table.checkWin());

        Button loadFileButton = new Button("Load from File");
        loadFileButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        loadFileButton.setOnAction(e->
        {
            selectedFile = fileChooser.showOpenDialog(primaryStage);

            if(selectedFile != null)
            {
                try
                {
                    table.readFile(selectedFile);
                }

                catch (FileNotFoundException ignored)
                {

                }
            }
        });

        textArea.setMinHeight(550);
        textArea.setFont(new Font("Calibri",14));
        VBox vBox = new VBox(textArea);

        stage.setTitle("Enter a Mathdoku puzzle");
        stage.setScene(new Scene(vBox,350,640));

        Button loadTextButton = new Button("Load from Text");
        loadTextButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");
        loadTextButton.setOnAction(e-> readLoadedText(vBox));
        
        MenuButton menuButton = new MenuButton("Change font size");
        MenuItem menuItem1 = new MenuItem("Small");
        MenuItem menuItem2 = new MenuItem("Medium");
        MenuItem menuItem3 = new MenuItem("Large");
        menuButton.getItems().addAll(menuItem1, menuItem2, menuItem3);
        menuButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                fontSize(((MenuItem)e.getSource()).getText());
            }
        };

        menuItem1.setOnAction(event);
        menuItem2.setOnAction(event);
        menuItem3.setOnAction(event);

        hBox1.getChildren().addAll(clearBoardButton, clearCellButton, loadFileButton, loadTextButton, showMistakesButton, menuButton);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(5);
    }

    public void clearBoard()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure that you want to clear the board");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            Block[][] blocks = table.getBlocks();

            for (int r = 0; r < dimensions; r++)
            {
                for (int c = 0; c < dimensions; c++)
                {
                    blocks[r][c].getTextField().clear();
                    blocks[r][c].getTextField().setStyle("-fx-text-box-border:transparent; -fx-alignment: center; -fx-max-width: 300");
                }
            }
        }
    }

    public void readLoadedText(VBox vBox)
    {
        Button closeButton = new Button("Load");
        Button clearButton = new Button("Clear");

        if(counter == 1)
        {
            closeButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");
            clearButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");

            HBox hBox = new HBox();
            hBox.getChildren().addAll(clearButton, closeButton);
            hBox.setAlignment(Pos.BOTTOM_CENTER);
            hBox.setSpacing(20);

            vBox.setSpacing(20);
            vBox.getChildren().add(hBox);

            counter++;
        }

        stage.show();

        closeButton.setOnAction(e->
        {
            try
            {
                File temp = File.createTempFile("tempfile", ".tmp");
                FileWriter fileWriter = new FileWriter(temp);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(textArea.getText());
                bufferedWriter.close();
                table.readFile( temp);

                stage.close();
            }

            catch (IOException ignored)
            {

            }
        });

        clearButton.setOnAction(e-> textArea.clear());
    }


    public void fontSize(String size)
    {
        Block[][] blocks = table.getBlocks();

        for (int r = 0; r < dimensions; r++)
        {
            for (int c = 0; c < dimensions; c++)
            {
                if(size.equals("Small"))
                {
                    blocks[r][c].getLabel().setFont(new Font("Calibri",12));
                    blocks[r][c].getTextField().setFont(new Font("Calibri",12));
                }

                if(size.equals("Medium"))
                {
                    blocks[r][c].getLabel().setFont(new Font("Calibri",17));
                    blocks[r][c].getTextField().setFont(new Font("Calibri",17));
                }

                if(size.equals("Large"))
                {
                    blocks[r][c].getLabel().setFont(new Font("Calibri",22));
                    blocks[r][c].getTextField().setFont(new Font("Calibri",22));
                }
            }
        }
    }

    public void setDimensions(int dimensions)
    {
        this.dimensions = dimensions;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}