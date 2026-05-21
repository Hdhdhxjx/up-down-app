package com.updown.app.ui.sub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.updown.app.R
import com.updown.app.data.MockBackendService
import com.updown.app.data.MockRepository

class ReferralRewardsFragment : Fragment(R.layout.fragment_referral_rewards) {

    private val adapter = LeaderboardAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backend = MockBackendService(requireContext())
        val result = backend.trackReferral(stayedHours = 25, downloadedVideos = 2, watchedRewardAds = 1)
        if (result.counted) {
            Toast.makeText(requireContext(), result.reason, Toast.LENGTH_SHORT).show()
        }

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.leaderboardRecyclerView)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        adapter.submitList(MockRepository.leaderboard)

        view.findViewById<MaterialButton>(R.id.shareReferralBtn).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "انضم عبر كودي: UPDOWN-9842")
            }
            startActivity(Intent.createChooser(shareIntent, "مشاركة"))
        }
    }
}
