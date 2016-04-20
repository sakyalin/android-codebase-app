# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/linxinzhe/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep class com.linxinzhe.android.codebaseapp.** { *; }

# ----- butterknife ----- #
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
# ----- butterknife ----- #

# -----  Okhttp  ----- #
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
# -----  Okhttp  ----- #

# -----  retrofit  ----- #
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# -----  retrofit  ----- #

# -----  Eventbus  ----- #
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# -----  Eventbus  ----- #

# -----  Picasso  ----- #
-dontwarn com.squareup.okhttp.**
# -----  Picasso  ----- #

# ----- realm ----- #
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**
# ----- realm ----- #

# ----- utraptr ----- #
-keep class in.srain.*
-dontwarn class in.srain.cube.image.ImageProvider
-dontwarn class android.graphics.Bitmap
-ignorewarnings class in.srain.cube.image.ImageProvider
-ignorewarnings class android.graphics.Bitmap
# ----- utraptr ----- #


# ----- fastjson ----- #
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *;}
# ----- fastjson ----- #

# ----- bugly ----- #
-keep public class com.tencent.bugly.**{*;}
# ----- bugly ----- #

# ----- umeng ----- #
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.linxinzhe.android.codebaseapp.R$*{
 public static final int *;
 }
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }
# ----- umeng ----- #

# ----- galleryfinal ----- #
-keep class cn.finalteam.galleryfinal.widget.*{*;}
-keep class cn.finalteam.galleryfinal.widget.crop.*{*;}
-keep class cn.finalteam.galleryfinal.widget.zoonview.*{*;}
# ----- galleryfinal ----- #