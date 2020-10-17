package tiralabra.reitinhaku.verkko;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mlkul
 */
public class RuutuTest {
    
    Ruutu ruutu;
    
    @Test
    public void kontruktoriToimiiOikein() {
        ruutu = new Ruutu(1, 2, 3);
        assertEquals(1, ruutu.getX());
        assertEquals(2, ruutu.getY());
    }
    
    @Test
    public void ruutujenVertailuToimiiPienemmanKanssa() {
        ruutu = new Ruutu(1, 2, 3);
        Ruutu vertailukohde = new Ruutu(1, 1, 1);
        assertEquals(1, ruutu.compareTo(vertailukohde));
    }
    
    @Test
    public void ruutujenVertailuToimiiSuuremmanKanssa() {
        ruutu = new Ruutu(1, 2, 3);
        Ruutu vertailukohde = new Ruutu(5, 4, 5);
        assertEquals(-1, ruutu.compareTo(vertailukohde));
    }
    
    @Test
    public void ruutujenVertailuToimiiYhtaSuurenKanssa() {
        ruutu = new Ruutu(1, 2, 3);
        Ruutu vertailukohde = new Ruutu(2, 1, 3);
        assertEquals(0, ruutu.compareTo(vertailukohde));
    }
    
}
