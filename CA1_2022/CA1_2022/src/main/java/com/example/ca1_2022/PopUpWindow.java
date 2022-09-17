package com.example.ca1_2022;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.*;

import static com.example.ca1_2022.HelloController.*;

public class PopUpWindow {
   static String names;
   static Hashtable<String, ArrayList> ht2 = new Hashtable<>();
    @FXML
    private TextField f;
    @FXML
    private ImageView img1;
    @FXML
    private CheckBox OC, bW, R;
    @FXML
    private TextField name;

    static int[] loc = new int[1];

    public void setImage(Image img){
        img1.setImage(img);
    }

    public void Reduce() throws IOException {
        Enumeration<Integer> keys = ht1.keys();
        Set<Integer> setOfKeys = ht1.keySet();
        int i = 0;
        System.out.println("Size before " + setOfKeys);
        while (keys.hasMoreElements()) {
            // Getting the key of a particular entry
            Integer key = keys.nextElement();
            int sizes = Integer.parseInt(f.getText());
           if (ht1.get(key).size() <= sizes) {
                ArrayList white = ht1.get(-1);
                ArrayList items = ht1.get(key);
                white.addAll(items);
                ht1.put(-1, white);
                ht1.remove(key);
                System.out.println("Added to the white pixels");
            }
        }
        loc = new int[setOfKeys.size()-1];
        addingArray();
        Add();
    }
    public void updateImage(){
        Enumeration<Integer> keys = ht1.keys();
        int width = (int)  img1.getImage().getWidth();
        WritableImage wimage = new WritableImage((int) img1.getImage().getWidth(), (int) img1.getImage().getHeight());
        PixelWriter pwriter = wimage.getPixelWriter();
        // Iterating through the Hashtable
        // object
        while (keys.hasMoreElements()) {
            // Getting the key of a particular entry
            Integer key = keys.nextElement();
            ArrayList<Integer> items;
            if (ht1.get(key).size() == ht1.get(-1).size()) {
                items = ht1.get(-1);
                for (Integer item : items) {
                    int row = item / width;
                    int columns = item % width;
                    pwriter.setColor(columns, row, Color.WHITE);
                }
            } else {
                items = ht1.get(key);
                Color random = randomColor();
                for (Integer item : items) {
                    int row = item / width;
                    int columns = item % width;
                    if(R.isSelected()) {
                        pwriter.setColor(columns, row, random);
                    }
                    if(bW.isSelected()){
                        pwriter.setColor(columns, row, Color.BLACK);
                    }
                    if(OC.isSelected()){
                        pwriter.setColor(columns, row, oc);
                    }
                }
            }
        }
        img1.setImage(wimage);
    }
    public Color randomColor() {
        Random r = new Random();
        return Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble());
    }
    public void SelectedOGBox() {
        if (OC.isSelected()) {
            R.setSelected(false);
            bW.setSelected(false);
        }
    }

    public void SelectedBoxR() {
        if (R.isSelected()) {
            OC.setSelected(false);
            bW.setScaleShape(false);
        }
    }

    public void SelectedBoxBW() {
        if (bW.isSelected()) {
            R.setSelected(false);
            OC.setSelected(false);
        }
    }
    public void Add() {
        names = name.getText();
        }
        public void addingArray(){
            Enumeration<Integer> keys = ht1.keys();
            int i = 0;
            while (keys.hasMoreElements()) {
                // Getting the key of a particular entry
                Integer key = keys.nextElement();
                if (ht1.get(key).size() != ht1.get(-1).size()) {
                    loc[i] = ht1.get(key).size();
                    i++;
                }
            }
            selectionSort(loc);
        }
    public static void selectionSort(int[] a){
        for(int bp=0;bp<a.length-1;bp++){
            int biggestIndex=bp;
            for(int i=bp+1;i<a.length;i++)
                if(a[i]>a[biggestIndex])
                    biggestIndex=i;
            if(biggestIndex!=bp) {
                int swap=a[bp];
                a[bp]=a[biggestIndex];
                a[biggestIndex]=swap;
            }
        }
    }
}
