package com.example.recetas;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2old extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String recipe_name = getIntent().getStringExtra("RecipeName");
        String recipe_ingredients = getIntent().getStringExtra("RecipeIngredients");
        String recipe = getIntent().getStringExtra("Recipe");
        int image = getIntent().getIntExtra("Image",0);

        TextView recipe_name_textView = findViewById(R.id.recipeName);
        TextView recipe_ingredients_textView = findViewById(R.id.recipeIngredients);
        TextView recipe_textView = findViewById(R.id.recipe);
        ImageView image_textView = findViewById(R.id.image);

        recipe_name_textView.setText(recipe_name);
        recipe_ingredients_textView.setText(recipe_ingredients);
        recipe_textView.setText(recipe);
        image_textView.setImageResource(image);
    }
}
