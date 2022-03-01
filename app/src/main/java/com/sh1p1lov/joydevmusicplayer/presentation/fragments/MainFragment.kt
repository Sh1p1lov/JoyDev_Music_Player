package com.sh1p1lov.joydevmusicplayer.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sh1p1lov.joydevmusicplayer.R
import com.sh1p1lov.joydevmusicplayer.databinding.FragmentMainBinding

class MainFragment: Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val sharedPrefs: SharedPreferences by lazy { requireActivity().getSharedPreferences(getString(R.string.shared_prefs_name), Context.MODE_PRIVATE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        binding.mainCurrentUsername.text = sharedPrefs.getString(getString(R.string.current_username_key), "username")

        binding.mainExitToAccountButton.setOnClickListener {
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
}