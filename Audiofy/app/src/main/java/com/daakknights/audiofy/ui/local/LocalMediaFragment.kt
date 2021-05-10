package com.daakknights.audiofy.ui.local

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_MUSIC
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daakknights.audiofy.R
import com.daakknights.audiofy.databinding.FragmentLocalMediaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


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
        getUri()
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        GlobalScope.launch(Dispatchers.IO) {
            val uri = getUri()
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(requireContext(), uri)
                prepare()
                launch(Dispatchers.Main) {
                    _binding.pbDownload.visibility = View.GONE
                    _binding.btnPlayer.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getUri(): Uri {
        /*
        Environment.getExternalStorageDirectory() is deprecated need to check the alternative
        */
        val filePath = Environment.getExternalStorageDirectory().toString() + "/Music/fast_car.mp3"
        val file =
            File(
                filePath
            )
        return Uri.fromFile(file)
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

    /*
    music is stopped everytime the onStop() is called, done on purpose,
    will update in the next release.
    */
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
            /*
            commented out release for this demo purpose, ideally we should be releasing the
            media player when not needed
            * */
            /* release()*/
        }
    }
}