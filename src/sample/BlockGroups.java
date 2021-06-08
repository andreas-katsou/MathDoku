package sample;

import java.util.ArrayList;

public class BlockGroups
{
    private ArrayList<Block> blockGroups;

    public BlockGroups()
    {
        blockGroups = new ArrayList<>();
    }

    public void addBlock(Block block)
    {
        blockGroups.add(block);
    }

    public ArrayList<Block> getBlockGroups()
    {
        return blockGroups;
    }

    public int getListSize()
    {
        return blockGroups.size();
    }
}
