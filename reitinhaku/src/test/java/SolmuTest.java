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
import tiralabra.reitinhaku.verkko.Ruutu;
import tiralabra.reitinhaku.verkko.Solmu;

/**
 *
 * @author mlkul
 */
public class SolmuTest {
    
    Solmu solmu;
    
    @Before
    public void setUp() {
        solmu = new Solmu(new Ruutu(0, 5, 0));
    }
    
    @Test
    public void konstruktoriToimii() {
        assertEquals(null, solmu.getEdellinen());
        assertEquals(null, solmu.getSeuraava());
    }
    
    @Test
    public void konstruktoriParametreillaToimii() {
        Solmu edellinen = new Solmu(new Ruutu(0, 0, 0));
        Solmu seuraava = new Solmu(new Ruutu(1, 1, 1));
        solmu = new Solmu(edellinen, seuraava, new Ruutu(2, 2, 2));
        assertEquals("(0, 0)", solmu.getEdellinen().getRuutu().toString());
        assertEquals("(1, 1)", solmu.getSeuraava().getRuutu().toString());
    }
    
    @Test
    public void setEdellinenToimii() {
        solmu.setEdellinen(new Solmu(new Ruutu(2, 2, 0)));
        assertEquals("(2, 2)", solmu.getEdellinen().getRuutu().toString());
    }
    
    @Test
    public void setSeuraavaToimii() {
        solmu.setSeuraava(new Solmu(new Ruutu(3, 0, 0)));
        assertEquals("(3, 0)", solmu.getSeuraava().getRuutu().toString());
    }
    
    
}
