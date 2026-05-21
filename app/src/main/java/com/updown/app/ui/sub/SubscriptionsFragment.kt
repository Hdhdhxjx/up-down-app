package com.updown.app.ui.sub

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.updown.app.R
import com.updown.app.data.MockBackendService

class SubscriptionsFragment : Fragment(R.layout.fragment_subscriptions) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backend = MockBackendService(requireContext())
        backend.purchaseMock("Ultra")
    }
}
