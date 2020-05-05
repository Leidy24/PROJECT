import java.io.*;  
  
import java.util.*; 
class Checker {
    static HashMap<String, Integer> map = new HashMap<>();
    public static boolean isInteger(String s) {
    return isInteger(s,10);
   }

   public static boolean isInteger(String s, int radix) {
    if(s.isEmpty()) return false;
    for(int i = 0; i < s.length(); i++) {
        if(i == 0 && s.charAt(i) == '-') {
            if(s.length() == 1) return false;
            else continue;
        }
        if(Character.digit(s.charAt(i),radix) < 0) return false;
    }
    return true;
    }
    public static void main(String[] args) {
        try{
            File fis=new File("D:/B.txt");       
            Scanner sc=new Scanner(fis);    //file to be scanned  
            //returns true if there is another line to read  
            while(sc.hasNextLine())  
            {  
                String a=sc.nextLine();
                String[] strArr = a.split(" ");
        
                assign(strArr);
            }
            System.out.println(map);
            sc.close();     //closes the scanner  
        }  
        catch(IOException e)  
        {  
        e.printStackTrace();  
    }  
    }
    public static void assign(String [] strArr){
        Checker check=new Checker();
        if(!Character.isLetter(strArr[0].charAt(0))||!strArr[1].equals("=")){
            System.out.println("Syntax Error");
            System.exit(0);
        }
        else{
            int a = check.calculator(strArr,2);
            if(map.containsKey(strArr[0])){
                map.remove(strArr[0]);
            }
            map.put(strArr[0],a);
        }
        
        
    }
    public int calculator(String[] strArr, int a) {
        Stack<Integer> operands = new Stack<Integer>();
        int flag=0;
        
        
        for(int i=a;i<strArr.length&&flag!=1;i++) {
            if (strArr[i].trim().equals("")) {
                continue;
            }
            
            switch (strArr[i]) {
                case "+":
                case "-":
                case "*":
                    Integer left = operands.pop();
                    
                    flag=1;
                    
                    switch(strArr[i]) {
                        case "+":
                            left = calculator(strArr,i+1) + left;
                            break;
                        case "-":
                            left = left - calculator(strArr,i+1);
                            break;
                        case "*":
                            if(map.containsKey(strArr[i+1])){
                                Integer b = map.get(strArr[i+1]);
                                operands.push(b);
                            }
                            else if(isInteger(strArr[i+1])){
                            operands.push(Integer.parseInt(strArr[i+1]));}
                            else{
                                System.out.println("Syntax Error");
                                System.exit(0);
                            }
                            i++;
                            left= left*operands.pop();
                            flag=0;
                            break;
                        
                        
                    }
                    operands.push(left);
                    
                    break;
                    
                default:
                    if(map.containsKey(strArr[i])){
                        Integer b = map.get(strArr[i]);
                        operands.push(b);
                    }
                    else if(isInteger(strArr[i])){
                    operands.push(Integer.parseInt(strArr[i]));}
                    else{
                        System.out.println("Syntax Error");
                        System.exit(0);
                    }
                   
                    break;  
                
            }
        }
        int c= operands.pop();
        if(operands.isEmpty()){
            return c;
        }
        else{
            System.out.println("Syntax Error");
            return 0;
        }
    }
}
