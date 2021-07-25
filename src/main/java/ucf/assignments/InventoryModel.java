package ucf.assignments;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryModel {
    private static InventoryModel model;
    private ArrayList<Item> items;

    private InventoryModel() {
        items = new ArrayList<>();
    }

    public static InventoryModel getInstance(){
        if (model == null){
            model = new InventoryModel();
        }
        return model;
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public void readDataFromFile(File file){
        if (file != null && file.exists()){
            StringBuilder fileContents = new StringBuilder();
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()){
                    fileContents.append(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String data = fileContents.toString();
            String extension = getExtension(file.getPath());
            System.out.println(extension);
            switch (extension){
                case "json":
                    items = DataFormatter.fromJson(data);
                    break;
            }
        }
    }

    public void writeDataToFile(File file){
        if (file != null){
            String extension = getExtension(file.getPath());
            String dataString = "";
            if (extension.equals("json")){
                Gson gson = new Gson();
                dataString = gson.toJson(items);
            }

            FileWriter writer;
            try {
                if(file.createNewFile()){
                    writer = new FileWriter(file);
                    writer.write(dataString);
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getExtension(String path){
        return path.substring(path.lastIndexOf("."));
    }

}