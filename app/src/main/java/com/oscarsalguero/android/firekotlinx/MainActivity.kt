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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.perf.FirebasePerformance
import com.oscarsalguero.android.firekotlinx.dummy.DummyContent
import com.oscarsalguero.android.firekotlinx.fragments.NotificationsFragment


/**
 * Main Activity
 * @author Oscar Salguero
 */
class MainActivity : AppCompatActivity(), NotificationsFragment.OnListFragmentInteractionListener {

    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logUser()
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = Navigation.findNavController(this, R.id.homeNavHostFragment)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        // Performance Monitoring trace
        val myTrace = FirebasePerformance.getInstance().newTrace("test_trace")
        myTrace.start()
        var i = 0
        while (i < 1000000) {
            if (i % 2 == 0) {
                myTrace.incrementMetric("item_cache_miss", 1)
            } else {
                myTrace.incrementMetric("item_cache_hit", 1)
            }
            i++
        }
        myTrace.stop()
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        // Send list item tap analytics
        val payload = Bundle()
        payload.putString("list_item_id", item!!.id)
        FirebaseAnalytics.getInstance(this).logEvent("list_item_tapped", payload)
    }

    /**
     * Adding relevant user data that will helps us to debug the app and better serve our users
     */
    private fun logUser() {
        FirebaseCrashlytics.getInstance().apply {
            // Set an user identifier (an unique user id internal to your system)
            // Don't use any Hardware ID (IMEI, Mac Address, etc.), Firebase User ID or Firebase Instance ID here
            setUserId("33")
            // We can add custom data using custom keys
            setCustomKey("full_name", "Pablo Perez")
            setCustomKey(
                "email",
                "pablo.perez@example.com"
            ) // Email is not recommended tho, since is personal identifiable info
        }
    }


}
