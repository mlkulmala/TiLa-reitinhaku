/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.verkko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import javafx.scene.image.PixelWriter;

/**
 *
 * @author mlkul
 */
public class Reitinhaku {
    
    int leveys, korkeus;
    boolean[][] kasitelty;
    PriorityQueue<Solmu> jono;
    int[][] kartta;
    double[][] etaisyydet;
    Solmu[][] mista;
    ArrayList<Solmu> lyhinReitti;
    int[][] suunnat;
    double reitinPituus;
    
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
        this.etaisyydet = new double[korkeus][leveys];
        int INF = 999999999;
        for (int i = 0; i < etaisyydet.length; i++) {
            for (int j = 0; j < etaisyydet[0].length; j++) {
                etaisyydet[i][j] = INF;
            }
        }
        this.mista = new Solmu[korkeus][leveys];
        jono = new PriorityQueue<>();
        kasitelty = new boolean[korkeus + 1][leveys + 1];
        lyhinReitti = new ArrayList<>();
        suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
    }
    
    //alustava hakualgoritmi
    public void suoritaHaku(List<Integer> koordinaatit, int hakutapa) {
        int ax = koordinaatit.get(0);
        int ay = koordinaatit.get(1);
        int bx = koordinaatit.get(2);
        int by = koordinaatit.get(3);
        etaisyydet[ay][ax] = 0;
        jono.add(new Solmu(ax, ay, 0));
        
        while (!jono.isEmpty()) {
            Solmu nykyinen = jono.poll();
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
                //tarkistetaan, että piste on kuva-alueen sisällä eikä ole seinää
                if (uy < korkeus && uy >= 0 && ux < leveys && ux >= 0 && kartta[uy][ux] != 1) {
                    double nykyinenEtaisyys = etaisyydet[uy][ux];
                    double etaisyydenLisays = 1;
                    //jos liikutaan viistosti (etäisyys on sqrt(2))
                    if (!(suunnat[i][0] == 0 || suunnat[i][1] == 0)) {
                        etaisyydenLisays = Math.sqrt(2);
                    } 
                    double uusiEtaisyys = etaisyydet[ny][nx] + etaisyydenLisays;
                    //Jos haku A* algorimilla (tapa 2), lisättävä vielä h(x) (arvio etaisyydestä)
                    double h = 0;
                    if (hakutapa == 2) {
                        int x = (ux - bx) > 0 ? (ux - bx) : (bx - ux);
                        int y = (uy - by) > 0 ? (uy - by) : (by - uy);
                        h = Math.sqrt(x * x + y * y);
                    }
                    if (uusiEtaisyys < nykyinenEtaisyys) {
                        etaisyydet[uy][ux] = uusiEtaisyys;
                        jono.add(new Solmu(ux, uy, uusiEtaisyys + h));
                        mista[uy][ux] = nykyinen;
                    }
                } 
            }
        }
        
        //lyhimmän reitin muodostus "peruuttamalla"
        while (!(bx == ax && by == ay)) {
            Solmu v = mista[by][bx];
            if (v != null) {
                lyhinReitti.add(v);
                by = v.getY();
                bx = v.getX();
                reitinPituus += v.getEtaisyys();
            } else {
                break;
            }
        }
        Collections.sort(lyhinReitti);

    }
    
    public ArrayList<Solmu> getLyhinReitti() {
        return lyhinReitti;
    }
    
    
    
}
