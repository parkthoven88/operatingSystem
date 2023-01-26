
/**
 * 12.1 Two versions of the process creation hierarchy
 * Run the test program repeatedly in a long loop using both versions 
   and compare the running times to see how much time is saved in version 2, 
   which avoids dynamic memory management.
 *
 * Hyojin Park
 * 09-25-2022
 */
public class Test
{
    public static long test(int n, int version){
         
        PCBS pcbs;
        if(version == 1)
            pcbs = new ProcessControlBlocks(100);
        else
            pcbs = new ProcessControlBlocks2(100);
            
        long start = System.currentTimeMillis();
        for(int i = 0; i < n; i++){//repeat this many times
            pcbs.create(0); // child process at location 1
            pcbs.create(0); // child process at location 1
            pcbs.create(2); // child process at location 1
            pcbs.create(3); // child process at location 1
            pcbs.create(0); // child process at location 1
            
            pcbs.destroy(2); // destroy all child process
            pcbs.destroy(1);
            pcbs.destroy(5);
        }
        long end = System.currentTimeMillis();
        return end-start;
        }
        
    public static void main(String [] args){
        //easy to mof=dify it to allow user to input how mant time to do the loop
        // here I used 1000000 times
        long t1 = test(1000000, 1); //version 1 time
        long t2 = test(1000000, 2); //version 2 time
        long difference = t1 - t2;
        
        System.out.println("Version 1 used " + t1 + " milliseconds");
        System.out.println("Version 2 used " + t2 + " milliseconds");
        if (difference > 0)
            System.out.println("Version 2 is " + difference + " milliseconds faster\n");
        else 
            System.out.println("Version 1 is " + Math.abs(difference) + " milliseconds faster\n");
    }    
}

/**
 * Sample output
   // If I used 10000 times
    Version 1 used 55 milliseconds
    Version 2 used 29 milliseconds
    Version 2 is 26 milliseconds faster
   
   // If I used 10000000 times
    Version 1 used 23167 milliseconds
    Version 2 used 22484 milliseconds
    Version 2 is 683 milliseconds faster
   
   //if I used 1000000 times
    Version 1 used 2329 milliseconds
    Version 2 used 2339 milliseconds
    Version 1 is 10 milliseconds faster
 */
