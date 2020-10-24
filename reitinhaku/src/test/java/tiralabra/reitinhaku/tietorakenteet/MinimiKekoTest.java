
package tiralabra.reitinhaku.tietorakenteet;


import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.reitinhaku.verkko.Ruutu;

/**
 *
 * @author mlkul
 */
public class MinimiKekoTest {
    
    @Test
    public void konstruktoriToimiiOikein() {
        MinimiKeko keko = new MinimiKeko();
        assertEquals(0, keko.haeKoko());
    }
    
    @Test
    public void onTyhjaAntaaOikeinKunKekoTyhja() {
        MinimiKeko keko = new MinimiKeko();
        assertEquals(true, keko.onTyhja());
    }
    
    @Test
    public void kekoKasvaaOikein() {
        MinimiKeko keko = new MinimiKeko();
        for (int i = 0; i < 51; i++) {
            keko.lisaaKekoon(new Ruutu(i,i,1));
        }
        assertEquals(100, keko.haeTaulukonKoko());
    }
    
    @Test
    public void poistaPieninTyhjastaKeostaPalauttaaNull() {
        MinimiKeko keko = new MinimiKeko();
        assertEquals(null, keko.poistaPienin());
    }
    
    @Test 
    public void poistaPieninPalauttaaPienimman() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(0, 0, 5));
        keko.lisaaKekoon(new Ruutu(10, 20, 1));
        keko.lisaaKekoon(new Ruutu(2, 3, 4));
        assertEquals(10, keko.poistaPienin().getX());
    }
    
    @Test 
    public void lisaaKekoonNeljaKasvattaaKekoaOikein() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(10, 20, 10));
        keko.lisaaKekoon(new Ruutu(0, 0, 7));
        keko.lisaaKekoon(new Ruutu(2, 3, 4));
        keko.lisaaKekoon(new Ruutu(1, 1, 1));
        assertEquals(1, keko.poistaPienin().getX());
    }
    
    @Test 
    public void lisaaKekoonViisiKasvattaaKekoaOikein() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(10, 20, 10));
        keko.lisaaKekoon(new Ruutu(6, 6, 8));
        keko.lisaaKekoon(new Ruutu(0, 0, 7));
        keko.lisaaKekoon(new Ruutu(2, 3, 4));
        keko.lisaaKekoon(new Ruutu(1, 1, 1));
        assertEquals(1, keko.poistaPienin().getX());
    }
    
    @Test 
    public void lisaaKekoonKaksiKasvattaaKekoaOikein() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(10, 20, 10));
        keko.lisaaKekoon(new Ruutu(7, 3, 4));
        keko.varmistaKekoehto(1);
        assertEquals(7, keko.poistaPienin().getX());
    }
    
    @Test 
    public void lisaaJaPoistaKasvattaaKekoaOikein() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(10, 20, 10));
        keko.lisaaKekoon(new Ruutu(1, 1, 7));
        keko.lisaaKekoon(new Ruutu(7, 3, 4));
        keko.poistaPienin();
        keko.poistaPienin();
        assertEquals(10, keko.poistaPienin().getX());
    }
    
    @Test 
    public void kekoehtoJarjestaaIsommanKeon() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(7, 8, 25));
        keko.lisaaKekoon(new Ruutu(4, 1, 18));
        keko.lisaaKekoon(new Ruutu(5, 6, 10));
        keko.lisaaKekoon(new Ruutu(0, 0, 8));
        keko.lisaaKekoon(new Ruutu(4, 4, 9));
        keko.lisaaKekoon(new Ruutu(2, 3, 5));
        keko.lisaaKekoon(new Ruutu(3, 3, 3));
        keko.lisaaKekoon(new Ruutu(1, 0, 1));
        keko.poistaPienin();
        assertEquals(3, keko.poistaPienin().getX());
    }
    
    @Test
    public void poistaPieninToimiiKunKeossaSamojaPainoja() {
        MinimiKeko keko = new MinimiKeko();
        for (int i = 1; i <= 10; i++) {
            keko.lisaaKekoon(new Ruutu(i, i, 1));
        }
        for (int i = 1; i <= 5; i++) {
            keko.poistaPienin();
        }
        assertEquals(6, keko.poistaPienin().getX());
    }
    
    @Test
    public void tyhjaKekoOnTyhja() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(1,1,1));
        keko.poistaPienin();
        assertEquals(true, keko.onTyhja());
    }
    
    @Test
    public void onTyhjaAntaaFalseKunKeossaAlkioita() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(1,1,1));
        assertEquals(false, keko.onTyhja());
    }
    
    
}
