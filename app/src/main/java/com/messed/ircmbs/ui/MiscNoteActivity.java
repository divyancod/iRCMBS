package com.messed.ircmbs.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.messed.ircmbs.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MiscNoteActivity extends AppCompatActivity {

    EditText editText;
    Toolbar toolbar;
    MaterialButton save, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc_note);
        editText = findViewById(R.id.note_edit);
        toolbar = findViewById(R.id.toolbar_all);
        save = findViewById(R.id.save_note);
        clear = findViewById(R.id.clear_note);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        editText.setText(readFile());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeFile(editText.getText().toString());
                Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(MiscNoteActivity.this);
                alertDialog.setMessage("Do you want to clear Note?");
                alertDialog.setTitle("Clear");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        writeFile("");
                        editText.setText("");
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

            }
        });
    }

    private void writeFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput("divyanconfig.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFile() {
        String ret = "";

        try {
            InputStream inputStream = getApplicationContext().openFileInput("divyanconfig.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
}