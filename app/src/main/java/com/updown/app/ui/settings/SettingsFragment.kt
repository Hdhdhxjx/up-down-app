package com.updown.app.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.imageview.ShapeableImageView
import com.updown.app.R
import com.updown.app.data.MockRepository
import com.updown.app.ui.main.MainNavigator
import com.updown.app.util.ThemeManager

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val adapter = SettingsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile = MockRepository.profile
        view.findViewById<TextView>(R.id.profileName).text = profile.name
        view.findViewById<TextView>(R.id.profileEmail).text = profile.email
        view.findViewById<TextView>(R.id.profileBadges).text = "${profile.planBadge} • Streak: ${profile.streakDays} Days"

        val avatar = view.findViewById<ShapeableImageView>(R.id.profileAvatar)
        avatar.strokeColor = android.content.res.ColorStateList.valueOf(ThemeManager.activeAccent(requireContext()))

        val paletteRow = view.findViewById<LinearLayout>(R.id.accentPaletteRow)
        buildPalette(paletteRow)

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.settingsRecyclerView)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        adapter.submitList(MockRepository.settingOptions)

        adapter.onClick = { option ->
            val nav = activity as? MainNavigator
            when (option.id) {
                "subs" -> nav?.openSubscriptions()
                "referral" -> nav?.openReferralRewards()
                "vault" -> nav?.openSecretVault()
            }
        }
    }

    private fun buildPalette(container: LinearLayout) {
        container.removeAllViews()
        val active = ThemeManager.activeAccent(requireContext())

        ThemeManager.accentPalette.forEach { colorRes ->
            val color = ContextCompat.getColor(requireContext(), colorRes)
            val dot = View(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(32.dp, 32.dp).also {
                    it.marginStart = 12.dp
                }
                background = android.graphics.drawable.GradientDrawable().apply {
                    shape = android.graphics.drawable.GradientDrawable.OVAL
                    setColor(color)
                    setStroke(if (color == active) 4 else 0, color)
                }
                setOnClickListener {
                    ThemeManager.saveAccent(requireContext(), color)
                    buildPalette(container)
                }
            }
            container.addView(dot)
        }
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()
}
