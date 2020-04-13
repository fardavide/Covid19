package studio.forface.covid.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import studio.forface.covid.android.Router
import studio.forface.viewstatestore.ViewStateActivity

/**
 * Base Activity for our app
 *
 * Inherit from [AppCompatActivity]
 *
 * Implements [KoinComponent] for inject dependencies
 * Implements [ViewStateActivity] for use ViewStateStore extensions
 *
 *
 * @author Davide Farella
 */
abstract class BaseActivity : AppCompatActivity(), KoinComponent, ViewStateActivity {

    /** Every Activity has a [Router] for navigate though pages */
    protected val router by inject<Router>()

    /** Override with initial UI setup */
    protected abstract fun initUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }
}
