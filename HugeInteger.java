/* 

    Muhammad Khan | 400198190 | khanm244
    COMPENG 2SI4 : Lab Two

*/ 


import java.security.InvalidParameterException;
import java.util.Random;

class HugeInteger{

    // Global Variables 
    int[] number = new int[0];
    int length = 0;
    boolean pos = true;

    // Constructors       
    public HugeInteger(int n){
        // Generates a random HugeInteger with a n digits. 

        // Catching exceptions
        if (n < 0){
            throw new InvalidParameterException("The input should only contain a positive number.");
        }

        if (n == 0){
            number = new int[0];
            length = 0;
        }

        else{
            // Setting up Random class
            Random random = new Random();

            // Creating the number list
            number = new int[n];
            length = n;

            // Setting the first digit
            number[0] = random.nextInt(9) + 1; // The '+1' makes sure 0 is not set as the first number

            // Filling in the rest of the list
            for (int i = 1; i < length; i++){   // Looping through the rest of the indexes 
                number[i] = random.nextInt(10);  // Assigning to an integer between 0 and 9 (inclusive)
            }
        }
        
    }

    public HugeInteger(String val){

        /*
        // Checking if input is valid
        if (val.matches("[0-9-]+") == false || val == "-"){
            throw new InvalidParameterException("Invalid Parameter.");
        }
        */
        // Coverting the String to a String array
        String [] strs = val.split("");

        // Checking if the number is negative
        if (strs[0].equals("-")){
            length = val.length() - 1;
            pos = false;
        }
        else{
            length = val.length();
        }

        // Updating the number array to the correct size
        number = new int[length];

        // Populating the number list
        int numIndex = 0;
        for (int i = 0; i < val.length(); i++){
            if (i == 0 && !pos){
                continue;
            }
            else{
                number[numIndex] = Integer.valueOf(strs[i]);
                numIndex++;
            }
        }

    }


    // Methods 
    public HugeInteger add(HugeInteger h){

        // Getting the two int arrays (these will be altered, but their parent will not)
        int[] num1 = this.getNumberArr();
        int[] num2 = h.getNumberArr();

        int [] sum = new int[0];
        String out = "";
        boolean outPos = true;

        // t + h
        if (h.pos && this.pos){
            sum = addArrOut(num1, num2);
        }
        // -t -h
        else if (!h.pos && !this.pos){
            sum = addArrOut(num1, num2);
            outPos = false;
        }
        // t-h
        else if(!h.pos && this.pos){

            sum = subtractArr(num1, num2);

            // If this is smaller, the output must be negative
            if (this.compareTo(h) == -1){
                outPos = false;
            }

        }
        // h-t
        else{

            sum = subtractArr(num1, num2);

            // If this is bigger, the output must be negative
            if (this.compareTo(h) == 1){
                outPos = false;
            }

        }

        sum = removeLeading(sum);
        out = makeString(sum);

        // Creating the HugeInteger object which will be returned 
        HugeInteger summed = new HugeInteger(out);

        if(!outPos){
            summed.pos = false;
        }

        // Returing the sum 
        return summed;

    }

    public HugeInteger subtract(HugeInteger h){
        // Returns this integer minus h. 

        // Getting the two int arrays (these will be altered, but their parent will not)
        int[] num1 = this.getNumberArr();
        int[] num2 = h.getNumberArr();

        int [] sum = new int[0];
        String out = "";
        boolean outPos = true;

        // t - h
        if (h.pos && this.pos){
            sum = subtractArr(num1, num2);
            if (this.compareTo(h) == -1){
                outPos = false;
            }
        }
        // h - t
        else if (!h.pos && !this.pos){
            sum = subtractArr(num2, num1);
            if (this.compareTo(h) == 1){
                outPos = false;
            }
        }
        // t + h
        else if(!h.pos && this.pos){
            sum = addArrOut(num1, num2);
        }
        // - t - h
        else{
            sum = addArrOut(num1, num2);
            outPos = false;
        }

        sum = removeLeading(sum);
        out = makeString(sum);

        // Creating the HugeInteger object which will be returned 
        HugeInteger summed = new HugeInteger(out);

        if(!outPos){
            summed.pos = false;
        }

        // Returing the sum 
        return summed;
    }



