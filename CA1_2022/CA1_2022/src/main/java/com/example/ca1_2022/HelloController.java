package com.example.ca1_2022;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class HelloController {
  static Hashtable<Integer, ArrayList> ht1 = new Hashtable<>();
    Enumeration<Integer> keys;
    static Color oc;
    @FXML
    private Button imagePicker;
    @FXML
    private ImageView img;
    private Image image1;
    @FXML
    private BufferedImage imgs;
    @FXML
    private CheckBox labels, rect;
    @FXML
    private Slider slideHue, slideB;
    private int[] arr;
    @FXML
    private Label warning;
    @FXML
    private Label label;
    private Rectangle rectangle;
    private Rectangle[] rectangles;
    private Label[] l;
    public HelloController() throws IOException {
    }


    public void initialize() {
        slideHue.setMax(20);
        slideHue.setMin(5);
        slideHue.setValue(12);
        slideHue.setMajorTickUnit(7);
        slideHue.setShowTickLabels(true);
        slideHue.setShowTickMarks(true);
        slideB.setMax(0.1);
        slideB.setMin(0.01);
        slideB.setValue(0.04);
        slideB.setShowTickLabels(true);
        slideB.setShowTickMarks(true);
    }

    public void imagePicked(javafx.event.ActionEvent e) throws Exception {
        if (e.getSource() == imagePicker) {
            FileChooser file = new FileChooser();
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            file.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            File file1 = file.showSaveDialog(imagePicker.getScene().getWindow());
            imgs = ImageIO.read(file1);
            System.out.println(file1);
            image1 = new Image(file1.toURI().toString());
            img.setImage(image1);//Shows the selected Image
        }
    }


    public void selectPixelOnce(MouseEvent e) throws Exception {
        if (img.getImage() != null) {
            PixelReader preader = image1.getPixelReader();
            WritableImage wimage = new WritableImage((int) image1.getWidth(), (int) image1.getHeight());
            PixelWriter pwriter = wimage.getPixelWriter();
            double xloc = e.getX();
            double yloc = e.getY();
            Bounds bounds = img.getLayoutBounds();
            double xscale = bounds.getWidth() / img.getImage().getWidth();
            double yscale = bounds.getHeight() / img.getImage().getHeight();
            int NROWS = (int) image1.getHeight();
            int NCOlS = (int) image1.getWidth();
            int[] arr = new int[NROWS * NCOlS];
            xloc = xloc / xscale;
            yloc = yloc / yscale;
            Color color = preader.getColor((int) xloc, (int) yloc);
            IsColorBlack(color);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PopUpWindow.fxml"));
            Parent root = loader.load();
            //Get controller of scene2
            PopUpWindow scene2Controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
            oc = color;
            for (int i = 0; i < (int) image1.getHeight(); i++) {
                for (int j = 0; j < (int) image1.getWidth(); j++) {
                    boolean alreadyExist = ht1.containsKey((find2(arr, (int) (i * image1.getWidth() + j))));
                    Color col = preader.getColor(j, i);
                    int a = i * NCOlS + j;
                        if (!IsColorBlack(color) && col.getHue() > color.getHue() - slideHue.getValue() && col.getHue() < color.getHue() + slideHue.getValue() || IsColorBlack(color) && col.getBrightness() > color.getBrightness() - slideB.getValue() && col.getBrightness() < color.getBrightness() + slideB.getValue() && col.getSaturation() > color.getSaturation() - 0.027 && col.getSaturation() < color.getSaturation() + 0.027) {
                           // if(ht1.isEmpty()) {
                             //   pwriter.setColor(j, i, Color.BLACK);
                            //}

                            arr[a] = a;
                        } else {
                            arr[a] = -1;
                           /* if(ht1.isEmpty()) {
                                pwriter.setColor(j, i, Color.WHITE);
                            }*/
                        }
                    /*else {
                        if (!IsColorBlack(color) && col.getHue() > color.getHue() - slideHue.getValue() && col.getHue() < color.getHue() + slideHue.getValue() || IsColorBlack(color) && col.getBrightness() > color.getBrightness() - slideB.getValue() && col.getBrightness() < color.getBrightness() + slideB.getValue() && col.getSaturation() > color.getSaturation() - 0.027 && col.getSaturation() < color.getSaturation() + 0.027) {

                        }
                        /*Enumeration<Integer> keys = ht1.keys();
                        int width = (int)  img.getImage().getWidth();
                        while (keys.hasMoreElements()) {
                            // Getting the key of a particular entry
                            Integer key = keys.nextElement();
                            ArrayList<Integer> items;
                            if (ht1.get(key) == ht1.get((find2(arr, (int) (i * image1.getWidth() + j)))) && alreadyExist) {
                                items = ht1.get(key);
                                for (Integer item : items) {
                                    int row = item / width;
                                    int columns = item % width;
                                    pwriter.setColor(columns, row, Color.BLACK);
                                }
                            }
                        }*/
                }
                    //scene2Controller.setImage(wimage);
                }
            for (int row = 0; row < NROWS; row++) {
                for (int col = 0; col < NCOlS; col++) {
                    int elementThatImChecking = row * NCOlS + col;
                    if (col < NCOlS - 1 && arr[elementThatImChecking] != -1 && arr[elementThatImChecking + 1] != -1) {
                        union(arr, elementThatImChecking, elementThatImChecking + 1);
                    }
                    if (row < NROWS - 1 && arr[elementThatImChecking] != -1 && arr[elementThatImChecking + NCOlS] != -1) {
                        union(arr, elementThatImChecking, elementThatImChecking + NCOlS);
                    }
                }
            }
            for (int i = 0; i < (int) image1.getHeight(); i++) {
                for (int j = 0; j < (int) image1.getWidth(); j++) {
                    int elementThatImChecking = i * NCOlS + j;
                    boolean alreadyExist = ht1.containsKey((find2(arr, (int) (i * image1.getWidth() + j))));
                    if (!alreadyExist || ht1.isEmpty()) {
                        ArrayList<Integer> items = new ArrayList<>();
                        items.add(elementThatImChecking);
                        ht1.put(find2(arr, (int) (i * image1.getWidth() + j)), items);
                        System.out.println(ht1.get(find2(arr, (int) (i * image1.getWidth() + j))));
                    } else {
                        ArrayList items = ht1.get(find2(arr, (int) (i * image1.getWidth() + j)));
                        items.add(elementThatImChecking);
                        ht1.put(find2(arr, (int) (i * image1.getWidth() + j)), items);
                    }
                }
            }
            int width = (int)  img.getImage().getWidth();
            Enumeration<Integer> keys = ht1.keys();
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
                    for (Integer item : items) {
                        int row = item / width;
                        int columns = item % width;
                        pwriter.setColor(columns, row, Color.BLACK);
                    }
                }
            }
            scene2Controller.setImage(wimage);
        }
    }

    public static void union(int[] a, int p, int q) {
        a[find2(a, q)] = find2(a, p); //The root of q is made reference the root of p
    }

    public static int find2(int[] a, int id) {
        if (a[id] == -1) return a[id];
        if (a[id] == id) return id;
        else return find2(a, a[id]);
    }

    public boolean IsColorBlack(Color col) {
        return col.getSaturation() <= 0.240;
    }
    public int gettingWhite() {
        Enumeration<Integer> keys = ht1.keys();
        int whitekey = 0;
        int biggest = 0;
        // Iterating through the Hashtable
        // object
        while (keys.hasMoreElements()) {
            // Getting the key of a particular entry
            int key = keys.nextElement();
            if (ht1.get(key).size() >= biggest) {
                biggest = ht1.get(key).size();
                whitekey = key;
            }
        }
        return whitekey;
    }

    public void rect() {
        if (img.getImage() != null && rect.isSelected() || labels.isSelected()) {
            Set<Integer> setOfKeys = ht1.keySet();
            rectangles = new Rectangle[setOfKeys.size()];
            l = new Label[setOfKeys.size()];
        int width = (int) image1.getWidth();
        int height = (int) image1.getHeight();
        int r = 0;
        keys = ht1.keys();
        int pixeSize = 0;
            while (keys.hasMoreElements()) {
                // Getting the key of a particular entry
                int locNum = 0;
                double startX = width * height;
                double endX = 0;
                double startY = width * height;
                double endY = 0;
                int key = keys.nextElement();
                ArrayList<Integer> items;
                if (ht1.get(key).size() != ht1.get(gettingWhite()).size()) {
                    items = ht1.get(key);
                    pixeSize = ht1.get(key).size();
                    for (Integer item : items) {
                        int row = item / width;
                        int columns = item % width;
                        if (startX > columns) {
                            startX = columns;
                        }
                        if (endX < columns) {
                            endX = columns;
                        }
                        if (startY > row) {
                            startY = row;
                        }
                        if (endY < row) {
                            endY = row;
                        }
                    }
                    for (int a = 0; a < setOfKeys.size() - 1; a++) {
                        if (PopUpWindow.loc[a] == ht1.get(key).size()) {
                            locNum = a + 1;
                        }
                    }
                }
                Bounds bounds = img.getLayoutBounds();
                double xScale = bounds.getWidth() / img.getImage().getWidth();
                double yScale = bounds.getHeight() / img.getImage().getHeight();
                startX /= xScale;
                startY /= yScale;
                endX /= xScale;
                endY /= yScale;
                //((Pane) img.getParent()).getChildren().add(rectangle);
                if (labels.isSelected()) {
                    label = new Label(String.valueOf(locNum));
                    label.setTranslateX(startX + 25);
                    label.setTranslateY(startY + 25);
                    Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 10);
                    label.setFont(font);
                    label.setTextFill(Color.YELLOW);
                    l[r] = label;
                    //Filling color to the label
                    ((Pane) img.getParent()).getChildren().add(label);
                }
                if (rect.isSelected()) {
                    rectangle = new Rectangle(startX, startY, endX - startX, endY - startY);
                    rectangle.setTranslateX(img.getLayoutX());
                    rectangle.setTranslateY(img.getLayoutY());
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setStroke(Color.RED);
                    rectangles[r] = rectangle;
                    String msg = "Component Type: " + PopUpWindow.names + ")\n Component Number: " + locNum + ")\n Estimated sie(pixel units): "
                            + pixeSize + ")";
                    Tooltip.install(rectangle, new Tooltip(msg));
                    ((Pane) img.getParent()).getChildren().add(rectangle);

                }
                r++;
            }
        }
        else {
            warning.setText("Select A Box");
        }
    }
    public void removeRectangel(){
        if(rectangles != null) {
            for (int a = 0; a < rectangles.length; a++) {
                rectangle = rectangles[a];
                ((Pane) img.getParent()).getChildren().removeAll(rectangle);
            }
        }
        if(l != null) {
            for (int a = 0; a < l.length; a++) {
                label = l[a];
                ((Pane) img.getParent()).getChildren().removeAll(label);
            }
        }
    }
    //Creating another hashtable for the names of the objects including their names;
}
