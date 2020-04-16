@file:Suppress("DEPRECATION")

package com.adnet.exoplayer.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adnet.exoplayer.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.*
import kotlinx.android.synthetic.main.fragment_video.*

@Suppress("DEPRECATION")
class VideoFragment(private val url: String) : Fragment() {

    private val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()

    private val mediaDataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(context, DefaultBandwidthMeter(), httpDataSourceFactory)
    }

    private val httpDataSourceFactory by lazy {
        DefaultHttpDataSourceFactory(
            "locoExoPlayer",
            DefaultBandwidthMeter(),
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
    }

//    private val mediaSource by lazy {
//        ProgressiveMediaSource.Factory(mediaDataSourceFactory)
//            .createMediaSource(Uri.parse(url))
//    }

    private lateinit var player: SimpleExoPlayer
    private var trackSelector: DefaultTrackSelector? = null
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_video, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        //initializePlayer()
    }

    private fun initView() {
        ///  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initializePlayer() {
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector).apply {
            prepare(ProgressiveMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse(url)), false, false)
            playWhenReady = false
        }
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        playerView.setKeepContentOnPlayerReset(true)
        playerView.player = player
        playerView.requestFocus()
    }

    private fun updateStartPosition() {
        with(player) {
            playbackPosition = currentPosition
            currentWindow = currentWindowIndex
            playWhenReady = playWhenReady
        }
    }

    private fun releasePlayer() {
        updateStartPosition()
        player.release()
        trackSelector = null
    }

    override fun onStart() {
        super.onStart()
        Log.d("Hien","onStart")
        Log.d("Hien",url)
        initializePlayer()

    }

    override fun onResume() {
        super.onResume()
        Log.d("Hien","onResume")
        Log.d("Hien",url)
        player.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
        Log.d("Hien","onPause")
        Log.d("Hien",url)

    }


    companion object {
        @JvmStatic
        fun newInstance(url: String) = VideoFragment(url)
    }
}