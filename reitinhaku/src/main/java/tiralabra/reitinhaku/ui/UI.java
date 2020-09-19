/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.ui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import tiralabra.reitinhaku.verkko.Reitinhaku;
import tiralabra.reitinhaku.verkko.Solmu;

/**
 *
 * @author mlkul
 */
public class UI extends Application {
    
    private Stage stage;
    private Reitinhaku reitinhaku;
    private PixelWriter kirjoittaja;
    private WritableImage reititKuvassa;
    
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Reitinhaku");
        this.stage = stage;
        reitinhaku = new Reitinhaku();
        
//        Canvas canvas = new Canvas(850, 550);
//        GraphicsContext piirtoalusta = canvas.getGraphicsContext2D();

        //kuvan lukeminen
        Image lontoo = new Image("file:Lontoo_rajattu_2.png");
        
        PixelReader pikselinLukija = lontoo.getPixelReader();
        
        ImageView kartta = new ImageView(lontoo);
        kartta.setFitWidth(700);
        kartta.setPreserveRatio(true);
        
        int leveys = (int) lontoo.getWidth();
        int korkeus = (int) lontoo.getHeight();
        
        //Miten piirretään reitti karttaan???
        reititKuvassa = new WritableImage(leveys, korkeus);
        kirjoittaja = reititKuvassa.getPixelWriter();
        
        //pohjusta taulukko [rivit][sarakkeet]
        reitinhaku.alustaKartta(leveys, korkeus);
        for (int y = 0; y < korkeus; y++) {
            for (int x = 0; x < leveys; x++) {
                Color vari = pikselinLukija.getColor(x, y);
                if(vari.equals(Color.BLACK)) {
                    reitinhaku.lisaaKarttaan(x, y, 1);
                } else {
                    reitinhaku.lisaaKarttaan(x, y, 0);
                }
            }
        }
        
        //gridin asettelu
        GridPane grid = new GridPane();
        grid.setPrefSize(900, 500);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.add(kartta, 0, 0);
        GridPane.setColumnSpan(kartta, 11);
        //piirraTausta(canvas.getGraphicsContext2D());
        //piirtoalusta.drawImage(lontoo, 0, 0);
        
        //Tekstikentät ja napit
        Label lahtoTeksti = new Label("Anna lähtöpaikan koordinaatit:");
        Label lahtoX = new Label("X");
        TextField lahtoXKentta = new TextField();
        Label lahtoY = new Label("Y");
        TextField lahtoYKentta = new TextField();
        
        Label maaliTeksti = new Label("Anna määränpään koordinaatit:");
        Label maaliX = new Label("X");
        TextField maaliXKentta = new TextField();
        Label maaliY = new Label("Y");
        TextField maaliYKentta = new TextField();
        
        ToggleGroup valintaNapit = new ToggleGroup();
        RadioButton rb_AStar = new RadioButton();
        RadioButton rb_Fringe = new RadioButton();
        rb_AStar.setToggleGroup(valintaNapit);
        rb_AStar.setText("A*");
        rb_Fringe.setToggleGroup(valintaNapit);
        rb_Fringe.setText("Fringe");
        
        //voiko koordinaatit laittaa valikkoon??? tämä kömpelöä
//        int aX = Integer.valueOf(lahtoXKentta.getText());
//        int aY = Integer.valueOf(lahtoYKentta.getText());
//        int bX = Integer.valueOf(maaliXKentta.getText());
//        int bY = Integer.valueOf(maaliYKentta.getText());
        
        Button hakuNappi = new Button("Hae");
        hakuNappi.setOnAction((event) -> {
            reitinhaku.alustaHaku();
            reitinhaku.suoritaHaku(0, 770, 600, 390, 1);
            piirraReitti(reitinhaku.getLyhinReitti());
            System.out.println("lyhin reitti alkaa: " + reitinhaku.getLyhinReitti().get(0));
        });
        
        //elementtien asettelu gridiin
        grid.add(lahtoTeksti, 1, 1);
        GridPane.setColumnSpan(lahtoTeksti, 4);
        grid.add(maaliTeksti, 5, 1);
        GridPane.setColumnSpan(maaliTeksti, 4);
        
        grid.add(lahtoX, 0, 2);
        grid.add(lahtoXKentta, 1, 2);
        grid.add(lahtoY, 2, 2);
        grid.add(lahtoYKentta, 3, 2);
        
        grid.add(maaliX, 4, 2);
        grid.add(maaliXKentta, 5, 2);
        grid.add(maaliY, 6, 2);
        grid.add(maaliYKentta, 7, 2);
        
        grid.add(rb_AStar, 8, 2);
        grid.add(rb_Fringe, 9, 2);
        grid.add(hakuNappi, 10, 2);
        //GridPane.setHalignment(rb_AStar, HPos.CENTER);
        
        
        
        
        Scene scene = new Scene(grid, 1000, 600);
       
        stage.setScene(scene);
        stage.show();
           
               
    }
    
    //Miten toteutetaan reitin piirtäminen??
    public void piirraReitti(ArrayList<Solmu> reitti) {
        for (Solmu s : reitti) {
            int x = s.getX();
            int y = s.getY();
            //kirjoittaja.setColor(x, y, Color.RED);
            //Circle c = new Circle(x, y, 10, Color.RED);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
