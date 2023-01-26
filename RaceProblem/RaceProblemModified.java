
/**
 * Project Two
 * Race condition with a shared buffer
 * 
 * Descriptive
 * Implement the solution to the bounded buffer problem 
 * from the section titled Semaphores, 
 * but without any P or V operations. 
 * Observe and eliminate a race condition, which refers to a situation 
 * where multiple processes manipulate some shared data concurrently 
 * and the outcome depends on the order of execution.
 * 
 * @author Hyojin Park
 * @version Oct. 23. 2022
 */

import java.util.concurrent.Semaphore;
import java.util.*;

public class RaceProblemModified
{
    static final int BUFFER_SIZE = 10;
    static final int ROUND = 20;
    static int [] buffer = new int [BUFFER_SIZE];
    static int limit = ROUND;
    static int in = 0, out = 0;
    static Semaphore emptyBuffer = new Semaphore(buffer.length);
    static Semaphore occupiedBuffer = new Semaphore(0);
    static int count = 0;
    static boolean done = false;
    
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread (new Runnable() {
            @Override
            public void run()
            {
                try {
                    producer();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread (new Runnable(){
            public void run() {
                try {
                    consumer();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
    
    public static void producer() throws InterruptedException {
        while(!done){
            Random r = new Random();
            int k1 = r.nextInt(BUFFER_SIZE/2)+1;
            for (int i = 0; i < k1-1; i++){
                if(emptyBuffer.availablePermits() > 0){
                
                emptyBuffer.acquire();
                buffer[i] = 1;
                occupiedBuffer.release();
                }
                else
                    break;
            }
           
            //update in
            in = (in + k1) % buffer.length;
            
            System.out.println("So far producer OK " + in);
            //update limit
            limit--;
            
            if (limit <= 0) {
                System.out.println("Producer exits system without any race problem");
                System.exit(0); 
            }
            Thread.sleep((int) (Math.random()*900 + 100));
        }
    }
    
    public static void consumer() throws InterruptedException {
        while(!done){
            Thread.sleep((int) (Math.random()*900 + 100));
            Random r = new Random();
            int k2 = r.nextInt(BUFFER_SIZE/2) + 1;
            int data;
            for(int i = 0; i < k2 -1; i++){
                
                occupiedBuffer.acquire();
                
                data = buffer[i];

                if(data != 1){
                    System.out.println("No value available in the buffer.");
                    emptyBuffer.release();
                    
                }
                
            }
            //update out
            out = (out + k2) % buffer.length;
            
            System.out.println("So far consumer OK " + out);
            //update limit
            limit--;
            
            if(limit <= 0){
                System.out.println("Consumer exits system without any race problem");
                System.exit(0); 
            }
            Thread.sleep((int) (Math.random()*900 + 100));
        }
    }
}
