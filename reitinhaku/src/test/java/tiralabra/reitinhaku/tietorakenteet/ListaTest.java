/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.tietorakenteet;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tiralabra.reitinhaku.verkko.Ruutu;

/**
 *
 * @author mlkul
 */
public class ListaTest {
    
   
    
    
    @Test
    public void konstruktoriToimiiOikein() {
        Lista lista = new Lista();
        assertEquals(0, lista.koko());
    }
    
    @Test
    public void listanTaulukkoKasvaaOikein() {
        Lista lista = new Lista();
        for (int i = 0; i < 51; i++) {
            lista.lisaa(new Ruutu(i,i,1));
        }
        assertEquals(100, lista.haeTaulukonKoko());
    }
    
    @Test
    public void listanKokoKasvaaOikein() {
        Lista lista = new Lista();
        for (int i = 0; i < 51; i++) {
            lista.lisaa(new Ruutu(i,i,1));
        }
        assertEquals(51, lista.koko());
    }
    
    @Test
    public void jarjestysKaantyyOikein() {
        Lista lista = new Lista();
        for (int i = 1; i <= 5 ; i++) {
            lista.lisaa(new Ruutu(i,i,1));
        }
        lista.kaannaJarjestys();
        assertEquals("(5, 5)", lista.hae(0).toString());
        assertEquals("(1, 1)", lista.hae(4).toString());
    }
    
    @Test
    public void onTyhjaAntaaOikeinKunListaTyhja() {
        Lista lista = new Lista();
        assertEquals(true, lista.onTyhja());
    }
    
    @Test
    public void onTyhjaAntaaOikeinKunListallaAlkioita() {
        Lista lista = new Lista();
        lista.lisaa(new Ruutu(1, 1, 1));
        lista.lisaa(new Ruutu(2, 2, 2));
        assertEquals(false, lista.onTyhja());
    }
    
    
}
