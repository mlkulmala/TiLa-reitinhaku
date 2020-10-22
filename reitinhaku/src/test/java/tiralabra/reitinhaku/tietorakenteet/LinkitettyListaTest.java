package tiralabra.reitinhaku.tietorakenteet;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.reitinhaku.verkko.Ruutu;
import tiralabra.reitinhaku.verkko.Solmu;

/**
 *
 * @author mlkul
 */
public class LinkitettyListaTest {
    
    LinkitettyLista lista;
    Solmu a, b, c;
    
    @Before
    public void setUp() {
        a = new Solmu(new Ruutu(0, 0, 0));
        b = new Solmu(new Ruutu(1, 2, 3));
        c = new Solmu(new Ruutu(3, 3, 1));
        lista = new LinkitettyLista();
    }
    
    @Test
    public void getEnsimmainenAntaaNullKunListaTyhja() {
        assertEquals(null, lista.getEnsimmainen());
    }
    
    @Test
    public void getViimeinenAntaaNullKunListaTyhja() {
        assertEquals(null, lista.getViimeinen());
    }
    
    
//    @Test
//    public void onkoListallaAntaaTrueJosSolmuListalla() {
//        assertEquals(true, lista.onkoListallaSolmu(a));
//    }
//    
//    @Test
//    public void onkoListallaAntaaFalseJosSolmuEiOleListalla() {
//        Solmu c = new Solmu(new Ruutu(3, 3, 3));
//        assertEquals(false, lista.onkoListallaSolmu(c));
//    }
//    
//    @Test
//    public void onkoListallaAntaaFalseJosListaTyhja() {
//        lista = new LinkitettyLista();
//        Solmu c = new Solmu(new Ruutu(3, 3, 3));
//        assertEquals(false, lista.onkoListallaSolmu(c));
//    }
    
    @Test
    public void lisaaSolmunJalkeenToimii() {
        lista.lisaaAlkuun(a);
        lista.lisaaSolmunJalkeen(a, b);
        lista.lisaaSolmunJalkeen(a, c);
        assertEquals("(3, 3)", lista.getEnsimmainen().getSeuraava().getRuutu().toString());
        assertEquals("(1, 2)", lista.getViimeinen().getRuutu().toString());
    }
   
    
    @Test
    public void lisaaEnnenSolmuaToimii() {
        lista.lisaaAlkuun(a);
        lista.lisaaEnnenSolmua(a, b);
        lista.lisaaEnnenSolmua(b, c);
        assertEquals("(1, 2)", lista.getEnsimmainen().getSeuraava().getRuutu().toString());
        assertEquals("(3, 3)", lista.getEnsimmainen().getRuutu().toString());
    }
    
    
    @Test
    public void lisaaAlkuunToimiiKunListaTyhja() {
        lista.lisaaAlkuun(b);
        assertEquals(b, lista.getEnsimmainen());
        assertEquals(b, lista.getViimeinen());
    }
    
    @Test
    public void lisaaAlkuunToimiiKunListallaAlkioita() {
        lista.lisaaAlkuun(a);
        lista.lisaaLoppuun(b);
        lista.lisaaAlkuun(c);
        assertEquals(c, lista.getEnsimmainen());
    }
    
    
    @Test
    public void lisaaLoppuunToimiiKunListaTyhja() {
        lista.lisaaLoppuun(c);
        assertEquals(c, lista.getViimeinen());
        assertEquals(c, lista.getEnsimmainen());
    }
    
    @Test
    public void lisaaLoppuunToimiiKunListallaAlkioita() {
        lista.lisaaLoppuun(a);
        lista.lisaaAlkuun(b);
        lista.lisaaLoppuun(c);
        assertEquals(c, lista.getViimeinen());
    }
    
    @Test
    public void poistaSolmuToimiiListanLopussa() {
        lista.lisaaAlkuun(a);
        lista.lisaaLoppuun(b);
        lista.lisaaLoppuun(c);
        lista.poistaSolmu(c);
        assertEquals(b, lista.getViimeinen());
    }
    
    @Test
    public void poistaSolmuToimiiListanAlussa() {
        lista.lisaaLoppuun(a);
        lista.lisaaLoppuun(b);
        lista.lisaaAlkuun(c);
        lista.poistaSolmu(c);
        assertEquals(a, lista.getEnsimmainen());
    }
     
    @Test
    public void poistaAlustaToimii() {
        lista.poistaAlusta();
        assertEquals(null, lista.getEnsimmainen());
        assertEquals(null, lista.getViimeinen());
    }
    
    @Test
    public void poistaLopustaToimii() {
        lista.poistaLopusta();
        assertEquals(null, lista.getEnsimmainen());
        assertEquals(null, lista.getViimeinen());
    }
    
    @Test
    public void onTyhjaAntaaFalseKunListallaSolmuja() {
        lista.lisaaAlkuun(a);
        assertEquals(false, lista.onTyhja());
    }
    
    @Test
    public void onTyhjaAntaaTrueKunListaTyhja() {
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void onTyhjaAntaaTrueKunListaTyhjennetty() {
        lista.lisaaLoppuun(a);
        lista.poistaAlusta();
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void poistaAlustaTyhjentaaListan() {
        lista.lisaaLoppuun(a);
        lista.lisaaLoppuun(c);
        lista.poistaAlusta();
        lista.poistaAlusta();
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void poistaLopustaTyhjentaaListan() {
        lista.lisaaAlkuun(c);
        lista.lisaaAlkuun(b);
        lista.poistaLopusta();
        lista.poistaLopusta();
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void poistoTyhjastaListastaEiTeeMitaan() {
        lista.poistaAlusta();
        assertEquals(null, lista.getEnsimmainen());
        assertEquals(true, lista.onTyhja());
    }
}


