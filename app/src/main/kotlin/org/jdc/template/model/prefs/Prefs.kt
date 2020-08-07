package org.jdc.template.model.prefs

import android.os.Build
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Prefs @Inject constructor() : PrefsContainer(COMMON_NAMESPACE) {
    var lastInstalledVersionCode by SharedPref(0, key = "lastInstalledVersionCode")
    var workSchedulerVersion by SharedPref(0, key = "workSchedulerVersion")

    var developerMode by SharedPref(false, key = "developerMode")
    var theme by EnumPref(getThemeDefault(), key = "displayThemeType")
    private var internalAppInstanceId by SharedPref("", key = "appInstanceId")

    private fun getThemeDefault(): DisplayThemeType {
        return if (Build.VERSION.SDK_INT > 28) {
            // support Android Q System Theme
            DisplayThemeType.SYSTEM_DEFAULT
        } else {
            DisplayThemeType.LIGHT
        }
    }

    @Synchronized
    fun getAppInstanceId(): String {
        if (internalAppInstanceId.isBlank()) {
            internalAppInstanceId = UUID.randomUUID().toString()
        }

        return internalAppInstanceId
    }
}

