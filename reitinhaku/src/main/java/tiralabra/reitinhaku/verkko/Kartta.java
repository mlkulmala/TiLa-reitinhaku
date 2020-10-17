 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.verkko;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import tiralabra.reitinhaku.tietorakenteet.Lista;
import tiralabra.reitinhaku.tietorakenteet.MinimiKeko;

/**
 *
 * @author mlkul
 */
public class Reitinhaku {
    
    int leveys, korkeus, inf;
    boolean[][] kasitelty;
    MinimiKeko jono;
    int[][] kartta, suunnat;
    double[][] etaisyys;
    Ruutu[][] mista;
    Lista lyhinReitti;
    double reitinPituus, fLimit, fMin;
    LinkitettyLista lista;
    
    //määritellään leveys ja korkeus
    public void alustaKartta(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        kartta = new int[korkeus][leveys];
    }
    
    //kuljettavat ruudut lisätään numeroina taulukkoon, kun kuva luetaan ui:ssa 
    //ja sen pikselit muutetaan arvoiksi.
    public void lisaaKarttaan(int x, int y, int arvo) {
        kartta[y][x] = arvo;
    }
    
    public boolean onkoKadulla(int x, int y) {
        if (kartta[y][x] == 0) {
            return true;
        }
        return false;
    }
    
    //alustetaan tietorakenteet
    public void alustaHaku() {
        this.etaisyys = new double[korkeus][leveys];
        inf = 999999999;
        for (int i = 0; i < etaisyys.length; i++) {
            for (int j = 0; j < etaisyys[0].length; j++) {
                etaisyys[i][j] = inf;
            }
        }
        this.mista = new Ruutu[korkeus][leveys];
        jono = new MinimiKeko();
        kasitelty = new boolean[korkeus + 1][leveys + 1];
        lyhinReitti = new Lista();
        suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        lista = new LinkitettyLista();
    }
    
    public void suoritaHaku(Lista<Integer> koordinaatit, int hakutapa) {
        int ax = koordinaatit.hae(0);
        int ay = koordinaatit.hae(1);
        int bx = koordinaatit.hae(2);
        int by = koordinaatit.hae(3);
        etaisyys[ay][ax] = 0;
        jono.lisaaKekoon(new Ruutu(ax, ay, 0));
        
        while (!jono.onTyhja()) {
            Ruutu nykyinen = jono.poistaPienin();
            int nx = nykyinen.getX();
            int ny = nykyinen.getY();
            if (kasitelty[ny][nx]) {
                continue;
            }
            kasitelty[ny][nx] = true;
            
            if (nx == bx && ny == by) {
                break;
            }
            //käy läpi 8 suuntaa
            for (int i = 0; i < 8; i++) {
                int uy = ny + suunnat[i][0];
                int ux = nx + suunnat[i][1];
                //if (uy < korkeus && uy >= 0 && ux < leveys && ux >= 0 && kartta[uy][ux] != 1) {
                if (ruutuOnKuljettavissa(ux, uy)) {                    
                    double nykyinenEtaisyys = etaisyys[uy][ux];
                    double etaisyydenLisays = 1;
                    //jos liikutaan viistosti (etäisyys on sqrt(2))
                    if (kuljetaanDiagonaalisesti(i)) {
                        etaisyydenLisays = Math.sqrt(2);
                    } 
                    double uusiEtaisyys = etaisyys[ny][nx] + etaisyydenLisays;
                    //Jos haku A* algorimilla (tapa 2), lisättävä vielä h(x) (arvio etaisyydestä)
                    double h = 0;
                    if (hakutapa == 2) {
                        h = euklidinenEtaisyys(ux, uy, bx, by);
                    }
                    if (uusiEtaisyys < nykyinenEtaisyys) {
                        etaisyys[uy][ux] = uusiEtaisyys;
                        jono.lisaaKekoon(new Ruutu(ux, uy, uusiEtaisyys + h));
                        mista[uy][ux] = nykyinen;
                    }
                } 
            }
        }
        muodostaReitti(ax, ay, bx, by);
        lyhinReitti.kaannaJarjestys();
    }
    
    public double euklidinenEtaisyys(int ax, int ay, int bx, int by) {
        int x = (ax - bx) > 0 ? (ax - bx) : (bx - ax);
        int y = (ay - by) > 0 ? (ay - by) : (by - ay);
        return Math.sqrt(x * x + y * y);
    }
    
    public boolean ruutuOnKuljettavissa(int x, int y) {
        if (y < korkeus && y >= 0 && x < leveys && x >= 0 && kartta[y][x] != 1) {
            return true;
        }
        return false;
    }
    
