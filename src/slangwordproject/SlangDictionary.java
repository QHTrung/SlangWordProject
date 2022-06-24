/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slangwordproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author HOME
 */
public class SlangDictionary {

    private static String ORIGINAL_SLANG_FILE = "src/data/slang_origin.txt";
    private static String SLANG_FILE = "src/data/slang.txt";
    private static String SEARCH_HISTORY_FILE = "src/data/history.txt";
    public static HashMap<String, List<String>> slangHashMap = new HashMap<>();// sử dụng HashMap dể lưu trữ data.
    public static List<String> slangHistory = new ArrayList<String>();
    private static SlangDictionary instance;
    private static Scanner sc = new Scanner(System.in);

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
            FileReader fr = new FileReader(SLANG_FILE);
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
            System.out.print("The definition of " + slangword + " is:\n" + result);
        }
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
        if (result.isEmpty()) {
            System.out.println("The " + definition + " does not exist!");
        } else {
            System.out.println("The slang word of " + definition + " is:");
            for (String key : result) {
                System.out.println(key + ":" + slangHashMap.get(key));
            }
        }
        slangHistory.add(definition);
        saveHistoryFile();
    }

    // Ham doc file lich su
    public void readHistoryFile() throws IOException {
        try {
            FileReader fr = new FileReader(SEARCH_HISTORY_FILE);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                slangHistory.add(str);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error!:" + e.getMessage());
        }

    }

    // Ham luu tu da tra vao file lich su
    public void saveHistoryFile() {
        try {
            FileWriter fw = new FileWriter(SEARCH_HISTORY_FILE);
            for (String historyItem : slangHistory) {
                fw.write(historyItem + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error!:" + ex.getMessage());
        }
    }

    // Ham hien thi lich su da tim kiem
    public void displayHistory() {
        for (String historyItem : slangHistory) {
            System.out.println(historyItem);
        }
    }

    // Ham kiem tra slang word co ton tai trong hashmap hay khong
    public boolean checkExist(String slangword) {
        return slangHashMap.containsKey(slangword.toUpperCase());
    }

    // Ham xoa 1 slang word
    public void deleteSlangWord(String slangword) {
        slangHashMap.remove(slangword.toUpperCase());
        saveSlangWord();
        System.out.println("Delete slang word successfully!");
    }

    // Ham them 1 slang word
    public void addSlangWord(String slangword, String definition) {
        if (checkExist(slangword)) {
            System.out.println("The " + slangword + " already exists so it can't be added!");
        } else {
            List<String> definitions = new ArrayList<>();
            definitions.add(definition);
            slangHashMap.put(slangword.toUpperCase(), definitions);
            saveSlangWord();
            System.out.println("Add slang word successfully!");
        }
    }

    // Ham chinh sua definition 1 slang word
    public void editSlangWord(String editslang) {
        if (checkExist(editslang)) {
            List<String> definitions = new ArrayList<>();
            for (;;) {
                System.out.println("Option");
                System.out.println("A.Add more definitions.");
                System.out.println("B.Replace definition");
                System.out.print("Enter your choice:");
                String option = sc.nextLine();
                switch (option.toUpperCase()) {
                    case "A":
                        System.out.print("Enter new definitions of " + editslang + " to add:");
                        String newdefinitions = sc.nextLine();
                        definitions.add(newdefinitions);
                        List<String> defintionList = slangHashMap.get(editslang.toUpperCase());
                        for (String item : defintionList) {
                            definitions.add(item);
                        }
                        slangHashMap.replace(editslang.toUpperCase(), definitions);
                        saveSlangWord();
                        System.out.println("Edit definition successfully");
                        return;
                    case "B":
                        System.out.print("Enter new definition of " + editslang + " to replace:");
                        String newdefinition = sc.nextLine();
                        definitions.add(newdefinition);
                        slangHashMap.replace(editslang.toUpperCase(), definitions);
                        saveSlangWord();
                        System.out.println("Edit definition successfully!");
                        return;
                    default:
                        System.out.println("Wrong! Please choose again!");
                }
            }
        } else {
            System.out.println(editslang + " does not exist!");
        }
    }

    // Ham luu file sau khi da them/xoa/sua slang word
    public static void saveSlangWord() {
        try {
            FileWriter fw = new FileWriter(SLANG_FILE);
            for (String key : slangHashMap.keySet()) {
                fw.write(key + "`");
                List<String> defintions = slangHashMap.get(key);
                for (int i = 0; i < defintions.size(); i++) {
                    fw.write(defintions.get(i));
                    if (i + 1 < defintions.size()) {
                        fw.write("|");
                    }
                }
                fw.write("\n");
            }
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error!: " + ex.getMessage());
        }
    }
}
