package com.example.susa;

import androidx.appcompat.app.AppCompatActivity;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoPlayerActiviy extends AppCompatActivity {

    private StyledPlayerView playerView;
    private ExoPlayer player;
    private ImaAdsLoader adsLoader;

//    String videoURL = "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4";
    String videoURL = "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player_activiy);

        playerView = findViewById(R.id.playerview);

        player  = new ExoPlayer.Builder(VideoPlayerActiviy.this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoURL);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);

        // Restore the playback position if it exists
        if (savedInstanceState != null) {
            long playbackPosition = savedInstanceState.getLong("playbackPosition", C.TIME_UNSET);
            if (playbackPosition != C.TIME_UNSET) {
                player.seekTo(playbackPosition);
            }
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        player.setPlayWhenReady(false);
        player.release();
        player = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            long playbackPosition = player.getCurrentPosition();
            outState.putLong("playbackPosition", playbackPosition);
        }
    }

}