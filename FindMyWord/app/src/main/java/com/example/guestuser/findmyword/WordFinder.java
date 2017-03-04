package com.example.guestuser.findmyword;

import java.util.ArrayList;

/**
 * Created by Karol Zdebel on 2017-03-04.
 */

public class WordFinder {
    private Category curCategory;
    private ArrayList<Category> categories;

    //Initialize WordFinder object
    public WordFinder(){
        curCategory = null;
        categories = null;
    }

    public WordFinder(Category curCategory){
        this.curCategory = curCategory;
        categories = curCategory.getNextCategories();
    }

    //Get all names of current categories
    public String[] getNames(){
        String[] s = new String[categories.size()];
        for (int i=0;i<categories.size();i++){
             s[i] = categories.get(i).getName();
        }

        return s;
    }

    //Select a particular category, resulting in new range of categories
    public void selectCategory(String categorySelected){
        for  (Category c: categories){
            if (c.getName().equals(categorySelected)){
                this.curCategory = c;
                this.categories = c.getNextCategories();
            }
        }
    }

    //Go to previous category set
    public void goBack(){
        curCategory = curCategory.getPrevCategory();
        categories = curCategory.getNextCategories();
    }

    //Set category and the following categories afterwards
    public void setCategory(Category c){
        this.curCategory = c;
        categories = c.getNextCategories();
    }

    private void getAllCategories(){

    }

}
