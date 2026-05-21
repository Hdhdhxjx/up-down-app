package com.updown.app.ui.share

import android.os.Bundle
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.updown.app.R
import com.updown.app.data.MockRepository

class DownloadBottomSheetFragment : BottomSheetDialogFragment(R.layout.bottom_sheet_download) {

    private val adapter = ResolutionAdapter()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setDimAmount(0.6f)

        val sheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        if (sheet != null) {
            val behavior = BottomSheetBehavior.from(sheet)
            behavior.skipCollapsed = true
            behavior.isHideable = true
            behavior.isDraggable = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val link = arguments?.getString(ARG_LINK).orEmpty()
        val icon = view.findViewById<ImageView>(R.id.platformIcon)
        val title = view.findViewById<TextView>(R.id.videoTitleText)

        title.text = when {
            link.contains("youtube", true) -> "فيديو من يوتيوب"
            link.contains("tiktok", true) -> "مقطع من تيك توك"
            link.contains("instagram", true) -> "مقطع من إنستغرام"
            else -> "محتوى قابل للتحميل"
        }
        icon.setImageResource(android.R.drawable.ic_menu_share)

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.resolutionRecyclerView)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = adapter
        adapter.submitList(MockRepository.resolutionOptions)
        adapter.selectByLabel("1080p FHD")

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.startDownloadButton)
            .setOnClickListener {
                Toast.makeText(requireContext(), "بدأ التحميل (${adapter.selectedLabel()})", Toast.LENGTH_SHORT).show()
                dismiss()
            }

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.mp3DownloadButton)
            .setOnClickListener {
                adapter.selectByLabel("MP3 صوت فقط")
                Toast.makeText(requireContext(), "تم اختيار MP3", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (activity is TransparentShareActivity) {
            activity?.finish()
        }
    }

    companion object {
        private const val ARG_LINK = "arg_link"

        fun newInstance(link: String): DownloadBottomSheetFragment {
            return DownloadBottomSheetFragment().apply {
                arguments = bundleOf(ARG_LINK to link)
            }
        }
    }
}
