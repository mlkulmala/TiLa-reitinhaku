
package tiralabra.reitinhaku.verkko;

/**
 * Kartan ruutuja kuvaava luokka. Sisältää ruudun koordinaatit sekä tiedon 
 * etäisyydestä lähtösolmuun kuljettua reittiä pitkin.
 * @author mlkul
 */
public class Ruutu implements Comparable<Ruutu> {
    
    private int x, y;
    double etaisyys;
    
    public Ruutu(int x, int y, double etaisyys) {
        this.x = x;
        this.y = y;
        this.etaisyys = etaisyys;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public double getEtaisyys() {
        return this.etaisyys;
    }
    
    /**
     * Ruudut voidaan laittaa järjestykseen etäisyyden mukaan. Tässä käytetty 
     * Javan valmista metodia.
     * @param s
     */
    @Override
    public int compareTo(Ruutu s) {
        if (this.etaisyys - s.etaisyys > 0) {
            return 1;
        } else if (this.etaisyys - s.etaisyys < 0) {
            return -1;
        } else {
            return 0;
        }
    }
    
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
