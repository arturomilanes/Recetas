package com.example.recetas;

import android.graphics.Bitmap;

public class RecetasModel {
    String Recipe_name;
    String Recipe_ingredients;
    String Recipe;
    Bitmap Img;


    public RecetasModel(String recipe_name, String recipe_ingredients, String recipe, Bitmap img){
        Recipe_name = recipe_name;
        Recipe_ingredients = recipe_ingredients;
        Recipe = recipe;
        Img = img;
    }

    public String getRecipe_name(){
        return Recipe_name;
    }

    public void setRecipe_name(String name){
        this.Recipe_name = name;
    }

    public  String getRecipe_ingredients(){
        return Recipe_ingredients;
    }

    public void setRecipe_ingredients(String ingredients){
        this.Recipe_ingredients = ingredients;
    }

    public  String getRecipe(){
        return Recipe;
    }

    public void setRecipe(String recipe){
        this.Recipe = recipe;
    }

    public Bitmap getImg(){
        return Img;
    }

    public void setImg(Bitmap img){
        this.Img = img;
    }
}
