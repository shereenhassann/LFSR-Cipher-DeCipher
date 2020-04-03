package p;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("What do you want to do \n 1-Cipher \n 2-Decipher");
        Scanner sc = new Scanner(System.in);
        int ch= sc.nextInt();

        if (ch == 1) {
            Cipher cipher = new Cipher();
        }
        else if (ch==2) {
            DeCipher des = new DeCipher();
        }


    }
}
