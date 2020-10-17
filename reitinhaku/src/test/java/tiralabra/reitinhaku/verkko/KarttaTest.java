package tiralabra.reitinhaku.verkko;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mlkul
 */
public class KarttaTest {
    
    Kartta kartta;
    int[][] suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
    
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
    public void karttaKuljettavissaKunArvoNolla() {
        kartta.lisaaKarttaan(0, 0, 1);
        kartta.lisaaKarttaan(0, 1, 0);
        assertEquals(true, kartta.onkoKuljettavissa(0, 1));
        assertEquals(false, kartta.onkoKuljettavissa(0, 0));
    }
    
}
