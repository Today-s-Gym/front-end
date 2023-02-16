package umc.standard.todaygym.util


import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun putSharedPreference(key: String?, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun putSharedPreference(key: String?, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }

    fun putSharedPreference(key: String?, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun putSharedPreference(key: String?, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun putSharedPreference(key: String?, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    fun putSharedPreference(key: String, value: HashSet<String>) {
        prefs.edit().putStringSet(key, value).apply()
    }
    fun getSharedPreference(key: String?, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    fun getSharedPreference(key: String?, defaultValue: Float): Float {
        return prefs.getFloat(key, defaultValue)
    }

    fun getSharedPreference(key: String?, defaultValue: Long): Long? {
        return prefs.getLong(key, defaultValue)
    }

    fun getSharedPreference(key: String?, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun getSharedPreference(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue)!!
    }

    fun getSharedPreference(key: String, defaultValue: HashSet<String>): Set<String> {
        return prefs.getStringSet(key, defaultValue)!!
    }


}
object APIPreferences{
    val SHARED_PREFERENCE_NAME_COOKIE:String = "cookie"
    val SHARED_PREFERENCE_NAME_EMAIL:String = "email"
    val SHARED_PREFERENCE_NAME_NICKNAME :String = "nickname"
    val SHARED_PREFERENCE_NAME_TYPE :String = "exerciseType"
    val SHARED_PREFERENCE_NAME_PROFILEID :String = "profileId"
    val SHARED_PREFERENCE_NAME_STATUS:String = "statusMsg"
}