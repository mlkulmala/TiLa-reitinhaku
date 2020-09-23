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
import java.util.List;
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
    private int leveys, korkeus;
    
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Reitinhaku");
        this.stage = stage;
        reitinhaku = new Reitinhaku();
        
        

        //kuvan lukeminen
        //Image lontoo = new Image("file:Lontoo_700px.JPG", 700, 471, true, false);
        //Image lontoo = new Image("file:Milano_rajattu.png, 700, 389, true, false");
        //Image lontoo = new Image("file:Lontoo_700px.JPG");
        Image karttaKuva = new Image("file:Milano_bitmap.bmp", 768, 427, true, false);
        leveys = (int) karttaKuva.getWidth();
        korkeus = (int) karttaKuva.getHeight();
        
        PixelReader pikselinLukija = karttaKuva.getPixelReader();
        
        //ImageView kartta = new ImageView(lontoo);
//        kartta.setFitWidth(700);
//        kartta.setPreserveRatio(true);

        //luodaan canvas ja tuodaan siihen kuva
        Canvas canvas = new Canvas(800, 430);
        GraphicsContext piirtoalusta = canvas.getGraphicsContext2D();
        piirtoalusta.drawImage(karttaKuva, 0, 0);
        
       
        
        //pohjusta taulukko [rivit][sarakkeet]
        reitinhaku.alustaKartta(leveys, korkeus);
        //kuvan pikselien lukeminen
        for (int y = 0; y < korkeus; y++) {
            for (int x = 0; x < leveys; x++) {
                Color vari = pikselinLukija.getColor(x, y);
                if (vari.equals(Color.BLACK)) {
                    reitinhaku.lisaaKarttaan(x, y, 1);
                } else {
                    reitinhaku.lisaaKarttaan(x, y, 0);
                }
            }
        }
        
        //gridin asettelu
        GridPane grid = new GridPane();
        grid.setPrefSize(900, 700);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(30);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.add(canvas, 0, 0);
        GridPane.setColumnSpan(canvas, 5);
        GridPane.setHalignment(canvas, HPos.CENTER);
        
        
        //Tekstikentät ja napit
        //lisaaTekstikentat();
        Label lahtoTeksti = new Label("Anna lähtöpaikan koordinaatit:");
        Label lahtoX = new Label("X");
        TextField lahtoXkentta = new TextField();
        lahtoXkentta.setPrefWidth(50);
        
        Label lahtoY = new Label("Y");
        TextField lahtoYkentta = new TextField();
        lahtoYkentta.setPrefWidth(50);
        HBox lahtoKoordinaatit = new HBox(8);
        lahtoKoordinaatit.getChildren().addAll(lahtoX, lahtoXkentta, lahtoY, lahtoYkentta);
        lahtoKoordinaatit.setAlignment(Pos.CENTER_LEFT);
        
        Label maaliTeksti = new Label("Anna määränpään koordinaatit:");
        Label maaliX = new Label("X");
        TextField maaliXkentta = new TextField();
        maaliXkentta.setPrefWidth(50);
        
        Label maaliY = new Label("Y");
        TextField maaliYkentta = new TextField();
        maaliYkentta.setPrefWidth(50);
        HBox maaliKoordinaatit = new HBox(8);
        maaliKoordinaatit.getChildren().addAll(maaliX, maaliXkentta, maaliY, maaliYkentta);
        maaliKoordinaatit.setAlignment(Pos.CENTER_LEFT);
        
        //lisää valintanapit ja buttonit
        Label hakutapa = new Label("Valitse hakualgoritmi:");
        ToggleGroup valintaNapit = new ToggleGroup();
        RadioButton rbAStar = new RadioButton();
        RadioButton rbFringe = new RadioButton();
        rbAStar.setToggleGroup(valintaNapit);
        rbAStar.setText("A*");
        rbFringe.setToggleGroup(valintaNapit);
        rbFringe.setText("Fringe");
        HBox napit = new HBox(8);
        napit.getChildren().addAll(rbAStar, rbFringe);
        napit.setAlignment(Pos.CENTER_LEFT);
        
        
        
        //poimitaan tekstikentistä koordinaatit
        
