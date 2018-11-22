package artalejo.com.btcgraph.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import artalejo.com.btcgraph.ui.Navigator
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity(), HasSupportFragmentInjector {

    private val DEFAULT_FRAGMENT_TAG = "DEFAULT_FRAGMENT_TAG"
    @Inject lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var navigator: Navigator
    abstract var layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layout)
        onViewLoaded(savedInstanceState)
    }

    abstract fun onViewLoaded(savedInstanceState: Bundle?)

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragmentInjector
    }

    protected fun addFragment(containerViewId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit()
    }

    protected fun replaceFragment(containerViewId: Int, fragment: Fragment,
                                  addToBackStack: Boolean = false,
                                  fragmentTag: String = DEFAULT_FRAGMENT_TAG) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerViewId, fragment)
        if (addToBackStack) transaction.addToBackStack(fragmentTag)
        transaction.commit()
    }


    protected fun FragmentManager.putFragmentSafe(outState: Bundle, key: String, fragment: Fragment?) {
        fragment?.let {
            if (it.isAdded) {
                putFragment(outState, key, it)
            }
        }
    }
}