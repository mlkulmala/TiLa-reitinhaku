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
public class Solmu {
    
    private Solmu edellinen;
    private Solmu seuraava;
    private Ruutu ruutu;
    
    public Solmu(Solmu edellinen, Solmu seuraava, Ruutu ruutu) {
        this.edellinen = edellinen;
        this.seuraava = seuraava;
        this.ruutu = ruutu;
    }
    
    public Solmu(Ruutu ruutu) {
        this.edellinen = null;
        this.seuraava = null;
        this.ruutu = ruutu;
    }
    
    public Solmu getEdellinen() {
        return this.edellinen;
    }
    
    public Solmu getSeuraava() {
        return this.seuraava;
    }
    
    public Ruutu getRuutu() {
        return this.ruutu;
    }
    
    public void setEdellinen(Solmu solmu) {
        this.edellinen = solmu;
    }
    
    public void setSeuraava(Solmu solmu) {
        this.seuraava = solmu;
    }
    
    
}
