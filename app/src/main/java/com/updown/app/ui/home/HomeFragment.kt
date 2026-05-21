package com.updown.app.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.updown.app.R
import com.updown.app.data.MockRepository
import com.updown.app.ui.main.MainNavigator
import com.updown.app.ui.share.DownloadBottomSheetFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val adapter = RunningDownloadAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchEdit = view.findViewById<EditText>(R.id.searchEditText)
        val searchButton = view.findViewById<ImageView>(R.id.searchButton)
        val watchAdButton = view.findViewById<MaterialButton>(R.id.watchAdButton)
        val clipboardBanner = view.findViewById<LinearLayout>(R.id.clipboardBanner)
        val avatar = view.findViewById<ShapeableImageView>(R.id.topAvatar)
        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.forYouRecyclerView)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        adapter.submitList(MockRepository.runningDownloads.toList())

        avatar.setOnClickListener {
            (activity as? MainNavigator)?.openSettingsTab()
        }

        watchAdButton.setOnClickListener {
            Toast.makeText(requireContext(), "تم تفعيل VIP لمدة 24 ساعة (محاكاة)", Toast.LENGTH_SHORT).show()
        }

        searchButton.setOnClickListener {
            val text = searchEdit.text?.toString().orEmpty()
            if (text.startsWith("http")) {
                showDownloadSheet(text)
            } else if (text.isNotBlank()) {
                Toast.makeText(requireContext(), "البحث: $text", Toast.LENGTH_SHORT).show()
            }
        }

        searchEdit.setOnEditorActionListener { _, _, _ ->
            val text = searchEdit.text?.toString().orEmpty()
            if (text.startsWith("http")) {
                showDownloadSheet(text)
                true
            } else false
        }

        val clipboardText = readClipboardText().orEmpty()
        val hasLink = clipboardText.startsWith("http")
        clipboardBanner.visibility = if (hasLink) View.VISIBLE else View.GONE
        clipboardBanner.setOnClickListener {
            searchEdit.setText(clipboardText)
            showDownloadSheet(clipboardText)
        }
    }

    private fun showDownloadSheet(link: String) {
        DownloadBottomSheetFragment.newInstance(link)
            .show(parentFragmentManager, "downloadSheet")
    }

    private fun readClipboardText(): String? {
        val manager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = manager.primaryClip ?: return null
        return clip.getItemAt(0)?.coerceToText(requireContext())?.toString()
    }
}
