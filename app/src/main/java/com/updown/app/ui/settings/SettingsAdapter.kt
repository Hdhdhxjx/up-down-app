package com.updown.app.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.updown.app.R
import com.updown.app.data.SettingOption

class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.Holder>() {

    private val items = mutableListOf<SettingOption>()
    var onClick: ((SettingOption) -> Unit)? = null

    fun submitList(newList: List<SettingOption>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_setting_option, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int = items.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText = itemView.findViewById<TextView>(R.id.titleText)
        private val subtitleText = itemView.findViewById<TextView>(R.id.subtitleText)
        private val badgeText = itemView.findViewById<TextView>(R.id.badgeText)

        fun bind(item: SettingOption, onClick: ((SettingOption) -> Unit)?) {
            titleText.text = item.title
            if (item.subtitle.isNullOrBlank()) {
                subtitleText.visibility = View.GONE
            } else {
                subtitleText.visibility = View.VISIBLE
                subtitleText.text = item.subtitle
            }
            badgeText.visibility = if (item.highlight) View.VISIBLE else View.GONE

            itemView.setOnClickListener { onClick?.invoke(item) }
        }
    }
}
