
package tiralabra.reitinhaku.algoritmit;

import tiralabra.reitinhaku.tietorakenteet.LinkitettyLista;
import tiralabra.reitinhaku.tietorakenteet.Lista;
import tiralabra.reitinhaku.ui.UI;
import tiralabra.reitinhaku.verkko.Kartta;
import tiralabra.reitinhaku.verkko.Ruutu;
import tiralabra.reitinhaku.verkko.Solmu;

/**
 * Fringe Search -reitinhakualgoritmin toteutus. 
 * @author mlkul
 */
public class FringeSearch {
    
    Kartta kartta;
    UI ui;
    int inf;
    boolean[][] listalla;
    int[][] suunnat;
    double[][] etaisyys;
    Ruutu[][] mista;
    double fLimit, fMin;
    LinkitettyLista lista;
    Solmu[][] solmut;
    Lista<Ruutu> kaydyt;

    public FringeSearch(Kartta kartta, UI ui) {
        this.kartta = kartta;
        this.ui = ui;
    }

    /**
     * Alustaa hakualgoritmin käyttämät tietorakenteet.
     */
    public void alustaHaku() {
        int korkeus = kartta.getKorkeus();
        int leveys = kartta.getLeveys();
        etaisyys = new double[korkeus + 1][leveys + 1];
        inf = 999999999;
        for (int i = 0; i < etaisyys.length; i++) {
            for (int j = 0; j < etaisyys[0].length; j++) {
                etaisyys[i][j] = inf;
            }
        }
        mista = new Ruutu[korkeus + 1][leveys + 1];
        listalla = new boolean[korkeus + 1][leveys + 1];
        suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        lista = new LinkitettyLista();
        solmut = new Solmu[korkeus + 1][leveys + 1];
        kaydyt = new Lista<>();
    }    
    
    /**
     * Hakee lyhimmän reitin parametrina annettujen koordinaattien välillä.
     * @param koordinaatit
     */
    public void haeReitti(Lista<Integer> koordinaatit) {
        int ax = koordinaatit.hae(0);
        int ay = koordinaatit.hae(1);
        int bx = koordinaatit.hae(2);
        int by = koordinaatit.hae(3);
        solmut[ay][ax] = (new Solmu(new Ruutu(ax, ay, 0))); 
        lista.lisaaLoppuun(solmut[ay][ax]);  
        etaisyys[ay][ax] = 0; 
        mista[ay][ax] = null;
        listalla[ay][ax] = true;
        fLimit = kartta.euklidinenEtaisyys(ax, ay, bx, by); 
        boolean loydetty = false; 
        
        while (!loydetty && !lista.onTyhja()) {  
            fMin = inf; 
            for (Solmu s = lista.getEnsimmainen(); s != null; s = s.getSeuraava()) {  
                kaydyt.lisaa(s.getRuutu());
                int nx = s.getRuutu().getX();
                int ny = s.getRuutu().getY();
                double f = etaisyys[ny][nx] + kartta.euklidinenEtaisyys(nx, ny, bx, by);
                if (f > fLimit) {
                    fMin = f < fMin ? f : fMin;
                    continue;
                }
                if (nx == bx && ny == by) {
                    loydetty = true; 
                    break;
                }
                for (int i = 0; i < 8; i++) { 
                    int uy = ny + suunnat[i][0];
                    int ux = nx + suunnat[i][1];
                    if (kartta.ruutuOnKuljettavissa(ux, uy)) {
                        double uusiAskel = kartta.ruutujenValinenEtaisyys(i);  
                        double uusiEtaisyys = etaisyys[ny][nx] + uusiAskel; 
                        double nykyinenEtaisyys = etaisyys[uy][ux]; 
                        if (mista[uy][ux] != null) {
                            if (uusiEtaisyys > nykyinenEtaisyys) {
                                continue;
                            }
                        }
                        etaisyys[uy][ux] = uusiEtaisyys;
                        mista[uy][ux] = s.getRuutu();
                        if (listalla[uy][ux]) {
                            lista.poistaSolmu(solmut[uy][ux]);
                        }
                        solmut[uy][ux] = new Solmu(new Ruutu(ux, uy, uusiEtaisyys));
                        lista.lisaaLoppuun(solmut[uy][ux]);
                        listalla[uy][ux] = true;
                    } 
                }
                lista.poistaSolmu(s);
                listalla[ny][nx] = false;
            }
            fLimit = fMin;
        }
        if (loydetty) {
            kartta.muodostaReitti(ax, ay, bx, by, mista);
            ui.varitaKaydytRuudut(kaydyt);
        }
    }
    
   
}
