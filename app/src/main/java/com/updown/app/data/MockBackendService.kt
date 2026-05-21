package com.updown.app.data

import android.content.Context
import org.json.JSONObject

data class RegisterResult(val accepted: Boolean, val reason: String)
data class ReferralTrackResult(val counted: Boolean, val reason: String)
data class PurchaseResult(val success: Boolean, val tier: String, val message: String)

class MockBackendService(private val context: Context) {

    private val disposableDomains = setOf("mailinator.com", "10minutemail.com", "temp-mail.org")

    fun register(email: String, deviceFingerprint: String): RegisterResult {
        val domain = email.substringAfter("@", "")
        if (domain in disposableDomains) {
            return RegisterResult(false, "تم رفض البريد المؤقت")
        }
        if (deviceFingerprint.isBlank()) {
            return RegisterResult(false, "فشل التحقق من بصمة الجهاز")
        }
        return RegisterResult(true, "تم التسجيل بنجاح")
    }

    fun trackReferral(stayedHours: Int, downloadedVideos: Int, watchedRewardAds: Int): ReferralTrackResult {
        val ok = stayedHours >= 24 && downloadedVideos >= 2 && watchedRewardAds >= 1
        return if (ok) {
            ReferralTrackResult(true, "تم احتساب الدعوة")
        } else {
            ReferralTrackResult(false, "لم تتحقق شروط الاحتساب")
        }
    }

    fun purchaseMock(tier: String): PurchaseResult {
        return PurchaseResult(true, tier, "تم تفعيل باقة $tier فوراً")
    }

    fun readMockJson(): JSONObject {
        val json = context.assets.open("mock_db.json").bufferedReader().use { it.readText() }
        return JSONObject(json)
    }
}
