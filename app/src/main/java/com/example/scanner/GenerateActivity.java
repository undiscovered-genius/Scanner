package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateActivity extends AppCompatActivity {

    MultiAutoCompleteTextView input;
    Button generate;
    ImageView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        input = findViewById(R.id.textCode);
        generate = findViewById(R.id.Gbutton);
        output = findViewById(R.id.output);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String code = input.getText().toString().trim();

                if (code.isEmpty()){
                    input.setError("Enter to generate QR Code");
                }else{
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE,350,350);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        output.setImageBitmap(bitmap);

                        InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        methodManager.hideSoftInputFromWindow(input.getApplicationWindowToken(),0);

                        Toast.makeText(GenerateActivity.this,"Take a Screenshot of QR Code",Toast.LENGTH_SHORT).show();
                    } catch (WriterException e) {
                        Toast.makeText(GenerateActivity.this,""+e,Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}