package com.updown.app.ui.share

import android.os.Bundle
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import com.updown.app.R
import com.updown.app.data.MockRepository
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
        val title = view.findViewById<TextView>(R.id.videoTitleText)
        val icon = view.findViewById<ImageView>(R.id.platformIcon)
        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.resolutionRecyclerView)
        
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = adapter

        // Initial Loading state
        title.text = "جاري جلب معلومات الرابط..."
        
        fetchVideoInfo(link, title)

        icon.setImageResource(android.R.drawable.ic_menu_share)

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.startDownloadButton).setOnClickListener {
            val selectedFormat = adapter.selectedLabel()
            if (selectedFormat.isBlank()) {
                Toast.makeText(requireContext(), "الرجاء اختيار جودة أولاً", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(requireContext(), "بدء التحميل الحقيقي: $selectedFormat", Toast.LENGTH_SHORT).show()
            // Here we will run the actual YoutubeDLRequest in the next iteration
            dismiss()
        }

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.mp3DownloadButton)
            .setOnClickListener {
                adapter.selectByLabel("MP3 صوت فقط")
                Toast.makeText(requireContext(), "تم اختيار MP3", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchVideoInfo(link: String, titleView: TextView) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val request = YoutubeDLRequest(link)
                request.addOption("-J") // get JSON info
                val info = YoutubeDL.getInstance().getInfo(request)

                withContext(Dispatchers.Main) {
                    titleView.text = info.title ?: "فيديو جاهز للتحميل"
                    
                    val parsedFormats = mutableListOf<ResolutionOption>()
                    
                    info.formats?.filter { it.vcodec != "none" }?.forEach { format ->
                        val height = format.height
                        val ext = format.ext ?: "mp4"
                        val sizeMB = if (format.fileSize > 0) String.format("%.1f MB", format.fileSize / 1024.0 / 1024.0) else "حجم غير معروف"
                        if (height > 0) {
                            parsedFormats.add(ResolutionOption("f_${format.formatId}", "${height}p $ext", sizeMB))
                        }
                    }

                    // Remove duplicates by label (keeping best)
                    val uniqueFormats = parsedFormats.distinctBy { it.label }.sortedByDescending { it.label.substringBefore("p").toIntOrNull() ?: 0 }

                    adapter.submitList(uniqueFormats)
                    if (uniqueFormats.isNotEmpty()) {
                        adapter.selectByLabel(uniqueFormats.first().label)
                    } else {
                        titleView.text = "لم يتم العثور على جودات مدعومة"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
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
