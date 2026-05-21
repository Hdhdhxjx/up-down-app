# up down (Android)

تطبيق Android مبني بالكامل من الصفر وفق مواصفات:
- `D:\Desktop\مواصفات_التطبيق_الشاملة_V1_0_1 (4).txt`
- نسخة V1.0.2 المعتمدة في `up_down_app_specifications_final.txt`

## الميزات المنفذة
- RTL عربي افتراضياً.
- Bottom Navigation بثلاث شاشات: الرئيسية / التنزيلات / الإعدادات.
- Home:
  - شريط بحث + صورة حساب.
  - إدخال رابط يدوي مع زر لصق فوري.
  - شريط رصد الحافظة.
  - بطاقة VIP مجانية.
  - قائمة تنزيلات جارية مع Progress + سرعة + إيقاف مؤقت.
- Downloads:
  - مكتبتي + فلاتر (الكل/MP4/MP3/4K).
  - بطاقات ملفات مع مدة الفيديو وأزرار تشغيل/مشاركة/حذف.
- Settings:
  - رأس الحساب + بطاقة Ultra.
  - قائمة 14 إعداد.
  - محرك لون تأكيد ديناميكي (5 ألوان).
- الشاشات الفرعية:
  - الاشتراكات.
  - الدوري والمكافآت + جدول متصدرين.
  - الخزنة السرية (PIN + اهتزاز خطأ).
- External Share:
  - `TransparentShareActivity` + `DownloadBottomSheetFragment`.
  - شبكة دقات 6 خيارات (1080p محددة افتراضياً).
- Mock Backend محلي:
  - `/api/register` (منطقي داخل `MockBackendService.register`).
  - `/api/referral/track`.
  - `/api/purchase/mock`.
  - بيانات JSON في `app/src/main/assets/mock_db.json`.
- تحميل الصور عبر Coil.

## ملاحظات تشغيل
البيئة الحالية لا تحتوي أمر `gradle` على النظام، لذلك لم يتم تشغيل `assembleDebug` من الطرفية.

للتشغيل:
1. افتح المشروع في Android Studio.
2. Sync Project with Gradle Files.
3. شغّل `app` على Emulator أو جهاز Android (API 26+).

## المسارات الأهم
- `app/src/main/java/com/updown/app/ui/main/MainActivity.kt`
- `app/src/main/java/com/updown/app/ui/home/HomeFragment.kt`
- `app/src/main/java/com/updown/app/ui/downloads/DownloadsFragment.kt`
- `app/src/main/java/com/updown/app/ui/settings/SettingsFragment.kt`
- `app/src/main/java/com/updown/app/ui/share/DownloadBottomSheetFragment.kt`
- `app/src/main/java/com/updown/app/data/MockBackendService.kt`