    public HugeInteger multiply(HugeInteger h){

        String multString = "";

        int[] num1 = this.getNumberArr();
        int[] num2 = h.getNumberArr();

        int[] out = multipyNew(num1, num2);
        out = removeLeading(out);

        for (int i = 0; i < out.length; i++) {
            multString += Integer.toString(out[i]);
        }


        HugeInteger multOut = new HugeInteger(multString);

        if (!h.pos && this.pos){
            multOut.pos = false;
        }
        else if (h.pos && !this.pos){
            multOut.pos = false;
        }

        return multOut;

    }

    private int[] multipyNew(int[] num1, int[] num2){
        
        // Making sure both arrays are the same length
        boolean num1Bigger = num1.length > num2.length;
        int neededLength = 0;

        if (num1Bigger){
            if (num1.length % 2 != 0){
                neededLength = num1.length + 1;
            }
            else{
                neededLength = num1.length;
            }
        }
        else{
            if (num2.length % 2 != 0){
                neededLength = num2.length + 1;
            }
            else{
                neededLength = num2.length;
            }
        }

        num1 = makeLength(num1, neededLength);
        num2 = makeLength(num2, neededLength);

        // Breaking up the Array
        int length = neededLength;
        int breakLen = length / 2;

        int[] a = new int[breakLen];    
        int[] b = new int[breakLen];
        int[] c = new int[breakLen];
        int[] d = new int[breakLen];

        for (int i = 0; i < length; i++){

            if (i < breakLen){
                a[i] = num1[i];
                c[i] = num2[i];
            }
            else{
                b[i - breakLen] = num1[i];
                d[i - breakLen] = num2[i];
            }

        }

        // Base Case
        if (breakLen == 1){
            // In this case, all the sub arrays have just one integer within them

            int first = a[0] * c[0];

            int second = b[0] * d[0];

            int third = ((a[0] + b[0]) * (c[0] + d[0])) - second - first;

            first *= 100;
            third *= 10;

            int out = first + second + third;

            // Converting int to an int[]
            String[] outString = Integer.toString(out).split("");
            

            int[] outArr = new int[outString.length]; // POPULATE THE ARRAY
            
            for (int i = 0; i < outString.length; i++){
                outArr[i] = Integer.parseInt(outString[i]);
            }
          
            return outArr;

        }

        // Recursive Case
        else{
            
            int[] first = multipyNew(a, c);
            int[] second = multipyNew(b, d);

            int[] c1 = addArrOut(a, b);
            int[] c2 = addArrOut(c, d);
            int[] c3 = multipyNew(c1, c2);
            int[] c4 = subtractArr(c3, second);
            int[] third = subtractArr(c4, first);

            int[] firstUpdated = addZeros(first, neededLength);
            int[] thirdUpdated = addZeros(third, neededLength/2);

            int[] o1 = addArrOut(firstUpdated, thirdUpdated);
            int[] finalOut = addArrOut(o1, second);

            return finalOut;

        }

    }

    private int[] addZeros(int[] arr, int num){
        int[] out = new int[arr.length + num];
        for (int i = 0; i < arr.length + num; i++){
            if(i < arr.length){
                out[i] = arr[i];
            }
            else{
                out[i] = 0;
            }
        } 
        return out;
    }

    public static String makeString(int[] in){
        // Returns a printable version of the HugeInteger

        // Setting up a blank string
        String str = "";

        // Converting to a string
        for (int i = 0; i < in.length; i++){
            str += Integer.toString(in[i]);
        }

        // Returning the string
        return str;

    }

    private int[] makeLength(int[] arr, int len){
        int lenDiff = len - arr.length;
        int[] output = new int[len];
        for (int i = 0; i < len; i++){
            if (i < lenDiff){
                output[i] = 0;
            }
            else{
                output[i] = arr[i - lenDiff];
            }
        }
        return output;
    }

