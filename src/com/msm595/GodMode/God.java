/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.msm595.GodMode;

import org.bukkit.entity.Player;
/**
 *
 * @author Alex
 */
public class God {
    private Player player;
    
    private boolean isGod;
    private boolean noFire;
    
    public God(Player p) {
        this(p, false, false);
    }
    
    public God(Player p, boolean g) {
        this(p, g, false);
    }
    
    public God(Player p, boolean g, boolean n) {
        player = p;
        isGod = g;
        noFire = n;
    }
    
    
    
    public boolean isGod() {
        return isGod;
    }
    
    public void isGod(boolean i) {
        isGod = i;
    }
    
    public boolean toggleGod() {
        isGod = !isGod;
        return isGod;
    }
    
    
    
    public boolean noFire() {
        return noFire;
    }
    
    public void noFire(boolean n) {
        noFire = n;
    }
    
    public boolean toggleFire() {
        noFire = !noFire;
        return noFire;
    }

}
