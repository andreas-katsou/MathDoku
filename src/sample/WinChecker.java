package sample;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class WinChecker
{
    private ArrayList<BlockGroups> groups;
    private Block[][] blocks;
    private int dimensions;

    public WinChecker(ArrayList<BlockGroups> groups, Block[][] blocks, int dimensions)
    {
        this.groups = groups;
        this.blocks = blocks;
        this.dimensions = dimensions;
    }

    public void checkWin()
    {
        int counter = 0;

        for (BlockGroups group: groups)
        {
            String operation = "";
            int targetValue = 0;

            ArrayList<Integer> blockInGroup = new ArrayList<>();

            for (Node block: group.getBlockGroups())
            {
                String value = ((Block) block).getLabel().getText();

                if (value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4") || value.equals("5") ||value.equals("6") || value.equals("7") || value.equals("8"))
                {
                    operation =" ";
                    targetValue = Integer.parseInt(value);
                }

                else if (!value.equals(""))
                {
                    operation = String.valueOf(value.charAt(value.length()-1));
                    targetValue = Integer.parseInt(value.substring(0, value.length()-1));
                }

                blockInGroup.add(Integer.parseInt(String.valueOf(((Block) block).getTextField().getText())));
            }

            if (operation.equals("+"))
            {
                int blockValue = 0;

                for(int integer: blockInGroup)
                {
                    blockValue = blockValue + integer;
                }

                if(blockValue == targetValue)
                {
                   counter++;
                }
            }

            else if (operation.equals("x"))
            {
                int blockValue = 1;

                for (int integer: blockInGroup)
                {
                    blockValue = blockValue * integer;
                }

                if (blockValue == targetValue)
                {
                   counter++;
                }
            }

            else if (operation.equals("-"))
            {
                int blockValue = Collections.max(blockInGroup);
                Collections.sort(blockInGroup);
                Collections.reverse(blockInGroup);

                for (int i = 1; i < blockInGroup.size(); i++)
                {
                    blockValue = blockValue - blockInGroup.get(i);
                }

                if (blockValue == targetValue)
                {
                    counter++;
                }
            }

            else if(operation.equals("÷"))
            {
                int blockValue = Collections.max(blockInGroup);
                Collections.sort(blockInGroup);
                Collections.reverse(blockInGroup);

                for (int i = 1; i < blockInGroup.size(); i++)
                {
                    blockValue = blockValue / blockInGroup.get(i);
                }

                if (blockValue == targetValue)
                {
                    counter++;
                }
            }

            else if(operation.equals(" "))
            {
                int blockValue = blockInGroup.get(0);

                if(blockValue == targetValue)
                {
                    counter++;
                }
            }
        }

        if (groups.size() == counter)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You Won");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                for (int r = 0; r < dimensions; r++)
                {
                    for (int c = 0; c < dimensions; c++)
                    {
                        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(4),blocks[r][c]);
                        rotateTransition.setByAngle(360);
                        rotateTransition.setCycleCount(2);
                        blocks[r][c].getTextField().setStyle("-fx-background-color: #00ff00");
                        rotateTransition.play();
                    }
                }
            }
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You Lost");
            alert.show();
        }
    }

}
