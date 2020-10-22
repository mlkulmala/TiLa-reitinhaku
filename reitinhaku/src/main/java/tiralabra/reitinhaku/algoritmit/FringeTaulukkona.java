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
public class FringeTaulukkona {
    
    Kartta kartta;
    int inf;
    int[][] indeksiListalla;
    int[][] suunnat;
    double[][] etaisyys;
    Ruutu[][] mista;
    double fLimit, fMin;
    //LinkitettyLista lista;
    //Ruutu[][] ruudut;
    Lista<Ruutu> fLista;

    public FringeTaulukkona(Kartta kartta) {
        this.kartta = kartta;
    }

    //alustetaan tietorakenteet
    public void alustaHaku() {
        int korkeus = kartta.getKorkeus();
        int leveys = kartta.getLeveys();
        etaisyys = new double[korkeus + 1][leveys + 1];
        indeksiListalla = new int[korkeus + 1][leveys + 1];
        inf = 999999999;
        for (int i = 0; i < etaisyys.length; i++) {
            for (int j = 0; j < etaisyys[0].length; j++) {
                etaisyys[i][j] = inf;
                indeksiListalla[i][j] = inf;
            }
        }
        mista = new Ruutu[korkeus + 1][leveys + 1];
        
        suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        //lista = new LinkitettyLista();
        fLista = new Lista<>();
        //ruudut = new Ruutu[korkeus + 1][leveys + 1];
    }    
    
    // Fringe Search taulukon avulla
    public void haeReitti(Lista<Integer> koordinaatit, GraphicsContext cg) {
        int ax = koordinaatit.hae(0);
        int ay = koordinaatit.hae(1);
        int bx = koordinaatit.hae(2);
        int by = koordinaatit.hae(3);
        //ruudut[ay][ax] = new Ruutu(ax, ay, 0); 
        int indeksi = 0;
        fLista.lisaa(new Ruutu(ax, ay, 0));
        indeksiListalla[ay][ax] = indeksi;
        etaisyys[ay][ax] = 0; 
        mista[ay][ax] = null;
        fLimit = kartta.euklidinenEtaisyys(ax, ay, bx, by); 
        boolean loydetty = false; 
        
        while (!loydetty && fLista.koko() != 0) {  
            fMin = inf; 
            for (int i = 0; i < fLista.koko(); i++) { 
                Ruutu ruutu = fLista.hae(i);
                //System.out.println("käsittelyssä " + ruutu.toString());
                int ruudunIndeksi = indeksi;
                int nx = ruutu.getX();
                int ny = ruutu.getY();
                cg.setFill(Color.CYAN);  //kartta.varitaRuutu(x, y);
                cg.fillRect(nx, ny, 1, 1);
                
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
                        if (mista[uy][ux] != null) {
                            if (uusiEtaisyys > nykyinenEtaisyys) {
                                continue;
                            }
                        }
                        etaisyys[uy][ux] = uusiEtaisyys;
                        mista[uy][ux] = ruutu;
                        if (indeksiListalla[uy][ux] != inf) {    //jos on jo indeksi, ruutu löytyy jo listalta
                            fLista.poista(indeksiListalla[uy][ux]);  //poistetaan ko. indeksin kohdalta
                            indeksi--;
                        }
                        //ruudut[uy][ux] = new Ruutu(ux, uy, uusiEtaisyys);
                        fLista.lisaa(new Ruutu(ux, uy, uusiEtaisyys));
                        indeksiListalla[uy][ux] = ++indeksi;
                    } 
                }
                fLista.poista(ruudunIndeksi);
                indeksiListalla[ny][nx] = inf;
            }
            fLimit = fMin;
        }
        if (loydetty) {
            kartta.muodostaReitti(ax, ay, bx, by, mista);
        }
    }
}
