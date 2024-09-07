package com.example.recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{


    ArrayList<RecetasModel> recetasModel = new ArrayList<>();
    DbHelper db;
    Boolean checkInsertData = false;
    Boolean checkDeleteData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHelper(this);

        //to create new database
//        db.onUpgrade(db.getWritableDatabase(),0,0);
//        db.onCreate(db.getWritableDatabase());

        if(getIntent().getExtras() != null)
        {
            RecetasModel newReceta;

            //receiving recipe info to create insert to data base
            String new_recipe_name = getIntent().getStringExtra("newRecipeName");
            String new_ingredients = getIntent().getStringExtra("newRecipeIngredients");
            String new_recipe = getIntent().getStringExtra("newRecipe");


            //receiving bytearray from image and translating it to bitmap
            byte[] byteArrImg = getIntent().getByteArrayExtra("newImage");
            Bitmap new_image = BitmapFactory.decodeByteArray(byteArrImg, 0, byteArrImg.length);

            newReceta = new RecetasModel(new_recipe_name, new_ingredients, new_recipe, new_image);

            if(Objects.equals(new_recipe_name, ""))
            {
                Toast.makeText(MainActivity.this,"Name needed", Toast.LENGTH_SHORT).show();
            }
            else {
                checkInsertData = db.insertRecipeData(newReceta);
            }
        }

        if (checkInsertData)
        {
            Toast.makeText(MainActivity.this,"inserted to db", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,"not inserted to db", Toast.LENGTH_SHORT).show();
        }

        displayData();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_id);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, recetasModel, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(position -> {

            delete(adapter,  position);

        });
    }

    private void displayData(){
        Cursor cursor = db.getData();
        while(cursor.moveToNext()){
            byte[] imgByteArr = cursor.getBlob(4);
            Bitmap bm = BitmapFactory.decodeByteArray(imgByteArr, 0, imgByteArr.length);
            recetasModel.add(new RecetasModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), bm));
        }
    }

    public void delete(RecyclerViewAdapter adapter, int position){
        recetasModel.remove(position);
        adapter.notifyItemRemoved(position);

        checkDeleteData = db.deleteRecipeData(Integer.toString(position));

        if (checkDeleteData)
        {
            Toast.makeText(MainActivity.this,"deleted from db", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,"NOT deleted from db", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, Receta.class);

        //receive and send recipe info to new activity that shows Receta
        intent.putExtra("RecipeName", recetasModel.get(position).getRecipe_name());
        intent.putExtra("RecipeIngredients", recetasModel.get(position).getRecipe_ingredients());
        intent.putExtra("Recipe", recetasModel.get(position).getRecipe());

        //receive image bitmap, translate it to bytearray to send out
        Bitmap imgBitMap = recetasModel.get(position).getImg();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        imgBitMap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
        intent.putExtra("Image", bs.toByteArray());

        startActivity(intent);
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, nuevaReceta.class);
        startActivity(intent);
    }
}