package p;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

import java.util.Scanner;

public class DeCipher {

    String filePath = "one.txt";

    private Integer[] p;
    private ArrayList<Integer> initial = new ArrayList<>();
    private String x = "";
    private String plainText = "";
    private String binaryOriginal = "";

    private ArrayList<Integer> key = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> deciphered = new ArrayList<>();

    DeCipher() throws IOException {
        System.out.println("enter your cipher");
        Scanner sc = new Scanner(System.in);
        binaryOriginal = sc.nextLine();
        readFromFile();
        LFSR lfsr = new LFSR(p, binaryOriginal.length(), Integer.parseInt(x), initial);
        key = lfsr.getKey();
        decrypt();


    }

    void decrypt(){

        int counter = 1;
        ArrayList<Integer> results = new ArrayList<>();

        for (int i = 0; i < binaryOriginal.length(); i++) {
            for (int j = 0; j < key.size(); j++, i++, counter++) {
                if (i >= binaryOriginal.length())
                    break;
                Character temp = binaryOriginal.charAt(i);
                int result = XORGateTwo(Integer.parseInt(temp.toString()), key.get(j));
                //System.out.println("result: "+result);
                if(counter < 8){
                    results.add(result);
                }
                else {
                    results.add(result);
                    counter = 0;
                    deciphered.add(results);
                    results = new ArrayList<>();
                }


            }

        }


        String output = "";
        for(int z = 0; z < deciphered.size(); z++)
        {
            Character temp = (char)binaryToASCII(deciphered.get(z));
            output = output.concat(temp.toString());
        }
        System.out.println("\nDeCiphered: \n" + output);
    }

    int binaryToASCII(ArrayList<Integer> binary)
    {
        int result = 0;
        for(int i =0; i<binary.size(); i++)
        {
            if(binary.get(i) == 1)
            {
                result += Math.pow(2,(binary.size() - 1 - i));
            }
        }
        return result;
    }


    void readFromFile() throws IOException {
        InputStream is = new FileInputStream(filePath);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        String line = buffer.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null) {
            sb.append(line).append("\n");
            line = buffer.readLine();
        }

        int index = 0;

        Character temp =' ';
        while(sb.charAt(index) != '|')
        {
            temp = sb.charAt(index);
            x = x.concat(temp.toString());
            index++;
        }
        index++; // to skip the delimeter

        int j = 0;
        p = new Integer[9];
        while(sb.charAt(index) != '|')
        {
            p[j] = Character.getNumericValue(sb.charAt(index));
            j++;
            index++;
        }
        index++;    //to skip the delimeter

        j=0;
        while(sb.charAt(index) != '|')
        {
            initial.add(Character.getNumericValue(sb.charAt(index)));
            j++;
            index++;
        }

        //to skip the delimeter
    }

    //XORing for only two numbers
    public int XORGateTwo(int x1, int x2)
    {
        if(x1 == x2)
            return 0;
        else
            return 1;
    }



}



