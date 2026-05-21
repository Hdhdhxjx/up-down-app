package com.updown.app.ui.share

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.updown.app.R
import com.updown.app.data.ResolutionOption

class ResolutionAdapter : RecyclerView.Adapter<ResolutionAdapter.Holder>() {

    private val items = mutableListOf<ResolutionOption>()
    private var selected = -1

    fun submitList(newList: List<ResolutionOption>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    fun selectByLabel(label: String) {
        selected = items.indexOfFirst { it.label == label }
        notifyDataSetChanged()
    }

    fun selectedLabel(): String {
        return items.getOrNull(selected)?.label ?: "غير محدد"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resolution, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position], position == selected)
        holder.itemView.setOnClickListener {
            selected = holder.bindingAdapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = items.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val card = itemView as MaterialCardView
        private val labelText = itemView.findViewById<TextView>(R.id.labelText)
        private val sizeText = itemView.findViewById<TextView>(R.id.sizeText)

        private val downloadIcon = itemView.findViewById<android.widget.ImageView>(R.id.downloadIcon)

        fun bind(item: ResolutionOption, selected: Boolean) {
            labelText.text = item.label
            sizeText.text = item.sizeText
            val context = itemView.context
            
            if (selected) {
                card.strokeColor = ContextCompat.getColor(context, R.color.color_accent)
                card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_surface_selected))
                downloadIcon.setColorFilter(ContextCompat.getColor(context, R.color.color_accent))
                labelText.setTextColor(ContextCompat.getColor(context, R.color.color_accent))
            } else {
                card.strokeColor = ContextCompat.getColor(context, R.color.color_divider)
                card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_surface))
                downloadIcon.setColorFilter(ContextCompat.getColor(context, R.color.color_text_secondary))
                labelText.setTextColor(ContextCompat.getColor(context, R.color.color_text_primary))
            }
        }

        private fun Int.dp(context: android.content.Context): Int {
            return (this * context.resources.displayMetrics.density).toInt()
        }
    }
}
