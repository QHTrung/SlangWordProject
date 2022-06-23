/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slangwordproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HOME
 */
public class SlangDictionary {

    private static String ORIGINAL_SLANG_FILE = "src/data/slang.txt";
    private  static String SEARCH_HISTORY_FILE="src/data/history.txt";
    public static HashMap<String, List<String>> slangHashMap = new HashMap<>();// sử dụng HashMap dể lưu trữ data.
    public static List<String> slangHistory=new ArrayList<String>();
    private static SlangDictionary instance;
    
    private SlangDictionary() {
        try {
            readFile();
            readHistoryFile();
        } catch (IOException ex) {
            System.out.println("Error!:" + ex.getMessage());
        }
    }

    public static SlangDictionary getInstance() {
        if (instance == null) {
            synchronized (SlangDictionary.class) {
                if (null == instance) {
                    instance = new SlangDictionary();
                }
            }
        }
        return instance;
    }

    // Ham doc file
    public void readFile() throws IOException {
        try {
            FileReader fr = new FileReader(ORIGINAL_SLANG_FILE);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                if (str.contains("`")) {
                    String[] dataDic = str.split("`");
                    String[] definition_item = dataDic[1].split("\\|");
                    List<String> definitionList = Arrays.asList(definition_item);
                    slangHashMap.put(dataDic[0], definitionList);
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error!:" + ex.getMessage());
        }

    }

    // Ham tim kiem dinh nghia theo slang word
    public void searchBySlangWord(String slangword) {
        List<String> result = slangHashMap.get(slangword.toUpperCase());
        if (result == null) {
            System.out.println("This word does not exist!");
        } else {
            System.out.print("The definition of " + slangword + " is:");
        }

        System.out.println(result);
        slangHistory.add(slangword);
        saveHistoryFile();
    }

    //Ham tim kiem slang word theo dinh nghia
    public void searchByDefinition(String definition) {
        List<String> result = new ArrayList<>();
        for (String item : slangHashMap.keySet()) {
            slangHashMap.get(item).forEach((value) -> {
                if (value.toUpperCase().contains(definition.toUpperCase())) {
                    result.add(item);
                    return;
                }
            });
        }
        System.out.println("The slang word of " + definition + " is:");
        for (String key : result) {
            System.out.println(key + ":" + slangHashMap.get(key));
        }
        slangHistory.add(definition);
        saveHistoryFile();
    }
    // Ham doc file lich su
    public void readHistoryFile() throws IOException {
        try {
            FileReader fr=new FileReader(SEARCH_HISTORY_FILE);
            BufferedReader br=new BufferedReader(fr);
            String str;
            while((str=br.readLine())!=null){
                slangHistory.add(str);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error!:"+e.getMessage());
        }
        
    }
    // Ham luu tu da tra vao file lich su
    public void saveHistoryFile() {
        try {
            FileWriter fw=new FileWriter(SEARCH_HISTORY_FILE);
            for(String historyItem:slangHistory){
                fw.write(historyItem+"\n");
            }
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error!:"+ex.getMessage());
        }
    }
    public void displayHistory(){
        for(String historyItem:slangHistory){
            System.out.println(historyItem);
        }
    }
}
