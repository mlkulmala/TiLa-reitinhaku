
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tiralabra.reitinhaku.algoritmit.DijkstraAStar;
import tiralabra.reitinhaku.algoritmit.FringeListoina;
import tiralabra.reitinhaku.algoritmit.FringeSearch;
import tiralabra.reitinhaku.algoritmit.FringeTaulukkona;
import tiralabra.reitinhaku.tietorakenteet.Lista;

import tiralabra.reitinhaku.verkko.Kartta;
import tiralabra.reitinhaku.verkko.Ruutu;

/**
 * Sovelluksen graafinen käyttöliittymä.
 * 
 * @author mlkul
 */
public class UI extends Application {
    
    private Stage stage;
    private Kartta kartta;
    private int leveys, korkeus, aX, bX, aY, bY;
    private Label virheilmoitus, testitulos; 
    Lista<Integer> koordinaatit;
    private boolean lahtoValittu;
    private GridPane grid;
    private Canvas canvas;
    private GraphicsContext piirtoalusta;
    private Image karttakuva;
    private TextField lahtoX, lahtoY, maaliX, maaliY;
    private RadioButton rbDijkstra, rbAStar, rbFringe;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Reitinhaku");
        this.stage = stage;
        kartta = new Kartta();

        karttakuva = new Image("file:Milano_bitmap.bmp", 768, 427, true, false);
        leveys = (int) karttakuva.getWidth();
        korkeus = (int) karttakuva.getHeight();
        
        PixelReader pikselinLukija = karttakuva.getPixelReader();
        luePikselitTaulukkoon(pikselinLukija);

        luoGridPane();
        luoPiirtoalusta();
        piirtoalusta.drawImage(karttakuva, 0, 0);
        
        lisaaKoordinaattienSyotto();
        lisaaKoordinaattienHakuKartalta();
                
        Lista<TextField> kentat = new Lista<>();
        kentat.lisaa(lahtoX);
        kentat.lisaa(lahtoY);
        kentat.lisaa(maaliX);
        kentat.lisaa(maaliY);
        
        lisaaAlgoritminValinta();
        lisaaReitinhaku(kentat);
        lisaaTyhjennaNappi();
        lisaaVirheIlmoitus();
        testitulos = new Label("");
        grid.add(testitulos, 2, 3);
        
