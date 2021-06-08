package sample;

import java.util.ArrayList;

public class DefaultTable
{
    private Block[][] blocks;
    private ArrayList<BlockGroups> groups;

    public DefaultTable(Block[][] blocks)
    {
        this.blocks = blocks;
        groups = new ArrayList<>();
    }

    public void createLabels()
    {
        blocks[0][0].getLabel().setText("11+");
        blocks[0][1].getLabel().setText("2÷");
        blocks[0][3].getLabel().setText("20x");
        blocks[0][4].getLabel().setText("6x");
        blocks[1][1].getLabel().setText("3-");
        blocks[1][4].getLabel().setText("3÷");
        blocks[2][0].getLabel().setText("240x");
        blocks[2][2].getLabel().setText("6x");
        blocks[3][2].getLabel().setText("6x");
        blocks[3][3].getLabel().setText("7+");
        blocks[3][4].getLabel().setText("30x");
        blocks[4][0].getLabel().setText("6x");
        blocks[4][5].getLabel().setText("9+");
        blocks[5][0].getLabel().setText("8+");
        blocks[5][3].getLabel().setText("2÷");
    }

    public void createGroups()
    {
        BlockGroups group1 = new BlockGroups();
        group1.addBlock(blocks[0][0]);
        group1.addBlock(blocks[1][0]);
        groups.add(group1);
        BlockGroups group2 = new BlockGroups();
        group2.addBlock(blocks[0][1]);
        group2.addBlock(blocks[0][2]);
        groups.add(group2);
        BlockGroups group3 = new BlockGroups();
        group3.addBlock(blocks[1][1]);
        group3.addBlock(blocks[1][2]);
        groups.add(group3);
        BlockGroups group4 = new BlockGroups();
        group4.addBlock(blocks[0][3]);
        group4.addBlock(blocks[1][3]);
        groups.add(group4);
        BlockGroups group5 = new BlockGroups();
        group5.addBlock(blocks[0][4]);
        group5.addBlock(blocks[0][5]);
        group5.addBlock(blocks[1][5]);
        group5.addBlock(blocks[2][5]);
        groups.add(group5);
        BlockGroups group6 = new BlockGroups();
        group6.addBlock(blocks[1][4]);
        group6.addBlock(blocks[2][4]);
        groups.add(group6);
        BlockGroups group7 = new BlockGroups();
        group7.addBlock(blocks[2][0]);
        group7.addBlock(blocks[2][1]);
        group7.addBlock(blocks[3][0]);
        group7.addBlock(blocks[3][1]);
        groups.add(group7);
        BlockGroups group8 = new BlockGroups();
        group8.addBlock(blocks[2][2]);
        group8.addBlock(blocks[2][3]);
        groups.add(group8);
        BlockGroups group9 = new BlockGroups();
        group9.addBlock(blocks[3][2]);
        group9.addBlock(blocks[4][2]);
        groups.add(group9);
        BlockGroups group10 = new BlockGroups();
        group10.addBlock(blocks[3][3]);
        group10.addBlock(blocks[4][3]);
        group10.addBlock(blocks[4][4]);
        groups.add(group10);
        BlockGroups group11 = new BlockGroups();
        group11.addBlock(blocks[3][4]);
        group11.addBlock(blocks[3][5]);
        groups.add(group11);
        BlockGroups group12 = new BlockGroups();
        group12.addBlock(blocks[4][0]);
        group12.addBlock(blocks[4][1]);
        groups.add(group12);
        BlockGroups group13 = new BlockGroups();
        group13.addBlock(blocks[4][5]);
        group13.addBlock(blocks[5][5]);
        groups.add(group13);
        BlockGroups group14 = new BlockGroups();
        group14.addBlock(blocks[5][0]);
        group14.addBlock(blocks[5][1]);
        group14.addBlock(blocks[5][2]);
        groups.add(group14);
        BlockGroups group15 = new BlockGroups();
        group15.addBlock(blocks[5][3]);
        group15.addBlock(blocks[5][4]);
        groups.add(group15);
    }

    public ArrayList<BlockGroups> getGroups()
    {
        return groups;
    }

}
