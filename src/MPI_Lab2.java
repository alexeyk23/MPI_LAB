/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import mpi.*;
public class MPI_Lab2 {
 
    public static void main(String[] args) throws IOException {
        MPI.Init(args);
        int myRank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int n;
        double a ,b,eps;
        double[] buff_x;
        if(myRank==0){
            System.out.println("input n");
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
            n = nextInt();
            System.out.println("input A");
            a = nextDouble();
            System.out.println("input B");
            b = nextDouble();
            System.out.println("input eps");
            eps = nextDouble();
            buff_x = new double[n];
            double h = (b-a)/(n-1);
            for (int i = 0; i < n; i++) {
                buff_x[i] = a+i*h;
            }
        }
    }
    static BufferedReader reader;
    static StringTokenizer tokenizer;

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
