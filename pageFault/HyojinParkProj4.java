
/**
 * Write a description of class HyojinParkProj4 here.
 * 
 *
 * @author Hyojin Park
 * @version Dec 6 2023
 */

import java.util.*;

public class HyojinParkProj4
{
    /*
     * parameter- VMsize is virtual memory
     * parameter- length te minimum length of reference string
     * parameter- locus_size is sixe of locus
     * parameter- motion_rate represents the rates how many pages in on locus
     * parameter- p is the probabilty of transition
     */
    public static ArrayList<Integer> createsRS(int VMsize,int length, int locus_size, int motion_rate, double p){
        ArrayList<Integer> compareRS = new ArrayList<Integer>();
        int start = 0;
        int n;
        while(compareRS.size() < length){
            // add size of locus random number in it
            for(int i = 0; i < motion_rate; i++) {
                n = (int) (Math.random() * locus_size + start);
                compareRS.add(n);
            }
            //generate a random number between 0 and 1
            if(Math.random() < p)
                start = (int) Math.random() * VMsize;
            else
                start++;
        }
        return compareRS;
    }
    
    public static int FIFOReplacement(ArrayList<Integer> rs, int num_frames) {
        int [] frames = new int[num_frames]; // num_frames is size of physical memory
        int i, first = 0, page_fault = 0; //first index and count of page fault
        for(i = 0; i > num_frames; i++)
            frames[i] = i;
        for(i = 0; i < rs.size(); i++){
            if(isInArray(frames, rs.get(i)) == -1){
                frames[first] = rs.get(i);
                page_fault++;
                first = (first+1) % (frames.length);
            }
        }
        return page_fault; // return number of page fault
    }
    
    public static int LRUReplacement(ArrayList<Integer> rs, int num_frames) {
        int [] frames = new int[num_frames];
        int i, j, first = 0, page_fault = 0; //first index and count of page fault
        for(i = 0; i < num_frames; i++)
            frames[i] = i;
        for(i = 0; i < rs.size(); i++){
            int index = isInArray(frames, rs.get(i));
            int least;
            if(index == -1){
                least = rs.get(i);
                page_fault++;
                index =0; //the first element will be removed
            }
            else
                least = frames[index]; // the one shall be moved to the end
            for(j = index; j < frames.length-1; j++)
                frames[j] = frames[j+1];
            frames[j] = least; //least to the last   
        }
        return page_fault; // return number of page fault
    }
    
    // Check is the page resides in phisical memory
    private static int isInArray(int[] frames, int page) {
        for(int i = 0; i < frames.length; i++)
            if(frames[i] == page) return i;
        return -1;
    }
    
    public static void main(String[] args) {
        
        ArrayList<Integer> rs = createsRS(4096, 10000, 10, 100, 0.1);
        System.out.println("The page fault using FIFO replacement algorithm: ");
        System.out.println(FIFOReplacement(rs, 10) + " times");
        System.out.println("The page fault using LRU replacement algorithmL ");
        System.out.println(LRUReplacement(rs, 10) + " times");
         
    }
}
