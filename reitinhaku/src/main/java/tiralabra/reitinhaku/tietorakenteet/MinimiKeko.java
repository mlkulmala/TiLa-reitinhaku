/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.tietorakenteet;

import tiralabra.reitinhaku.verkko.Ruutu;

/**
 *
 * @author mlkul
 */
public class MinimiKeko {
    
    private Ruutu[] keko;
    private int viimeinen;
    
    public MinimiKeko() {
        this.keko = new Ruutu[50];
        this.viimeinen = 0;
    }
    
    public int vanhempi(int indeksi) {
        return indeksi / 2;
    }
    
    public int vasenLapsi(int indeksi) {
        return 2 * indeksi;
    }
    
    public int oikeaLapsi(int indeksi) {
        return 2 * indeksi + 1;
    }
    
    public void lisaaKekoon(Ruutu r) {
        viimeinen++;  //taulukon kohtaa 0 ei käytetä
        if (viimeinen == keko.length) {
            kasvata();
        }
        int i = viimeinen;
        while (i > 1 && keko[vanhempi(i)].compareTo(r) == 1) {     //jos viimeisen ruudun vanhempi on suurempi
            keko[i] = keko[vanhempi(i)];                   //vaihdetaan ruutujen paikkaa
            i = vanhempi(i);
        }
        keko[i] = r;
    }
    
    // sama kuin Lista-luokassa
    public void kasvata() {
        int uusiKoko = keko.length * 2;  
        Ruutu[] kopio = new Ruutu[uusiKoko];  
        for (int i = 0; i < keko.length; i++) {
            kopio[i] = keko[i];
        }
        keko = kopio;    
    }
    
    /**
     * Poimii keon juuresta ruudun, jonka arvo on pienin. Vertailtava arvo on
     * ruudun etäisyys tai etäisyysarvio algoritmista riippuen.
     * @return 
     */
    public Ruutu poistaPienin() {
        Ruutu r = keko[1];             
        keko[1] = keko[viimeinen];      
        keko[viimeinen] = null;
        viimeinen--;
        varmistaKekoehto(1);
        return r;
    }
    
    public void varmistaKekoehto(int indeksi) {
        int vasen = vasenLapsi(indeksi);
        int oikea = oikeaLapsi(indeksi);
        int pienin = 0;
        if (oikea <= viimeinen) {
            if (keko[oikea].compareTo(keko[vasen]) == 1) {
                pienin = vasen;
            } else {
                pienin = oikea;
            }
            if (keko[indeksi].compareTo(keko[pienin]) == 1) {
                vaihdaRuutujenPaikkaa(indeksi, pienin);
                varmistaKekoehto(pienin);
            }
        } else if (vasen == viimeinen && keko[indeksi].compareTo(keko[vasen]) == 1) {
            vaihdaRuutujenPaikkaa(indeksi, vasen);
        }
    }
    
    public void vaihdaRuutujenPaikkaa(int eka, int toka) {
        Ruutu apu = keko[eka];
        keko[eka] = keko[toka];
        keko[toka] = apu;
    }
    
    public boolean onTyhja() {
        return viimeinen == 0;
    }
    
    public int haeKoko() {
        return viimeinen;
    }
    
    public int haeTaulukonKoko() {
        return keko.length;
    }
}
