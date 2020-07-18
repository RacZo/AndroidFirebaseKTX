/***
 * Copyright (c) 2020 Oscar Salguero www.oscarsalguero.dev
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        // Specify defaults
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        // Fetch variables
        remoteConfig.fetchAndActivate().addOnCompleteListener {
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