package com.updown.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.updown.app.R
import com.updown.app.ui.downloads.DownloadsFragment
import com.updown.app.ui.home.HomeFragment
import com.updown.app.ui.settings.SettingsFragment
import com.updown.app.ui.sub.ReferralRewardsFragment
import com.updown.app.ui.sub.SecretVaultFragment
import com.updown.app.ui.sub.SubscriptionsFragment

class MainActivity : AppCompatActivity(), MainNavigator {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNav)

        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_home
            loadRootFragment(HomeFragment())
        }

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> loadRootFragment(HomeFragment())
                R.id.nav_downloads -> loadRootFragment(DownloadsFragment())
                R.id.nav_settings -> loadRootFragment(SettingsFragment())
            }
            true
        }
    }

    override fun openSettingsTab() {
        bottomNav.selectedItemId = R.id.nav_settings
        loadRootFragment(SettingsFragment())
    }

    override fun openSubscriptions() {
        openSubScreen(SubscriptionsFragment())
    }

    override fun openReferralRewards() {
        openSubScreen(ReferralRewardsFragment())
    }

    override fun openSecretVault() {
        openSubScreen(SecretVaultFragment())
    }

    private fun loadRootFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_fast)
            .replace(R.id.mainFragmentContainer, fragment)
            .commit()
    }

    private fun openSubScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.mainFragmentContainer, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }
}
