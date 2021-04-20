import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author tanmay_porwal
 */
public class minHeap {
    RBT r1=new RBT(); 
    public List<Node> ls=new ArrayList<>(); //Array list which contains the node and used as a main data structure to implement min heap
    
    public void insert(Node newNode){//insert into min heap
        if(!ls.contains(newNode)){
            ls.add(newNode);
            checkHeap(ls);//check for any violation of properties of min heap
        }
        return;
    }
    
    public void checkHeap(List heap){
        int x = ls.size()-1;
        int p=x;
        while(p>0){
            if(x%2==0)
                p=(x-2)/2;
            else
                p=(x-1)/2;
            if(((Node)ls.get(p)).execTime>((Node)ls.get(x)).execTime){
                swap(p,x);
            }
            if(((Node)ls.get(p)).execTime==((Node)ls.get(x)).execTime){
                if(((Node)ls.get(p)).bNum>((Node)ls.get(x)).bNum)//this conditons help to maintain the min heap structure when two nodes that have same execution time
                    swap(p,x);
            }
            x=p;
        }
        return;
    }
    public void swap(int p,int c){//used to swap two nodes in arrayList
        Node temp=ls.get(p);
        ls.set(p,ls.get(c));
        ls.set(c,temp);
        return;
    }
    
      
    public void removeMin(){//remove min from min heap
        if(ls.size()>0){
        swap(0,ls.size()-1);
       // System.out.println(ls+":"+"yaha dikkt hori h" +":"+ls.get(ls.size()-1).bNum+":"+ls.get(0).bNum);
            ls.remove(ls.size()-1);
            
         if(ls.size()>0)   
                checkHeapadv(0);
        }
    }

    public void checkHeapadv(int p){//check the heap after remove min and corrects the min heap if neccessary
        int c1=2*p+1;
        int c2=2*p+2;
        if(c1<ls.size() && c2<ls.size()){
            int x=(Math.min(ls.get(c2).execTime,ls.get(c1).execTime));
            if(x==ls.get(c1).execTime){
                x=c1;
            }
            else
                x=c2;
            if(ls.get(c2).execTime==ls.get(c1).execTime){
                if(ls.get(c2).bNum>ls.get(c1).bNum){
                    x=c1;
                }
                else
                    x=c2;
            }
            if(ls.get(p).execTime>ls.get(x).execTime){
                    swap(p,x);
                    checkHeapadv(x);
                }
            else if(ls.get(p).execTime==ls.get(x).execTime){
                if(ls.get(p).bNum>ls.get(x).bNum){
                    swap(p,x);
                    checkHeapadv(x);
                }
            }
            else{
                return;
            }
        }
        else if(c1>=ls.size() && c2>=ls.size())
            return;
        else if(c2>=ls.size()){
            if(ls.get(p).execTime>ls.get(c1).execTime){
                    swap(p,c1);
                    checkHeapadv(c1);
                }
            else if(ls.get(p).execTime==ls.get(c1).execTime){
                if(ls.get(p).bNum>ls.get(c1).bNum){
                    swap(p,c1);
                    checkHeapadv(c1);
                }
            }
            else{
                return;
            }
        }
    }
}