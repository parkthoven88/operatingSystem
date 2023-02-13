import java.util.Scanner;

/**
 * Write a description of class ClaimGraph here.
 *
 * @author Hyojin Park
 *
 */
public class ClaimGraph
{
    protected int num_process; // how many process
    protected int [] resource; // how many recources available
    protected int [][] max; //maximum request each process for each resource
    protected int [][] cur_allocation; // current allocation
    
    public ClaimGraph(){}

    public ClaimGraph(int num_process, int[] resource, int [][] max, int [][] cur_allocation)
    {
        this.num_process = num_process;
        this.resource = resource;
        this.max = max;
        this.cur_allocation = cur_allocation;
    }
    
    /**
     * process p request resource r amount many
     * parameter p process index
     * parameter r resource index
     * parameter amount request resource amount
     * return true if the request can be granted and state updated
     */
    public boolean request(int p, int r, int amount){
        if(resource[r] < amount)
            return false; // available less than requests
        if(max[p][r]-cur_allocation[p][r] < amount) //if request more than neede, also reject
            return false;                           
        resource[r] -= amount; // update available resource
        cur_allocation[p][r] += amount; //update cur_allocation
        return true;
    }
    /**
     * process p release resource r amount many
     * parameter p process index
     * parameter r resource index
     * parameter amount request resource amount
     * return true if the request can be granted and state updated
     */
    public void relaease(int p, int r, int amount){
        resource[r] += amount; //update available resource
        cur_allocation[p][r] -= amount; //update current allocation
    }
    
    //isDeadLocked
    public boolean isDeadLock(){
        // if give all resource to one process, it cannot finish
        // then the system is deadlocked
        int count = 0; 
        boolean detected;
        for(int i = 0; i < num_process; i++){
            detected = false;
            for(int j = 0; !detected && j < resource.length; j++){
                if(max[i][j]-cur_allocation[i][j] > resource[j])
                    detected = true;
            }
            if(detected)
                count++;
        }
        return count == num_process;
    }
    /**
     * initialize the state
     * user will be prompt to enter informarion regarding
     * number if process, resource. maximum request and allocation matrix
     */
    public static void initializeState(ClaimGraph cg){
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter number of processes: ");
        int p = input.nextInt();
        System.out.println("Enter the number of resources: ");
        int r = input.nextInt();
        int[] resource = new int[r];
        System.out.println("Enter number of each resource, seperated by white space: ");
        int i, j;
        for(i = 0; i < r; i++)
            resource[i] = input.nextInt();
        System.out.println("Enter claim matrix: ");
        
        int[][] claimMatrix = new int[p][r];
        for(i = 0; i < p; i++){
            System.out.println("Enter maximum claim of process " + i + " for each resourse");
            System.out.println("Seperated by white space: ");
            for(j = 0; j < r; j++)
                claimMatrix[i][j] = input.nextInt();
            }
            
         cg.num_process = p;
         cg.resource = resource;
         cg.max = claimMatrix;
         cg.cur_allocation = new int[p][r]; // everything is 0
    }
    /*
     * print out claim matrix, allocation matrix, and available resource
     */
    public String toString(){
        String result = "Claim Matrix: \n";
        int i, j;
        for(i = 0; i < num_process; i++){
            for(j = 0; j < resource.length; j++)
                result = result + "\t" + max[i][j];
             result += "\n";   
        }
        result += "\nAllocation Matrix: \n";
        for(i = 0; i < num_process; i++){
            for(j = 0; j < resource.length; j++)
                result = result + "\t" + cur_allocation[i][j];
            result += "\n";
        }
        result += "\nAvailable Resources: \n";
        for(i = 0; i < resource.length; i++)
            result += "\t" + resource[i];
        return result;
    }
    public static void main(String [] args){
        Scanner input = new Scanner(System.in);
        ClaimGraph cg = new ClaimGraph();
        initializeState(cg);
        System.out.println("System initial state is: \n" + cg);
        while(!cg.isDeadLock()){
            System.out.println("Enter your next command in form request(i, j, k) or release(i, j, k): ");
            String command[] = input.nextLine().split("\\(|,|\\)");
            int i = Integer.parseInt(command[1].trim());
            int j = Integer.parseInt(command[2].trim());
            int k = Integer.parseInt(command[3].trim());
            if(command[0].trim().equals("request")){
                if(!cg.request(i, j, k))
                    System.out.println("The request rejected");
                else
                    System.out.println("The request granted");
            }
            else if(command[0].trim().equals("release")){
                cg.relaease(i, j, k);
            }
            else{
                System.out.println("Wrong command format");
            }
            System.out.println("System current state is: \n" + cg);
        }
        System.out.println("Now the system is deadlocked");
    }
}

/*

 * Sample Run
 
 Enter number of processes: 
4
Enter the number of resources: 
3
Enter number of each resource, seperated by white space: 
9 3 6
Enter claim matrix: 
Enter maximum claim of process 0 for each resourse
Seperated by white space: 
3 2 2
Enter maximum claim of process 1 for each resourse
Seperated by white space: 
6 1 3
Enter maximum claim of process 2 for each resourse
Seperated by white space: 
3 1 4
Enter maximum claim of process 3 for each resourse
Seperated by white space: 
4 2 2
System initial state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	0	0	0
	0	0	0
	0	0	0
	0	0	0

Available Resources: 
	9	3	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
request(0, 1, 1)
The request granted
Systme. current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	0	1	0
	0	0	0
	0	0	0
	0	0	0

Available Resources: 
	9	2	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
request(1, 1, 1)
The request granted
Systme. current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	0	1	0
	0	1	0
	0	0	0
	0	0	0

Available Resources: 
	9	1	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
request(3, 1, 1)
The request granted
Systme. current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	0	1	0
	0	1	0
	0	0	0
	0	1	0

Available Resources: 
	9	0	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
request(2, 0, 3)
The request granted
Systme. current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	0	1	0
	0	1	0
	3	0	0
	0	1	0

Available Resources: 
	6	0	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
release(0, 1, 1)
Systme. current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	0	0	0
	0	1	0
	3	0	0
	0	1	0

Available Resources: 
	6	1	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
request(0, 0, 3)
The request granted
Systme. current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	3	0	0
	0	1	0
	3	0	0
	0	1	0

Available Resources: 
	3	1	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
request(1, 0, 2)
The request granted
Systme. current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	3	0	0
	2	1	0
	3	0	0
	0	1	0

Available Resources: 
	1	1	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
request(0, 1, 2)
The request rejected
System current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	3	0	0
	2	1	0
	3	0	0
	0	1	0

Available Resources: 
	1	1	6
Enter your next command in form request(i, j, k) or release(i, j, k): 
request(0, 1, 1)
The request granted
Systme. current state is: 
Claim Matrix: 
	3	2	2
	6	1	3
	3	1	4
	4	2	2

Allocation Matrix: 
	3	1	0
	2	1	0
	3	0	0
	0	1	0

Available Resources: 
	1	0	6
Now the system is deadlocked
 */
