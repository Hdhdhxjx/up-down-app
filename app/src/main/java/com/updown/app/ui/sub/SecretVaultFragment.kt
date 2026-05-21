package com.updown.app.ui.sub

import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.updown.app.R

class SecretVaultFragment : Fragment(R.layout.fragment_secret_vault) {

    private var pin = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dots = view.findViewById<TextView>(R.id.pinDots)
        val pad = view.findViewById<GridLayout>(R.id.numpadGrid)

        repeat(9) { index ->
            addPadButton(pad, (index + 1).toString(), dots)
        }
        addPadButton(pad, "0", dots)

        view.findViewById<MaterialButton>(R.id.fingerprintBtn).setOnClickListener {
            dots.text = "✓ ✓ ✓ ✓"
        }
    }

    private fun addPadButton(grid: GridLayout, text: String, dots: TextView) {
        val btn = MaterialButton(requireContext()).apply {
            this.text = text
            layoutParams = GridLayout.LayoutParams().apply {
                width = 150
                height = 150
                setMargins(8, 8, 8, 8)
            }
            setOnClickListener {
                if (pin.length < 4) {
                    pin += text
                    updateDots(dots)
                }
                if (pin.length == 4) {
                    validatePin(dots)
                }
            }
        }
        grid.addView(btn)
    }

    private fun updateDots(dots: TextView) {
        val filled = "● ".repeat(pin.length)
        val empty = "○ ".repeat(4 - pin.length)
        dots.text = (filled + empty).trim()
    }

    private fun validatePin(dots: TextView) {
        if (pin == "1234") {
            dots.text = "✓ ✓ ✓ ✓"
            return
        }

        dots.setTextColor(resources.getColor(R.color.color_danger, null))
        val vibrator = requireContext().getSystemService<Vibrator>()
        vibrator?.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
        pin = ""
        dots.postDelayed({
            dots.setTextColor(resources.getColor(R.color.color_text_primary, null))
            updateDots(dots)
        }, 350)
    }
}
