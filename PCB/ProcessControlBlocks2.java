
/**
 * 12.1 Two versions of the process creation hierarchy
 * Version 2 of the same process creation hierarchy uses no linked lists. Instead, 
   each PCB contains the 4 integer fields parent, first_child, younger_sibling, 
   and older_sibling, as described in the subsection "Avoiding linked lists".
 *
 * Hyojin Park
 * 09-25-2022
 */

public class ProcessControlBlocks2 implements PCBS
{
    // instance variables
    private PCBEntry2[] pcbs;

    public ProcessControlBlocks2(int n) {
        pcbs = new PCBEntry2[n];
        pcbs[0] = new PCBEntry2(-1); // assume process 0 exists
    }
    
    public String create (int p){
        if(pcbs[p] == null)
            return "No such a parent process";
        //find an empty spot for child process
        int count = 1;
        int total = pcbs.length;
        int q = (p + count) % total;
        while (pcbs[q] != null && count < total){
            count++;
            q = (q + 1) % total;
        }
        if(count == total) return "No space for new child process";
        PCBEntry2 newEntry = new PCBEntry2(p);
        pcbs[q] = newEntry;
        
        int index = pcbs[p].child;
        int prevIndex = -1;
        if (index == -1) // the first child
            pcbs[p].child = q; // the newEntry is the first child
        else{//already has children
            while(index != -1){
                prevIndex = index;
                index = pcbs[index].ys;
            }
            // prevIndex is the os of newEntry
            pcbs[q].os = prevIndex;
            pcbs[prevIndex].ys = q;
        }
        return null;    
    }

    public void destroy (int p) {// destroy all descendants of p (not p itself)
        if(p == -1 || pcbs[p] == null) return;
        int index = pcbs[p].child; // index for p's first child
        int current = -1;
        // go through pcbs[p] each child, destroy it
        while(index != -1){// as long as p has more children
            current = index; // current hold the index
            index = pcbs[current].ys; // index for p's nest child
            destroy(current); //recursively destroy all children of current child
            pcbs[current] = null; // set current to be null, i.e. remove cuttent itself
        }
        pcbs[p].child = -1; // p has noo more children
    }
    
    /**
     * @return a String describe all processes in this PCBS
     */
    public String toString()
    {
        String result = "";
        for(int i = 0; i <pcbs.length; i++){
            if(pcbs[i] != null)
                result += "Process " + i + " Information: " + pcbs[i].toString() + "\n";
        }
        return result;
    }
    
    // a main function test out this version
    public static void main(String[] args){
    
        // follow instruction's sample execution
        ProcessControlBlocks2 pcbs = new ProcessControlBlocks2(10);
        
        // create several process
        pcbs.create(0); // create a child for 1 at 1
        pcbs.create(2); // create second child for 0 at 2
        pcbs.create(0); // create first child for 2 at 3
        pcbs.create(2); // create third child process 0 at 4
        pcbs.create(4); // create first child for 4 at 6
        pcbs.create(3); // create first child for 3 at 7
        pcbs.create(6); // create first child for 6 at 8
        
        // print out the current [cbs information to verify the result
        System.out.println(pcbs);
        
        pcbs.destroy(0); // destroy all children (and grand children) from pcb[0]
        System.out.println("After destroy all children of process 0. pcbs information is: ");
        System.out.println(pcbs);
         
    }
}

/**
 * Smaple output
    Process 0 Information: The parent is: -1
    The first child is 1
    There is no younger sibling
    there is no older sibling
    
    Process 1 Information: The parent is: 0
    There is no child
    The younger sibling is 2
    there is no older sibling
    
    Process 2 Information: The parent is: 0
    The first child is 3
    There is no younger sibling
    The old sibling is 1
    
    Process 3 Information: The parent is: 2
    The first child is 4
    There is no younger sibling
    there is no older sibling
    
    Process 4 Information: The parent is: 3
    There is no child
    There is no younger sibling
    there is no older sibling
    
    
    After destroy all children of process 0. pcbs information is: 
    Process 0 Information: The parent is: -1
    There is no child
    There is no younger sibling
    there is no older sibling

 */
