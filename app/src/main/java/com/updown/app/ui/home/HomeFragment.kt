package com.updown.app.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.updown.app.R
import com.updown.app.ui.main.MainNavigator
import com.updown.app.ui.share.DownloadBottomSheetFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val avatar = view.findViewById<ShapeableImageView>(R.id.topAvatar)
        val watchAdButton = view.findViewById<MaterialButton>(R.id.watchAdButton)

        avatar.setOnClickListener {
            (activity as? MainNavigator)?.openSettingsTab()
        }

        watchAdButton.setOnClickListener {
            Toast.makeText(requireContext(), "جاري تحميل الإعلان...", Toast.LENGTH_SHORT).show()
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
