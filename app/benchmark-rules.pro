# Правила для бенчмарков
-dontobfuscate
-ignorewarnings

# Сохраняем классы для бенчмарков
-keep class androidx.benchmark.** { *; }
-keep class androidx.test.** { *; }

# Отключаем логирование
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
} 