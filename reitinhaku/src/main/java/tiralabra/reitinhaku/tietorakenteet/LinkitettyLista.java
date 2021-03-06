
package tiralabra.reitinhaku.tietorakenteet;

import tiralabra.reitinhaku.verkko.Solmu;

/**
 * Kahteen suuntaan linkitetty lista, joka pitää sisällään Solmu-olioita. 
 * Kullakin solmulla on tieto edellisestä ja seuraavasta solmusta.
 * 
 * @author mlkul
 */
public class LinkitettyLista {
    
    private Solmu ensimmainen;
    private Solmu viimeinen;
    
    
    /**
     * Konstruktori, joka luo tyhjän listan.
     */
    public LinkitettyLista() {
        this.ensimmainen = null;
        this.viimeinen = null;
    }
    
//    public LinkitettyLista(Solmu solmu) {
//        this.ensimmainen = solmu;
//        this.viimeinen = solmu;
//    }
    
    public Solmu getEnsimmainen() {
        return this.ensimmainen;
    }
    
    public Solmu getViimeinen() {
        return this.viimeinen;
    }
    
    
    /**
     * Lisää solmun jonkin tietyn solmun jälkeen.
     * @param solmu Solmu, jonka perään lisätään.
     * @param uusiSolmu Lisättävä solmu.
     */
    public void lisaaSolmunJalkeen(Solmu solmu, Solmu uusiSolmu) {
        uusiSolmu.setEdellinen(solmu);
        if (solmu.getSeuraava() == null) {
            this.viimeinen = uusiSolmu;
        } else {
            uusiSolmu.setSeuraava(solmu.getSeuraava());
            solmu.getSeuraava().setEdellinen(uusiSolmu);
        }
        solmu.setSeuraava(uusiSolmu);
    }
    
    /**
     * Lisää solmun tiettyä solmua.
     * @param solmu Solmu, jonka edelle lisätään.
     * @param uusiSolmu LIsättävä solmu.
     */
    public void lisaaEnnenSolmua(Solmu solmu, Solmu uusiSolmu) {
        uusiSolmu.setSeuraava(solmu);
        if (solmu.getEdellinen() == null) {
            this.ensimmainen = uusiSolmu;
        } else {
            uusiSolmu.setEdellinen(solmu.getEdellinen());
            solmu.getEdellinen().setSeuraava(uusiSolmu);
        }
        solmu.setEdellinen(uusiSolmu);
    }
    
    /**
     * Lisää solmun listan loppuun.
     * @param solmu 
     */
    public void lisaaLoppuun(Solmu solmu) {
        if (this.viimeinen != null) {
            this.viimeinen.setSeuraava(solmu);
            solmu.setEdellinen(this.viimeinen);
        }
        if (this.ensimmainen == null && this.viimeinen == null) {
            this.ensimmainen = solmu;
        }
        this.viimeinen = solmu;
    }
    
    /**
     * Lisää solmun listan alkuun.
     * @param solmu 
     */
    public void lisaaAlkuun(Solmu solmu) {
        if (this.ensimmainen != null) {
            this.ensimmainen.setEdellinen(solmu);
            solmu.setSeuraava(this.ensimmainen);
        }
        if (this.ensimmainen == null && this.viimeinen == null) {
            this.viimeinen = solmu;
        }
        this.ensimmainen = solmu;
        
    }
    
    /**
     * Poistaa parametrina annetun solmun listalta.
     * @param solmu 
     */
    public void poistaSolmu(Solmu solmu) {
        if (solmu.getEdellinen() == null) {
            this.ensimmainen = solmu.getSeuraava();
        } else {
            solmu.getEdellinen().setSeuraava(solmu.getSeuraava());
        }
        if (solmu.getSeuraava() == null) {
            this.viimeinen = solmu.getEdellinen();
        } else {
            solmu.getSeuraava().setEdellinen(solmu.getEdellinen());
        }
    }
    
  
    
    /**
     * Poistaa solmun listan alusta.
     */
    public void poistaAlusta() {
        if (this.ensimmainen != null) {  //true
            Solmu poistettava = this.ensimmainen;  
            if (poistettava.getSeuraava() != null) {  
                this.ensimmainen = poistettava.getSeuraava();
                this.ensimmainen.setEdellinen(null);  
            } else {
                this.ensimmainen = null;
                this.viimeinen = null;
            }
            poistettava.setEdellinen(null);
            poistettava.setSeuraava(null);
        }
        
        
    }
    
    /**
     * Poistaa solmun listan lopusta.
     */
    public void poistaLopusta() {
        if (this.viimeinen != null) { //true
            Solmu poistettava = this.viimeinen;  // = (1, 0)
            if (poistettava.getEdellinen() != null) {
                this.viimeinen = poistettava.getEdellinen();
                this.viimeinen.setSeuraava(null);
            } else {
                this.viimeinen = null;
                this.ensimmainen = null;
            }
            poistettava.setEdellinen(null);
            poistettava.setSeuraava(null);
        }
    }
    
    /**
     * Tarkistaa, onko lista tyhjä. 
     * @return Palauttaa true, jos listalla ei ole alkioita.
     */
    public boolean onTyhja() {
        if (this.ensimmainen == null && this.viimeinen == null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Tulostaa listan solmut (luotu omia tarkistuksia varten).
     */
//    public void tulostaListanSolmut() {
//        Solmu s = this.ensimmainen;
//        while (s != null) {
//            System.out.print(s.getRuutu().toString() + ", ");
//            s = s.getSeuraava();
//        }
//    }
}
