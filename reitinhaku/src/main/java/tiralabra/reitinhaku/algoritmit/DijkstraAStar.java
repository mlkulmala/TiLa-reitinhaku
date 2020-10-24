
package tiralabra.reitinhaku.algoritmit;

import tiralabra.reitinhaku.tietorakenteet.Lista;
import tiralabra.reitinhaku.tietorakenteet.MinimiKeko;
import tiralabra.reitinhaku.ui.UI;
import tiralabra.reitinhaku.verkko.Kartta;
import tiralabra.reitinhaku.verkko.Ruutu;

/**
 * Dijkstran ja AStar -algoritmien toteutus.
 * @author mlkul
 */
public class DijkstraAStar {
    
    Kartta kartta;
    UI ui;
    int inf;
    double[][] etaisyys;
    boolean[][] kasitelty;
    MinimiKeko keko;
    Ruutu[][] mista;
    int[][] suunnat;
    Lista<Ruutu> kaydyt;
    
    public DijkstraAStar(Kartta kartta, UI ui) {
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
        kasitelty = new boolean[korkeus + 1][leveys + 1];
        keko = new MinimiKeko();
        mista = new Ruutu[korkeus + 1][leveys + 1];
        suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        kaydyt = new Lista<>();
    } 
    
    /**
     * Hakee lyhimmän reitin parametrina annettujen koordinaattien välillä.
     * @param koordinaatit
     * @param hakutapa 
     */
    public void haeReitti(Lista<Integer> koordinaatit, int hakutapa) {
        int ax = koordinaatit.hae(0);
        int ay = koordinaatit.hae(1);
        int bx = koordinaatit.hae(2);
        int by = koordinaatit.hae(3);
        etaisyys[ay][ax] = 0;
        keko.lisaaKekoon(new Ruutu(ax, ay, 0));
        
        while (!keko.onTyhja()) {
            Ruutu nykyinen = keko.poistaPienin();
            kaydyt.lisaa(nykyinen);
            int nx = nykyinen.getX();
            int ny = nykyinen.getY();
            if (kasitelty[ny][nx]) {
                continue;
            }
            kasitelty[ny][nx] = true;
            
            if (nx == bx && ny == by) {
                break;
            }
            //käy läpi 8 ilmansuuntaa
            for (int i = 0; i < 8; i++) {
                int uy = ny + suunnat[i][0];
                int ux = nx + suunnat[i][1];
                if (kartta.ruutuOnKuljettavissa(ux, uy)) {                    
                    double nykyinenEtaisyys = etaisyys[uy][ux];
                    double uusiAskel = kartta.ruutujenValinenEtaisyys(i);
                    double uusiEtaisyys = etaisyys[ny][nx] + uusiAskel;
                    double h = 0;
                    if (hakutapa == 2) {
                        h = kartta.euklidinenEtaisyys(ux, uy, bx, by);
                    }
                    if (uusiEtaisyys < nykyinenEtaisyys) {
                        etaisyys[uy][ux] = uusiEtaisyys;
                        keko.lisaaKekoon(new Ruutu(ux, uy, uusiEtaisyys + h));
                        mista[uy][ux] = nykyinen;
                    }
                } 
            }
        }
        kartta.muodostaReitti(ax, ay, bx, by, mista);
        ui.varitaKaydytRuudut(kaydyt);
    }
    
  
}