        Scene scene = new Scene(grid, 1000, 600);
        stage.setScene(scene);
        stage.show();      
    }
    
    /**
     * Lukee karttakuvan pikselit ja tallentaa tiedon binäärilukutaulukkona (kartta).
     * 0 = kuljettavissa, 1 = rakennus tai muu este.
     * @param lukija 
     */
    public void luePikselitTaulukkoon(PixelReader lukija) {
        kartta.alustaKartta(leveys, korkeus);
        for (int y = 0; y < korkeus; y++) {
            for (int x = 0; x < leveys; x++) {
                Color vari = lukija.getColor(x, y);
                if (vari.equals(Color.BLACK)) {
                    kartta.lisaaKarttaan(x, y, 1);
                } else {
                    kartta.lisaaKarttaan(x, y, 0);
                }
            }
        }
    }
    
    /**
     * Luo gridin eli näkymän asettelun perusrungon.
     */
    public void luoGridPane() {
        grid = new GridPane();
        grid.setPrefSize(900, 700);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(30);
        grid.setPadding(new Insets(20, 20, 20, 20));
    }
    
    /**
     * Luo piirtoalustan, jolle haettu reitti piirretään.
     */
    public void luoPiirtoalusta() {
        canvas = new Canvas(800, 430);
        piirtoalusta = canvas.getGraphicsContext2D();
        grid.add(canvas, 0, 0);
        GridPane.setColumnSpan(canvas, 5);
        GridPane.setHalignment(canvas, HPos.CENTER);
    }
    
    /**
     * Lisää tekstikentät koordinaattien syöttöä varten.
     */
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
    
    /**
     * Lisää piirtoalustaan ominaisuuden, joka lukee piirtoalustalla olevasta 
     * karttakuvasta koordinaatit. 
     */
    public void lisaaKoordinaattienHakuKartalta() {
        canvas.setOnMouseClicked(event -> {
            double x = event.getX(), y = event.getY();
            if (!lahtoValittu) {
                aX = (int) Math.round(x);
                aY = (int) Math.round(y);
                lahtoValittu = true;
                lahtoX.setText(Integer.toString(aX));
                lahtoY.setText(Integer.toString(aY));
                piirtoalusta.setFill(Color.RED);
                piirtoalusta.fillOval(aX - 3, aY - 3, 6, 6);
            } else {
                bX = (int) Math.round(x);
                bY = (int) Math.round(y);
                lahtoValittu = false;
                maaliX.setText(Integer.toString(bX));
                maaliY.setText(Integer.toString(bY));
                piirtoalusta.setFill(Color.RED);
                piirtoalusta.fillOval(bX - 3, bY - 3, 6, 6);
            }
        });
    }
    
    /**
     * Lisää radionapit, joilla haluttu hakualgoritmi valitaan.
     */
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
    
    /**
     * Lisää hakunapin, jota painamalla reitinhaku käynnistyy
     * @param kentat Tekstikentät, joista haettavan reitin alku- ja loppupisteen 
     * koordinaatit luetaan.
     */
    public void lisaaReitinhaku(Lista<TextField> kentat) {
        Button hakuNappi = new Button("Hae");
        grid.add(hakuNappi, 3, 2);
        
        hakuNappi.setOnAction((event) -> {
            kartta.tyhjennaReitti();
            //putsataan pöytä jos vanhoja reittihakuja:
            virheilmoitus.setText("");
            testitulos.setText("");
            piirtoalusta.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            piirtoalusta.drawImage(karttakuva, 0, 0);

            Lista<Integer> koordinaatit = haeKoordinaatit(kentat);
            if (koordinaatit.koko() < 4) {
                return;
            }
            aX = koordinaatit.hae(0);
            aY = koordinaatit.hae(1);
            bX = koordinaatit.hae(2);
            bY = koordinaatit.hae(3);
            if (aX < 0 || aX > leveys - 1 || aY < 0 || aY > korkeus - 1) {
                virheilmoitus.setText("Tarkista lähtöpisteen koordinaatit. Voit myös valita reitin alku- ja päätepisteen kartalta.");
                return;
            } 
            if (bX < 0 || bX > leveys - 1 || bY < 0 || bY > korkeus - 1) {
                virheilmoitus.setText("Tarkista päätepisteen koordinaatit. Voit myös valita reitin alku- ja päätepisteen kartalta.");
                return;
            } 
            if (!kartta.onkoKuljettavissa(aX, aY) || !kartta.onkoKuljettavissa(bX, bY)) {
                virheilmoitus.setText("Jompikumpi valitsemistasi pisteistä on rakennuksen tai seinän sisällä.");
            } else {
                long alku = System.nanoTime();
                if (rbDijkstra.isSelected()) {
                    DijkstraAStar da = new DijkstraAStar(kartta);
                    da.alustaHaku();
                    da.haeReitti(koordinaatit, 1, piirtoalusta);
                } else if (rbAStar.isSelected()) {
                    DijkstraAStar da = new DijkstraAStar(kartta);
                    da.alustaHaku();
                    da.haeReitti(koordinaatit, 2, piirtoalusta);
                } else {
//                    FringeSearch fringe = new FringeSearch(kartta);
//                    fringe.alustaHaku();
//                    fringe.haeReitti(koordinaatit, piirtoalusta);   
                    FringeTaulukkona fringe = new FringeTaulukkona(kartta); 
                    fringe.alustaHaku();
                    fringe.haeReitti(koordinaatit, piirtoalusta);
                }
                long loppu = System.nanoTime();
                double kesto = 1.0 * (loppu - alku) / 1000000;
                testitulos.setText("Aikaa kului: " + kesto + " ms");
                if (kartta.haeLyhinReitti().onTyhja()) {
                    virheilmoitus.setText("Valitsemiesi pisteiden välillä ei ole reittiä.");
                } else {
                    piirraReitti(kartta.haeLyhinReitti(), piirtoalusta);
                }
            }
        });
        
    }
    
    /**
     * Lisää napin, josta aiemmat valinnat saa tyhjennettyä. Tyhjentää sekä 
     * tekstikentät että aiemmin piirretyn reitin.
     */
    public void lisaaTyhjennaNappi() {
        Button tyhjennaNappi = new Button("Tyhjennä valinnat");
        tyhjennaNappi.setOnAction((event) -> {
            //kartta.alustaHaku();
            //tyhjennä tekstikentät ja piirretty reitti
            lahtoX.clear();
            lahtoY.clear();
            maaliX.clear();
            maaliY.clear();
            piirtoalusta.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            piirtoalusta.drawImage(karttakuva, 0, 0);
            virheilmoitus.setText("");
            testitulos.setText("");
        });
        grid.add(tyhjennaNappi, 4, 2);
    }
    
    /**
     * Lisää tekstikentän virheilmoituksia varten.
     */
    public void lisaaVirheIlmoitus() {
        virheilmoitus = new Label("");
        grid.add(virheilmoitus, 0, 3);
        GridPane.setColumnSpan(virheilmoitus, 4);
    }
    
    /**
     * Lisää tekstikentän, jossa näytetään algoritmin suorittaman reitinhaun 
     * kesto.
     */
    public void lisaaTestitulokset() {
        Label testitulos = new Label("");
        grid.add(testitulos, 2, 3);
        
    }
    
    /**
     * Piirtää reitin piirtoalustaan.
     * @param reitti 
     * @param alusta 
     */
    public void piirraReitti(Lista<Ruutu> reitti, GraphicsContext alusta) {
        for (int i = 0; i < reitti.koko(); i++) {
            alusta.setFill(Color.RED);
            alusta.fillOval(reitti.hae(i).getX() - 2, reitti.hae(i).getY() - 2, 4, 4);
        }
    }
    
    /**
     * Pyyhkii aiemmin piirretyn reitin piirtoalustalta.
     * @param reitti
     * @param alusta 
     */
    public void tyhjennaReitti(Lista<Ruutu> reitti, GraphicsContext alusta) {
        for (int i = 0; i < reitti.koko(); i++) {
            alusta.setFill(Color.TRANSPARENT);
            alusta.fillOval(reitti.hae(i).getX() - 2, reitti.hae(i).getY() - 2, 4, 4);
        }
    }
    
    /**
     * Lukee tekstikenttiin kirjoitetut koordinaatit ja muuttaa ne kokonaisluvuiksi.
     * Antaa virheilmoituksen, jos yksikin kentistä on tyhjä.
     * @param kentat Luettavat tekstikentät.
     * @return Palauttaa koordinaatit kokonaislukulistana.
     */
    public Lista<Integer> haeKoordinaatit(Lista<TextField> kentat) {
        koordinaatit = new Lista();
        try {
            for (int i = 0; i < kentat.koko(); i++) {
                int koordinaatti = Integer.valueOf(kentat.hae(i).getText());
                koordinaatit.lisaa(koordinaatti);
            }
        } catch (Exception e) {
            virheilmoitus.setText("Et antanut kaikkia koordinaatteja. Voit myös valita reitin alku- ja päätepisteen kartalta.");
        } 
        return koordinaatit;
    } 
    
 
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
