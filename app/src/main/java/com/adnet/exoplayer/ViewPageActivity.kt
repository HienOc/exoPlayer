package com.adnet.exoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adnet.exoplayer.fragment.VideoFragment
import kotlinx.android.synthetic.main.activity_view_page.*

class ViewPageActivity : AppCompatActivity() {

    private val _adapter: PagerAdapter by lazy {
        PagerAdapter(supportFragmentManager).apply {
            addFragment(VideoFragment.newInstance("http://techslides.com/demos/sample-videos/small.mp4"))
            addFragment(VideoFragment.newInstance("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
            addFragment(VideoFragment.newInstance("https://kaimin.adnet.plus//public/upload/home_scenes/a4.mp4"))
            addFragment(VideoFragment.newInstance("https://kaimin.adnet.plus//public/upload/home_scenes/a3.mp4"))
            addFragment(VideoFragment.newInstance("https://kaimin.adnet.plus//public/upload/home_scenes/a2.mp4"))
            addFragment(VideoFragment.newInstance("https://kaimin.adnet.plus//public/upload/home_scenes/a1.mp4"))
        }
    }

    private var video = mutableListOf<String>().apply {
        add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a4.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a3.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a2.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a1.mp4")
        add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a4.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a3.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a2.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a1.mp4")
        add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a4.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a3.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a2.mp4")
        add("https://kaimin.adnet.plus//public/upload/home_scenes/a1.mp4")
    }

    private val detailHistoryAdapter: Pager2Adapter by lazy {
        Pager2Adapter(video)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_page)
    //    initViewPager()
        viewPagerMain.adapter=detailHistoryAdapter

    }

//    private fun initViewPager() {
//        viewPagerMain?.apply {
//            this.adapter = _adapter
//            setCurrentItem(0, true)
//        }
//    }
}
