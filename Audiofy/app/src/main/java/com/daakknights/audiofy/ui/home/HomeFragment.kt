package com.daakknights.audiofy.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daakknights.audiofy.R
import com.daakknights.audiofy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private var isPlaying: Boolean = false
    private var isPaused: Boolean = false
    private var playBackPosition: Int = 0

    private lateinit var mediaPlayer: MediaPlayer
    /* private val mediaPlayer by lazy {
         MediaPlayer.create(context, R.raw.audio_long)
     }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        _binding.btnPlayer.setOnClickListener {
            if (!isPlaying) {
                playMediaPlayer()
            } else {
                pauseMediaPlayer()
            }
        }

        return _binding.root
    }

    private fun playMediaPlayer() {
        mediaPlayer.apply {
            seekTo(playBackPosition)
            start()
        }
        isPlaying = true
        isPaused = false
        _binding.btnPlayer.text = resources.getString(R.string.pause)
    }


    private fun pauseMediaPlayer() {
        mediaPlayer.let {
            mediaPlayer.pause()
        }
        playBackPosition = mediaPlayer.currentPosition
        isPlaying = false
        isPaused = true
        _binding.btnPlayer.text = resources.getString(R.string.play)
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer = MediaPlayer.create(context, R.raw.audio_long)
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        mediaPlayer.apply {
            if (mediaPlayer.isPlaying || isPaused) {
                playBackPosition = mediaPlayer.currentPosition
                stop()
            }
            release()
        }
    }
}