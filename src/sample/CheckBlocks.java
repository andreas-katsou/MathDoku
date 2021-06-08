package sample;

import javafx.scene.control.Alert;

import java.util.ArrayList;

public class CheckBlocks
{
    private int dimensions;
    private Block[][] blocks;
    private ArrayList<BlockGroups> groups;
    private int counter = 0;
    private boolean hasMistakes;
    private boolean complete;

    public CheckBlocks(int dimensions, Block[][] blocks, ArrayList<BlockGroups> groups)
    {
        this.dimensions = dimensions;
        this.blocks = blocks;
        this.groups = groups;
        hasMistakes = false;
        complete = false;
    }

    public void checksBoxes()
    {
        for (int r = 0; r < dimensions; r++)
        {
            for (int c = 0; c < dimensions; c++)
            {
                try
                {
                    Integer.parseInt(blocks[r][c].getTextField().getText());
                    complete = true;
                }

                catch (NumberFormatException e)
                {
                    if(counter == 0)
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("You haven't complete all the cells");
                        alert.show();
                    }

                    counter++;
                    complete = false;
                    blocks[r][c].getTextField().setStyle("-fx-background-color:grey; -fx-alignment:center; -fx-max-width: 300");
                }
            }
        }

        if(complete)
        {
            for (int k = 0; k < dimensions; k++)
            {
                for (int r = 0; r < dimensions; r++)
                {
                    try
                    {
                        int checker1 = Integer.parseInt(blocks[k][r].getTextField().getText());

                        for (int c = r + 1; c < dimensions; c++)
                        {
                            int checker2 = Integer.parseInt(blocks[k][c].getTextField().getText());

                            if (checker1 == checker2)
                            {
                                for (int i = 0; i < dimensions; i++)
                                {
                                    blocks[k][i].getTextField().setStyle("-fx-background-color:red; -fx-alignment: center; -fx-max-width: 300");
                                    hasMistakes = true;
                                }
                            }
                        }
                    }

                    catch (NumberFormatException ignored)
                    {

                    }
                }
            }

            for (int k = 0; k < dimensions; k++)
            {
                for (int r = 0; r < dimensions; r++)
                {
                    try
                    {
                        int checker1 = Integer.parseInt(blocks[r][k].getTextField().getText());

                        for (int c = r + 1; c < dimensions; c++)
                        {
                            int checker2 = Integer.parseInt(blocks[c][k].getTextField().getText());

                            if (checker1 == checker2)
                            {
                                for (int i = 0; i < dimensions; i++)
                                {
                                    blocks[i][k].getTextField().setStyle("-fx-background-color:red; -fx-alignment: center");
                                    hasMistakes = true;
                                }
                            }
                        }
                    }

                    catch (NumberFormatException ignored)
                    {

                    }
                }
            }

            if(!hasMistakes)
            {
                new WinChecker(groups, blocks, dimensions).checkWin();
            }
        }
    }

}
