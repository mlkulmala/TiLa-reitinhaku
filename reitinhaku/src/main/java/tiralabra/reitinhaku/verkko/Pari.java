/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.verkko;

/**
 *
 * @author mlkul
 */
public class Pari {
    
    private double etaisyys;
    private Ruutu edellinen;
    
    public Pari(double etaisyys, Ruutu edellinen) {
        this.etaisyys = etaisyys;
        this.edellinen = edellinen;
    }
    
    public double getEtaisyys() {
        return this.etaisyys;
    }
}
