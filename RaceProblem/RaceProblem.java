
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
import java.util.*;

public class RaceProblem
{
    static int [] buffer = new int [100];
    static int in = 0, out = 0;
    static int count = 0; 
    
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
            @Override
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
    public static void producer() throws InterruptedException{
        while(true){
            int k1 = (int) (Math.random() * buffer.length / 3) + 1;
            for (int i = 0; i < k1; i++)
                buffer[(in + i) % buffer.length] += 1;
            in = (in + k1) % buffer.length;

            Thread.sleep((int) (Math.random() * 1000));
        }
    
    }
    public static void consumer() throws InterruptedException{
        while(true){
            Thread.sleep((int) (Math.random() + 1000));
            int k2 = (int) (Math.random() * buffer.length / 3) + 1;
            for(int i = 0; i < k2; i++) {
                int data = buffer[(out + i) % buffer.length];
                if(data > 1) {
                    System.out.println("Race condition detected! Consumer too slow");
                    System.exit(1);
                }
                else if(data == 0){
                    System.out.println("Race Condition detected! Procedure too slow");
                    System.exit(1);
                }
                else 
                    buffer[(out + i) % buffer.length] = 0; //clear buffer
    
            }
            out = (out + k2) % buffer.length;
            System.out.println("So far OK " + ++count);
        }
    }
}

