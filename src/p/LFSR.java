package p;

import java.util.ArrayList;

public class LFSR {

    ArrayList<Integer> xorElements = new ArrayList<>();
    ArrayList<ArrayList<Integer>> flipFlops = new ArrayList<>();
    ArrayList<Integer> output;
    ArrayList<Integer> key;

    public LFSR(Integer[] p, int length, int x, ArrayList<Integer> initial) {
        Integer result = 0;
        output= new ArrayList<>();
        for(int i =0; i<initial.size(); i++)
        {
            ArrayList<Integer> in = new ArrayList<>();
            in.add(initial.get(i));
            flipFlops.add(in);
        }
        output.add(flipFlops.get(0).get(0)); //for the initial state

        //specify the P equation
        for(int i =0; i< p.length; i++)
        {
            if(p[i] == 1)
                xorElements.add(i);
        }


        //specify the FF to be XORed
        ArrayList<Integer> xoredFFs;
        int numOfRows = length+x - 1;
        for(int i =0; i< numOfRows; i++) //loop for plaintext size+x for TT rows
        {
            xoredFFs = new ArrayList<>();
            for (int ii = 0; ii < xorElements.size(); ii++) {
                int FF = xorElements.get(ii);
                int lastIndex = flipFlops.get(FF).size() - 1;
                xoredFFs.add(flipFlops.get(FF).get(lastIndex));    //to get last value of each flipflop to xor
            }

            for (int k = 1; k <= 9; k++) {
                result = XORGate(xoredFFs);
                //System.out.println("result: "+result);
                int last = flipFlops.get(9 - k).size()-1;
                if(k ==1)
                    flipFlops.get(9 - k).add(result);
                else {

                    flipFlops.get(9 - k).add(flipFlops.get(9 - k + 1).get(last));
                }
            }

            Integer temp = flipFlops.get(0).get(flipFlops.get(0).size()-1);
            output.add(temp);
        }

        System.out.println("X bits: ");
        for(int i =0; i < x ; i++)
            System.out.print(output.get(i));
        System.out.println(" ");

        key = new ArrayList<>();
        for(int i = x; i<output.size(); i++)
            key.add(output.get(i));


        printTT();

    }

    //XORing for more than two numbers
    public int XORGate(ArrayList<Integer> array)
    {
        int sum = 0;
        for(int i =0; i<array.size(); i++)
        {
            sum+=array.get(i);
        }

        if(sum%2 == 0)  //even number of 1s therefore
            return 0;
        else        //odd number of 1s
            return 1;
    }



    void printTT()
    {
        System.out.println(" ");
        for(int i =0; i<flipFlops.size(); i++)
        {
            System.out.print("FF"+i+": ");
            for(int j =0; j<flipFlops.get(i).size(); j++)
            {
                //System.out.print( "- size: "+j +" -");
                System.out.print(" "+flipFlops.get(i).get(j));
            }
            System.out.println(" ");

        }
        System.out.println(" ");

    }

    public ArrayList<Integer> getKey() {
        return key;
    }
}
