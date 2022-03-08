package com.sh1p1lov.joydevmusicplayer.presentation.fragments

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sh1p1lov.joydevmusicplayer.R
import com.sh1p1lov.joydevmusicplayer.databinding.FragmentMainBinding
import com.sh1p1lov.joydevmusicplayer.models.MusicTrackInfo
import com.squareup.picasso.Picasso
import java.lang.Exception

private const val MEDIA_PLAYER_SEEK_TO_VALUE = 5000
private const val BUNDLE_MUSIC_TRACK_KEY = "MUSIC_TRACK"

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private var mp: MediaPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        val musicTrackInfo = arguments?.getSerializable(BUNDLE_MUSIC_TRACK_KEY) as MusicTrackInfo

        val imageView = binding.mainImageView
        Picasso.with(context)
            .load(musicTrackInfo.image)
            .into(imageView)

        println(mp.toString())
        mp?.release()

        val url = musicTrackInfo.audio
        mp = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }

        try {
            mp?.setDataSource(url)
            mp?.prepareAsync()
        }catch (e: Exception) {
            val toast = Toast.makeText(context, "Ошибка воспроизведения", Toast.LENGTH_SHORT)
            toast.show()
            mp?.release()
            mp = null
        }

        mp?.setOnPreparedListener {
            it.start()
            binding.mainMediaPlayButton.setImageResource(R.drawable.ic_baseline_pause_32)
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    try {
                        binding.mainSeekBar.progress = it.currentPosition / (it.duration / 100)
                        binding.mainStartTime.text = millisecondsToNormalFormat(it.currentPosition)
                        handler.postDelayed(this, 1000)
                    } catch (e: Exception) {
                        binding.mainSeekBar.progress = 0
                    }
                }
            }, 0)

            binding.mainEndTime.text = millisecondsToNormalFormat(it.duration)
        }

        val errorListener = MediaPlayer.OnErrorListener { mp, what, extra ->
            val toast = Toast.makeText(context, "Ошибка воспроизведения", Toast.LENGTH_SHORT)
            toast.show()
            this.mp?.release()
            this.mp = null
            true
        }
        mp?.setOnErrorListener(errorListener)

        val bufferingUpdateListener = MediaPlayer.OnBufferingUpdateListener { mp, percent ->
            binding.mainSeekBar.secondaryProgress = percent
        }
        mp?.setOnBufferingUpdateListener(bufferingUpdateListener)

        binding.mainMediaRewButton.setOnClickListener {
            mp?.seekTo(mp!!.currentPosition - MEDIA_PLAYER_SEEK_TO_VALUE)
        }

        binding.mainMediaPlayButton.setOnClickListener {
            if (mp != null && mp!!.isPlaying) {
                mp!!.pause()
                binding.mainMediaPlayButton.setImageResource(R.drawable.ic_baseline_play_arrow_32)
            }else if (mp != null) {
                mp!!.start()
                binding.mainMediaPlayButton.setImageResource(R.drawable.ic_baseline_pause_32)
            }
        }

        binding.mainMediaStopButton.setOnClickListener {
            mp?.pause()
            mp?.seekTo(0)
            binding.mainMediaPlayButton.setImageResource(R.drawable.ic_baseline_play_arrow_32)
        }

        binding.mainMediaFfButton.setOnClickListener {
            mp?.seekTo(mp!!.currentPosition + MEDIA_PLAYER_SEEK_TO_VALUE)
        }

        binding.mainExitToMusicList.setOnClickListener {
            mp?.release()
            mp = null

            findNavController().navigate(R.id.action_mainFragment_to_musicListFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mp?.release()
                mp = null

                findNavController().navigate(R.id.action_mainFragment_to_musicListFragment)
            }
        })
    }

    private fun millisecondsToNormalFormat(milliseconds: Int): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000
        return String.format("%d:%d", minutes, seconds - minutes * 60)
    }
}