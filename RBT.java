/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;

class Node {//a class node that defines the structure of a node which would be used for rbt 
    int bNum,execTime,totalTime;
    char colour;
    Node left, right,parent; 
    public static Node nil = new Node(-1,0);
  
     Node(int bnum,int total) //Constructor used to create a node with building_number and total_time as parameters  
    { 
        bNum = bnum;
        execTime=0;
        totalTime=total;
        left = right = nil;
        colour='R';
    }
}


public class RBT {//class that handles all operations on red black tree
    public Node tnil=Node.nil;//used like external node when needed 
    Node root=tnil;
    public void bst(Node h){//insert a node in red black tree
        if(root == tnil){// if first node make it root
            root=h;
            root.parent=tnil;
            root.colour='B';
        }
        else{
            insertbst(h,root);//insert maintaining the property of a binary search tree
          if(h.parent.colour=='R'){
               checkRbt(h);//check for violation and to correct it
            }
        }
    
    }
    
    public void insertbst(Node newNode,Node current){// insert the node according to the properties of a binary search tree
        if(newNode.bNum>current.bNum){
            if(current.right==tnil){
                current.right=newNode;
                newNode.parent=current;
                return;
            }
            else
                insertbst(newNode,current.right);
        }
        if(newNode.bNum<current.bNum){
            if(current.left==tnil){
                current.left=newNode;
                newNode.parent=current;
                return;
            }
            else
                insertbst(newNode,current.left);
        }
        return;
    }
    
    public Node searchBnum(int bNum,Node root){// search for a node in red black tree
        if(root==tnil || bNum==root.bNum)
            return root;
        if(bNum<root.bNum)
            return searchBnum(bNum,root.left);
        else
            return searchBnum(bNum,root.right);
    }
    
    public Node minimum(Node x){//used to find the successor of a node
        while(x.left!=tnil)
            x=x.left;
        return x;
    
    }
        
    public void rotationR(Node gp,Node p){//right rotation in rbt
        if(gp.parent!=tnil){
            if(gp.parent.left==gp)
                gp.parent.left=p;
            else
                gp.parent.right=p;
        }
        else
            root=p;
        p.parent=gp.parent;
        gp.parent=p;
        gp.left=p.right;
        p.right=gp;
        if(gp.left!=tnil)
            gp.left.parent=gp;
    }
    
    public void rotationL(Node gp,Node p){//left rotation in rbt
        if(gp.parent!=tnil){
            if(gp.parent.left==gp)
                gp.parent.left=p;
            else
                gp.parent.right=p;
        }
        else
            root=p;
        p.parent=gp.parent;
        gp.parent=p;
        gp.right=p.left;
        p.left=gp;
        if(gp.right!=tnil)
            gp.right.parent=gp;
        
    }
    
    public void checkRbt(Node c){//corrects the rbt if violation occurs
        if(c==root){
            return;
                    }
        Node p=c.parent;
        Node gp = tnil;
        Node u = tnil;
        if(p.parent==tnil){
            root=p;
            p.colour='B';
            return;
        }
            gp=p.parent;
        
            if(gp.left==p)
                u=gp.right;
            else
                u=gp.left;
        
        
            if(u==tnil){
                if(gp.left==p){
                    if(p.left==c){
                        p.colour='B';
                        gp.colour='R';
                        rotationR(gp,p);
                    }
                    else{
                        gp.colour='R';
                        c.colour='B';
                        rotationL(p,c);
                        rotationR(gp,c);
                    }
                }
                if(gp.right==p){
                    if(p.right==c){
                        p.colour='B';
                        gp.colour='R';
                        rotationL(gp,p);
                    }
                    else{
                        gp.colour='R';
                        c.colour='B';
                        rotationR(p,c);
                        rotationL(gp,c);
                    }
                }
        }
        else{
            if(u.colour=='R'){
                rbtXYr(gp,p,u);
            }
            else{
                if(gp.left==p){
                    if(p.left==c){
                        p.colour='B';
                        gp.colour='R';
                        rotationR(gp,p);
                    }
                    else{
                        gp.colour='R';
                        c.colour='B';
                        rotationL(p,c);
                        rotationR(gp,c);
                    }
                }
                if(gp.right==p){
                    if(p.right==c){
                        p.colour='B';
                        gp.colour='R';
                        rotationL(gp,p);
                    }
                    else{
                        gp.colour='R';
                        c.colour='B';
                        rotationR(p,c);
                        rotationL(gp,c);
                    }
                }
                }
        }
    }
    
    public void rbtXYr(Node gp,Node p,Node u){//handles the XYr case
        p.colour='B';
        u.colour='B';
        if(gp.parent!=tnil){
            gp.colour='R';
            if(gp.parent.colour=='R')
                checkRbt(gp);
        }
    }
    
    public void rbtTransplant(Node u,Node v){//used to transplant while delete
        if(u.parent==tnil)
            root=v;
        else if(u==u.parent.left)
            u.parent.left=v;
        else
            u.parent.right=v;
        v.parent=u.parent;
    }
    
    public void rbtDelete(Node z){//used to delete a node from rbt *reference from CLRS*
        Node y=z;
        char a=y.colour;
        Node x=tnil;
        if(z.left==tnil){
            x=z.right;
            rbtTransplant(z,z.right);
        }
        else if(z.right==tnil){
            x=z.left;
            rbtTransplant(z,z.left);
        }
        else{ 
            y=minimum(z.right);
            a=y.colour;
            x=y.right;
            if(y.parent==z)
                x.parent=y;
            else{
                rbtTransplant(y,y.right);
                y.right=z.right;
                y.right.parent=y;
            }
            rbtTransplant(z,y);
            y.left=z.left;
            y.left.parent=y;
            y.colour=z.colour;
        }
        if(a=='B')
            rbtcheckDelete(x);
        
    }
    
    public void rbtcheckDelete(Node x){//checks any violation afterv deleting a node
        while(x!=root && x.colour=='b')
            if(x==x.parent.left){
                Node w=x.parent.right;
                if(w.colour=='R'){
                    w.colour='B';
                    x.parent.colour='R';
                    rotationL(x.parent.parent,x.parent);
                    w=x.parent.right;
                }
                if(w.left.colour=='B' && w.right.colour=='B'){
                    w.colour='R';
                    x=x.parent;
                }
                else if(w.right.colour=='B'){
                    w.left.colour='B';
                    w.colour='R';
                    rotationR(w.parent,w);
                    w=x.parent.right;
                }
                w.colour=x.parent.colour;
                x.parent.colour='B';
                w.right.colour='B';
                rotationL(x.parent.parent,x.parent);
                x=root; 
            }
            else{
                Node w=x.parent.left;
                if(w.colour=='R'){
                    w.colour='B';
                    x.parent.colour='R';
                    rotationL(x.parent.parent,x.parent);
                    w=x.parent.left;
                }
                if(w.right.colour=='B' && w.left.colour=='B'){
                    w.colour='R';
                    x=x.parent;
                }
                else if(w.left.colour=='B'){
                    w.right.colour='B';
                    w.colour='R';
                    rotationR(w.parent,w);
                    w=x.parent.left;
                }
                w.colour=x.parent.colour;
                x.parent.colour='B';
                w.left.colour='B';
                rotationL(x.parent.parent,x.parent);
                x=root; 
            }
        x.colour='B';
    }
    
}

