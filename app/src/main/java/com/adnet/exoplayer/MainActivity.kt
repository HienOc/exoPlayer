@file:Suppress("DEPRECATION")

package com.adnet.exoplayer

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_test.*


//class MainActivity : Activity(),Player.EventListener {
//
//    private lateinit var player: SimpleExoPlayer
//    private lateinit var mediaDataSourceFactory: DataSource.Factory
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//
//    private fun initializePlayer() {
//
//        if (player == null) {
//            player = ExoPlayerFactory.newSimpleInstance(
//                DefaultRenderersFactory(applicationContext),
//                DefaultTrackSelector(),
//                DefaultLoadControl()
//            )
//            playerView?.setPlayer(player)
//            player.setPlayWhenReady(playWhenReady)
//            player.seekTo(currentWindow, playbackPosition)
//        }
//        val mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_mp4)))
//        player.prepare(mediaSource, true, false)
//
//        player = ExoPlayerFactory.newSimpleInstance(this)
//
//        mediaDataSourceFactory = DefaultDataSourceFactory(
//            this, Util.getUserAgent(
//                this,
//                "mediaPlayerSample")
//        )
//
//        val mediaSource = ProgressiveMediaSource.Factory(
//            mediaDataSourceFactory
//        ).createMediaSource(Uri.parse(STREAM_URL))
//
//
//        with(player) {
//            prepare(mediaSource, true, false)
//            playWhenReady = true
//        }
//
//        playerView.setShutterBackgroundColor(Color.TRANSPARENT)
//        playerView.player = player
//        playerView.requestFocus()
//
//    }
//
//    private fun releasePlayer() {
//        player.release()
//    }
//
//    public override fun onStart() {
//        super.onStart()
//
//        if (Util.SDK_INT > 23) initializePlayer()
//    }
//
//    public override fun onResume() {
//        super.onResume()
//
//        if (Util.SDK_INT <= 23) initializePlayer()
//    }
//
//    public override fun onPause() {
//        super.onPause()
//
//        if (Util.SDK_INT <= 23) releasePlayer()
//    }
//
//    public override fun onStop() {
//        super.onStop()
//
//        if (Util.SDK_INT > 23) releasePlayer()
//    }
//
//    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//
//    }
//
//    companion object {
//        const val STREAM_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
//    }
//}

class MainActivity : AppCompatActivity(), Player.EventListener {
    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING)
            progressBar.visibility = View.VISIBLE
        else if (playbackState == Player.STATE_READY)
            progressBar.visibility = View.INVISIBLE
    }

    override fun onLoadingChanged(isLoading: Boolean) {
    }

    override fun onPositionDiscontinuity(reason: Int) {
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
    }

    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPosition = 0L
    private val dashUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
    private val bandwidthMeter by lazy {
        DefaultBandwidthMeter()
    }
    private val adaptiveTrackSelectionFactory by lazy {
        AdaptiveTrackSelection.Factory(bandwidthMeter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    override fun onStart() {
        super.onStart()

        initializeExoplayer()
    }

    override fun onStop() {

        releaseExoplayer()
        super.onStop()
    }

    private fun initializeExoplayer() {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
           this,
            DefaultTrackSelector(adaptiveTrackSelectionFactory),
            DefaultLoadControl()
        )

        prepareExoplayer()
        simpleExoPlayerView.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)
    }

    private fun releaseExoplayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("ua", bandwidthMeter)
        val dashChunkSourceFactory = DefaultDashChunkSource.Factory(dataSourceFactory)
        return DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null)
    }

    private fun prepareExoplayer() {
        val uri = Uri.parse(dashUrl)
        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource)
    }
}