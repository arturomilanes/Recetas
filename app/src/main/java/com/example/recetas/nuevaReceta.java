package com.example.recetas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class nuevaReceta extends AppCompatActivity {

    ActivityResultLauncher<Intent> resultLauncher;
    EditText nombre, ingredients, receta;
    ImageView imagen;
    Bitmap imgBitMap;
    Button btnPickImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);

        nombre = findViewById(R.id.new_nombre);
        ingredients= findViewById(R.id.new_ingredientes);
        receta = findViewById(R.id.new_receta);
        imagen = (ImageView) findViewById(R.id.imgView);
        setImage();
        btnPickImg = findViewById(R.id.btnPickImg);

        btnPickImg.setOnClickListener(view -> pickImg());

    }

    private void pickImg(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void setImage(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        Uri imgUri = o.getData().getData();
                        imagen.setImageURI(imgUri);


                        try {
                            imgBitMap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                            imagen.setImageBitmap(imgBitMap);
                        } catch (IOException e) {
                            Toast.makeText(nuevaReceta.this,"select image", Toast.LENGTH_SHORT).show();
                            throw new RuntimeException(e);
                        }
//
                    }
                }
        );
    }
    public void onButtonClick(View view) {
        Intent intent = new Intent(nuevaReceta.this, MainActivity.class);

        //send recipe info to main activity to add to data base
        intent.putExtra("newRecipeName", nombre.getText().toString());
        intent.putExtra("newRecipeIngredients", ingredients.getText().toString());
        intent.putExtra("newRecipe", receta.getText().toString());

        //send image from bitmap to bytearray format
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        imgBitMap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
        intent.putExtra("newImage", bs.toByteArray());

        startActivity(intent);
    }

}