package com.example.myapp; // Replace with your package name

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int FILE_SELECT_REQUEST_CODE = 1;
    private MediaPlayer mediaPlayer;
    private TextView txtFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSelectFile = findViewById(R.id.btnSelectFile);
        txtFilePath = findViewById(R.id.txtFilePath);

        btnSelectFile.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*"); 
            startActivityForResult(intent, FILE_SELECT_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri audioFileUri = data.getData();
                playAudio(audioFileUri);
            }
        }
    }

    private void playAudio(Uri audioFileUri) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, audioFileUri);
        txtFilePath.setText(audioFileUri.getPath()); // Display the path
        mediaPlayer.setOnCompletionListener(mp -> Toast.makeText(MainActivity.this, "Playback finished", Toast.LENGTH_SHORT).show());
        mediaPlayer.start();
    }
}