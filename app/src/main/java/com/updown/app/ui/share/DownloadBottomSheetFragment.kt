package com.updown.app.ui.share

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.updown.app.R
import com.updown.app.data.MockRepository
import com.updown.app.data.ResolutionOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        val titleView = view.findViewById<TextView>(R.id.videoTitleText)
        val channelView = view.findViewById<TextView>(R.id.videoChannelText)
        val loadingLayout = view.findViewById<LinearLayout>(R.id.loadingLayout)
        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.resolutionRecyclerView)
        val tabVideo = view.findViewById<TextView>(R.id.tabVideo)
        val tabAudio = view.findViewById<TextView>(R.id.tabAudio)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        loadingLayout.visibility = View.VISIBLE
        titleView.text = "جاري جلب معلومات الرابط..."

        tabVideo.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_accent))
        tabAudio.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_text_secondary))

        tabVideo.setOnClickListener {
            tabVideo.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_accent))
            tabAudio.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_text_secondary))
        }
        tabAudio.setOnClickListener {
            tabAudio.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_accent))
            tabVideo.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_text_secondary))
            adapter.selectByLabel("MP3 صوت فقط")
        }

        fetchVideoInfo(link, titleView, channelView, loadingLayout)

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.startDownloadButton).setOnClickListener {
            val selectedFormat = adapter.selectedLabel()
            if (selectedFormat.isBlank() || selectedFormat == "غير محدد") {
                Toast.makeText(requireContext(), "الرجاء اختيار جودة أولاً", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(requireContext(), "بدء التحميل: $selectedFormat", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.mp3DownloadButton)
            .setOnClickListener {
                adapter.selectByLabel("MP3 صوت فقط")
                Toast.makeText(requireContext(), "تم اختيار MP3", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchVideoInfo(
        link: String,
        titleView: TextView,
        channelView: TextView,
        loadingLayout: LinearLayout
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(800)
            withContext(Dispatchers.Main) {
                loadingLayout.visibility = View.GONE
                val domain = runCatching { java.net.URL(link).host }.getOrDefault("فيديو")
                titleView.text = "فيديو من $domain"
                channelView.text = ""
                adapter.submitList(MockRepository.resolutionOptions)
                adapter.selectByLabel(MockRepository.resolutionOptions.first().label)
            }
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
