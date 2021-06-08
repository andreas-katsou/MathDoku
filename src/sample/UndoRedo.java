package  sample;

import javafx.scene.control.Button;

import java.util.Stack;

public class UndoRedo
{
    private Stack<String> stack1;
    private Stack<String> stack2;
    private Button undoButton;
    private Button redoButton;
    private Block block;
    private boolean canPop1;
    private boolean canPop2;
    private boolean undoEnable;
    private boolean redoEnable;

    public UndoRedo(Button undoButton, Button redoButton)
    {
        this.undoButton = undoButton;
        this.redoButton = redoButton;
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        canPop1 = false;
        canPop2 = true;
        undoEnable = false;
        redoEnable = false;
    }

    public void undoMethod()
    {
        if(!stack1.isEmpty())
        {
            if(canPop1)
            {
                stack1.pop();
            }

            if(!stack1.isEmpty())
            {
                String top = stack1.pop();

                if(!canPop1)
                {
                    stack2.push(block.getTextField().getText());
                    redoEnable = true;
                    redoButton.setDisable(false);
                }

                stack2.push(top);
                block.getTextField().setText(String.valueOf(top));
                canPop1 = true;

                if(stack1.size()==1)
                {
                    stack1.clear();
                    canPop2 = true;
                    undoEnable = false;
                    undoButton.setDisable(true);
                }
            }
        }
    }

    public void redoMethod()
    {
        if(canPop2)
        {
            stack2.pop();
        }

        if(!stack2.isEmpty())
        {
            String top = stack2.pop();
            block.getTextField().setText(String.valueOf(top));
            canPop2 = false;

            undoEnable = true;
            undoButton.setDisable(false);

            if(stack2.size() == 0)
            {
                canPop1 = false;

                redoEnable = false;
                redoButton.setDisable(true);
            }
        }
    }

    public void push(UndoRedoHelper undoRedoHelper)
    {
        if(!undoRedoHelper.getOldValue().equals(""))
        {
            stack1.push(undoRedoHelper.getOldValue());
            undoEnable = true;
            undoButton.setDisable(false);
        }

        block = undoRedoHelper.getBlock();
    }

    public boolean isUndoEnable()
    {
        return undoEnable;
    }

    public boolean isRedoEnable()
    {
        return redoEnable;
    }

}