/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.tietorakenteet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void kekoehtoJarjestaaNeljanKeon() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(0, 0, 5));
        keko.lisaaKekoon(new Ruutu(10, 20, 10));
        keko.lisaaKekoon(new Ruutu(2, 3, 4));
        keko.lisaaKekoon(new Ruutu(1, 1, 7));
        keko.varmistaKekoehto(1);
        assertEquals(2, keko.poistaPienin().getX());
    }
    
    @Test 
    public void kekoehtoJarjestaaKolmenKeon() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(0, 0, 1));
        keko.lisaaKekoon(new Ruutu(10, 20, 10));
        keko.lisaaKekoon(new Ruutu(2, 3, 4));
        keko.varmistaKekoehto(1);
        assertEquals(0, keko.poistaPienin().getX());
    }
    
    @Test 
    public void kekoehtoJarjestaaYhdenRuudunKeon() {
        MinimiKeko keko = new MinimiKeko();
        keko.lisaaKekoon(new Ruutu(2, 3, 4));
        keko.varmistaKekoehto(1);
        assertEquals(2, keko.poistaPienin().getX());
    }
    
    
}
