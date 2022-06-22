/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slangwordproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public static HashMap<String, List<String>> slangHashMap = new HashMap<>();// sử dụng HashMap dể lưu trữ data.
    private static SlangDictionary instance;

    private SlangDictionary() {
        try {
            readFile();
        } catch (IOException ex) {
            Logger.getLogger(SlangDictionary.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println("Error!:" + ex);
            Logger.getLogger(SlangDictionary.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    // Ham tim kiem dinh nghia theo slang word
    public void searchBySlangWord(String slangword){
        List<String>result=slangHashMap.get(slangword.toUpperCase());
        System.out.print("The definition of "+slangword+" is:");
        System.out.println(result);
    }
}