    public String toString(){
        // Returns a printable version of the HugeInteger

        // Setting up a blank string
        String str = "";

        // Accounting for negative numbers
        if (!pos){
            str = "-";
        }

        // Converting to a string
        for (int i = 0; i < length; i++){
            str += Integer.toString(number[i]);
        }

        // Returning the string
        return str;

    }

    public int[] getNumberArr(){
        // Returns an unrelated copy of the number array. 

        int[] newArr = number.clone();

        return newArr;
    }

    private String addForAdd(int[] a, int[] b){

        // Checking which array is larger.
        boolean num1Bigger = a.length >= b.length;

        int[] big = new int[0];
        int[] sml = new int[0];

        if (num1Bigger) {
            big = a;
            sml = b;
        } else {
            big = b;
            sml = a;
        }

        // Defining varibled required for addition
        int diff;
        int carry = 0;
        int s = 0;
        int tempSum = 0;
        String out = "";

        // Ignore the indent, thre used to be an if statement here

        diff = big.length - sml.length; // Stores the length difference between the two lists.
        for (int i = sml.length - 1; i >= 0; i--) { // Looping throgh backwards

            s = 0;
            tempSum = big[i + diff] + sml[i] + carry;

            // If there is a carry
            if (tempSum > 9) {
                s = tempSum - 10;
                carry = 1;
            }

            // If there isn't a carry
            else {
                s = tempSum;
                carry = 0;
            }

            // Updating the list
            big[i + diff] = s;

        }

        // if carry still leftover
        if (carry != 0) {

            // Loop through and keep trying to add until nothing left to add it to.
            for (int i = (big.length - sml.length) - 1; i >= 0; i--) {

                s = 0;
                tempSum = big[i] + carry;

                // If there is still a carry
                if (tempSum > 9) {
                    s = tempSum - 10;
                    carry = 1;
                    big[i] = s;
                }

                // If there is no longer a carry.
                else {
                    s = tempSum;
                    carry = 0;
                    big[i] = s;
                    break;
                }

            }

        }

        // Creating the sum string
        if (carry != 0) {
            out = "1"; // If there is still a carry, it must occupy the new spot, all the way left.
        } else {
            out = "";
        }

        // Convering the sum to a string (so it can be made into a HugeInteger object)
        for (int i = 0; i < big.length; i++) {
            out += Integer.toString(big[i]);
        }

        return out;

    }
    
    private int[] addArrOut(int[] a, int[] b){

        // Checking which array is larger.
        boolean num1Bigger = a.length >= b.length;

        int[] big = new int[0];
        int[] sml = new int[0];

        if (num1Bigger) {
            big = a;
            sml = b;
        }
        else {
            big = b;
            sml = a;
        }

        // Defining varibled required for addition
        int diff;
        int carry = 0;
        int s = 0;
        int tempSum = 0;

        // Ignore the indent, thre used to be an if statement here

        diff = big.length - sml.length; // Stores the length difference between the two lists.
        for (int i = sml.length - 1; i >= 0; i--) { // Looping throgh backwards

            s = 0;
            tempSum = big[i + diff] + sml[i] + carry;

            // If there is a carry
            if (tempSum > 9) {
                s = tempSum - 10;
                carry = 1;
            }

            // If there isn't a carry
            else {
                s = tempSum;
                carry = 0;
            }

            // Updating the list
            big[i + diff] = s;

        }

        // if carry still leftover
        if (carry != 0) {

            // Loop through and keep trying to add until nothing left to add it to.
            for (int i = (big.length - sml.length) - 1; i >= 0; i--) {

                s = 0;
                tempSum = big[i] + carry;

                // If there is still a carry
                if (tempSum > 9) {
                    s = tempSum - 10;
                    carry = 1;
                    big[i] = s;
                }

                // If there is no longer a carry.
                else {
                    s = tempSum;
                    carry = 0;
                    big[i] = s;
                    break;
                }

            }

        }

        int[] outArr = new int[0];

        // Creating the sum string
        if (carry != 0) {
            outArr = new int[big.length + 1];
            outArr[0] = 1; // If there is still a carry, it must occupy the new spot, all the way left.
            int outArrIndex = 1;
            for (int i = 0; i < big.length; i++) {
                outArr[outArrIndex] = big[i];
                outArrIndex++;
            }
        } 

        else {
            outArr = new int[big.length];
            for (int i = 0; i < big.length; i++) {
                outArr[i] = big[i];
            }
        }
    
        return outArr;

    }
    
