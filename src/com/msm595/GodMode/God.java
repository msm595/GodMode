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
    private boolean isGod;
    private boolean noFire;
    private boolean airBubble;
    private String world;
    
    public God() {
        this(false, false, false, "");
    }
    public God(boolean g) {
        this(g, false, false, "");
    }
    public God(boolean g, boolean n) {
        this(g, n, false, "");
    }
    public God(boolean g, boolean n, boolean a) {
        this(g, n, a, "");
    }
    public God(boolean g, boolean n, boolean a, String w) {
        isGod = g;
        noFire = n;
        airBubble = a;
        world = w;
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
    
    
    
//    public boolean airBubble() {
//        return airBubble;
//    }
//    
//    public void airBubble(boolean a) {
//        airBubble = a;
//    }
//    
//    public boolean toggleBubble() {
//        airBubble = !airBubble;
//        return airBubble;
//    }
    
    
    
    public String getWorld() {
        return world;
    }
    
    public void setWorld(String w) {
        world = w;
    }
}
