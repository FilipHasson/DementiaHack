package com.example.guestuser.findmyword;

import android.util.Log;

import com.example.guestuser.findmyword.API.FindMyWordAPIController;
import com.example.guestuser.findmyword.API.WordResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Karol Zdebel on 2017-03-04.
 */

public class WordsJson {

    public WordsJson(){
        this.createJSONStructure();
        json = new JSONObject();
        this.json = this.createJson(jsonMain);
    }

    class JSONCategory{
        ArrayList<JSONCategory> subcategories;
        String name;

        public JSONCategory(String name){
            subcategories = new ArrayList<>();
            this.name = name;
        }
    }

    private JSONCategory jsonMain;
    private JSONObject json;
    private JSONObject jsonUse;

    public JSONObject getJson(){
        return json;
    }


    private JSONObject createJson(JSONCategory main){
        final JSONObject jsonObj = new JSONObject();

        try{
            //Insert name
            jsonObj.put("Name",main.name);

            //Check to see if categories exist if so recursive call

            Log.d("debug_karol","Size: "+main.subcategories.size());
            if (main.subcategories.size() > 0){
                JSONArray arr = new JSONArray();
                for (int i=0;i<main.subcategories.size();i++){
                    arr.put(createJson(main.subcategories.get(i)));
                }
                jsonObj.put("Categories",arr);
                Log.d("debug_karol","112: "+jsonObj.toString());
            }

            //Leaf node, call query for words unless hard-coded
            else{
                //query call

                FindMyWordAPIController controller = new FindMyWordAPIController();
                controller.searchWord(main.name, new Callback<List<WordResult>>() {
                    @Override
                    public void onResponse(Call<List<WordResult>> call, Response<List<WordResult>> response) {
                        if (response.isSuccessful()) {
                            //List of word results
                            List<WordResult> wordResults = response.body();
                            //Access first result and get word
                            JSONArray arr = new JSONArray();

                            Log.d("debug_karol","words length: "+wordResults.size());

                            int wordNum;
                            if (wordResults.size() < 6){
                                wordNum = wordResults.size();
                            }else{
                                wordNum = 6;
                            }

                            for (int j=0;j<wordNum;j++){
                                arr.put(wordResults.get(j).getWord());
                                Log.d("debug_karol", "Got word: "+wordResults.get(j).getWord());
                            }
                            try{
                                jsonObj.put("Words",arr);
                                Log.d("debug_karol", "arrr here: "+jsonObj.toString());

                            }catch(org.json.JSONException e){
                                Log.d("debug_karol","Failed to add array");
                            }
                        }
                        else {
                            Log.d("debug_karol", "failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WordResult>> call, Throwable t) {
                        Log.d("debug_karol", "call failed");
                        t.printStackTrace();
                    }
                });

            }

        }catch(org.json.JSONException e){
            Log.d("debug_karol","Error inputing json");
        }

        Log.d("debug_karol","returning json obj: "+jsonObj);

       return jsonObj;

    }

    private void createJSONStructure(){

        /*Main categories*/
        jsonMain = new JSONCategory("All");
        JSONCategory clothes = new JSONCategory("Clothes");
        JSONCategory activities = new JSONCategory("Activities");
        JSONCategory food = new JSONCategory("Food");
        JSONCategory places = new JSONCategory("Places");
        JSONCategory feelings = new JSONCategory("Feelings");
        JSONCategory people = new JSONCategory("People");

        /*Add main categories to jsonMain*/
        jsonMain.subcategories.add(clothes);
        jsonMain.subcategories.add(activities);
        jsonMain.subcategories.add(food);
        jsonMain.subcategories.add(places);
        jsonMain.subcategories.add(feelings);
        jsonMain.subcategories.add(people);

        /*Home categories*/
        JSONCategory kitchen = new JSONCategory("Kitchen");
        JSONCategory washroom = new JSONCategory("Washroom");
        JSONCategory garage = new JSONCategory("Garage");
        JSONCategory bedroom = new JSONCategory("Bedroom");
        JSONCategory backyard = new JSONCategory("Backyard");

        /*Clothes*/
        JSONCategory jacket = new JSONCategory("Jacket");
        JSONCategory shirt = new JSONCategory("Shirt");
        JSONCategory pants = new JSONCategory("Pants");

        /*Activities*/
        JSONCategory sports = new JSONCategory("Sports");
        JSONCategory eat = new JSONCategory("Eat");

        /*Food*/
        JSONCategory meat = new JSONCategory("Meat");
        JSONCategory fruit = new JSONCategory("Fruit");
        JSONCategory vegetable = new JSONCategory("Vegetable");
        JSONCategory candy = new JSONCategory("Candy");
        JSONCategory soup = new JSONCategory("Soup");

        /*Places*/
        JSONCategory restaurant = new JSONCategory("Restaurant");
        JSONCategory park = new JSONCategory("Park");
        JSONCategory home = new JSONCategory("Home");
        JSONCategory church = new JSONCategory("Church");

        /*Feelings*/
        JSONCategory anger = new JSONCategory("Anger");
        JSONCategory sad = new JSONCategory("Sad");
        JSONCategory happy = new JSONCategory("Happy");

        /*People*/
        JSONCategory  family = new JSONCategory("Family");
        JSONCategory politician = new JSONCategory("Politician");
        JSONCategory caregiver = new JSONCategory("Caregiver");

        /*Kitchen*/
        JSONCategory appliances = new JSONCategory("Appliances");
        JSONCategory kitchenUtensils = new JSONCategory("Kitchen Utensils");

        /*Washroom*/
        JSONCategory toiletries = new JSONCategory("Toiletries");
        JSONCategory bathroomAppliances = new JSONCategory("Bathroom");

        /*Add subcategories to home categories*/
        home.subcategories.add(kitchen);
        home.subcategories.add(washroom);
        home.subcategories.add(garage);
        home.subcategories.add(bedroom);
        home.subcategories.add(backyard);

        /*Add subcategories to clothes categories*/
        clothes.subcategories.add(jacket);
        clothes.subcategories.add(shirt);
        clothes.subcategories.add(pants);

        /*Add subcategories to clothes categories*/
        activities.subcategories.add(washroom);
        activities.subcategories.add(sports);
        activities.subcategories.add(eat);

        /*Add subcategories to Food*/
        food.subcategories.add(meat);
        food.subcategories.add(fruit);
        food.subcategories.add(vegetable);
        food.subcategories.add(candy);
        food.subcategories.add(soup);

        /*Add subcategories to Places*/
        places.subcategories.add(restaurant);
        places.subcategories.add(park);
        places.subcategories.add(home);
        places.subcategories.add(church);

        /*Add subcategories to people*/
        people.subcategories.add(family);
        people.subcategories.add(politician);
        people.subcategories.add(caregiver);

        /*Add subcategories to Kitchen*/
        kitchen.subcategories.add(appliances);
        kitchen.subcategories.add(kitchenUtensils);
        kitchen.subcategories.add(food);

        /*Add subcategories to washroom*/
        washroom.subcategories.add(toiletries);
        washroom.subcategories.add(bathroomAppliances);

        /*Add subcategories to eat*/
        eat.subcategories.add(food);
        eat.subcategories.add(restaurant);
    }

}
