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
public class Lista {
    
    private Ruutu[] lista;
    private int indeksi;
    
    public Lista() {
        this.lista = new Ruutu[50];
        this.indeksi = 0;
    }
    
    public void lisaa(Ruutu r) {
        if (indeksi == lista.length) {
            kasvata();
        } 
        lista[indeksi++] = r;
    }
    
    public void kasvata() {
        int uusiKoko = lista.length * 2;  
        Ruutu[] kopio = new Ruutu[uusiKoko];  
        for(int i = 0; i < lista.length; i++) {
            kopio[i] = lista[i];
        }
        lista = kopio;    
    }
    
    public void kaannaJarjestys() {
        int alku = 0;
        int loppu = indeksi - 1;
        while (alku < loppu) {
            Ruutu apu = lista[alku];
            lista[alku] = lista[loppu];
            lista[loppu] = apu;
            alku++;
            loppu--;
        }
    }
    
    public int haeListanKoko() {
        return indeksi;
    }
    
    public int haeTaulukonKoko() {
        return lista.length;
    }
    
    public Ruutu haeIndeksista(int i) {
        return lista[i];
    }
}
