package com.github.qingmei2.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.qingmei2.R
import kotlinx.android.synthetic.main.fragment_layout.*

class AFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            textView.text = getString(DISPLAY_TEXT_TAG)
        }
    }

    companion object {

        private const val DISPLAY_TEXT_TAG = "DISPLAY_TEXT_TAG"

        fun newInstance(display: String = "AFragment"): Fragment {
            val bundle = Bundle()
            bundle.putString(DISPLAY_TEXT_TAG, display)
            return AFragment().apply {
                arguments = bundle
            }
        }
    }
}