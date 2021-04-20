
import java.io.BufferedReader; 
import java.io.FileOutputStream;
import java.io.FileReader; 
import java.io.IOException; 
import java.io.PrintStream;
/**
 *
 * @author tanmay_porwal
 */
public class risingCity {
    FileOutputStream fout;  //to use the PrintStream
    PrintStream out;  //To write in afile with using print and println methods of this class
    int d=0; //counter used for checking "," in the output.txt
    
    public void Print(Node x) {  //this method is for printing single building
        String s=("("+x.bNum+","+x.execTime+","+x.totalTime+")");
        out.println(s); // to write in the file instantiated in main method
    }
    public int i=0;
    public void Print(int x,int y,Node root){ // For PrintBuilding(0,100)
       
        printTree1(root,x,y); //This function is used for inorder traversing and printing in the file
        if(i>0){
            out.println();
            i=0;
        }
        else if(i==0)
            out.println("(0,0,0)");
        d=0; //reseting the counter
    }
    public void printTree1(Node h,int x,int y){ //inorder traversing Node h is the root in rbt when first called from Print(int x,int y,Node root)
        if(h.bNum==-1)
            return;
        printTree1(h.left,x,y);
        if(h.bNum>=x && h.bNum<=y){ // check on the range given in PrintBuilding(x,y)
            if(d==0){ //This if-else helps to decide whether to write "," in the output.txt
                d++;
                out.print("("+h.bNum+","+h.execTime+","+h.totalTime+")");
                i++;
            }
            else
                out.print(",("+h.bNum+","+h.execTime+","+h.totalTime+")");
        }
        printTree1(h.right,x,y);
        return;
    }
   
	public static void main(String[] args) throws IOException{
            
            risingCity i=new risingCity();  //Object of risingCity classzTo use non static variables and methods in main 
            RBT r =new RBT();   //Object of RBT(red black tree) class
            minHeap m=new minHeap(); //Object of minHeap class
     
            i.fout=new FileOutputStream("output_file.txt"); //declaring fileOutStream object
            i.out=new PrintStream(i.fout);  //declaring PrintStream object
            FileReader fr = new FileReader(args[0]); //declaring FileReader object 
            BufferedReader br = new BufferedReader(fr); //BufferReader object
                
            int globalTime=0; //global time counter
                
            String cmd="",s1=""; //Used for reading input
                
            if((cmd=br.readLine())==null)
                return;
            for(char c:cmd.toCharArray()){ // this is used to convert the format of input line. Example is 0:Insert:Building_Number:Total_time
                if(c=='('||c==',')
                    c=':';
                if(c!=')')
                    s1=s1+c;
                }
            String []data=s1.split(":"); //Splitting the above converted string to usable data
            s1="";
                
            Node currNode=r.tnil; //current node whose execution time is increased
                
            int counter=1; // counter which keeps the 5 day or less check on a single building
            int h=0;
            for(globalTime=0;;globalTime++){//loop which increases the global time with no condition
                    if(globalTime==Integer.parseInt(data[0])){ // condition where we take the input on the given time in input file
                    if(data[1].contains("Insert")){//If input line has insert int it
                        Node x=new Node(Integer.parseInt(data[2]),Integer.parseInt(data[3]));
                        r.bst(x);//insert in red black tree
                        m.insert(x);//insert in min heap
                        if(currNode==r.tnil){
                            h++;
                            currNode=r.searchBnum(m.ls.get(0).bNum,r.root);
                            m.removeMin(); //remove min from minheap
                        }
                    }
                    else if(data[1].contains("Print")){//If input line has print int it
                        currNode.execTime++;
                        if(data.length==4){ //If input line has PrintBuilding with 2 parameters
                            i.Print(Integer.parseInt(data[2]),Integer.parseInt(data[3]),r.root);
 
                        }
                        else if(data.length==3){//If input line has PrintBuilding with 1 parameters
                            Node a=r.tnil;
                            a=r.searchBnum(Integer.parseInt(data[2]),r.root);
                            if(a==r.tnil||a==null)
                                i.out.println("(0,0,0)");
                            else
                                i.Print(a);
                        }
                        currNode.execTime--;
                    }
                   if((cmd=br.readLine())!=null){//reading next line from input 
                    for(char c:cmd.toCharArray()){
                        if(c=='('||c==',')
                            c=':';
                        if(c!=')')
                            s1=s1+c;
                    }
                    data=s1.split(":");
                    s1="";
                    }
                }
                if(globalTime>0){
                    currNode.execTime++;
                    counter++;
                    if(currNode.execTime==currNode.totalTime){ //where execution time gets equal to the total time 
                        counter=1;
                        Node a=r.searchBnum(currNode.bNum,r.root);
                        i.out.println("("+a.bNum+","+globalTime+")");
                        r.rbtDelete(a);
                        if(m.ls.size()>0){
                        a=m.ls.get(0);
                        currNode=a;
                        m.removeMin();
                        }
                    }
                    else if(counter==6){//when the counter for a particular building reaches it limits that is 5
                        counter=1;
                        m.insert(currNode);
                        currNode=m.ls.get(0);
                        m.removeMin();
                    }
                }
                
                
                if(r.root==r.tnil && cmd==null){//exit condition for main
                    //closing file reader and writer
                    i.out.close();
                    i.fout.close();
                    br.close();
                    fr.close();
                    return;
                }
            }
        }   
}