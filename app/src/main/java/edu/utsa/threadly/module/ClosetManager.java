package edu.utsa.threadly.module;

import java.util.ArrayList;

public class ClosetManager {

    private ArrayList<Closet> closets;

    ClosetManager(){
        this.closets = new ArrayList<Closet>();
    }
    public ArrayList<Closet> getClosets() {
        return this.closets;
    }

    public void setClosets(ArrayList<Closet> closets) {
        this.closets = closets;
    }

    public void addCloset(Closet closet){
        this.closets.add(closet);
    }

    public Closet removeCloset(int index){
        return this.closets.remove(index);
    }

}


