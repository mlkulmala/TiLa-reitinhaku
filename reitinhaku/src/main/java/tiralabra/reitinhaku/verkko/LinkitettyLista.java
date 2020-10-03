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
public class LinkitettyLista {
    
    private Solmu ensimmainen;
    private Solmu viimeinen;
    
    public LinkitettyLista(Solmu ensimmainen, Solmu viimeinen) {
        this.ensimmainen = ensimmainen;
        this.viimeinen = viimeinen;
        this.ensimmainen.setSeuraava(viimeinen);
        this.viimeinen.setEdellinen(ensimmainen);
    }
    
    public LinkitettyLista() {
        this.ensimmainen = null;
        this.viimeinen = null;
    }
    
    public Solmu getEnsimmainen() {
        return this.ensimmainen;
    }
    
    public Solmu getViimeinen() {
        return this.viimeinen;
    }
    
    public void setEnsimmainen(Solmu solmu) {
        this.ensimmainen = solmu;
    }
    
    public void setViimeinen(Solmu solmu) {
        this.viimeinen = solmu;
    }
    
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
    
    public void poistaAlusta() {
        if (this.ensimmainen != null) {  //true
            Solmu poistettava = this.ensimmainen;  //(0,0)
            if (poistettava.getSeuraava() != null) {  // true, getSEuraava = (1, 0)
                this.ensimmainen = poistettava.getSeuraava();
                this.ensimmainen.setEdellinen(null);//this.ensimmainen = (1, 0)
            } else {
                this.ensimmainen = null;
                this.viimeinen = null;
            }
            poistettava.setEdellinen(null);
            poistettava.setSeuraava(null);
        }
        
        
    }
    
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
    
    public boolean onTyhja() {
        if (this.ensimmainen == null && this.viimeinen == null) {
            return true;
        } else {
            return false;
        }
    }
    public void tulostaListanSolmut() {
        Solmu s = this.ensimmainen;
        while (s != null) {
            System.out.print(s.getRuutu().toString() + ", ");
            s = s.getSeuraava();
        }
    }
}
