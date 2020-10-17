
package tiralabra.reitinhaku.verkko;

import tiralabra.reitinhaku.tietorakenteet.Lista;

/**
 * Luokka, joka 
 * @author mlkul
 */
public class Kartta {
    
    int leveys, korkeus, inf;
    int[][] ruudukko, suunnat;
    double[][] etaisyys;
    Ruutu[][] mista;
    Lista<Ruutu> lyhinReitti;
    double reitinPituus;
    
    /**
     * Luokan Kartta tietorakenteiden alustus ja kartan mittojen määrittely.
     * @param leveys
     * @param korkeus 
     */
    //määritellään leveys ja korkeus (kutsutaan start-metodissa)
    public void alustaKartta(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        ruudukko = new int[korkeus][leveys];
        suunnat = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        lyhinReitti = new Lista<>();
    }
    
    public int getLeveys() {
        return this.leveys;
    }
    
    public int getKorkeus() {
        return this.korkeus;
    }
    
    /**
     * Lisää binäärilukutaulukkoon pikseleitä vastaavat arvot (0 = kuljettavissa,
     * 1 = rakennus tai muu este) taulukkoon pikseliä vastaavien koordinaattien 
     * mukaan.
     * @param x pikselin x-koordinaatti
     * @param y pikselin y-koordinaatti
     * @param arvo 0 tai 1
     */
    public void lisaaKarttaan(int x, int y, int arvo) {
        if (arvo == 0 || arvo == 1) {
            ruudukko[y][x] = arvo;
        } else {
            return;
        }
    }
    
    /**
     * Tarkistaa, onko annetuilla koordinateilla kartalla sijaitseva piste
     * kuljettavissa eli kadulla.
     * @param x
     * @param y
     * @return 
     */
    public boolean onkoKuljettavissa(int x, int y) {
        return (ruudukko[y][x] == 0);
    }
    
    /**
     * Laskee pisteiden välisen euklidisen etäisyyden. Apuväline, joka ei
     * loogisesti kuuluisi tähän luokkaan.
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @return 
     */
    public double euklidinenEtaisyys(int ax, int ay, int bx, int by) {
        int x = (ax - bx) > 0 ? (ax - bx) : (bx - ax);
        int y = (ay - by) > 0 ? (ay - by) : (by - ay);
        return Math.sqrt(x * x + y * y);
    }
    
    /**
     * Tarkistaa, onko koordinaattien mukainen ruutu kartan reunojen sisällä.
     * @param x
     * @param y
     * @return 
     */
    public boolean ruutuOnKuljettavissa(int x, int y) {
        if (y < korkeus && y >= 0 && x < leveys && x >= 0 && ruudukko[y][x] != 1) {
            return true;
        }
        return false;
    }
    
    /**
     * Tarkistaa onko siirtymä (naapuriruutuun) diagonaalinen.
     * @param i Indeksi i viittaa suunnat-taulukon arvoihin. Suunnat ilmaisevat 
     * oikeaa lisäystä ruudun koordinaatteihin eri ilmansuunnissa sijaitseviin 
     * naapuriruutuihin siirryttäessä.
     */
    public boolean kuljetaanDiagonaalisesti(int i) {
        return (!(suunnat[i][0] == 0 || suunnat[i][1] == 0)); 
    }
    
    /**
     * Palauttaa ruutujen välisen etäisyyden. Parametrina annettava i viittaa
     * suunnat-taulukon arvoihin.
     * @param i 
     * @return 
     */
    public double ruutujenValinenEtaisyys(int i) {
        double etaisyys = 1;
        if (kuljetaanDiagonaalisesti(i)) {
            etaisyys = Math.sqrt(2);
        }
        return etaisyys;
    }

    /**
     * Muodostaa lyhimmän reitin "peruuttamalla" maalisolmusta lähtösolmuun.
     * @param ax lähtösolmun x-koordinaatti
     * @param ay lähtösolmun y-koordinaatti
     * @param bx maalisolmun x-koordinaatti
     * @param by maalisolmun y-koordinaatti
     * @param mista Taulukko, johon on talletettu kunkin ruudun vanhempi.
     */
    public void muodostaReitti(int ax, int ay, int bx, int by, Ruutu[][] mista) {
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
        lyhinReitti.kaannaJarjestys();
    }
    
    /**
     * Palauttaa lyhimmän reitin.
     * @return 
     */
    public Lista haeLyhinReitti() {
        return lyhinReitti;
    }

    /**
     * Tyhjetää listan, jolle lyhin reitti talletettu.
     */
    public void tyhjennaReitti() {
        lyhinReitti = new Lista<>();
    }
    
    /**
     * Palauttaa reitin pituuden (ei käytössä tällä hetkellä).
     * @return 
     */
    public double haeReitinPituus() {
        return reitinPituus;
    }
    
    

    
}
