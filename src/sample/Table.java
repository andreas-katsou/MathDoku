package sample;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Table
{
    private Block[][] blocks;
    private int dimensions;
    private ArrayList<BlockGroups> groups;
    private GridPane grid;
    private BorderPane pane;
    private Block lastBlock;
    private VBox vBox1;
    private HBox hBox1;
    private Button undoButton;
    private Button redoButton;
    private int counter = 1;

    public Table(int dimensions, ArrayList<BlockGroups> groups)
    {
        this.dimensions = dimensions;
        this.groups = groups;
        blocks = new Block[8][8];
    }

    public void createTable()
    {
        grid.setAlignment(Pos.CENTER);
        grid.getChildren().clear();
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();

        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                Block block = new Block(dimensions, undoButton, redoButton);
                block.setStyle("-fx-border-width: 1; -fx-border-color: black");
                block.setPrefSize(100,100);
                block.getTextField().setOnMouseClicked(e-> undoRedoMethod(block));
                blocks[r][c] = block;
            }
        }

        for (int r = 0; r < dimensions; r++)
        {
            for (int c = 0; c < dimensions; c++)
            {
                grid.add(blocks[r][c],c,r);
            }
        }


        for (int d = 0; d < dimensions; d++)
        {
            grid.getColumnConstraints().add(new ColumnConstraints(90));
            grid.getRowConstraints().add(new RowConstraints(90));
        }

        pane.setCenter(grid);

        if(counter==1)
        {
            createDefaultTable();
            counter++;
        }
    }

    public boolean isBlockInGroup(BlockGroups group, Block block)
    {
        for (Node b: group.getBlockGroups())
        {
            if(b.equals(block))
            {
                return true;
            }
        }

        return false;
    }

    public void setGroupBorder()
    {
        for(BlockGroups group: groups)
        {
            int row = 0;
            int column = 0;
            int top = 1;
            int right = 1;
            int bottom = 1;
            int left = 1;

            for (Node block : group.getBlockGroups())
            {
                for (int r = 0; r < dimensions; r++)
                {
                    for (int c = 0; c < dimensions; c++)
                    {
                        if (block.equals(blocks[r][c]))
                        {
                            row = r;
                            column = c;
                        }
                    }
                }

                if (row - 1 >= 0)
                {
                    Block topNeighbour = blocks[row - 1][column];
                    if (!isBlockInGroup(group, topNeighbour))
                    {
                        top = 3;
                    }
                }

                if (column + 1 < dimensions)
                {
                    Block rightNeighbour = blocks[row][column + 1];
                    if (!isBlockInGroup(group, rightNeighbour))
                    {
                        right = 3;
                    }
                }

                if (row + 1 < dimensions)
                {
                    Block bottomNeighbour = blocks[row + 1][column];
                    if (!isBlockInGroup(group, bottomNeighbour))
                    {
                        bottom = 3;
                    }
                }

                if (column - 1 >= 0)
                {
                    Block leftNeighbour = blocks[row][column - 1];
                    if (!isBlockInGroup(group, leftNeighbour))
                    {
                        left = 3;
                    }
                }

                block.setStyle("-fx-border-width: " + top + " " + right + " " + bottom + " " + left + "; -fx-border-color: black");

                row = 0;
                column = 0;
                top = 1;
                right = 1;
                bottom = 1;
                left = 1;
            }
        }
    }

    public void createDefaultTable()
    {
        DefaultTable defaultTable = new DefaultTable(blocks);
        defaultTable.createLabels();
        defaultTable.createGroups();
        groups = defaultTable.getGroups();
        setGroups(groups);
        setGroupBorder();
    }

    public void createNumberButtons()
    {
        vBox1.getChildren().clear();
        vBox1.setAlignment(Pos.CENTER);

        for(int number = 1; number <= dimensions; number++)
        {
            Button button = new Button(String.valueOf(number));
            vBox1.getChildren().addAll(button);
            button.setMinSize(50,50);
            int finalNumber = number;
            button.setOnAction(e->
            {
                if(lastBlock!=null)
                {
                    lastBlock.getTextField().setText(String.valueOf(finalNumber));
                }
            });
        }
    }

    public void readFile(File selectedFile) throws FileNotFoundException
    {
        ReadFile readFile = new ReadFile(blocks, selectedFile);
        readFile.readFileMethod();

        if(readFile.getDimensions() != 0)
        {
            setDimensions(readFile.getDimensions());
        }

        readFile.checkFileMethod();

        if(readFile.isCorrectFile())
        {
            createTable();
            createNumberButtons();
            readFile.loadFile();
            groups = readFile.getGroups();
            setGroupBorder();
       }
    }

    public void checkWin()
    {
        new CheckBlocks(dimensions, blocks, groups).checksBoxes();
    }

    public void undoRedoMethod(Block block)
    {
        lastBlock = block;

        UndoRedo undoRedo = lastBlock.getUndoRedo();
        undoButton.setOnAction(e-> undoRedo.undoMethod());
        redoButton.setOnAction(e-> undoRedo.redoMethod());

        if(undoRedo.isUndoEnable())
        {
            undoButton.setDisable(false);
        }

        else if(!undoRedo.isUndoEnable())
		{
            undoButton.setDisable(true);
		}

        if(undoRedo.isRedoEnable())
        {
            redoButton.setDisable(false);
        }

        else if(!undoRedo.isRedoEnable())
		{
            redoButton.setDisable(true);
        }
    }

    public void createUndoRedoButtons()
    {
        undoButton = new Button("Undo");
        undoButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");
        redoButton = new Button("Redo");
        redoButton.setStyle("-fx-background-color: white; -fx-color-label-visible: black; -fx-border-color: gray; -fx-border-radius: 50%");

        hBox1.getChildren().addAll(undoButton, redoButton);
    }

    public Block[][] getBlocks()
    {
        return blocks;
    }

    public Block getLastBlock()
    {
        return lastBlock;
    }

    public void setPane(BorderPane pane)
    {
        this.pane = pane;
    }

    public void setGrid(GridPane grid)
    {
        this.grid = grid;
    }

    public void setvBox1(VBox vBox1)
    {
        this.vBox1 = vBox1;
    }

    public void sethBox1(HBox hBox1)
    {
        this.hBox1 = hBox1;
    }

    public void setGroups(ArrayList<BlockGroups> groups)
    {
        this.groups = groups;
    }

    public void setDimensions(int dimensions)
    {
        this.dimensions = dimensions;
    }

}