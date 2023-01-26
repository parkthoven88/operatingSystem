
/**
 * 12.1 Two versions of the process creation hierarchy
 * Version 1 of the process creation hierarchy uses linked lists to keep track of child processes 
   as described in section "The process control block", subsection "The PCB data structure".
 *
 * Hyojin Park
 * 09-25-2022
 */

public class ProcessControlBlocks implements PCBS
{
    // instance variables - replace the example below with your own
    private PCBEntry[] pcbs;

    public ProcessControlBlocks(int n) {
        pcbs = new PCBEntry[n];
        pcbs[0] = new PCBEntry(-1); // assume process 0 exists
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
        // create child process and add to p's children list
        PCBEntry child = new PCBEntry(p);
        pcbs[q] = child;
        pcbs[p].children.add(q);
        return null;
    }

    public void destroy (int p) {
        if(pcbs[p] == null) return;
        // go through pcbs[p] child, destroy it
        while (!pcbs[p].children.isEmpty()){
            //remove q from its parent's children list
            int q = pcbs[p].children.remove(0);
            destroy(q);
            //deallocate
            pcbs[q] = null;
        }
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
        ProcessControlBlocks pcbs = new ProcessControlBlocks(10);
        
        // create several process
        pcbs.create(0); // create first process of pcbs[0], it will be stores in pcbs[1]
        pcbs.create(0); // create second process of pcbs[0], it will be stores in pcbs[2]
        pcbs.create(2); // create first child of pcbs[2], it will be stored in pcbs[3]
        pcbs.create(0); // create third child process of pcbs[0], it will be stored in pcbs[4]
         
        // print out the current [cbs information to verify the result
        System.out.println(pcbs);
        
        pcbs.destroy(0); // destroy all children (and grand children) from pcb[0]
        System.out.println("After destroy all children of process 0. pcbs information is: ");
        System.out.println(pcbs);
         
    }
}

/**
 * Sample output
 * 
    Process 0 Information: The parent is: -1
    The children are processes: [1, 2, 4]
    
    Process 1 Information: The parent is: 0
    There is no child
    
    Process 2 Information: The parent is: 0
    The children are processes: [3]
    
    Process 3 Information: The parent is: 2
    There is no child
    
    Process 4 Information: The parent is: 0
    There is no child
    
    
    After destroy all children of process 0. pcbs information is: 
    Process 0 Information: The parent is: -1
    There is no child

 */