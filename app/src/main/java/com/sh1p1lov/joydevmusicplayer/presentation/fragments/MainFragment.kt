package com.sh1p1lov.joydevmusicplayer.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
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
import java.lang.Exception

private const val EXAMPLE_URL = "https://www.ssaurel.com/tmp/mymusic.mp3"
private const val MEDIA_PLAYER_SEEK_TO_VALUE = 5000

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val sharedPrefs: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            getString(R.string.shared_prefs_name),
            Context.MODE_PRIVATE
        )
    }
    private var mp: MediaPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        binding.mainCurrentUsername.text = sharedPrefs.getString(getString(R.string.current_username_key), "username")
        binding.mainUrlInputField.setText(EXAMPLE_URL)

        binding.mainPlayFromUrlButton.setOnClickListener {
            mp?.release()

            val url = binding.mainUrlInputField.text.toString()
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
        }

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

        binding.mainExitToAccountButton.setOnClickListener {
            mp?.release()
            mp = null

            sharedPrefs
                .edit()
                .putBoolean(getString(R.string.login_status_key), false)
                .remove(getString(R.string.current_username_key))
                .apply()

            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    private fun millisecondsToNormalFormat(milliseconds: Int): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000
        return String.format("%d:%d", minutes, seconds - minutes * 60)
    }
}