    public boolean kuljetaanDiagonaalisesti(int i) {
        return (!(suunnat[i][0] == 0 || suunnat[i][1] == 0)); 
    }
    
    public double ruutujenValinenEtaisyys(int i) {
        double etaisyys = 1;
        if (kuljetaanDiagonaalisesti(i)) {
            etaisyys = Math.sqrt(2);
        }
        return etaisyys;
    }

    public void muodostaReitti(int ax, int ay, int bx, int by) {
        while (!(bx == ax && by == ay)) {
            Ruutu v = mista[by][bx];
            if (v != null) {
                lyhinReitti.lisaa(v);
                by = v.getY();
                bx = v.getX();
                reitinPituus += v.getEtaisyys();
            } else {
                break;
            }
        }
    }
    
    //alustava Fringe Search
    public void fringeSearch(Lista<Integer> koordinaatit) {
        int ax = koordinaatit.hae(0);
        int ay = koordinaatit.hae(1);
        int bx = koordinaatit.hae(2);
        int by = koordinaatit.hae(3);
        lista.lisaaLoppuun(new Solmu(new Ruutu(ax, ay, 0)));  
        etaisyys[ay][ax] = 0; 
        mista[ay][ax] = null;
        fLimit = euklidinenEtaisyys(ax, ay, bx, by); 
        boolean loydetty = false; 
        
        while (!loydetty && !lista.onTyhja()) {  
            fMin = inf; 
            for (Solmu s = lista.getEnsimmainen(); s != null; s = s.getSeuraava()) {  
                int nx = s.getRuutu().getX();
                int ny = s.getRuutu().getY();
                double f = etaisyys[ny][nx] + euklidinenEtaisyys(nx, ny, bx, by);
                if (f > fLimit) {
                    fMin = Math.min(f, fMin);
                    continue;
                }
                if (nx == bx && ny == by) {
                    loydetty = true; 
                    break;
                }

                for (int i = 0; i < 8; i++) { 
                    int uy = ny + suunnat[i][0];
                    int ux = nx + suunnat[i][1];
                    if (uy < korkeus && uy >= 0 && ux < leveys && ux >= 0 && kartta[uy][ux] != 1) {
                        double etaisyydenLisays = 1;           
                        if (!(suunnat[i][0] == 0 || suunnat[i][1] == 0)) {
                            etaisyydenLisays = Math.sqrt(2);
                        } 
                        double uusiEtaisyys = etaisyys[ny][nx] + etaisyydenLisays; 
                        double nykyinenEtaisyys = etaisyys[uy][ux]; 
                        if (etaisyys[uy][ux] != inf && uusiEtaisyys > nykyinenEtaisyys) {
                            continue;
                        }
                        etaisyys[uy][ux] = uusiEtaisyys;
                        mista[uy][ux] = s.getRuutu();
                        lista.poistaSolmuJosOnListalla(ux, uy);
                        lista.lisaaLoppuun(new Solmu(new Ruutu(ux, uy, uusiEtaisyys)));
                    } 
                }
                lista.poistaSolmu(s);
            }
            fLimit = fMin;
        }
        muodostaReitti(ax, ay, bx, by);
        lyhinReitti.kaannaJarjestys();
    }
    
    //tämä ei käytössä
    public List<Ruutu> getNaapurit(int nx, int ny) {
        ArrayList<Ruutu> naapurit = new ArrayList<>();
        
        for (int i = 0; i < 8; i++) {
            int uy = ny + suunnat[i][0];
            int ux = nx + suunnat[i][1];
            if (uy < korkeus && uy >= 0 && ux < leveys && ux >= 0 && kartta[uy][ux] != 1) {
                double etaisyydenLisays = 1;
                if (!(suunnat[i][0] == 0 || suunnat[i][1] == 0)) {
                    etaisyydenLisays = Math.sqrt(2);
                } 
                double uusiEtaisyys = etaisyys[ny][nx] + etaisyydenLisays;
                double nykyinenEtaisyys = etaisyys[uy][ux];
                if (etaisyys[uy][ux] != inf && uusiEtaisyys > nykyinenEtaisyys) {
                    continue;
                }
                etaisyys[uy][ux] = uusiEtaisyys;
                naapurit.add(new Ruutu(ux, uy, uusiEtaisyys));
            }           
        }
        return naapurit;
    }
     
    public Lista getLyhinReitti() {
        return lyhinReitti;
    }

    
    public double haeReitinPituus() {
        return reitinPituus;
    }
    
    
    
}
