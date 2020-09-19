/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralabra.reitinhaku.verkko;

/**
 *
 * @author mlkul
 */
public class Solmu implements Comparable<Solmu> {
    
    private int x, y;
    double etaisyys;
    
    public Solmu(int x, int y, double etaisyys) {
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
    
    @Override
    public int compareTo(Solmu s) {
        if (this.etaisyys - s.etaisyys > 0) {
            return 1;
        } else if(this.etaisyys - s.etaisyys < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}