
/**
 * Write a description of class PCBEntry here.
 *
 */
import java.util.*;

public class PCBEntry
{
    protected int parent; // hold the index of parent
    protected ArrayList<Integer> children;
    
    public PCBEntry(int parent){
        this.parent = parent;
        children = new ArrayList<Integer>();
    }
    
    /**
     * @return a String describe who is this process's parent, who are its children
     */
    public String toString(){
        String result = "The parent is: " + parent + "\n";
        if(children.size() == 0 )
            result += "There is no child\n";
        else
            result += "The children are processes: " + children.toString() + "\n";
        return result;
    }
}
