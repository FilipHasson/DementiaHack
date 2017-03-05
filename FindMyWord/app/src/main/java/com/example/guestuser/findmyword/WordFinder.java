package com.example.guestuser.findmyword;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Karol Zdebel on 2017-03-04.
 */

public class WordFinder {
    private String name;
    private Category curCategory;
    private ArrayList<Category> categories;
    private Context context;

    //Initialize WordFinder object
    public WordFinder(Context context){
        curCategory = null;
        categories = null;
        this.context = context;
        this.getAllCategories();
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
                Category prev = this.curCategory;
                this.curCategory = c;
                c.setPrevCategory(prev);
                this.categories = c.getNextCategories();
            }
        }
    }

    //Are words being displayed instead of categories
    public boolean hasWords(){
        return curCategory.hasWords();
    }

    //Do more categories exist
    public boolean hasNextCategories(){
        return !(curCategory.getNextCategories()== null);
    }

    //If this is a top-most category
    public boolean hasPrevCategories(){
       return !(curCategory.getPrevCategory() == null);
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

    private Category getCategories(JSONObject jsonObj){
        Category curCategory = new Category();
        ArrayList<Category> nextCategories = new ArrayList<>();
        String curCatName;
        String[] words;

        //Get and set name
        try{
            curCatName = jsonObj.getString("Name");
            Log.d("debug_karol","Parsed Name:"+curCatName);
            curCategory.setName(curCatName);
        }catch(org.json.JSONException e){
            Log.d("debug_karol","Could not parse name");
            return null;
        }


        //Get words
        JSONArray wordsArr=null;
        try{
            wordsArr = jsonObj.getJSONArray("Words");
            Log.d("debug_karol","Parsed Words");

            //Store words
            words = new String[wordsArr.length()];
            for (int j=0;j<wordsArr.length();j++){
                words[j] = wordsArr.getString(j);
            }
            curCategory.setWords(words);

        }catch(org.json.JSONException e) {
            Log.d("debug_karol", "Could not parse words");
            words = null;
        }

        //Get following categories recursively
        JSONArray categoriesNext;
        try{

            categoriesNext = jsonObj.getJSONArray("Categories");

            for (int i=0;i<categoriesNext.length();i++){
                Log.d("debug_karol","Getting category next for: "+categoriesNext.getString(i));
                nextCategories.add(getCategories(categoriesNext.getJSONObject(i)));
            }
            curCategory.setNextCategories(nextCategories);
            Log.d("debug_karol","Parsed Category");
            return curCategory;

        }catch(org.json.JSONException e){
            Log.d("debug_karol","Reached end");
            return curCategory;
        }
    }

    //get all categories
    public void getAllCategories(){
        String words[];  //If it's a leaf category, words are stored here
        JSONArray categoriesNext; //Categories following category parsed
        String categoryName;  //Name of current category
        Category curCategory = new Category();
        ArrayList<Category> allCategories = new ArrayList<>();

        try{
            JSONObject jsonObj = new JSONObject(loadJSONFromAsset("data.JSON",context));
            Log.d("debug_karol","Loaded JSON object");
            JSONArray jsonArr = jsonObj.getJSONArray("all");
            Log.d("debug_karol","Got JSON array, length:"+jsonArr.length());

            Log.d("debug_karol","here");
            for (int i=0;i<jsonArr.length();i++){
                allCategories.add(getCategories(jsonArr.getJSONObject(i)));
                Log.d("debug_karol","Returned category name:"+allCategories.get(i).getName());
            }

            this.categories = allCategories;
            this.curCategory = null;

        }catch(org.json.JSONException e) {
            Log.d("debug_karol","JSONEXCEPTION");
            this.curCategory = null;
            this.categories = null;
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
