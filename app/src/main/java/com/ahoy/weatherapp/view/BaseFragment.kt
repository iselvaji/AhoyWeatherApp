package com.ahoy.weatherapp.view

import androidx.fragment.app.Fragment
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.utils.ConnectionUtils
import com.ahoy.weatherapp.utils.UiUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment(){

    protected fun canMakeApiCall() : Boolean {
        if (!ConnectionUtils.hasInternetConnection(context)) {
            UiUtils.showDialogWithOKCancelButton(requireContext(), getString(R.string.network_error_message))
            return false
        }
        return true
    }
}