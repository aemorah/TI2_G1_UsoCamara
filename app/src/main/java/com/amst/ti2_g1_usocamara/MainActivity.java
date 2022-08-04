package com.amst.ti2_g1_usocamara;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int PERM_CODE = 101;
    public static final int codigoCamara = 102;
    ImageView imagenSeleccionada;
    Button camara;
    Button galeria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagenSeleccionada = findViewById(R.id.imagen);
        camara = findViewById(R.id.button_camara);
        galeria = findViewById(R.id.button_galeria);
    }

    public void tomarFoto(View v) {
        Toast.makeText(MainActivity.this, "Tome una foto", Toast.LENGTH_SHORT).show();
        solicitarPermiso();
    }

    public void seleccionarGaleria(View v) {
        Toast.makeText(MainActivity.this, "Seleccione una foto de la galeria", Toast.LENGTH_SHORT).show();
    }

    private void solicitarPermiso() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERM_CODE);
        } else {
            abrirCamara();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                Toast.makeText(MainActivity.this, "No se tiene permiso de la camara", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void abrirCamara(){
        Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camara, codigoCamara);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == codigoCamara){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imagenSeleccionada.setImageBitmap(image);
        }
    }
}