package com.oscarsalguero.android.firekotlinx.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.perf.metrics.AddTrace
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.oscarsalguero.android.firekotlinx.R
import kotlinx.android.synthetic.main.fragment_home.*

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
        Log.d(TAG, "Variant: $variant")

        when {
            variant.equals("none", true) -> {
                itemImageView!!.setBackgroundColor(resources.getColor(R.color.transparent))
            }
            variant.equals("blue", true) -> {
                itemImageView!!.setBackgroundColor(resources.getColor(R.color.blue))
            }
            variant.equals("red", true) -> {
                itemImageView!!.setBackgroundColor(resources.getColor(R.color.red))
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