package com.daakknights.audiofy.ui.raw

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daakknights.audiofy.R
import com.daakknights.audiofy.databinding.FragmentRawMediaBinding

class RawMediaFragment : Fragment() {

    private lateinit var _binding: FragmentRawMediaBinding
    private var isMediaPlaying: Boolean = false
    private var isMediaPaused: Boolean = false
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
        _binding = FragmentRawMediaBinding.inflate(inflater, container, false)

        _binding.btnPlayer.setOnClickListener {
            if (!isMediaPlaying) {
                playMediaPlayer()
            } else {
                pauseMediaPlayer()
            }
        }

        return _binding.root
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer = MediaPlayer.create(context, R.raw.audio_long)
    }

    private fun playMediaPlayer() {
        mediaPlayer.apply {
            seekTo(playBackPosition)
            start()
        }
        isMediaPlaying = true
        isMediaPaused = false
        _binding.btnPlayer.text = resources.getString(R.string.pause)
    }


    private fun pauseMediaPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            playBackPosition = mediaPlayer.currentPosition
        }
        isMediaPlaying = false
        isMediaPaused = true
        _binding.btnPlayer.text = resources.getString(R.string.play)
    }

    override fun onStop() {
        releasePlayer()
        super.onStop()
    }

    private fun releasePlayer() {
        mediaPlayer.apply {
            if (isPlaying || isMediaPaused) {
                playBackPosition = currentPosition
                isMediaPlaying = false
                _binding.btnPlayer.text = resources.getString(R.string.play)
                stop()
            }
            release()
        }
    }
}