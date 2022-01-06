package com.muhsantech.videoplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import com.muhsantech.videoplayer.databinding.ActivityVideoPlayerBinding;

import java.util.Objects;

public class VideoPlayerActivity extends AppCompatActivity {

    ActivityVideoPlayerBinding binding;
    int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        position = getIntent().getIntExtra("position", -1);
        PlayerVideo();
    }
    // Video Player Function Runner and ManageVideo // Used MediaController
    private void PlayerVideo() {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(binding.myPlayer);

        binding.myPlayer.setMediaController(mediaController);
        binding.myPlayer.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position)));
        binding.myPlayer.requestFocus();

        binding.myPlayer.setOnPreparedListener(mediaPlayer -> binding.myPlayer.start());

        binding.myPlayer.setOnCompletionListener(mediaPlayer -> {
            binding.myPlayer.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position = position +1)));
            binding.myPlayer.start();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        binding.myPlayer.stopPlayback();
    }
}