package tiralabra.reitinhaku.verkko;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.reitinhaku.algoritmit.DijkstraAStar;
import tiralabra.reitinhaku.tietorakenteet.Lista;

/**
 *
 * @author mlkul
 */
public class KarttaTest {
    
    Kartta kartta;
    int[][] suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
    Lista<Ruutu> lyhinReitti;
    
    @Before
    public void setUp() {
        kartta = new Kartta();
        kartta.alustaKartta(100, 50);
    }
    
    @Test
    public void kartanAlustusToimiiOikein() {
        assertEquals(100, kartta.getLeveys());
        assertEquals(50, kartta.getKorkeus());
    }
    
    @Test
    public void onkoKuljettavissaToimiiOikein() {
        kartta.lisaaKarttaan(0, 0, 1);
        kartta.lisaaKarttaan(0, 1, 0);
        assertEquals(true, kartta.onkoKuljettavissa(0, 1));
        assertEquals(false, kartta.onkoKuljettavissa(0, 0));
    }
    
    @Test
    public void karttaanEiVoiLisataMuitaLukuja() {
        kartta.lisaaKarttaan(5, 5, 5);
        assertEquals(true, kartta.onkoKuljettavissa(5, 5));
    }
    
    @Test
    public void ruutuEiOleKuljettavissaKartanUlkopuolella() {
        assertEquals(false, kartta.ruutuOnKuljettavissa(-1, 0));
        assertEquals(false, kartta.ruutuOnKuljettavissa(0, -1));
        assertEquals(false, kartta.ruutuOnKuljettavissa(0, kartta.getKorkeus() + 1));
        assertEquals(false, kartta.ruutuOnKuljettavissa(kartta.getLeveys() + 1, 0));
    }
    
    @Test
    public void ruutuOnKuljettavissaKartanSisallaKunArvoNolla() {
        kartta.lisaaKarttaan(7, 7, 0);
        assertEquals(true, kartta.ruutuOnKuljettavissa(7, 7));
    }
    
    @Test
    public void euklidinenEtaisyysLasketaanOikein() {
        assertEquals(5, (int) kartta.euklidinenEtaisyys(2, 3, 5, 7));
    }
    
    @Test
    public void kuljetaanDiagonaalisestiTrueKunLiikutaanViistoon() {
        assertEquals(true, kartta.kuljetaanDiagonaalisesti(1));
        assertEquals(true, kartta.kuljetaanDiagonaalisesti(3));
        assertEquals(true, kartta.kuljetaanDiagonaalisesti(5));
        assertEquals(true, kartta.kuljetaanDiagonaalisesti(7));
    }
    
    @Test
    public void kuljetaanDiagonaalisestiFalseKunLiikutaanSuoraan() {
        assertEquals(false, kartta.kuljetaanDiagonaalisesti(0));
        assertEquals(false, kartta.kuljetaanDiagonaalisesti(2));
        assertEquals(false, kartta.kuljetaanDiagonaalisesti(4));
        assertEquals(false, kartta.kuljetaanDiagonaalisesti(6));
    }
    
    @Test
    public void ruutujenValinenEtaisyysOikeinKunRuudutSivuittain() {
        assertEquals(1, (int) kartta.ruutujenValinenEtaisyys(0));
        assertEquals(1, (int) kartta.ruutujenValinenEtaisyys(2));
        assertEquals(1, (int) kartta.ruutujenValinenEtaisyys(4));
        assertEquals(1, (int) kartta.ruutujenValinenEtaisyys(6));
    }
    
     @Test
    public void ruutujenValinenEtaisyysOikeinKunRuudutDiagonaalisesti() {
        assertEquals(Math.sqrt(2), kartta.ruutujenValinenEtaisyys(1), 0.0f);
        assertEquals(Math.sqrt(2), kartta.ruutujenValinenEtaisyys(3), 0.0f);
        assertEquals(Math.sqrt(2), kartta.ruutujenValinenEtaisyys(5), 0.0f);
        assertEquals(Math.sqrt(2), kartta.ruutujenValinenEtaisyys(7), 0.0f);
    }
    
//    @Test
//    public void tyhjennaReittiLuoTyhjanListan() {
//        lyhinReitti = new Lista<>();
//        lyhinReitti.lisaa(new Ruutu(0, 0, 0));
//        lyhinReitti.lisaa(new Ruutu(1, 1, 1));
//        kartta.tyhjennaReitti();
//        assertEquals(null, lyhinReitti.hae(0));
//    }
    

}
