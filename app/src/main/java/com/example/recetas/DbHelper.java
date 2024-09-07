package com.example.recetas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper( Context context) {
        super(context, "RecipeData.db", null, 10);
    }

    private ByteArrayOutputStream byteAOS;
    private  byte[] imgInBytes;

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table RecipeDetails(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "recipeName TEXT, " +
                "ingredients TEXT, recipe TEXT, image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        SQLiteDatabase db = this.getWritableDatabase();
        DB.execSQL("drop Table if exists RecipeDetails");
    }

    public Boolean insertRecipeData(RecetasModel receta){//String recipeName, String ingredients, String recipe, Bitmap image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //add recipe info to cv
        cv.put("recipeName", receta.getRecipe_name());
        cv.put("ingredients", receta.getRecipe_ingredients());
        cv.put("recipe", receta.getRecipe());

        //Make image into byte array and add to cv
        byte[] img = insertImage(receta.getImg());
        cv.put("image", img);

        //insert cv into database
        long result = db.insert("RecipeDetails",null, cv);
        return result != -1;
    }

    //function to return image from Bitmap to byte array
    private byte[] insertImage(Bitmap image){
        byteAOS = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,byteAOS);

        imgInBytes = byteAOS.toByteArray();
        return imgInBytes;
    }

    public Boolean deleteRecipeData(String position){
        SQLiteDatabase db = this.getWritableDatabase();

        return 0 != db.delete("RecipeDetails", "ID = ?", new String[]{position});
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from RecipeDetails", null);
        return cursor;
    }

}
