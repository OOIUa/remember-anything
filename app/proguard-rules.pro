# Add project specific ProGuard rules here.
-keep class com.example.jileme.data.local.** { *; }
-keep @androidx.room.Entity class *
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**
