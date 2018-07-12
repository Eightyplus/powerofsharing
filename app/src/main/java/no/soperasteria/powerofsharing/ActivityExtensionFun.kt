package no.soperasteria.powerofsharing

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

fun FragmentActivity.showFragment(tag: String, fragment: Fragment) {
    supportFragmentManager.findFragmentByTag(tag) ?: run {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment, tag)
                .commit()
    }
}

fun FragmentActivity.hideFragment(tag: String) {
    supportFragmentManager.findFragmentByTag(tag)?.let { fragment ->
        supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
    }
}