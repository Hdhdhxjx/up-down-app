text_content = """══════════════════════════════════════════════════════════════════════════════════════
          وثيقة المواصفات الفنية والهندسية الشاملة والمحكمة الكاملة
                     تطبيق تنزيل الفيديوهات والوسائط الذكي (up down)
                          الإصدار المعتمد النهائي V 1.0.2
══════════════════════════════════════════════════════════════════════════════════════

المنصة المستهدفة: Android (API Level 26+)
اتجاه الواجهة: RTL افتراضي
اللغة الافتراضية: العربية الفصحى

القسم 1: منظومة التصميم
- color_bg_primary: #0C0C0C
- color_surface: #1E1E1E
- color_accent: #F1C40F (افتراضي)
- ألوان التأكيد الديناميكية: #F1C40F / #2196F3 / #4CAF50 / #9C27B0 / #F44336
- الخطوط: Cairo (عربي) + Google Sans (إنجليزي/أرقام)

القسم 2: واجهة المشاركة الخارجية
- TransparentShareActivity
- BottomSheetDialogFragment
- Drag Handle 40x4dp
- شبكة 6 دقات (360p, 720p, 1080p, 4K, 8K, MP3)
- 1080p محدد افتراضياً

القسم 3: التنقل
- BottomNavigation بثلاث تبويبات: الرئيسية، التنزيلات، الإعدادات

القسم 4: Home
- بحث + Avatar
- إدخال رابط + زر لصق وتحليل
- Banner رصد الحافظة
- بطاقة VIP مجانية عبر إعلان مكافأة
- قائمة تنزيلات جارية (Progress + سرعة + إيقاف)

القسم 5: Downloads
- رأس "مكتبتي"
- فلاتر (الكل، MP4، MP3، 4K)
- بطاقات ملفات مع مدة + أزرار تشغيل/مشاركة/حذف

القسم 6: Settings
- رأس الحساب (80dp Avatar + اسم + إيميل + Badge + Streak)
- بطاقة Ultra متدرجة
- 14 خيار إعداد
- صف اختيار لون التأكيد (5 دوائر)

القسم 7: الشاشات الفرعية
- الاشتراكات (Pro / VIP / Ultra)
- الدوري والمكافآت + لوحة متصدرين
- الخزنة السرية (PIN 4 + Numpad + اهتزاز خطأ)

القسم 8: Mock Backend
- /api/register
- /api/referral/track
- /api/purchase/mock

القسم 9: الإعلانات
- Interstitial بحد يومي
- Rewarded VIP 24 ساعة
- تحدي Ultra مجاني

القسم 10: الانتقالات
- فتح: Slide 250ms
- إغلاق: Slide 200ms
- تحميل الصور عبر Glide أو Coil

القسم 11: بيانات تجريبية
- المستخدم: أحمد حامد (المركز #16)
- تنزيلات جارية + مكتبة + متصدرين جاهزة للاختبار
"""

with open("up_down_app_specifications_final.txt", "w", encoding="utf-8") as f:
    f.write(text_content)

print("Text file generated successfully: up_down_app_specifications_final.txt")
