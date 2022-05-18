package com.example.itunessearchapp.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.itunessearchapp.R
import com.example.itunessearchapp.databinding.FragmentDetailsBinding
import java.time.LocalDate

class DetailsFragment : Fragment(R.layout.fragment_details) {

    //navArgs passes ItunesItem as parcelable
    private val args by navArgs<DetailsFragmentArgs>()
    //media player used for playing samples
    private val mediaPlayer = MediaPlayer()

    private fun pauseMediaPlayer(mediaPlayer: MediaPlayer) {
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
    }

    private fun startMediaPlayer(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)
        val audioUrl = args.item.previewUrl

        mediaPlayer.setDataSource(audioUrl)
        mediaPlayer.prepare()

        binding.idBtnPause.setOnClickListener {
            pauseMediaPlayer(mediaPlayer)

        }
        binding.idBtnPlay.setOnClickListener {
            startMediaPlayer(mediaPlayer)
        }

        //binding views with passed parcelable
        binding.apply {

            Glide.with(requireActivity())
                .load(args.item.artworkUrl100?.replace("100", "400"))
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageViewArtwork)

            val uri = Uri.parse(args.item.artistViewUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            tvArtUrl.apply {
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }

            tvArtist.text = args.item.artistName
            tvTrackName.text = args.item.trackName
            tvTrackPrice.text = "$" + args.item.trackPrice.toString()
            tvCollectionName.text = args.item.collectionName

            val date = args.item.releaseDate!!.substring(0, 10)
            tvReleaseDate.text = "Release Date: " + LocalDate.parse(date).toString()
        }

    }

    //turns off music onStop
    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }

    //turns off music when fragment is closed
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

}