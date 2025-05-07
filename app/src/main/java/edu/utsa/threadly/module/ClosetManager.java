package edu.utsa.threadly.module;

import java.util.ArrayList;

public class ClosetManager {
    private static ArrayList<Closet> closets;

    private int closetCount;

    /**
     *  Helps to manage and store closet objects
     */
    ClosetManager(){
        this.closets = new ArrayList<Closet>()
        ;
    }

    /**
     * grabs the whole closet array
     * @return
     */

    public void setCount(){
        this.closetCount = closets.size();
    }

    public void addCount(){
        this.closetCount++;
    }
    public ArrayList<Closet> getClosets() {
        return this.closets;
    }

    /**
     * sets the whole closet array with inserted one
     * @param closets
     */
    public void setClosets(ArrayList<Closet> closets) {
        this.closets = closets;
    }

    /**
     * adds a closet to the closet array
     * @param closet
     */
    public void addCloset(Closet closet){
        this.closets.add(closet);
    }

    /**
     * removes a closet from the given index
     * @param index
     * @return
     */
    public Closet removeCloset(int index){
        return this.closets.remove(index);
    }

}


