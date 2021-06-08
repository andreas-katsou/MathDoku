package sample;

public class UndoRedoHelper
{
    private Block block;
    private String oldValue;

    public UndoRedoHelper(Block block, String oldValue)
    {
        this.block = block;
        this.oldValue = oldValue;
    }

    public Block getBlock()
    {
        return block;
    }

    public String getOldValue()
    {
        return oldValue;
    }

}
