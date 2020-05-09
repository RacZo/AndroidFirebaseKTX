package com.oscarsalguero.android.firekotlinx

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

/**
 * Application class where we will initialize some stuff early on
 * @author Oscar Salguero
 */
class AFKApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // Remote Config KTX
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
            fetchTimeoutInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        // Specify defaults
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        // Fetch variables
        remoteConfig.fetch().addOnCompleteListener {
            Log.d(TAG, "Remote config fetch success!")
        }

        // Get the device's Firebase Instance ID (used to test A/B Testing variants)
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { result ->
                Log.d(TAG, "Firebase Instance ID Token (IID_TOKEN): ${result.token}")
            }

    }

    companion object {
        @JvmField
        val TAG = AFKApplication::class.java.simpleName
    }

}