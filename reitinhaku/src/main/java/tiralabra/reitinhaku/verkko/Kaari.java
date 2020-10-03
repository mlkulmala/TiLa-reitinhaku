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
public class Kaari {
    
    private Ruutu lahtoSolmu, maaliSolmu;
    private double kaarenPituus;
    
    /**
     * Kaari solmusta lahto solmuun maali. Kaaren pituus on solmujen 
     * välinen etäisyys.
     * @param lahto solmu, josta reittiä alkavaa reittiä haetaan
     * @param maali solmu, johon haettava reitti päättyy
     * @param pituus lähtö- ja maalisolmun välinen etäisyys
     */
    public Kaari(Ruutu lahto, Ruutu maali, double pituus) {
        this.lahtoSolmu = lahto;
        this.maaliSolmu = maali;
        this.kaarenPituus = pituus;
                         
    }
    
    public Ruutu getLahtoSolmu() {
        return this.lahtoSolmu;
    }
    
    public Ruutu getMaaliSolmu() {
        return this.maaliSolmu;
    }
    
    public double getKaarenPituus() {
        return this.kaarenPituus;
    }
}
