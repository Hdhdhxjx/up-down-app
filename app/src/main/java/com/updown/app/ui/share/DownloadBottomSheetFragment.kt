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
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import com.updown.app.R
import com.updown.app.data.ResolutionOption
import kotlinx.coroutines.Dispatchers
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
            try {
                val request = YoutubeDLRequest(link)
                request.addOption("-J")
                val info = YoutubeDL.getInstance().getInfo(request)

                withContext(Dispatchers.Main) {
                    loadingLayout.visibility = View.GONE
                    titleView.text = info.title ?: "فيديو جاهز للتحميل"
                    channelView.text = info.uploader ?: ""

                    val parsedFormats = mutableListOf<ResolutionOption>()

                    info.formats?.filter { it.vcodec != "none" }?.forEach { format ->
                        val height = format.height ?: 0
                        val ext = format.ext ?: "mp4"
                        val fileSizeBytes = format.filesize ?: 0L
                        val sizeMB = if (fileSizeBytes > 0L)
                            String.format("%.1f MB", fileSizeBytes / 1024.0 / 1024.0)
                        else "—"
                        if (height > 0) {
                            parsedFormats.add(ResolutionOption("${height}p $ext", sizeMB))
                        }
                    }

                    val uniqueFormats = parsedFormats
                        .distinctBy { it.label }
                        .sortedByDescending { it.label.substringBefore("p").toIntOrNull() ?: 0 }

                    adapter.submitList(uniqueFormats)
                    if (uniqueFormats.isNotEmpty()) {
                        adapter.selectByLabel(uniqueFormats.first().label)
                    } else {
                        titleView.text = "لم يتم العثور على جودات مدعومة"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loadingLayout.visibility = View.GONE
                    titleView.text = "فشل في استخراج الرابط"
                    Toast.makeText(requireContext(), "عذراً: ${e.message}", Toast.LENGTH_LONG).show()
                }
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
