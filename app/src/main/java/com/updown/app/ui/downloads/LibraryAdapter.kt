package com.updown.app.ui.downloads

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.updown.app.R
import com.updown.app.data.LibraryItem

class LibraryAdapter : RecyclerView.Adapter<LibraryAdapter.Holder>() {

    private val items = mutableListOf<LibraryItem>()
    var onDelete: ((LibraryItem) -> Unit)? = null

    fun submitList(newList: List<LibraryItem>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_library_download, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position], onDelete)
    }

    override fun getItemCount(): Int = items.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbImage = itemView.findViewById<ImageView>(R.id.thumbImage)
        private val durationText = itemView.findViewById<TextView>(R.id.durationText)
        private val titleText = itemView.findViewById<TextView>(R.id.titleText)
        private val metaText = itemView.findViewById<TextView>(R.id.metaText)
        private val playButton = itemView.findViewById<ImageButton>(R.id.playButton)
        private val shareButton = itemView.findViewById<ImageButton>(R.id.shareButton)
        private val deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)

        fun bind(item: LibraryItem, onDelete: ((LibraryItem) -> Unit)?) {
            thumbImage.load(item.thumbnailUrl)
            durationText.text = item.durationText
            titleText.text = item.title
            metaText.text = "${item.sizeText} | ${item.format} | ${item.quality} | ${item.dateText}"

            playButton.setOnClickListener {
                metaText.text = "جاري التشغيل..."
            }

            shareButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, item.title)
                itemView.context.startActivity(Intent.createChooser(intent, "مشاركة"))
            }

            deleteButton.setOnClickListener {
                onDelete?.invoke(item)
            }
        }
    }
}
