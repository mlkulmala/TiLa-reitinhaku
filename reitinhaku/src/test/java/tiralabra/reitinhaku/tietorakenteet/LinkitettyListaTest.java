/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.reitinhaku.verkko.LinkitettyLista;
import tiralabra.reitinhaku.verkko.Ruutu;
import tiralabra.reitinhaku.verkko.Solmu;

/**
 *
 * @author mlkul
 */
public class LinkitettyListaTest {
    
    LinkitettyLista lista;
    Solmu a, b;
    
    @Before
    public void setUp() {
        a = new Solmu(new Ruutu(0, 0, 0));
        b = new Solmu(new Ruutu(1, 0, 1));
        lista = new LinkitettyLista(a, b);
    }
    
    @Test
    public void getEnsimmainenToimii() {
        lista = new LinkitettyLista();
        assertEquals(null, lista.getEnsimmainen());
    }
    
    @Test
    public void getViimeinenToimii() {
        lista = new LinkitettyLista();
        assertEquals(null, lista.getViimeinen());
    }
    
    @Test
    public void konstruktoriParametreillaToimii() {
        assertEquals(a, lista.getEnsimmainen());
        assertEquals(b, lista.getViimeinen());
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
    public void lisaaSolmunJalkeenToimiiKeskella() {
        lista.lisaaSolmunJalkeen(a, new Solmu(new Ruutu(0, 2, 2)));
        assertEquals("(0, 2)", lista.getEnsimmainen().getSeuraava().getRuutu().toString());
    }
   
    @Test
    public void lisaaSolmunJalkeenToimiiLopussa() {
        lista.lisaaSolmunJalkeen(b, new Solmu(new Ruutu(0, 2, 2)));
        assertEquals("(0, 2)", lista.getViimeinen().getRuutu().toString());
    }
    
    @Test
    public void lisaaEnnenSolmuaToimiiKeskella() {
        lista.lisaaEnnenSolmua(b, new Solmu(new Ruutu(0, 2, 2)));
        assertEquals("(0, 2)", lista.getEnsimmainen().getSeuraava().getRuutu().toString());
    }
    
    @Test
    public void lisaaEnnenSolmuaToimiiAlussa() {
        lista.lisaaEnnenSolmua(a, new Solmu(new Ruutu(0, 2, 2)));
        assertEquals("(0, 2)", lista.getEnsimmainen().getRuutu().toString());
    }
    
    @Test
    public void lisaaAlkuunToimii() {
        lista.lisaaAlkuun(new Solmu(new Ruutu(0, 2, 2)));
        assertEquals("(0, 2)", lista.getEnsimmainen().getRuutu().toString());
    }
    
    @Test
    public void lisaaTyhjanListanAlkuunToimii() {
        lista = new LinkitettyLista();
        lista.lisaaAlkuun(new Solmu(new Ruutu(0, 2, 2)));
        assertEquals("(0, 2)", lista.getEnsimmainen().getRuutu().toString());
        assertEquals("(0, 2)", lista.getViimeinen().getRuutu().toString());
    }
    
    @Test
    public void lisaaLoppuunToimii() {
        lista.lisaaLoppuun(new Solmu(new Ruutu(0, 2, 2)));
        assertEquals("(0, 2)", lista.getViimeinen().getRuutu().toString());
    }
    
    @Test
    public void lisaaTyhjanListanLoppuunToimii() {
        lista = new LinkitettyLista();
        lista.lisaaLoppuun(new Solmu(new Ruutu(0, 2, 2)));
        assertEquals("(0, 2)", lista.getEnsimmainen().getRuutu().toString());
        assertEquals("(0, 2)", lista.getViimeinen().getRuutu().toString());
    }
    
    @Test
    public void poistaSolmuToimiiListanLopussa() {
        Solmu c = new Solmu(new Ruutu(3, 3, 3));
        lista.lisaaLoppuun(c);
        lista.poistaSolmu(c);
        assertEquals("(1, 0)", lista.getViimeinen().getRuutu().toString());
    }
    
    @Test
    public void poistaSolmuToimiiListanAlussa() {
        Solmu c = new Solmu(new Ruutu(3, 3, 3));
        lista.lisaaAlkuun(c);
        lista.poistaSolmu(c);
        assertEquals("(0, 0)", lista.getEnsimmainen().getRuutu().toString());
    }
    
    @Test
    public void poistaAlustaToimii() {
        lista.poistaAlusta();
        assertEquals("(1, 0)", lista.getEnsimmainen().getRuutu().toString());
    }
    
    @Test
    public void poistaLopustaToimii() {
        lista.poistaLopusta();
        assertEquals("(0, 0)", lista.getViimeinen().getRuutu().toString());
    }
    
    @Test
    public void onTyhjaAntaaFalseKunListallaSolmuja() {
        lista.poistaAlusta();
        assertEquals(false, lista.onTyhja());
    }
    
    @Test
    public void onTyhjaAntaaTrueKunListaTyhja() {
        lista = new LinkitettyLista();
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void onTyhjaAntaaTrueKunListaTyhjennetty() {
        lista.poistaAlusta();
        lista.poistaLopusta();
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void poistaAlustaTyhjentaaListan() {
        Solmu c = new Solmu(new Ruutu(3, 3, 3));
        lista.lisaaLoppuun(c);
        lista.poistaAlusta();
        lista.poistaAlusta();
        lista.poistaAlusta();
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void poistaLopustaTyhjentaaListan() {
        Solmu c = new Solmu(new Ruutu(3, 3, 3));
        lista.lisaaLoppuun(c);
        lista.poistaLopusta();
        lista.poistaLopusta();
        lista.poistaLopusta();
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void poistoTyhjastaListastaEiOnnistu() {
        lista = new LinkitettyLista();
        lista.poistaAlusta();
        assertEquals(null, lista.getEnsimmainen());
        assertEquals(true, lista.onTyhja());
    }
}


