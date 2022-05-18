package com.example.itunessearchapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.itunessearchapp.data.ItunesList
import com.example.itunessearchapp.databinding.ListItemChildBinding

class ItunesItemAdapter(
    private val activity: Activity,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ItunesItemAdapter.ItunesViewHolder>() {
    private var itemList: ItunesList? = null

    fun setItemList(itemList: ItunesList) {
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItunesViewHolder {
        val binding =
            ListItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItunesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItunesViewHolder, position: Int) {
        holder.bind(itemList!!, activity)
    }

    override fun getItemCount(): Int {
        return if (itemList == null) 0
        else itemList?.results?.size!!
    }

    inner class ItunesViewHolder(binding: ListItemChildBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private val artistImage = binding.imageViewArtwork
        private val tvTrackName = binding.tvTrackName
        private val tvGenre = binding.tvGenre
        private val tvPrice = binding.tvPrice
        private val tvArtist = binding.tvArtist

        //bind views with passed data item
        @SuppressLint("SetTextI18n")
        fun bind(data: ItunesList, activity: Activity) {
            tvTrackName.text = data.results[adapterPosition].trackName
            tvGenre.text = data.results[adapterPosition].primaryGenreName
            tvPrice.text = "$${data.results[adapterPosition].trackPrice}"
            tvArtist.text = data.results[adapterPosition].artistName
            Glide.with(activity)
                .load(data.results[adapterPosition].artworkUrl100?.replace("100","400"))
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(artistImage)
        }

        //initialize onClickListener
        init {
            binding.root.setOnClickListener(this)
        }

        //makes each item in recycler view clickable
        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, itemList!!)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, itemList: ItunesList)
    }

}
