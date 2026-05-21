package com.updown.app.ui.sub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.updown.app.R
import com.updown.app.data.LeaderboardEntry

class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.Holder>() {

    private val items = mutableListOf<LeaderboardEntry>()

    fun submitList(newList: List<LeaderboardEntry>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rankText = itemView.findViewById<TextView>(R.id.rankText)
        private val nameText = itemView.findViewById<TextView>(R.id.nameText)
        private val metaText = itemView.findViewById<TextView>(R.id.metaText)

        fun bind(item: LeaderboardEntry) {
            rankText.text = "#${item.rank}"
            nameText.text = item.name + if (item.isCurrentUser) " ← أنت" else ""
            metaText.text = "${item.points} نقطة — ${item.invites} دعوة ناجحة"

            if (item.isElite) {
                rankText.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_gold_elite))
            } else {
                rankText.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_text_primary))
            }

            itemView.alpha = if (item.isCurrentUser) 1f else 0.92f
        }
    }
}
