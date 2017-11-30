

import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String text = null;
        Map<String, Integer> wordMap;
        try {
            BufferedReader textFile = new BufferedReader(new FileReader("purloined.txt"));
            text = textFile.lines().collect(Collectors.joining());
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        Map<String, ArrayList<Pair<Integer, String>>> pairMap = new HashMap<>();

//        System.out.println(makeSentencePerLine(text));
        text = makeSentencePerLine(text);
        findAllKeyWords(text);
        pairMap = concordance(text);
        printConcordance(pairMap);


    }

    public static void printConcordance(Map<String, ArrayList<Pair<Integer, String>>> map) {

        for (Map.Entry<String, ArrayList<Pair<Integer, String>>> entry : map.entrySet()) {
            String key = entry.getKey();
            ArrayList<Pair<Integer, String>> value = entry.getValue();

            System.out.println(key);
            for (int i = 0; i < value.size(); i++) {
                System.out.println(value.get(i).getKey() + " " + value.get(i).getValue());
            }
            System.out.println();

        }
    }

    public static Map<String, ArrayList<Pair<Integer, String>>> concordance(String text) {
        Scanner scanner = new Scanner(text);

        HashMap<String, ArrayList<Pair<Integer, String>>> concordanceMap = new HashMap<>();

        ArrayList<Pair<Integer, String>> list;
        Pair<Integer, String> pair;

        int lineCount = 0;
        while (scanner.hasNextLine()) {
            lineCount++;
            String line = scanner.nextLine();
            Scanner scanner2 = new Scanner(line);
            while (scanner2.hasNext()) {
                String word = scanner2.next();

                if (concordanceMap.containsKey(word)) {
                    pair = new Pair<>(lineCount, line);
                    concordanceMap.get(word).add(pair);
                    concordanceMap.put(word, concordanceMap.get(word));
                } else {
                    pair = new Pair<>(lineCount, line);
                    list = new ArrayList<>();
                    list.add(pair);
                    concordanceMap.put(word, list);
                }

            }
        }

//        System.out.println(concordanceMap.toString());
        return concordanceMap;
    }


    public static Map<String, ArrayList<Pair<Integer, String>>> findAllKeyWords(String text) {

        Map<String, Integer> wordMap = new HashMap<>();
        Map<Integer, String> mMap = new HashMap<>();
        Map<Map<Integer, String>, String> superMap = new HashMap();

        Map<String, ArrayList<Pair<Integer, String>>> pairMap = new HashMap<>();
        int count = 0;
        ArrayList<Pair<Integer, String>> wordList;
        Pair<Integer, String> pair;

        Scanner scanner1 = new Scanner(text);
        while (scanner1.hasNextLine()) {
            count++;
            Scanner scanner2 = new Scanner(scanner1.nextLine());
            while (scanner2.hasNext()) {
                String word = scanner2.next();
                if (word.equals("a") || word.equals("the") || word.equals("to") ||
                        word.equals("is") || word.equals("for") || word.equals("I") ||
                        word.equals("at") || word.equals("a") || word.equals("or")) {
                } else {
                    if (pairMap.containsKey(word)) {
                        pair = new Pair<>(count, word);
                        pairMap.get(word).add(pair);
                        wordList = pairMap.get(word);
                        pairMap.put(word, wordList);
                    } else {
                            ArrayList<Pair<Integer, String>> tempPairList = new ArrayList<>();
                            pair = new Pair<>(count, word);
                            tempPairList.add(pair);

                            //pairMap.get(word).add(pair);
                            pairMap.put(word,tempPairList);
                    }
                }
            }
        }

//        for (ArrayList<Pair<Integer, String>> key : pairMap.values()) {
//
//            for (int i = 0; i < key.size(); i++) {
//                System.out.print(key.get(i).toString() + " ");
//            }
//            System.out.println();
//        }


        return pairMap;
    }

    public static String makeSentencePerLine(String text2) {

        Pattern pattern = Pattern.compile("[.!?]");
        Matcher matcher = pattern.matcher(text2);

        StringBuilder superString = new StringBuilder(text2);

        String string = matcher.replaceAll(".\n");

        try (PrintWriter output = new PrintWriter("newwwww.txt")) {
            output.println(string);
        } catch (FileNotFoundException ex) {

        }

//        System.out.println(string);
        return string;
    }
}


/*

Map<String, ArrayList<Pair<Integer, String>>



if (wordMap.containsKey(word)) {
                        wordMap.put(word, wordMap.get(word) + 1);
                    } else {
                        wordMap.put(word, 1);
                    }
 */