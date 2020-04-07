package com.example.digitalproductmarketplace.boundary;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private InputStream fileName;
    private ArrayList<String> lines;

    public FileReader(InputStream fileName) {
        this.fileName = fileName;
        lines = new ArrayList<>();
    }
    public void readFile() {
        try {
            BufferedReader fileToRead = new BufferedReader(new InputStreamReader(fileName));
            String header = fileToRead.readLine();
            String csvLine;
            while ((csvLine = fileToRead.readLine()) != null) {
                lines.add(csvLine);
            }


        } catch (IOException ix) {
            Log.e("File Exception", "File Not found");
        }

    }

    public ArrayList<String> getLines() {
        return lines;
    }
}
