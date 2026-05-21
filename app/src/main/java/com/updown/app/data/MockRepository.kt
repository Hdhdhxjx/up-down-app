package com.updown.app.data

object MockRepository {

    val profile = UserProfile(
        name = "أحمد حامد",
        email = "ahmed.hamed@example.com",
        rank = 16,
        points = 1200,
        successfulInvites = 12,
        streakDays = 8,
        planBadge = "VIP"
    )

    val runningDownloads = mutableListOf(
        RunningDownload(
            id = "run-1",
            title = "وصفة المطبخ التركي الأصيل",
            sizeMb = 28,
            quality = "720p HD",
            progressPercent = 75,
            speedText = "5.5 MB/s",
            source = "يوتيوب",
            thumbnailUrl = "https://picsum.photos/300/180?1"
        ),
        RunningDownload(
            id = "run-2",
            title = "أفضل تمارين اللياقة في المنزل",
            sizeMb = 64,
            quality = "1080p",
            progressPercent = 42,
            speedText = "3.2 MB/s",
            source = "إكس",
            thumbnailUrl = "https://picsum.photos/300/180?2"
        )
    )

    val libraryItems = mutableListOf(
        LibraryItem(
            id = "lib-1",
            title = "تعلم اللغة التركية",
            sizeText = "180 MB",
            format = "MP4",
            quality = "720p HD",
            dateText = "أمس",
            durationText = "04:12",
            thumbnailUrl = "https://picsum.photos/300/180?3"
        ),
        LibraryItem(
            id = "lib-2",
            title = "ملخص مباراة كلاسيكو ناري",
            sizeText = "64 MB",
            format = "MP4",
            quality = "1080p",
            dateText = "اليوم",
            durationText = "09:25",
            thumbnailUrl = "https://picsum.photos/300/180?4"
        ),
        LibraryItem(
            id = "lib-3",
            title = "بودكاست القيادة وريادة الأعمال",
            sizeText = "24 MB",
            format = "MP3",
            quality = "Audio",
            dateText = "اليوم",
            durationText = "16:40",
            thumbnailUrl = "https://picsum.photos/300/180?5"
        )
    )

    val settingOptions = listOf(
        SettingOption("subs", "خطط الاشتراك والترقية"),
        SettingOption("referral", "الدعوة والمكافآت", "لديك تنبيه جديد", highlight = true),
        SettingOption("vault", "الخزنة السرية"),
        SettingOption("vault_timer", "مؤقت قفل الخزنة"),
        SettingOption("default_quality", "جودة التنزيل الافتراضية", "1080p FHD"),
        SettingOption("save_path", "مسار الحفظ", "Movies/up-down"),
        SettingOption("parallel_limit", "حد التنزيلات المتزامنة", "3"),
        SettingOption("wifi_only", "التنزيل عبر Wi-Fi فقط"),
        SettingOption("screen_mode", "وضع الشاشة", "ليلي"),
        SettingOption("accent_color", "لون التأكيد"),
        SettingOption("download_done", "تنبيه اكتمال التحميل", "مفعل"),
        SettingOption("league_notifications", "تنبيهات الدوري", "مفعل"),
        SettingOption("region_lang", "المنطقة واللغة", "العربية - السعودية"),
        SettingOption("about", "حول التطبيق", "الإصدار 1.0.2")
    )

    val leaderboard = listOf(
        LeaderboardEntry(1, "محمد العمري", 5200, 52, isElite = true),
        LeaderboardEntry(2, "فاطمة الزهراء", 4800, 48, isElite = true),
        LeaderboardEntry(3, "خالد المنصور", 4300, 43, isElite = true),
        LeaderboardEntry(4, "نورة السالم", 3900, 39),
        LeaderboardEntry(12, "عمر الزهراني", 2000, 20),
        LeaderboardEntry(13, "رنا الشمري", 1900, 19),
        LeaderboardEntry(14, "حسين الغامدي", 1500, 15),
        LeaderboardEntry(15, "أميرة الحربي", 1300, 13),
        LeaderboardEntry(16, "أحمد حامد", 1200, 12, isCurrentUser = true)
    )

    val resolutionOptions = listOf(
        ResolutionOption("360p SD", "12 MB"),
        ResolutionOption("720p HD", "28 MB"),
        ResolutionOption("1080p FHD", "45 MB"),
        ResolutionOption("4K UHD", "120 MB"),
        ResolutionOption("8K Ultra", "480 MB"),
        ResolutionOption("MP3 صوت فقط", "4 MB")
    )
}
