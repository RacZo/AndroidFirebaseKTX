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
package com.oscarsalguero.android.firekotlinx.fragments


import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.firebase.perf.metrics.AddTrace
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.oscarsalguero.android.firekotlinx.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.itemImageView

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * @author Oscar Salguero
 */
class HomeFragment : Fragment() {

    @AddTrace(name = "onCreateTrace", enabled = true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @AddTrace(name = "onCreateViewTrace", enabled = true)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val variant = FirebaseRemoteConfig.getInstance().getString("home_color_test")
        Log.d(TAG, "Valor de la variable remota home_color_test: $variant")

        when {
            variant.equals("blue", true) -> {
                itemImageView!!.setColorFilter(resources.getColor(R.color.blue))
            }
            variant.equals("red", true) -> {
                itemImageView!!.setColorFilter(resources.getColor(R.color.red))
            }
        }

        val buttonVariant = FirebaseRemoteConfig.getInstance().getString("button_color_test")
        Log.d(HomeFragment.TAG, "Valor de la variable remota button_color_test: $buttonVariant")
        when (buttonVariant) {
            "blue" -> {
                crashButton!!.setBackgroundColor(ContextCompat.getColor(context!!, R.color.blue))
            }
            "red" -> {
                crashButton!!.setBackgroundColor(ContextCompat.getColor(context!!, R.color.red))
            }
        }

        crashButton.setOnClickListener {
            forceCrash()
        }

    }

    /**
     * Cause a Crashlytics crash
     */
    private fun forceCrash() {
        throw RuntimeException("Test Crash!") // Forcing a crash
    }

    companion object {

        @JvmField
        val TAG: String = HomeFragment::class.java.simpleName

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
