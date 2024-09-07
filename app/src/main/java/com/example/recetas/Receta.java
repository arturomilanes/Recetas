package com.example.recetas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Receta extends AppCompatActivity {
    RecetasModel receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        //receive recipe data from Intent
        recieveRecetaInfo();

        //display recipe
        displayRecetaInfo();

        //todo: edit functionality
    }

    private void recieveRecetaInfo(){
        String recipe_name = getIntent().getStringExtra("RecipeName");
        String recipe_ingredients = getIntent().getStringExtra("RecipeIngredients");
        String recipe = getIntent().getStringExtra("Recipe");

        byte[] byteArrImg = getIntent().getByteArrayExtra("Image");
        Bitmap image = BitmapFactory.decodeByteArray(byteArrImg, 0, byteArrImg.length);

        receta = new RecetasModel(recipe_name,recipe_ingredients,recipe,image);
    }

    private void displayRecetaInfo(){
        TextView recipe_name_textView = findViewById(R.id.recipeName);
        TextView recipe_ingredients_textView = findViewById(R.id.recipeIngredients);
        TextView recipe_textView = findViewById(R.id.recipe);
        ImageView image_View = findViewById(R.id.image);

        recipe_name_textView.setText(receta.getRecipe_name());
        recipe_ingredients_textView.setText(receta.getRecipe_ingredients());
        recipe_textView.setText(receta.getRecipe());
        image_View.setImageBitmap(receta.getImg());
    }
}