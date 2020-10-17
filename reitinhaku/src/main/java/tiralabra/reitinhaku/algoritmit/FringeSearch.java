
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
public class FringeSearch {
    
    Kartta kartta;
    int inf;
    boolean[][] listalla;
    int[][] suunnat;
    double[][] etaisyys;
    Ruutu[][] mista;
    double fLimit, fMin;
    LinkitettyLista lista;
    Solmu[][] jarjestys;

    public FringeSearch(Kartta kartta) {
        this.kartta = kartta;
    }

    //alustetaan tietorakenteet
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
        jarjestys = new Solmu[korkeus + 1][leveys + 1];
    }    
    
    // Fringe Search
    public void haeReitti(Lista<Integer> koordinaatit, GraphicsContext cg) {
        int ax = koordinaatit.hae(0);
        int ay = koordinaatit.hae(1);
        int bx = koordinaatit.hae(2);
        int by = koordinaatit.hae(3);
        lista.lisaaLoppuun(new Solmu(new Ruutu(ax, ay, 0)));  
        etaisyys[ay][ax] = 0; 
        mista[ay][ax] = null;
        fLimit = kartta.euklidinenEtaisyys(ax, ay, bx, by); 
        boolean loydetty = false; 
        int laskuri = 0;
        
        while (!loydetty && !lista.onTyhja()) {  
            fMin = inf; 
            for (Solmu s = lista.getEnsimmainen(); s != null; s = s.getSeuraava()) {  
                laskuri++;
                //System.out.println("käsittelyssä solmu " + s.getRuutu().toString());
                cg.setFill(Color.CYAN);
                cg.fillRect(s.getRuutu().getX(), s.getRuutu().getY(), 1, 1);
                int nx = s.getRuutu().getX();
                int ny = s.getRuutu().getY();
                System.out.println("Käsittelyssä solmu (" + nx + "," + ny + ")");
                double f = etaisyys[ny][nx] + kartta.euklidinenEtaisyys(nx, ny, bx, by);
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
                    if (kartta.ruutuOnKuljettavissa(ux, uy)) {
                        double uusiAskel = kartta.ruutujenValinenEtaisyys(i);           
                        double uusiEtaisyys = etaisyys[ny][nx] + uusiAskel; 
                        double nykyinenEtaisyys = etaisyys[uy][ux]; 
                        if (etaisyys[uy][ux] != inf && uusiEtaisyys > nykyinenEtaisyys) {
                            continue;
                        }
                        etaisyys[uy][ux] = uusiEtaisyys;
                        mista[uy][ux] = s.getRuutu();
                        if (listalla[uy][ux]) {
                            lista.poistaSolmuJosOnListalla(ux, uy);
                        }
                        lista.lisaaLoppuun(new Solmu(new Ruutu(ux, uy, uusiEtaisyys)));
                        listalla[uy][ux] = true;
                    } 
                }
                lista.poistaSolmu(s);
                System.out.println(" poistettu solmu (" + s.getRuutu().getX() + "," + s.getRuutu().getY() + ")");
                listalla[ny][nx] = false;
            }
            fLimit = fMin;
            System.out.println("fLimin = fMin");
        }
        if (loydetty) {
            //System.out.println("käsiteltiin yhteensä " + laskuri + " solmua");
            kartta.muodostaReitti(ax, ay, bx, by, mista);
        }
    }
    
    // Fringe Search
    public void haeReitti2(Lista<Integer> koordinaatit, GraphicsContext cg) {
        int ax = koordinaatit.hae(0);
        int ay = koordinaatit.hae(1);
        int bx = koordinaatit.hae(2);
        int by = koordinaatit.hae(3);
        lista.lisaaLoppuun(new Solmu(new Ruutu(ax, ay, 0))); 
        jarjestys[ay][ax] = lista.getEnsimmainen();
        etaisyys[ay][ax] = 0; 
        mista[ay][ax] = null;
        fLimit = kartta.euklidinenEtaisyys(ax, ay, bx, by); 
        boolean loydetty = false; 
        int laskuri = 0;
        
        while (!loydetty && !lista.onTyhja()) {  
            fMin = inf; 
            for (Solmu s = lista.getEnsimmainen(); s != null; s = s.getSeuraava()) {  
                laskuri++;
                //System.out.println("käsittelyssä solmu " + s.getRuutu().toString());
                cg.setFill(Color.CYAN);
                cg.fillRect(s.getRuutu().getX(), s.getRuutu().getY(), 1, 1);
                int nx = s.getRuutu().getX();
                int ny = s.getRuutu().getY();
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
                for (int i = 0; i < 8; i++) { 
                    int uy = ny + suunnat[i][0];
                    int ux = nx + suunnat[i][1];
                    if (kartta.ruutuOnKuljettavissa(ux, uy)) {
                        double uusiAskel = kartta.ruutujenValinenEtaisyys(i);           
                        double uusiEtaisyys = etaisyys[ny][nx] + uusiAskel; 
                        double nykyinenEtaisyys = etaisyys[uy][ux]; 
                        if (etaisyys[uy][ux] != inf && uusiEtaisyys > nykyinenEtaisyys) {
                            continue;
                        }
                        etaisyys[uy][ux] = uusiEtaisyys;
                        mista[uy][ux] = s.getRuutu();
                        if (listalla[uy][ux]) {
                            lista.poistaSolmuJosOnListalla(ux, uy);
                        }
                        lista.lisaaLoppuun(new Solmu(new Ruutu(ux, uy, uusiEtaisyys)));
                        listalla[uy][ux] = true;
                    } 
                }
                lista.poistaSolmu(s);
                System.out.println(" poistettu solmu (" + s.getRuutu().getX() + "," + s.getRuutu().getY() + ")");
                listalla[ny][nx] = false;
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
