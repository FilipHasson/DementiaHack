package com.example.guestuser.findmyword;

import java.util.ArrayList;

/**
 * Created by Karol Zdebel on 2017-03-04.
 */

public class Category {
    private boolean hasWords;
    private String name;
    private ArrayList<Category> nextCategory;
    private Category prevCategory;

    public Category(){
        name = "";
        hasWords = false;
        nextCategory = null;
        prevCategory = null;
    }

    public void setPrevCategories(ArrayList<Category> next){
        nextCategory = next;
    }

    public Category getPrevCategory(){
        return prevCategory;
    }

    public void setNextCategories(ArrayList<Category> next){
        nextCategory = next;
    }

    public ArrayList<Category> getNextCategories(){
        return nextCategory;
    }

    public String getName(){
        return this.name;
    }

    public boolean hasWords(){
        return hasWords;
    }
}