    private int[] subtractArr(int[] num1, int[] num2){
        // Returns this integer minus h. 

        // Finding out which one is bigger, and setting up array accordingly
        int lenDiff = 0;
        int[] big;
        int[] sml;
        if (num1.length >= num2.length){
            lenDiff = num1.length - num2.length;
            big = num1.clone();
            sml = num2.clone();
        }
        else{
            lenDiff = num2.length - num1.length;
            big = num2.clone();
            sml = num1.clone();
        }

        // Adding leading zeros to the smaller array
        if (lenDiff != 0){
            
            int[] temp = new int[big.length];
            for (int i = 0; i < big.length; i ++){

                // If the small list has not begun yet, 0s are added
                if (i < lenDiff){
                    temp[i] = 0;
                }

                // Sourcing from sml array
                else{
                    temp[i] = sml[i - lenDiff];
                }   
            
            }

            // Setting sml to temp, so it contains leading 0s
            sml = temp;

        }

        // Looping through sml and finding the difference between each digit
        for (int i = 0; i < sml.length; i++){

            // On the last digit in list
            if (i == sml.length - 1){
                sml[i] = 10 - sml[i];
            }

            // Every other digit
            else{
                sml[i] = 9 - sml[i];
            }  

        }

        // Adding the new modified sml to big
        String postAdd = addForAdd(big, sml).substring(1); // The substring(1) drops the fist charcter

        // Dropping the leading zero if there is one
        if (postAdd.charAt(0) == '0'){
            postAdd = postAdd.substring(1);
        }  
        
        // Converting the string postAdd to an int[]
        String[] strs = postAdd.split("");
        int[] ouput = new int[postAdd.length()];

        for (int i = 0; i < postAdd.length(); i++){
            ouput[i] = Integer.parseInt(strs[i]);
        }

        return ouput;

    }

    private int[] removeLeading(int[] arr){

        int startIndex = -1;
        int[] out = new int[0];

        for (int i = 0; i < arr.length; i++){
            if (arr[i] != 0){
                startIndex = i;
                break;
            }
        }

        if (startIndex == -1){
            return new int[]{0};
        }
        else{
            out = new int[arr.length - startIndex];
            int outIndex = 0;
            for (int i = startIndex; i < arr.length; i++){
                out[outIndex] = arr[i];
                outIndex++;
            }
            return out;
        }
    }


    public int compareTo(HugeInteger h){

        int[] num1 = this.getNumberArr();
        int[] num2 = h.getNumberArr();

        num1 = removeLeading(num1);
        num2 = removeLeading(num2);

        boolean num1Pos = this.pos;
        boolean num2Pos = h.pos;


        // this larger than h
        if (num1.length > num2.length){
            if (num1Pos && num2Pos){
                return 1;
            }
            else if (!num1Pos && num2Pos){
                return -1;
            }
            else if(num1Pos && !num2Pos){
                return 1;
            }
            else {
                return -1;
            }
        
        }
        // h larger than this
        else if (num2.length > num1.length){
            if (num1Pos && num2Pos){
                return -1;
            }
            else if (!num1Pos && num2Pos){
                return -1;
            }
            else if(num1Pos && !num2Pos){
                return 1;
            }
            else {
                return 1;
            }
        }

        // Equivlant (in length)
        else{
            
            if ((num1Pos && num2Pos) || (!num1Pos && !num2Pos)){
                int len = num1.length;

                for (int i = len - 1; i >= 0; i--) {
                    if (num1[i] > num2[i]) {
                        return 1;
                    } else if (num2[i] > num1[i]) {
                        return -1;
                    }
                }

                // If the code has made it to this point, the Ints must be the same
                return 0;
            }

            else if (num1Pos && !num2Pos){
                return 1;
            }
            else{
                return -1;
            }

        }


    }


}