//        int aX = Integer.valueOf(lahtoXKentta.getText());
//        int aY = Integer.valueOf(lahtoYKentta.getText());
//        int bX = Integer.valueOf(maaliXKentta.getText());
//        int bY = Integer.valueOf(maaliYKentta.getText());
        Label virheTeksti = new Label();

        Button hakuNappi = new Button("Hae");
        hakuNappi.setOnAction((event) -> {
            reitinhaku.alustaHaku();
            
            int aX = haeKoordinaatti(lahtoXkentta.getText(), virheTeksti);
            int aY = haeKoordinaatti(lahtoYkentta.getText(), virheTeksti);
            int bX = haeKoordinaatti(maaliXkentta.getText(), virheTeksti);
            int bY = haeKoordinaatti(maaliYkentta.getText(), virheTeksti);
            reitinhaku.suoritaHaku(aX, aY, bX, bY, 1);
            piirraReitti(reitinhaku.getLyhinReitti(), piirtoalusta);
        });
        
        Button tyhjennaNappi = new Button("Tyhjennä valinnat");
        tyhjennaNappi.setOnAction((event) -> {
            reitinhaku.alustaHaku();
            //tyhjennä tekstikentät ja piirretty reitti
            lahtoXkentta.clear();
            lahtoYkentta.clear();
            maaliXkentta.clear();
            maaliYkentta.clear();
            piirtoalusta.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            piirtoalusta.drawImage(karttaKuva, 0, 0);
            virheTeksti.setText("");
        });
        
        
        //elementtien asettelu gridiin
        grid.add(lahtoTeksti, 0, 1);
        grid.add(maaliTeksti, 1, 1);
        grid.add(lahtoKoordinaatit, 0, 2);
        grid.add(maaliKoordinaatit, 1, 2);
        grid.add(napit, 2, 2);
        grid.add(hakuNappi, 3, 2);
        grid.add(tyhjennaNappi, 4, 2);
        grid.add(virheTeksti, 0, 3);
        GridPane.setColumnSpan(virheTeksti, 4);
        
        
        
        
        Scene scene = new Scene(grid, 1000, 600);
       
        stage.setScene(scene);
        stage.show();
           
               
    }
    
    //reitin piirtäminen kartalle
    public void piirraReitti(ArrayList<Solmu> reitti, GraphicsContext alusta) {
        for (Solmu s : reitti) {
            alusta.setFill(Color.RED);
            alusta.fillOval(s.getX() - 2, s.getY() - 2, 4, 4);
        }
    }
    
    //reitin pyyhkiminen kartalta
    public void tyhjennaReitti(ArrayList<Solmu> reitti, GraphicsContext alusta) {
        for (Solmu s : reitti) {
            //tyhjennä väritykset
            alusta.setFill(Color.TRANSPARENT);
            alusta.fillOval(s.getX() - 2, s.getY() - 2, 4, 4);
        }
    }
    
    //tämä vielä koodissa ylempänä
    public void lisaaTekstikentat() {
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
    }
    
    public int haeKoordinaatti(String numero, Label label) {
        int koordinaatti = -1;
        if (!numero.isEmpty()) {
            try {
                koordinaatti = Integer.parseInt(numero);
                if (koordinaatti < 0 || koordinaatti > leveys) {
                    throw new IllegalArgumentException("Syötit negatiivisen luvun."); 
                    //label.setText("Syötit negatiivisen luvun.");
                }
//            } catch (IllegalArgumentException i) {
//                label.setText("väärä luku");
            } catch (Exception e) {
                label.setText("Et syöttänyt kunnollista numeroa.");
            } 
        } else {
            label.setText("Anna koordinaatit.") ;
        }
        
        return koordinaatti;
    } 
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
