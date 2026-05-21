package com.updown.app.ui.downloads

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.updown.app.R
import com.updown.app.data.LibraryItem
import com.updown.app.data.MockRepository

class DownloadsFragment : Fragment(R.layout.fragment_downloads) {

    private val adapter = LibraryAdapter()
    private var source = MockRepository.libraryItems.toList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.libraryRecyclerView)
        val chips = view.findViewById<ChipGroup>(R.id.filterChipGroup)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        adapter.submitList(source)

        chips.setOnCheckedStateChangeListener { _, checkedIds ->
            val filtered = when (checkedIds.firstOrNull()) {
                R.id.chipMp4 -> source.filter { it.format.equals("MP4", true) }
                R.id.chipMp3 -> source.filter { it.format.equals("MP3", true) }
                R.id.chip4k -> source.filter { it.quality.contains("4K", true) }
                else -> source
            }
            adapter.submitList(filtered)
        }

        adapter.onDelete = { item ->
            source = source.filterNot { it.id == item.id }
            adapter.submitList(source)
        }
    }
}
