package com.sh1p1lov.joydevmusicplayer.presentation.fragments

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.sh1p1lov.joydevmusicplayer.R
import com.sh1p1lov.joydevmusicplayer.models.MusicTrackInfo
import com.squareup.picasso.Picasso

class MusicListAdapter(
    private val context: Activity,
    private val arrayList: ArrayList<MusicTrackInfo>
) : ArrayAdapter<MusicTrackInfo>(context, R.layout.music_list_item, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.music_list_item, null)

        val image = view.findViewById<ImageView>(R.id.imageView)
        val trackName = view.findViewById<TextView>(R.id.music_track_name)
        val artistName = view.findViewById<TextView>(R.id.artist_name)

        Picasso.with(context).load(arrayList[position].image).into(image)
        trackName.text = arrayList[position].name
        artistName.text = arrayList[position].artist_name

        return view
    }
}