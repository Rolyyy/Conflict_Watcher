package com.conflictwatcher.Other;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.content.Context;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


// CODE FROM THIS CLASS IS SOURCED FROM:
// https://en.proft.me/2017/07/6/how-read-csv-file-android/


//Class for reading data from a CSV file within the project directory
public class CSVReader {

    private String fileName;
    private Context context;
    private List<String[]> rows = new ArrayList<>();

   public CSVReader(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;

    }


   public List<String[]> readCSV() throws IOException {
        InputStream is = context.getAssets().open(fileName); //gets the CSV file

        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;
        String csvSplitBy = ","; //used to separate columns

        br.readLine();

        //each row is read until file is finished
        while ((line = br.readLine()) != null) {
            String[] row = line.split(csvSplitBy);
            rows.add(row);
        }
        return rows;
    }
}
