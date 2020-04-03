package p;

import java.io.*;
import java.util.ArrayList;

public class Cipher {

    String filePath = "one.txt";

    private Integer[] p;
    private ArrayList<Integer> initial = new ArrayList<>();
    private String x="";
    private String plainText="";
    private String binaryOriginal="";
    private ArrayList<Integer> key = new ArrayList<>();
    private ArrayList<Integer> ciphered = new ArrayList<>();

    Cipher() throws IOException {

        Integer[] FFs = new Integer[9];
        readFromFile();
        convertToBinary();
        LFSR lfsr = new LFSR(p, binaryOriginal.length(), Integer.parseInt(x), initial);
        key = lfsr.getKey();
        encrypt();
        writeToFile();

    }

    void encrypt()
    {
        for(int i = 0; i < binaryOriginal.length(); i++)
        {
            for(int j = 0; j<key.size(); j++,i++)
            {
                if(i >= binaryOriginal.length())
                    break;
                Character temp = binaryOriginal.charAt(i);
                int result = XORGateTwo(Integer.parseInt(temp.toString()),key.get(j));
                ciphered.add(result);

            }
        }

        System.out.println("\nCiphered: ");
        for(int j = 0; j<ciphered.size(); j++)
        {
            System.out.print(ciphered.get(j));
        }
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

        index++;    //to skip the delimeter

        temp = ' ';
        for(index = index; index<sb.length(); index++)
        {
            temp = sb.charAt(index);
            plainText = plainText.concat(temp.toString());
        }
        System.out.println(plainText);

    }

    //XORing for only two numbers
    public int XORGateTwo(int x1, int x2)
    {
        if(x1 == x2)
            return 0;
        else
            return 1;
    }


    void convertToBinary()
    {
        int length = plainText.length();

        for (int i = 0; i < length; i++)
        {
            // convert each char to
            // ASCII value
            int value = Integer.valueOf(plainText.charAt(i));

            // Convert ASCII value to binary
            String bin = "";
            while (value > 0)
            {
                if (value % 2 == 1)
                {
                    bin += '1';
                }
                else
                    bin += '0';
                value /= 2;
            }
            bin = reverse(bin);
            if(bin.length() < 8)
            {
                while(bin.length()<8)
                    bin = "0" + bin;
            }
            //System.out.print(bin + " ");
            binaryOriginal = binaryOriginal.concat(bin);
        }
    }

    static String reverse(String input)
    {
        char[] a = input.toCharArray();
        int left, right = 0;
        right = a.length - 1;

        for (left = 0; left < right; left++, right--)
        {
            // Swap values of left and right
            char temp = a[left];
            a[left] = a[right];
            a[right] = temp;
        }
        return String.valueOf(a);
    }


    void writeToFile() throws IOException {
        String fileName = "Ciphered.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
       writer.write(x);
       writer.append('|');
        writer.append(initial.toString());
        writer.append('|');
        writer.append(ciphered.toString());
        writer.close();
    }
}
