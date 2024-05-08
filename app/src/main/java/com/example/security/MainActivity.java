package com.example.security;

import static com.example.security.R.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import  com.example.security.HillCipher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    public int REQUEST_CODE = 1;
EditText EntrerEncyptmessage  , entrerkey , ciphertext,keycipher;
TextView afichierencryptmessage , afichierplain ,up1 , up2;
Button ENCRYPTER ,btnenc , btndec , Decrypt ,encrypterfile , decryptfile;

LinearLayout Linearencrypt , decryptionlayout ;
ImageView endupload , uplfiledcr ;

StorageReference imgref ;
Uri file ;

String text = null ;
String textdec = null ;
int cmp = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
init();






        Drawable drawable1 = getResources().getDrawable(R.drawable.bak1);
        Drawable drawable2 = getResources().getDrawable(drawable.bak2);
        btnenc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           cmp = 0 ;
                btnenc.setBackground(drawable1);
                btndec.setBackground(drawable2);
                Linearencrypt.setVisibility(View.VISIBLE);
                decryptionlayout.setVisibility(View.INVISIBLE);

            }
        });
        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmp = 1 ;

                btnenc.setBackground(drawable2);
                btndec.setBackground(drawable1);
                Linearencrypt.setVisibility(View.INVISIBLE);
                decryptionlayout.setVisibility(View.VISIBLE);

            }
        });



        ENCRYPTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



             String key =  entrerkey.getText().toString();
             String mesage = EntrerEncyptmessage.getText().toString();
                if (mesage.isEmpty()){
                    EntrerEncyptmessage.setError("Please don't less cipher text empty");
                }else if (key.isEmpty()){
                    entrerkey.setError("Please don't less key empty");
                }else{
                    if (HillCipher.verifierKey(key) ){

                        int [][] keymatrice =HillCipher.stringToSquareMatrix(key);
                        if (HillCipher.CalcDetermina(keymatrice)){
                            String result = HillCipher.encrypt(mesage,keymatrice);
                            afichierencryptmessage.setText(result);
                        }else {
                            Toast.makeText(MainActivity.this, "ENTER A VALID KEY", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "ENTER A VALID KEY", Toast.LENGTH_SHORT).show();
                    }
                }





            }
        });

        Decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key =  keycipher.getText().toString();
                String mesage = ciphertext.getText().toString();
                if (mesage.isEmpty()){
                    ciphertext.setError("Please don't less cipher text empty");
                }else if (key.isEmpty()){
                    keycipher.setError("Please don't less key empty");
                }else {
                    if (HillCipher.verifierKey(key) ){

                        int [][] keymatrice =HillCipher.stringToSquareMatrix(key);
                        if (HillCipher.CalcDetermina(keymatrice)){
                            if (mesage.isEmpty()){
                                ciphertext.setError("Please don't less anything empty");
                            } else{
                                String result = HillCipher.Decrypt(mesage,keymatrice);
                                afichierplain.setText(result);
                            }

                        }else {
                            Toast.makeText(MainActivity.this, "ENTER A VALID KEY", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "ENTER A VALID KEY", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        endupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();



            }
        });

        uplfiledcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();



            }
        });

        encrypterfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key =  entrerkey.getText().toString();
                if (key.isEmpty()){
                    entrerkey.setError("Please don't less key empty");
                }else if (text == null){
                    Toast.makeText(MainActivity.this, "Please upload your file", Toast.LENGTH_SHORT).show();
                }else {
                    if (HillCipher.verifierKey(key) ){

                        int [][] keymatrice =HillCipher.stringToSquareMatrix(key);
                        if (HillCipher.CalcDetermina(keymatrice)){
                            String result = HillCipher.encrypt(text.toString(),keymatrice);
                            afichierencryptmessage.setText(result);
                        }else {
                            Toast.makeText(MainActivity.this, "ENTER A VALID KEY", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "ENTER A VALID KEY", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        decryptfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key =  keycipher.getText().toString();
                if (key.isEmpty()){
                    keycipher.setError("Please don't less key empty");
                }else if (textdec == null){
                    Toast.makeText(MainActivity.this, "Please upload your file", Toast.LENGTH_SHORT).show();
                }else {
                    if (HillCipher.verifierKey(key) ){
                        int [][] keymatrice =HillCipher.stringToSquareMatrix(key);
                        if (HillCipher.CalcDetermina(keymatrice)){

                                String result = HillCipher.Decrypt(textdec.toString(),keymatrice);
                                afichierplain.setText(result);


                        }else {
                            Toast.makeText(MainActivity.this, "ENTER A VALID KEY", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "ENTER A VALID KEY", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    public  void  init(){
        EntrerEncyptmessage = findViewById(id.plaintext);
        entrerkey = findViewById(id.editTextKey);
        afichierencryptmessage = findViewById(id.afichierencryptresult);
        ENCRYPTER = findViewById(id.ENCRYPT) ;
        Linearencrypt = findViewById(id.encryptlayot);
        btnenc = findViewById(id.btnenc);
        btndec = findViewById(id.btndec);
        decryptionlayout = findViewById(R.id.decryptionlayout);
        Decrypt = findViewById(R.id.Decrypt);
        ciphertext = findViewById(R.id.ciphertext);
        keycipher = findViewById(R.id.keycipher);
        afichierplain = findViewById(R.id.afichierplain);
        endupload = findViewById(R.id.endupload);
        up1 = findViewById(R.id.up1);
        imgref = FirebaseStorage.getInstance().getReference().child("fileupload");
        encrypterfile = findViewById(R.id.encrypterfile);
        decryptfile = findViewById(R.id.decryptfile);
        uplfiledcr = findViewById(R.id.uplfiledcr);
        up2 = findViewById(R.id.up2);
    }



    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain"); // Limiting to text files
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                // Do something with the URI, like reading the file content
                handleSelectedFile(uri);
            }
        }
    }
    private void handleSelectedFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            reader.close();

            String fileContent = stringBuilder.toString();

            if (cmp == 0){
                text = fileContent ;


                up1.setText(text);
            }else if (cmp ==1){
                textdec = fileContent ;
                up2.setText(textdec);
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}


