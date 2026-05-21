package com.updown.app.ui.share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TransparentShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedText = intent.getStringExtra(android.content.Intent.EXTRA_TEXT).orEmpty()
        DownloadBottomSheetFragment.newInstance(sharedText)
            .show(supportFragmentManager, "externalDownloadSheet")

        supportFragmentManager.executePendingTransactions()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
