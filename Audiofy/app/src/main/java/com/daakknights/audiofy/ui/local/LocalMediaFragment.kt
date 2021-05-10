package com.daakknights.audiofy.ui.local

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daakknights.audiofy.R
import com.daakknights.audiofy.databinding.FragmentLocalMediaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URI


class LocalMediaFragment : Fragment() {

    private lateinit var _binding: FragmentLocalMediaBinding
    private var isMediaPlaying: Boolean = false
    private var isMediaPaused: Boolean = false
    private lateinit var mediaPlayer: MediaPlayer
    private var playBackPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalMediaBinding.inflate(layoutInflater, container, false)

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
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        GlobalScope.launch(Dispatchers.IO) {
            /*val uri: URI = ""*/
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource("")
                prepare()
                launch(Dispatchers.Main) {
                    _binding.pbDownload.visibility = View.GONE
                    _binding.btnPlayer.visibility = View.VISIBLE
                }
            }
        }
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
            playBackPosition = mediaPlayer.currentPosition
            mediaPlayer.pause()
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
            /* release()*/
        }
    }
}