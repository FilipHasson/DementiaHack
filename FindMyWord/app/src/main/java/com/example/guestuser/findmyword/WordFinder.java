package com.example.guestuser.findmyword;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
    public void setCategory(Category c) {
        this.curCategory = c;
        categories = c.getNextCategories();
    }

    //get all categories
    public ArrayList<Category> getAllCategories(Context context){
        String words;  //If it's a leaf category, words are stored here
        String categoriesNext; //Categories following category parsed
        String categoryName;  //Name of current category
        ArrayList<Category> allCategories = new ArrayList<>();

        try{
            JSONObject jsonObj = new JSONObject(loadJSONFromAsset("data.JSON",context));

            //Get Name
            categoryName = jsonObj.getString("Name");

            //Get words
            words = jsonObj.getString("Words");

            //Get following categories
            categoriesNext = jsonObj.getString("Categories");

            Log.d("debug_karol","categoryName:"+categoryName);
            Log.d("debug_karol","categoryNext:"+categoriesNext);
            Log.d("debug_karol","words:"+words);



        }catch(org.json.JSONException e) {
            return null;
        }
        return allCategories;
    }

    //Load json file and return it as string
    private String loadJSONFromAsset(String name, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
