/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p51;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author 77203
 */
public class P51 {

    public static ArrayList irreversibleChoiceBySteinsGate(ArrayList goal,ArrayList<Integer> source,int n,int b){
        ArrayList<ArrayList>r=new ArrayList<>();
        ArrayList<Integer>temp=new ArrayList<>();
        if(n==b){
            temp.add(b);
            r.add(temp);
        }else{
            temp.add(n);
            ArrayList<Integer>c=new ArrayList<>();
            r.add(temp);
            for(int i=0;i<source.size();i++){
                c.add(source.get(i));
            }
            c.add(n);
            for(int i=0;i<((ArrayList)goal.get(n)).size();i++){
                if(!c.contains((int)(((ArrayList)(goal.get(n))).get(i)))){
                    r.add(irreversibleChoiceBySteinsGate(goal,c,(int)(((ArrayList)(goal.get(n))).get(i)),b));
                }
            }
        }
        return r;
    }
    public static ArrayList extractionOfTimeParadoxThroughWarmHole(ArrayList a,ArrayList pathStored,int b){
        ArrayList<Integer>r=new ArrayList<>();
        while(!a.isEmpty()){
            try{
                int n=(int)a.get(0);
                pathStored.add(n);
                a.remove((int)0);
            }catch(Exception e){
                try{
                    if((a.size()==1&&(int)((ArrayList)a.get(0)).get(0)==b)||a.size()>1){
                        pathStored=extractionOfTimeParadoxThroughWarmHole((ArrayList)a.get(0),pathStored,b);
                    }
                }catch(Exception d){
                    pathStored=extractionOfTimeParadoxThroughWarmHole((ArrayList)a.get(0),pathStored,b);
                }
                a.remove(0);
            }
        }
        for(int i=0;i<pathStored.size();i++){
            r.add((int)pathStored.get(i));
        }
        return r;
    }
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int n=in.nextInt(),m=in.nextInt(),a=in.nextInt()-1,b=in.nextInt()-1,q=in.nextInt();
        int[]operation=new int[q];
        ArrayList<ArrayList>nodes=new ArrayList<>();
        ArrayList<ArrayList>r=new ArrayList<>();
        int[][]time=new int[n][n];
        for(int i=0;i<n;i++){
            nodes.add(new ArrayList<Integer>());
        }
        for(int i=0;i<m;i++){
            int x=in.nextInt()-1,y=in.nextInt()-1,z=in.nextInt();
            nodes.get(x).add(y);
            nodes.get(y).add(x);
            time[x][y]=z;
            time[y][x]=z;
        }
        for(int i=0;i<q;i++){
            operation[i]=in.nextInt()-1;
        }
        r=irreversibleChoiceBySteinsGate(nodes,new ArrayList<Integer>(),a,b);
        ArrayList<Integer>path=extractionOfTimeParadoxThroughWarmHole(r,new ArrayList<ArrayList>(),b);
        ArrayList<ArrayList>p=new ArrayList<>();
        //mathematicalProofOfDeterminism
        boolean valid=true;
        int index=0;
        while(!path.isEmpty()){
            p.add(new ArrayList<Integer>());
            for(int i=0;i<path.size();i++){
                if(!p.get(p.size()-1).contains(path.get(i))){
                    p.get(p.size()-1).add(path.get(i));
                }else{
                    valid=false;
                    break;
                }
                if(path.get(i)==b){
                    break;
                }else if(path.get(i)!=b&&i==path.size()-1){
                    valid=false;
                }
            }
            if(Collections.frequency(p,p.get(p.size()-1))>1){
                valid=false;
            }
            for(int i=0;i<p.get(p.size()-1).size()-1;i++){
                if(time[(int)(p.get(p.size()-1).get(i))][(int)(p.get(p.size()-1).get(i+1))]==0){
                    valid=false;
                }
            }
            if(valid){
                path.remove(p.get(p.size()-1).size()-1);
                index=p.get(p.size()-1).size()-1;
            }else{
                index--;
                path.remove(index);
                p.remove(p.size()-1);
            }
            valid=true;
            System.out.println(index+" "+path);
        }
        System.out.println(p);
        //myFunction
        ArrayList<Integer>total=new ArrayList<>();
        ArrayList<Integer>mini=new ArrayList<>();
        for(int i=0;i<p.size();i++){
            int t=0,temp=0,min=0;
            for(int j=0;j<p.get(i).size()-1;j++){
                temp=time[(int)p.get(i).get(j)][(int)p.get(i).get(j+1)];
                if(j==0){
                    min=temp;
                }else if(temp<min){
                    min=temp;
                }
                t+=temp;
            }
            if(!total.contains(t)){
                total.add(t);
                mini.add(min);
            }else{
                if(min<mini.get(total.indexOf(t))){
                    mini.set(total.indexOf(t),min);
                }
            }
        }
        ArrayList<Integer>tempList=new ArrayList<>();
        for(int i=0;i<total.size();i++){
            tempList.add(total.get(i));
        }
        Collections.sort(tempList);
        ArrayList<Integer>newmin=new ArrayList<>();
        for(int i=0;i<tempList.size();i++){
            newmin.add(mini.get(total.indexOf(tempList.get(i))));
        }
        for(int i=0;i<q;i++){
            try{
                System.out.println(tempList.get(operation[i])+" "+newmin.get(operation[i]));
            }catch(Exception e){
                System.out.println(-1);
            }
        }
    }
}