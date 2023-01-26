
/**
 * Write a description of interface PCBS here.
 *
 * 
 */
public interface PCBS

{
    /**
      * create a child process of process p
      * If process p not exist, return eoor message "No such a parent process"
      * If this process control blocks is full, return "No space for new process"
      * Return null if the creation is sucess
      * @param p the process index of parent prcess
      */
    public String create(int p);
    
    /**
     * destroy all children of the process ar location p
     */
    public void destroy(int p);
}

