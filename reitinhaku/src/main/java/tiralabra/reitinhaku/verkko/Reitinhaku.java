 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.verkko;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import tiralabra.reitinhaku.tietorakenteet.MinimiKeko;

/**
 *
 * @author mlkul
 */
public class Reitinhaku {
    
    int leveys, korkeus, inf;
    boolean[][] kasitelty;
    //MinimiKeko jono;
    PriorityQueue<Ruutu> jono;
    int[][] kartta, suunnat;
    double[][] etaisyys;
    Ruutu[][] mista;
    ArrayList<Ruutu> lyhinReitti;
    double reitinPituus, fLimit, fMin;
    ArrayDeque<Ruutu> pakka;
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
        //jono = new MinimiKeko();
        jono = new PriorityQueue<>();
        kasitelty = new boolean[korkeus + 1][leveys + 1];
        lyhinReitti = new ArrayList<>();
        suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        pakka = new ArrayDeque<>();
        lista = new LinkitettyLista();
    }
    
    //alustava hakualgoritmi
    public void suoritaHaku(List<Integer> koordinaatit, int hakutapa) {
        int ax = koordinaatit.get(0);
        int ay = koordinaatit.get(1);
        int bx = koordinaatit.get(2);
        int by = koordinaatit.get(3);
        etaisyys[ay][ax] = 0;
        //jono.lisaaKekoon(new Ruutu(ax, ay, 0));
        jono.add(new Ruutu(ax, ay, 0));
        
        //while (!jono.onTyhja()) {
        while (!jono.isEmpty()) {    
            //Ruutu nykyinen = jono.poistaPienin();
            Ruutu nykyinen = jono.poll();
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
                    double nykyinenEtaisyys = etaisyys[uy][ux];
                    double etaisyydenLisays = 1;
                    //jos liikutaan viistosti (etäisyys on sqrt(2))
                    if (!(suunnat[i][0] == 0 || suunnat[i][1] == 0)) {
                        etaisyydenLisays = Math.sqrt(2);
                    } 
                    double uusiEtaisyys = etaisyys[ny][nx] + etaisyydenLisays;
                    //Jos haku A* algorimilla (tapa 2), lisättävä vielä h(x) (arvio etaisyydestä)
                    double h = 0;
                    if (hakutapa == 2) {
                        //int x = (ux - bx) > 0 ? (ux - bx) : (bx - ux);
                        //int y = (uy - by) > 0 ? (uy - by) : (by - uy);
                        //h = Math.sqrt(x * x + y * y);
                        h = euklidinenEtaisyys(ux, uy, bx, by);
                    }
                    if (uusiEtaisyys < nykyinenEtaisyys) {
                        etaisyys[uy][ux] = uusiEtaisyys;
                        //jono.lisaaKekoon(new Ruutu(ux, uy, uusiEtaisyys + h));
                        jono.add(new Ruutu(ux, uy, uusiEtaisyys + h));
                        mista[uy][ux] = nykyinen;
                    }
                } 
            }
        }
        
        //lyhimmän reitin muodostus "peruuttamalla"
        while (!(bx == ax && by == ay)) {
            Ruutu v = mista[by][bx];
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
        //lista.kaannaJarjestys(lyhinReitti)
    }
    
    public double euklidinenEtaisyys(int ax, int ay, int bx, int by) {
        int x = (ax - bx) > 0 ? (ax - bx) : (bx - ax);
        int y = (ay - by) > 0 ? (ay - by) : (by - ay);
        return Math.sqrt(x * x + y * y);
    }

    
    //alustava Fringe Search
    public void fringeSearch(List<Integer> koordinaatit) {
        int ax = koordinaatit.get(0);
        int ay = koordinaatit.get(1);
        int bx = koordinaatit.get(2);
        int by = koordinaatit.get(3);
        lista.lisaaLoppuun(new Solmu(new Ruutu(ax, ay, 0)));  //Fringe F <- (s)
        etaisyys[ay][ax] = 0;                                 //Cache C[start] <- (0, null)
        fLimit = euklidinenEtaisyys(ax, ay, bx, by);          //fLimit <- h(start)
        boolean loydetty = false;                             //found <- false
        
        while (!loydetty && !lista.onTyhja()) {               //Repeat until found = true or F empty
            fMin = inf;                                       //fMin <- inf
            for (Solmu s = lista.getEnsimmainen(); s != null; s = s.getSeuraava()) {  //Iterate over nodes n in F from left to right
                //lista.tulostaListanSolmut();
                //System.out.println("");
                int nx = s.getRuutu().getX();
                int ny = s.getRuutu().getY();
                System.out.println("Käsittelyssä solmu (" + nx + "," + ny + ")");
                double f = etaisyys[ny][nx] + euklidinenEtaisyys(nx, ny, bx, by);
                //System.out.println("  jonka f on " + f);
                //System.out.println("  fLimit on tässä kohtaa " + fLimit);
                if (f > fLimit) {
                    fMin = Math.min(f, fMin);
                    //System.out.println("   f > fLimit, joten palataan alkuun ja otetaan listalta uusi solmu\n");
                    continue;
                }
                if (nx == bx && ny == by) {                   // If n = goal
                    loydetty = true;                          // found <- true
                    break;
                }
//                for (Ruutu naapuri : getNaapurit(nx, ny)) {
//                    
//                    lista.lisaaSolmunJalkeen(s, new Solmu(naapuri));
//                }
                for (int i = 0; i < 8; i++) {                 // Iterate over s in successors(n)
                    int uy = ny + suunnat[i][0];
                    int ux = nx + suunnat[i][1];
                    //System.out.println("     Solmulle haetaan lapsia:");
                    if (uy < korkeus && uy >= 0 && ux < leveys && ux >= 0 && kartta[uy][ux] != 1) {
                        double etaisyydenLisays = 1;           
                        if (!(suunnat[i][0] == 0 || suunnat[i][1] == 0)) {
                            etaisyydenLisays = Math.sqrt(2);
                        } 
                        double uusiEtaisyys = etaisyys[ny][nx] + etaisyydenLisays;   //g_s <- g + cost(n,s)
                        double nykyinenEtaisyys = etaisyys[uy][ux];                  //(g', parent) <- C[s] 
                        if (etaisyys[uy][ux] != inf && uusiEtaisyys > nykyinenEtaisyys) {
                            System.out.println("solmu (" + ux + "," + uy + ") hylättiin, koska etäisyys ei parantunut");
                            continue;
                        }
                        etaisyys[uy][ux] = uusiEtaisyys;
                        mista[uy][ux] = s.getRuutu();
                        if (lista.onkoListallaSolmu(ux, uy)) {
                            lista.poistaSolmuKoordinaateilla(ux, uy);
                        }
                        lista.lisaaSolmunJalkeen(s, new Solmu(new Ruutu(ux, uy, uusiEtaisyys)));
                        System.out.println("\tLisätty listan loppuun solmu, jonka koordinaatit on (" + ux + "," + uy + "), "
                               + "ja etäisyys " + uusiEtaisyys );
                        //System.out.println("\tEdellinen solmu (" + nx + "," + ny + ")");
                    } 
                }
                //System.out.println("onko listalla solmu s = " + lista.onkoListallaSolmu(s));
                lista.poistaSolmu(s);
                //System.out.println("poistettu solmu " + s.getRuutu().toString());
//                s = lista.getEnsimmainen();
                //System.out.println("Ensimmainen on nyt " + lista.getEnsimmainen().getRuutu().toString());
            }
            fLimit = fMin;
        }
        //System.out.println("silmukka päättyi");
        //lyhimmän reitin muodostus "peruuttamalla"
        while (!(bx == ax && by == ay)) {
            Ruutu v = mista[by][bx];
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
     
    
    public ArrayList<Ruutu> getLyhinReitti() {
        return lyhinReitti;
    }
    
    public double haeReitinPituus() {
        return reitinPituus;
    }
    
    
    
}
