
package tiralabra.reitinhaku.verkko;

/**
 * Luokka, joka kuvaa linkitetyn listan sisältämiä alkioita. Solmu sisältää tiedon
 * edellisestä ja seuraavasta solmusta, sekä siihen liittyvästä Ruudusta, joka 
 * kertoo sijainnin ja etäisyyden lähtösolmusta.
 * 
 * @author mlkul
 */
public class Solmu {
    
    private Solmu edellinen;
    private Solmu seuraava;
    private Ruutu ruutu;
    
    /**
     * Kontruktori, joka luo solmun, jonka edellinen ja seuraava solmu annetaan 
     * parametrina. 
     * @param edellinen
     * @param seuraava
     * @param ruutu 
     */
    public Solmu(Solmu edellinen, Solmu seuraava, Ruutu ruutu) {
        this.edellinen = edellinen;
        this.seuraava = seuraava;
        this.ruutu = ruutu;
    }
    
    /**
     * Konstruktori, joka luo uuden solmun, joka liittyy parametrina annettuun 
     * ruutuun.
     * @param ruutu 
     */
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
    
    /**
     * Palauttaa solmuun liittyvän ruudun, joka sisältää tiedon sijainnista.
     * @return 
     */
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
