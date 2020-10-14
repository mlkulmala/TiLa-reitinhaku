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
public class Lista<T> {
    
    private T[] lista;
    private int indeksi;
    
    public Lista() {
        this.lista = (T[]) new Object[50];
        this.indeksi = 0;
    }
    
    public void lisaa(T arvo) {
        if (indeksi == lista.length) {
            kasvata();
        } 
        lista[indeksi++] = arvo;
    }
    
    public void kasvata() {
        int uusiKoko = lista.length * 2;  
        T[] kopio = (T[]) new Object[uusiKoko];  
        for(int i = 0; i < lista.length; i++) {
            kopio[i] = lista[i];
        }
        lista = kopio;    
    }
    
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
    
    public boolean onTyhja() {
        return indeksi == 0;
    }
    
    public int koko() {
        return indeksi;
    }
    
   
    public int haeTaulukonKoko() {
        return lista.length;
    }
    
    public T hae(int i) {
        return lista[i];
    }
}
