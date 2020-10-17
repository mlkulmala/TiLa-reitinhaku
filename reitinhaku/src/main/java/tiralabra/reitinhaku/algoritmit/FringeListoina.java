
package tiralabra.reitinhaku.algoritmit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import tiralabra.reitinhaku.tietorakenteet.LinkitettyLista;
import tiralabra.reitinhaku.tietorakenteet.Lista;
import tiralabra.reitinhaku.verkko.Kartta;
import tiralabra.reitinhaku.verkko.Ruutu;
import tiralabra.reitinhaku.verkko.Solmu;
/**
 *
 * @author mlkul
 */
public class FringeListoina {
    
    Kartta kartta;
    int inf;
    int[][] listalla;
    int[][] suunnat;
    double[][] etaisyys;
    Ruutu[][] mista;
    double fLimit, fMin;
    Lista<Ruutu> eka, toka;

    public FringeListoina(Kartta kartta) {
        this.kartta = kartta;
    }

    //alustetaan tietorakenteet
    public void alustaHaku() {
        int korkeus = kartta.getKorkeus();
        int leveys = kartta.getLeveys();
        etaisyys = new double[korkeus + 1][leveys + 1];
        listalla = new int[korkeus + 1][leveys + 1];
        inf = 999999999;
        for (int i = 0; i < etaisyys.length; i++) {
            for (int j = 0; j < etaisyys[0].length; j++) {
                etaisyys[i][j] = inf;
                listalla[i][j] = inf;
            }
        }
        mista = new Ruutu[korkeus + 1][leveys + 1];
        suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        eka = new Lista<>();
        toka = new Lista<>();
    }    
    
    // Fringe Search
    public void haeReitti(Lista<Integer> koordinaatit, GraphicsContext cg) {
        int ax = koordinaatit.hae(0);
        int ay = koordinaatit.hae(1);
        int bx = koordinaatit.hae(2);
        int by = koordinaatit.hae(3);
        eka.lisaa(new Ruutu(ax, ay, 0));  
        etaisyys[ay][ax] = 0; 
        mista[ay][ax] = null;
        fLimit = kartta.euklidinenEtaisyys(ax, ay, bx, by); 
        boolean loydetty = false; 
        int laskuri = 0;
        
        while (!loydetty && !eka.onTyhja()) {  
            fMin = inf; 
            for (int i = 0; i < eka.koko(); i++) {  
                Ruutu ruutu = eka.hae(i);
                int nx = ruutu.getX();
                int ny = ruutu.getY();
                cg.setFill(Color.CYAN);
                cg.fillRect(nx, ny, 1, 1);
                System.out.println("Käsittelyssä solmu (" + nx + "," + ny + ")");
                double f = etaisyys[ny][nx] + kartta.euklidinenEtaisyys(nx, ny, bx, by);
                if (f > fLimit) {
                    fMin = f < fMin ? f : fMin;
                    continue;
                }
                if (nx == bx && ny == by) {
                    loydetty = true; 
                    break;
                }
                for (int j = 0; j < 8; j++) { 
                    int uy = ny + suunnat[j][0];
                    int ux = nx + suunnat[j][1];
                    if (kartta.ruutuOnKuljettavissa(ux, uy)) {
                        double uusiAskel = kartta.ruutujenValinenEtaisyys(j);           
                        double uusiEtaisyys = etaisyys[ny][nx] + uusiAskel; 
                        double nykyinenEtaisyys = etaisyys[uy][ux]; 
                        if (etaisyys[uy][ux] != inf && uusiEtaisyys > nykyinenEtaisyys) {
                            continue;
                        }
                        etaisyys[uy][ux] = uusiEtaisyys;
                        mista[uy][ux] = ruutu;
                        if (listalla[uy][ux] != inf) {
                            eka.poista(listalla[uy][ux]);
                        }
                        listalla[uy][ux] = eka.koko();
                        toka.lisaa(new Ruutu(ux, uy, uusiEtaisyys));
                    } 
                }
                eka.poista(i);
                for (int k = 0; k < toka.koko(); k++) {
                    eka.lisaa(toka.hae(k));
                }
                toka = new Lista<>();
            }
            fLimit = fMin;
            System.out.println("fLimin = fMin");
        }
        if (loydetty) {
            //System.out.println("käsiteltiin yhteensä " + laskuri + " solmua");
            kartta.muodostaReitti(ax, ay, bx, by, mista);
        }
    }
    
   
    
}
