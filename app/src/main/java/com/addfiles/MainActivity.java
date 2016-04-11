package com.addfiles;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by nidhigupta on 4/11/16.
 */
public class MainActivity extends Activity {

    int sum = 0, nextInt = 0;
    String fileName = "A.txt";
    LinkedHashMap<String, Integer> filesValueshash = new LinkedHashMap<>();
    LinkedHashMap<String, Integer> pendingValueshash = new LinkedHashMap<>();
    List<Integer> pendingValues = new ArrayList<Integer>(pendingValueshash.values());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(fileName)));
            Scanner textfile = new Scanner(reader);
          //  Log.e("textfile", "" + textfile);
            performAddition(textfile);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int performAddition(Scanner textfile) {
        sum = 0;
        sum = Integer.parseInt(textfile.nextLine());
        Log.e("first int", "" + sum + " " + fileName);
        while (textfile.hasNext()) {
            String nextLine = textfile.nextLine();
            Log.e("nextLine", "" + nextLine);
            try {
                nextInt = Integer.parseInt(nextLine);
                sum += nextInt;
           //     Log.e("123", "" + sum + " " + fileName);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                pendingValueshash.put(fileName, sum);
                pendingValues = new ArrayList<Integer>(pendingValueshash.values());
            //    Log.e("jfn", "" + pendingValueshash.keySet() + " " + pendingValueshash.size());

                nextInt = getNextFileValue(nextLine);
                if (nextInt != 0) {
                    int size = pendingValues.size() - 1;

                 //   Log.e("cs", " " + size);
                  //  Log.e("value", "dfg " + pendingValues.get(size));
                    int value = pendingValues.get(size);

                    for (Map.Entry<String, Integer> entry : pendingValueshash.entrySet()) {
                        if (entry.getValue().equals(value)) {
                            System.out.println(entry.getKey());
                            fileName = entry.getKey();
                        }
                    }

                    sum = nextInt + pendingValues.get(size);
                 //   Log.e("cjvjk", sum + " " + fileName);
                    pendingValues.remove(pendingValues.get(size));
                    pendingValueshash.remove(size);
                }
            }
        }
        Log.e("File Sum", "" + sum);
        filesValueshash.put(fileName, sum);
     //   Log.e("hash", "" + filesValueshash.keySet());
        return sum;
    }

    private int getNextFileValue(String nextLine) {
        int value = 0;
       // Log.e("filehash2232", "" + sum + " " + fileName);
        fileName = nextLine;
      //  Log.e("fileNamenew", " " + fileName);
        if (filesValueshash.containsKey(fileName)) {
            value = filesValueshash.get(fileName);
        //    Log.e("1234", "" + value + " " + fileName);
        } else {
            try {
                if (Arrays.asList(getResources().getAssets().list("")).contains(fileName)) {
              //      Log.e("fileName", fileName);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(getAssets().open(fileName)));
                    Scanner newfile = new Scanner(reader);
              //      Log.e("newfile", "" + newfile);
                    value = performAddition(newfile);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return value;
    }
}
