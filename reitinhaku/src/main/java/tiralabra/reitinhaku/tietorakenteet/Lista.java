
package tiralabra.reitinhaku.tietorakenteet;

import tiralabra.reitinhaku.verkko.Ruutu;

/**
 * Taulukkolista, jonka sisältämien olioiden tyyppi määräytyy ohjelman 
 * suorituksen aikana.
 * @author mlkul
 */
public class Lista<T> {
    
    private T[] lista;
    private int indeksi;
    
    /**
     * Luo uuden listan, jonka sisällä oleva taulukon koko on 50.
     */
    public Lista() {
        this.lista = (T[]) new Object[50];
        this.indeksi = 0;
    }
    
    /**
     * Lisää listalle alkion, jonka tyyppi on T.
     * @param arvo 
     */
    public void lisaa(T arvo) {
        if (indeksi == lista.length) {
            kasvata();
        } 
        lista[indeksi++] = arvo;
    }
    
    /**
     * Poistaa annetusta indeksistä alkion.
     * @param mista 
     */
    public void poista(int mista) {
        if (mista < 0) {
            return;
        }
        for (int i = mista; i < indeksi - 1; i++) {
            lista[i] = lista[i + 1];
        }
    }
    
    /**
     * Luokan sisäinen metodi, joka kasvattaa taulukkoa listan kasvaessa.
     */
    private void kasvata() {
        int uusiKoko = lista.length * 2;  
        T[] kopio = (T[]) new Object[uusiKoko];  
        for (int i = 0; i < lista.length; i++) {
            kopio[i] = lista[i];
        }
        lista = kopio;    
    }
    
    /**
     * Kaantaa listan järjestyksen päinvastaiseksi.
     */
    public void kaannaJarjestys() {
        int alku = 0;
        int loppu = indeksi - 1;
        while (alku < loppu) {
            T apu = lista[alku];
            lista[alku] = lista[loppu];
            lista[loppu] = apu;
            alku++;
            loppu--;
        }
    }
    
    /**
     * Tarkistaa, onko listalla alkioita.
     * @return Palauttaa true, jos lista on tyhjä.
     */
    public boolean onTyhja() {
        return indeksi == 0;
    }
    
    /**
     * Palauttaa listan koon (ja samalla seuraavaksi lisättävän alkion indeksin).
     */
    public int koko() {
        return indeksi;
    }
    
    /**
     * Palauttaa taulukon koon. Metodi luotu testejä varten.
     * @return 
     */
    public int haeTaulukonKoko() {
        return lista.length;
    }
    
    /**
     * Hakee alkion parametrina annetusta indeksistä.
     * @param i
     */
    public T hae(int i) {
        return lista[i];
    }
}
