package becoding.matheuspimenta;

import becoding.matheuspimenta.models.OperationValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Get Size and Number of Operations
        List<Integer> xyList = xyLineRead(reader);

        //Get Input Operations calling operationInput
        List<OperationValues> operationsList = operationInput(reader, xyList);

        //Create Array. int Type is already created with zero value
        int[] array = new int[xyList.get(0)];

        //Do the operations
        for(OperationValues op : operationsList){
            for(int i = op.getI(); i <= op.getJ(); i++){
                array[i] += op.getK();
            }
            System.out.println(String.format("Operation: %s",Arrays.toString(array)));
        }

        //Print Max Value from the array
        System.out.println(String.format("Max Value: %s", Arrays.stream(array).max().getAsInt()));
    }

    private static List<OperationValues> operationInput(BufferedReader reader, List<Integer> xyList) throws IOException {
        //Create a list of OperationValues that contains the I J K values, object created with Lombok Annotation, so it was not necessary to write Getters and Setters
        List<OperationValues> operationsValuesList = new ArrayList<>();

        //Read line for each operation and add to the list
        for(int i=0; i < xyList.get(1); i++){
            OperationValues operation = operationLineRead(reader, xyList.get(0));
            operationsValuesList.add(operation);
        }
        return operationsValuesList;
    }

    private static OperationValues operationLineRead(BufferedReader reader, Integer x) throws IOException {
        System.out.println("I and J are the range where you want to add K value.\nSpecify 'I J K' values separated with space: \n");
        String firstLine = reader.readLine();

        //Split the input and validate the length
        String[] list = firstLine.split(" ");
        if(list.length == 3){
            //Create OperationValues object
            OperationValues oprValues = new OperationValues();

            //Convert the input values to Integer
            List<Integer> arrayList = Arrays.stream(list).map(Integer::parseInt).toList();

            //Set the values in the object, subtracting one from I and J to match array positions
            oprValues.setI(arrayList.get(0)-1);
            oprValues.setJ(arrayList.get(1)-1);
            oprValues.setK(arrayList.get(2));

            //Validate constraints
            if(oprValues.getJ() >= x){
                System.out.println(String.format("'J' CANNOT BE HIGHER THAN: %s", x));
                return operationLineRead(reader, x);
            }
            if(oprValues.getI() > oprValues.getJ()){
                System.out.println("'I' CANNOT BE HIGHER THAN 'J'\n");
                return operationLineRead(reader, x);
            }
            if(oprValues.getI() < 0){
                System.out.println("'I' VALUE IS NOT ALLOWED\n");
                return operationLineRead(reader, x);
            }
            if(oprValues.getK() < 0 || oprValues.getK() > Math.pow(10, 9)){
                System.out.println("'K' VALUE IS NOT ALLOWED\n");
                return operationLineRead(reader, x);
            }

            return oprValues;
        }else{
            System.out.println("YOU MUST SPECIFY 3 NUMBERS IN THIS LINE!!!\n");
            return operationLineRead(reader, x);
        }
    }

    private static List<Integer> xyLineRead(BufferedReader reader) throws IOException {
        System.out.println("X is the SIZE of the List \nY is the number of operations you want to perform in the list.\nSpecify 'X Y' values separated with space: ");
        String firstLine = reader.readLine();

        //Split the input and validate the length
        String[] list = firstLine.split(" ");
        if(list.length == 2){
            //I've first written this part using a Lambda Expression .map(n -> Integer.parseInt(n)).collect(Collectors.toList()) and after a SonarLint suggestion I changed it to a method reference for a more readable code
            List<Integer> arrayList = Arrays.stream(list).map(Integer::parseInt).toList();

            //Validate input Limits
            if(arrayList.get(0).compareTo(3) < 0 || arrayList.get(0) > Math.pow(10, 7)){
                System.out.println("X VALUE IS NOT ALLOWED\n");
                return xyLineRead(reader);
            }
            if(arrayList.get(1).compareTo(1) < 0 || arrayList.get(1) > 2*Math.pow(10, 5)){
                System.out.println("Y VALUE IS NOT ALLOWED\n");
                return xyLineRead(reader);
            }
            return arrayList;
        }else{
            System.out.println("YOU MUST SPECIFY 2 NUMBERS IN THIS LINE!!!\n");
            return xyLineRead(reader);
        }
    }
}