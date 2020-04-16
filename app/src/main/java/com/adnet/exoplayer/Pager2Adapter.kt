package com.adnet.exoplayer

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.item_video.view.*

@Suppress("DEPRECATION")
class Pager2Adapter(
    private var video: List<String>
) : RecyclerView.Adapter<Pager2Adapter.ViewHolder>() {
    private var players = mutableListOf<SimpleExoPlayer>()
    private lateinit var view: ViewGroup
    private val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
    private val httpDataSourceFactory by lazy {
        DefaultHttpDataSourceFactory(
            "locoExoPlayer",
            DefaultBandwidthMeter(),
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            viewGroup.context
        ).inflate(R.layout.item_video, viewGroup, false)
        view = viewGroup
        initPage()
        return ViewHolder(itemView, viewGroup.context)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bindata(players,holder.adapterPosition)
    }

    private fun initPage() {
        for (i in video.indices) {
            val mediaDataSourceFactory = DefaultDataSourceFactory(
                view.context,
                DefaultBandwidthMeter(),
                httpDataSourceFactory
            )
            val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
            val loopingSource = LoopingMediaSource(
                ProgressiveMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(Uri.parse(video[i]))
            )
            players.add(ExoPlayerFactory.newSimpleInstance(view.context, trackSelector).apply {
                prepare(loopingSource)
                playWhenReady = false
            })
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = video.size

    class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        fun bindata(players: List<SimpleExoPlayer>,position: Int) {
            players[position].playWhenReady=true
            itemView.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            itemView.playerView.hideController()
            itemView.playerView.setKeepContentOnPlayerReset(true)
            itemView.playerView.player = players[position]
            itemView.playerView.requestFocus()
        }
    }

}