/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
import com.sun.org.apache.bcel.internal.generic.F2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import mpi.*;
public class MPI_Lab2 {
 
    public static void main(String[] args) throws IOException {
        MPI.Init(args);
        int myRank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int[] n = new int[1];        
        double[] a = new double[1],
                b = new double[1],
                eps = new double[1];
        double[] sendbuff_x,readbuff_x;
        if(myRank==0){                       
            n[0] = nextInt();         
            a[0] = nextDouble();        
            b[0] = nextDouble();            
            eps[0] = nextDouble();            
            sendbuff_x = new double[n[0]];
            double h = (b[0]-a[0])/(n[0]-1);
            for (int i = 0; i < n[0]; i++) {
                sendbuff_x[i] = a[0]+i*h;
            }    
        }
        else sendbuff_x = new double[0];
        //отправить всем eps и n 
        MPI.COMM_WORLD.Bcast(eps, 0, 1, MPI.DOUBLE, 0);
        MPI.COMM_WORLD.Bcast(n, 0, 1, MPI.INT, 0);
        int tail = (n[0]%size ==0)?0:1;
        readbuff_x = new double[n[0]/size+tail];
        System.out.println(Arrays.toString(sendbuff_x));
        try {
              MPI.COMM_WORLD.Scatter(sendbuff_x,0,n[0]/size,MPI.DOUBLE, readbuff_x,0,n[0]/size,MPI.DOUBLE,0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println(myRank + " i recv" + Arrays.toString(readbuff_x));
        MPI.Finalize();
    }
    static BufferedReader reader;
    static StringTokenizer tokenizer;
    static Scanner sc= new Scanner(System.in);
    static {
        try {
            reader = new BufferedReader(new FileReader("src/input.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MPI_Lab2.class.getName()).log(Level.SEVERE, null, ex);
        }
        tokenizer = null;
    }
    
    static int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    static double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    static String nextToken() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(reader.readLine());
        }
        return tokenizer.nextToken();
    }
}
