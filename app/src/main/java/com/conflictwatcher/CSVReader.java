package com.conflictwatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.content.Context;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//Class for reading data from a CSV file within the project directory
class CSVReader {

    private String fileName;
    private Context context;
    private List<String[]> rows = new ArrayList<>();

    CSVReader(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;

    }


    List<String[]> readCSV() throws IOException {
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
