package com.example.guestuser.findmyword;

import java.util.ArrayList;

/**
 * Created by Karol Zdebel on 2017-03-04.
 */

public class WordsJson {

    class JSONCategory{
        ArrayList<JSONCategory> subcategories;
        String name;

        public JSONCategory(String name){
            this.name = name;
        }
    }

    public void getJSON(){

        /*Main categories*/
        JSONCategory jsonMain = new JSONCategory("All");
        JSONCategory clothes = new JSONCategory("Clothes");
        JSONCategory activities = new JSONCategory("Activities");
        JSONCategory food = new JSONCategory("Food");
        JSONCategory places = new JSONCategory("Places");
        JSONCategory feelings = new JSONCategory("Feelings");
        JSONCategory people = new JSONCategory("People");

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
