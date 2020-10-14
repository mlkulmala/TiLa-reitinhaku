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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tiralabra.reitinhaku.tietorakenteet.Lista;

import tiralabra.reitinhaku.verkko.Reitinhaku;
import tiralabra.reitinhaku.verkko.Ruutu;

/**
 *
 * @author mlkul
 */
public class UI extends Application {
    
    private Stage stage;
    private Reitinhaku reitinhaku;
    private int leveys, korkeus, aX, bX, aY, bY;
    private Label virheIlmoitus = new Label("");
    Lista<Integer> koordinaatit;
    private boolean lahtoValittu;
    private GridPane grid;
    private Canvas canvas;
    private GraphicsContext piirtoalusta;
    private Image kartta;
    private TextField lahtoX, lahtoY, maaliX, maaliY;
    private RadioButton rbDijkstra, rbAStar, rbFringe;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Reitinhaku");
        this.stage = stage;
        reitinhaku = new Reitinhaku();

        kartta = new Image("file:Milano_bitmap.bmp", 768, 427, true, false);
        leveys = (int) kartta.getWidth();
        korkeus = (int) kartta.getHeight();
        
        PixelReader pikselinLukija = kartta.getPixelReader();
        luePikselitTaulukkoon(pikselinLukija);

        luoGridPane();
        luoPiirtoalusta();
        piirtoalusta.drawImage(kartta, 0, 0);
        
        lisaaKoordinaattienSyotto();
        lisaaKoordinaattienHakuKartalta();
                
        Lista<TextField> kentat = new Lista<>();
        kentat.lisaa(lahtoX);
        kentat.lisaa(lahtoY);
        kentat.lisaa(maaliX);
        kentat.lisaa(maaliY);
        
        lisaaAlgoritminValinta();
      
        lisaaReitinhakuNappi(kentat);
        lisaaTyhjennaNappi();
     
        grid.add(virheIlmoitus, 0, 3);
        GridPane.setColumnSpan(virheIlmoitus, 4);
        
