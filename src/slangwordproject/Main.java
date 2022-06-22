/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package slangwordproject;

import java.util.Scanner;

/**
 *
 * @author HOME
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static Scanner sc = new Scanner(System.in);
    public static SlangDictionary slangDictionary=SlangDictionary.getInstance();
    
    public static void showMenu() {
        System.out.println("==========   SLANG WORD MENU   ==========");
        System.out.println("1. Search by slang word.");
        System.out.println("2. Search by definition.");
        System.out.println("3. Show search history.");
        System.out.println("4. Add new slang word.");
        System.out.println("5. Edit slang word.");
        System.out.println("6. Delete slang word.");
        System.out.println("7. Reset the original slang words list.");
        System.out.println("8. Random 1 slang word.");
        System.out.println("9. Quiz game to find the definition of slang word.");
        System.out.println("10.Quiz game to find slangword by definition.");
        System.out.println("11. Exit.");
    }

    public static void main(String[] args) {
        // TODO code application logic here
        int choose;
        for(;;) {
            showMenu();
            do {                
            System.out.print("Enter your choice:");
            choose = Integer.parseInt(sc.nextLine());
            } while (choose<=0||choose>11);
            switch (choose) {
                case 1:
                    System.out.print("Enter slang word to find:");
                    String slangWord=sc.nextLine();
                    slangDictionary.searchBySlangWord(slangWord);
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 11:
                    System.out.println("Exit!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong! Please choose again!");
            }
        }
    }

}