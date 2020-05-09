package com.oscarsalguero.android.firekotlinx.fragments


import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.oscarsalguero.android.firekotlinx.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.fragment_dashboard.*


/**
 * A fragment with a floating action button
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * @author Oscar Salguero
 */
class DashboardFragment : Fragment(), View.OnClickListener {

    private var firebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(activity!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab!!.setOnClickListener(this)
        val variant = FirebaseRemoteConfig.getInstance()
            .getString("button_color_test")
        Log.d(TAG, "Variant: $variant")
        when (variant) {
            "blue" -> {
                fab!!.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.blue))
            }
            "red" -> {
                fab!!.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context!!, R.color.red))
            }
        }
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.fab -> {
                // Analytics tracking for button tap
                firebaseAnalytics?.logEvent("add_fab_button_tap", null)
            }
        }
    }

    companion object {

        @JvmField
        val TAG: String = DashboardFragment::class.java.simpleName

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DashboardFragment.
         */
        fun newInstance(param1: String, param2: String): DashboardFragment {
            return DashboardFragment()
        }
    }

}
