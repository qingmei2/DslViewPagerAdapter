package com.github.qingmei2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.github.qingmei2.dsladapter.adapter
import com.github.qingmei2.fragments.AFragment
import com.github.qingmei2.fragments.BFragment
import com.github.qingmei2.fragments.CFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fragments: List<Fragment> =
            listOf(AFragment(), BFragment(), CFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter(
                fragmentManager = { supportFragmentManager },
                fragments = { fragments },
                defaultItem = { 1 },
                recycle = { old, _ ->
                    when (old) {
                        is AFragment -> true
                        else -> false
                    }
                }
        )

        btnChange.setOnClickListener {
            fragments = listOf(AFragment(), BFragment(), CFragment())   // new Fragment instance
            viewPager.adapter?.notifyDataSetChanged()
        }
    }
}
