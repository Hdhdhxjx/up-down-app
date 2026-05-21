package com.updown.app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.updown.app.R
import com.updown.app.data.RunningDownload

class RunningDownloadAdapter : RecyclerView.Adapter<RunningDownloadAdapter.Holder>() {

    private val items = mutableListOf<RunningDownload>()

    fun submitList(newList: List<RunningDownload>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_running_download, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbImage = itemView.findViewById<ImageView>(R.id.thumbImage)
        private val titleText = itemView.findViewById<TextView>(R.id.titleText)
        private val metaText = itemView.findViewById<TextView>(R.id.metaText)
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
        private val speedText = itemView.findViewById<TextView>(R.id.speedText)
        private val pauseButton = itemView.findViewById<ImageButton>(R.id.pauseButton)

        fun bind(item: RunningDownload) {
            thumbImage.load(item.thumbnailUrl)
            titleText.text = item.title
            metaText.text = "${item.sizeMb} MB | ${item.quality} | ${item.progressPercent}%"
            progressBar.progress = item.progressPercent
            speedText.text = item.speedText
            pauseButton.setOnClickListener {
                speedText.text = "متوقف مؤقتاً"
            }
        }
    }
}
