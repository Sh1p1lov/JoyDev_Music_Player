package com.sh1p1lov.joydevmusicplayer.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import com.sh1p1lov.joydevmusicplayer.R
import com.sh1p1lov.joydevmusicplayer.databinding.FragmentMusicListBinding
import com.sh1p1lov.joydevmusicplayer.models.MusicTracksList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

private const val API_JSON_URL = "https://api.jamendo.com/v3.0/tracks/?client_id=aa269428&format=json&limit=20&boost=popularity_month"
private const val BUNDLE_MUSIC_TRACK_KEY = "MUSIC_TRACK"

class MusicListFragment : Fragment(R.layout.fragment_music_list) {

    private lateinit var binding: FragmentMusicListBinding
    private val sharedPrefs: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            getString(R.string.shared_prefs_name),
            Context.MODE_PRIVATE
        )
    }
    private lateinit var musicTracksList: MusicTracksList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMusicListBinding.bind(view)
        binding.musicListCurrentUsername.text = sharedPrefs.getString(getString(R.string.current_username_key), "username")

        updateMusicTracksList()

        binding.musicListExitToAccountButton.setOnClickListener {
            sharedPrefs
                .edit()
                .putBoolean(getString(R.string.login_status_key), false)
                .remove(getString(R.string.current_username_key))
                .apply()

            findNavController().navigate(R.id.action_musicListFragment_to_loginFragment)
        }

        binding.musicListUpdateButton.setOnClickListener {
            updateMusicTracksList()
        }

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val bundle = Bundle()
            bundle.putSerializable(BUNDLE_MUSIC_TRACK_KEY, musicTracksList.results[position])
            findNavController().navigate(R.id.action_musicListFragment_to_mainFragment, bundle)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    private fun updateMusicTracksList() {
        val client = OkHttpClient()
        val request =
            Request.Builder()
                .url(API_JSON_URL)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                CoroutineScope(Dispatchers.Main).launch {
                    val toast = Toast.makeText(context, "Ошибка загрузки списка музыки", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                CoroutineScope(Dispatchers.Main).launch {
                    val gsonBuilder = GsonBuilder().create()
                    musicTracksList = gsonBuilder.fromJson(response.body!!.string(), MusicTracksList::class.java)
                    binding.listView.adapter = MusicListAdapter(requireActivity(), musicTracksList.results)
                }
            }
        })
    }
}
