package sample;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ReadFile
{
    private Block[][] blocks;
    private File file;
    private String[] splittedData1;
    private String[] splittedData2;
    private int numberOfBlocks;
    private int dimensions;
    private ArrayList<Block> blockList;
    private ArrayList<ArrayList<Integer>> blockArrayList;
    private ArrayList<BlockGroups> groups;
    private ArrayList<String> labelList;
    private ArrayList<Integer> numberList;
    private boolean correctFile;
    private boolean wrongFile;
    private boolean alertShowed;

    public ReadFile(Block[][] blocks, File file)
    {
        this.blocks = blocks;
        this.file = file;
        splittedData1 = new String[0];
        numberOfBlocks = 0;
        blockList = new ArrayList<>();
        blockArrayList = new ArrayList<>();
        labelList = new ArrayList<>();
        numberList = new ArrayList<>();
        groups = new ArrayList<>();
        correctFile = false;
        wrongFile = true;
        alertShowed = false;
    }

    public void readFileMethod() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine())
        {
            String data = scanner.nextLine();
            int length = data.length();
            String label;

            for (int i = 0; i < length; i++)
            {
                if (data.charAt(i) == '+' || data.charAt(i) == 'x' || data.charAt(i) == '-' || data.charAt(i) == '÷')
                {
                    label = data.substring(0, i + 1);
                    labelList.add(label);
                    splittedData1 = data.split(" ");
                    break;
                }

                else if (data.charAt(i) == ' ')
                {
                    label = String.valueOf(data.charAt(i-1));
                    labelList.add(label);
                    splittedData1 = data.split(" ");
                    break;
                }
            }
            splittedData2 = splittedData1[1].split(",");

            ArrayList<Integer> blocksInCage = new ArrayList<>();

            for (String block: splittedData2)
            {
                blocksInCage.add(Integer.parseInt(block));
                numberList.add(Integer.parseInt(block));
            }

            getNumberOfBlocks();
            blockArrayList.add(blocksInCage);
        }

        scanner.close();
        setDimensions();
    }


    public void checkFileMethod()
    {
        addBlocks();
        assignValueToBlock();
        checkFile(blockArrayList);
    }

    public void loadFile()
    {
        blockList.clear();
        addBlocks();
        assignValueToBlock();
        createGroups(blockArrayList);
    }

    public void getNumberOfBlocks()
    {
        for(int i = 0; i < splittedData2.length; i++)
        {
            numberOfBlocks++;
        }
    }

    public void setDimensions()
    {
        if (numberOfBlocks == 4)
        {
            dimensions = 2;
        }

        else if (numberOfBlocks == 9)
        {
            dimensions = 3;
        }

        else if (numberOfBlocks == 16)
        {
            dimensions = 4;
        }

        else if (numberOfBlocks == 25)
        {
            dimensions = 5;
        }

        else if (numberOfBlocks == 36)
        {
            dimensions = 6;
        }

        else if (numberOfBlocks == 49)
        {
            dimensions = 7;
        }

        else if (numberOfBlocks == 64)
        {
            dimensions = 8;
        }
    }

    public void addBlocks()
    {
        for (int r = 0; r < dimensions; r++)
        {
            for (int c = 0; c < dimensions; c++)
            {
                blockList.add(blocks[r][c]);
            }
        }
    }

    public void assignValueToBlock()
    {
       int number = 1;

       for(Block block: blockList)
       {
           block.setValue(number);
           number++;
       }
    }

    public void createGroups(ArrayList<ArrayList<Integer>> blockArrayList)
    {

        int index = 0;

        for(ArrayList<Integer> group : blockArrayList)
        {
            int counter = 0;
            BlockGroups blockGroups = new BlockGroups();

            for (Block block : blockList)
            {
                if(group.contains(block.getValue()))
                {
                    counter++;

                    if(counter==1)
                    {
                        block.getLabel().setText(labelList.get(index));
                        index++;
                    }

                    blockGroups.addBlock(block);
                }

                if(group.size() == blockGroups.getListSize())
                {
                    break;
                }
            }

            groups.add(blockGroups);
        }
    }

    public void checkFile(ArrayList<ArrayList<Integer>> blockArrayList)
    {
        checkNumbers();
        int counter = 1;

        if (!wrongFile)
        {
            for (ArrayList<Integer> group : blockArrayList)
            {
                int row = 0;
                int column = 0;
                correctFile = false;

                for (Integer block : group)
                {
                    if (group.size() == 1)
                    {
                        correctFile = true;
                    }

                    for (int r = 0; r < dimensions; r++)
                    {
                        for (int c = 0; c < dimensions; c++)
                        {
                            if (block == blocks[r][c].getValue())
                            {
                                row = r;
                                column = c;
                            }
                        }
                    }

                    if (!alertShowed)
                    {
                        if (row - 1 >= 0 && group.contains(blocks[row - 1][column].getValue()))
                        {
                            correctFile = true;
                        }

                        else if (column + 1 < dimensions && group.contains(blocks[row][column + 1].getValue()))
                        {
                            correctFile = true;
                        }

                        else if (row + 1 < dimensions && group.contains(blocks[row + 1][column].getValue()))
                        {
                            correctFile = true;
                        }

                        else if (column - 1 >= 0 && group.contains(blocks[row][column - 1].getValue()))
                        {
                            correctFile = true;
                        }
                    }

                    if (!correctFile)
                    {
                        if (counter == 1)
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Be careful your table configuration is wrong");
                            alert.show();
                            alertShowed = true;
                            counter++;
                        }
                    }
                }
            }
        }
    }

    public void checkNumbers()
    {
        int counter = 1;

        for(Integer integer: numberList)
        {
            int times = Collections.frequency(numberList, integer);

            if (times == 1 && counter==1)
            {
                wrongFile = false;
            }

            else if (times > 1 && counter == 1)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Be careful you have enter the same number multiple times");
                alert.show();
                wrongFile = true;
                counter++;
            }
        }
    }

    public int getDimensions()
    {
        return dimensions;
    }

    public ArrayList<BlockGroups> getGroups()
    {
        return groups;
    }

    public boolean isCorrectFile()
    {
        return correctFile;
    }

}