        Scene scene = new Scene(grid, 1000, 600);
        stage.setScene(scene);
        stage.show();      
    }
    
    public void luePikselitTaulukkoon(PixelReader lukija) {
        reitinhaku.alustaKartta(leveys, korkeus);
        for (int y = 0; y < korkeus; y++) {
            for (int x = 0; x < leveys; x++) {
                Color vari = lukija.getColor(x, y);
                if (vari.equals(Color.BLACK)) {
                    reitinhaku.lisaaKarttaan(x, y, 1);
                } else {
                    reitinhaku.lisaaKarttaan(x, y, 0);
                }
            }
        }
    }
    
    public void luoGridPane() {
        grid = new GridPane();
        grid.setPrefSize(900, 700);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(30);
        grid.setPadding(new Insets(20, 20, 20, 20));
    }
    
    public void luoPiirtoalusta() {
        canvas = new Canvas(800, 430);
        piirtoalusta = canvas.getGraphicsContext2D();
        grid.add(canvas, 0, 0);
        GridPane.setColumnSpan(canvas, 5);
        GridPane.setHalignment(canvas, HPos.CENTER);
    }
    
    public void lisaaKoordinaattienSyotto() {
        Label lbLahtoTeksti = new Label("Anna lähtöpaikan koordinaatit:");
        Label lbLahtoX = new Label("X");
        lahtoX = new TextField();
        lahtoX.setPrefWidth(50);
        
        Label lbLahtoY = new Label("Y");
        lahtoY = new TextField();
        lahtoY.setPrefWidth(50);
        HBox lahtoKoordinaatit = new HBox(8);
        lahtoKoordinaatit.getChildren().addAll(lbLahtoX, lahtoX, lbLahtoY, lahtoY);
        lahtoKoordinaatit.setAlignment(Pos.CENTER_LEFT);
        
        Label maaliTeksti = new Label("Anna määränpään koordinaatit:");
        Label lbMaaliX = new Label("X");
        maaliX = new TextField();
        maaliX.setPrefWidth(50);
        
        Label lbMaaliY = new Label("Y");
        maaliY = new TextField();
        maaliY.setPrefWidth(50);
        HBox maaliKoordinaatit = new HBox(8);
        maaliKoordinaatit.getChildren().addAll(lbMaaliX, maaliX, lbMaaliY, maaliY);
        maaliKoordinaatit.setAlignment(Pos.CENTER_LEFT);
        
        grid.add(lbLahtoTeksti, 0, 1);
        grid.add(maaliTeksti, 1, 1);
        grid.add(lahtoKoordinaatit, 0, 2);
        grid.add(maaliKoordinaatit, 1, 2);
    }
    
    public void lisaaKoordinaattienHakuKartalta() {
        canvas.setOnMouseClicked(event -> {
            double x = event.getX(), y = event.getY();
            if (!lahtoValittu) {
                aX = (int) Math.round(x);
                aY = (int) Math.round(y);
                lahtoValittu = true;
                lahtoX.setText(Integer.toString(aX));
                lahtoY.setText(Integer.toString(aY));
            } else {
                bX = (int) Math.round(x);
                bY = (int) Math.round(y);
                lahtoValittu = false;
                maaliX.setText(Integer.toString(bX));
                maaliY.setText(Integer.toString(bY));
            }
        });
    }
    
    public void lisaaAlgoritminValinta() {
        Label hakutapa = new Label("Valitse hakualgoritmi:");
        ToggleGroup valintaNapit = new ToggleGroup();
        rbDijkstra = new RadioButton("Dijkstra");
        rbAStar = new RadioButton("A*");
        rbFringe = new RadioButton("Fringe Search");
        rbDijkstra.setSelected(true);
        rbDijkstra.setToggleGroup(valintaNapit);
        rbAStar.setToggleGroup(valintaNapit);
        rbFringe.setToggleGroup(valintaNapit);
        HBox napit = new HBox(8);
        napit.getChildren().addAll(rbDijkstra, rbAStar, rbFringe);
        napit.setAlignment(Pos.CENTER_LEFT);
        grid.add(napit, 2, 2);
    }
    
    public void lisaaReitinhakuNappi(Lista<TextField> kentat) {
        Button hakuNappi = new Button("Hae");
        grid.add(hakuNappi, 3, 2);
        
        hakuNappi.setOnAction((event) -> {
            reitinhaku.alustaHaku();
            //putsataan pöytä jos vanhoja reittihakuja:
            virheIlmoitus.setText("");
            piirtoalusta.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            piirtoalusta.drawImage(kartta, 0, 0);

            Lista<Integer> koordinaatit = haeKoordinaatit(kentat);
            if (koordinaatit.koko() < 4) {
                return;
            }
            aX = koordinaatit.hae(0);
            aY = koordinaatit.hae(1);
            bX = koordinaatit.hae(2);
            bY = koordinaatit.hae(3);
            if (aX < 0 || aX > leveys - 1 || aY < 0 || aY > korkeus - 1) {
                virheIlmoitus.setText("Tarkista lähtöpisteen koordinaatit. Voit myös valita reitin alku- ja päätepisteen kartalta.");
                return;
            } 
            if (bX < 0 || bX > leveys - 1 || bY < 0 || bY > korkeus - 1) {
                virheIlmoitus.setText("Tarkista päätepisteen koordinaatit. Voit myös valita reitin alku- ja päätepisteen kartalta.");
                return;
            } 
            if (!reitinhaku.onkoKadulla(aX, aY) || !reitinhaku.onkoKadulla(bX, bY)) {
                virheIlmoitus.setText("Jompikumpi valitsemistasi pisteistä on rakennuksen tai seinän sisällä.");
            } else {
                if (rbDijkstra.isSelected()) {
                    reitinhaku.suoritaHaku(koordinaatit, 1);
                } else if (rbAStar.isSelected()) {
                    reitinhaku.suoritaHaku(koordinaatit, 2);
                } else {
                    reitinhaku.fringeSearch(koordinaatit);
                }
                //reitinhaku.fringeSearch(koordinaatit);
                //reitinhaku.suoritaHaku(koordinaatit, 2);   //hakutapa 1 viittaa tällä hetkellä Dijkstraan
                if (reitinhaku.getLyhinReitti().onTyhja()) {
                    virheIlmoitus.setText("Valitsemiesi pisteiden välillä ei ole reittiä.");
                } else {
                    piirraReitti(reitinhaku.getLyhinReitti(), piirtoalusta);
                }
            }
        });
        
    }
    
    public void lisaaTyhjennaNappi() {
        Button tyhjennaNappi = new Button("Tyhjennä valinnat");
        tyhjennaNappi.setOnAction((event) -> {
            reitinhaku.alustaHaku();
            //tyhjennä tekstikentät ja piirretty reitti
            //
            lahtoX.clear();
            lahtoY.clear();
            maaliX.clear();
            maaliY.clear();
            piirtoalusta.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            piirtoalusta.drawImage(kartta, 0, 0);
            virheIlmoitus.setText("");
        });
        grid.add(tyhjennaNappi, 4, 2);
    }
    
    
    //reitin piirtäminen kartalle
    public void piirraReitti(Lista<Ruutu> reitti, GraphicsContext alusta) {
        for (int i = 0; i < reitti.koko(); i++) {
            alusta.setFill(Color.RED);
            alusta.fillOval(reitti.hae(i).getX() - 2, reitti.hae(i).getY() - 2, 4, 4);
        }
    }
    
    //reitin pyyhkiminen kartalta
    public void tyhjennaReitti(Lista<Ruutu> reitti, GraphicsContext alusta) {
        for (int i = 0; i < reitti.koko(); i++) {
            //tyhjennä väritykset
            alusta.setFill(Color.TRANSPARENT);
            alusta.fillOval(reitti.hae(i).getX() - 2, reitti.hae(i).getY() - 2, 4, 4);
        }
    }
    
    
    public Lista<Integer> haeKoordinaatit(Lista<TextField> kentat) {
        koordinaatit = new Lista();
        try {
            for (int i = 0; i < kentat.koko(); i++) {
                int koordinaatti = Integer.valueOf(kentat.hae(i).getText());
                koordinaatit.lisaa(koordinaatti);
            }
        } catch (Exception e) {
            virheIlmoitus.setText("Et antanut kaikkia koordinaatteja. Voit myös valita reitin alku- ja päätepisteen kartalta.");
        } 
        return koordinaatit;
    } 
    
    
    
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
