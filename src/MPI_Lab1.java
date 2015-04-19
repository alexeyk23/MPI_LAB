/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import mpi.*;
/**
 * Каждый процесс обмениваентся со всеми остальными и выводит номера тех,
 * у которых ни одно значение не совпадает с его собственными.
 * @author admin
 */
public class MPI_Lab1 {
    static int N = 3;
    static int m = 5;
    public static void main(String args[]) throws Exception {
        MPI.Init(args);
        int myRank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();       
        // Создание и заполнение массива
        int[] arraySourse = new int[N],
              arrayRes = new int[N];
        for (int i = 0; i < N; i++) {
            arraySourse[i] = (int) (Math.random() * 10) - m;
        }        
        System.out.println(myRank + " send: " + Arrays.toString(arraySourse));
        //каждый отправляет всем
        for (int i = 0; i < size; i++) {
           MPI.COMM_WORLD.Isend(arraySourse, 0, N, MPI.INT, i, 0);                      
        }
        Set<Integer> answer = new HashSet<Integer>();
        for (int i = 0; i < size; i++) {
           Request request =  MPI.COMM_WORLD.Irecv(arrayRes, 0, N, MPI.INT, i, 0);      
           request.Wait(); //ждать, пока не примем что-то         
           if(checkArrays(arraySourse, arrayRes)) 
              answer.add(i);
        }    
        System.out.println("for rank "+myRank+" good: "+answer);
        MPI.Finalize();
    }
    static boolean checkArrays(int[] a1,int[] a2)
    {
        for (int x : a1) {
            for (int y : a2) {
                if(x==y)
                    return  false;
            }
        }
        return true;
    }
}
