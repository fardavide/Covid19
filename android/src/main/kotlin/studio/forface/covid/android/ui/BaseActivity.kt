package studio.forface.covid.android.ui

import androidx.appcompat.app.AppCompatActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import studio.forface.covid.android.Router
import studio.forface.viewstatestore.ViewStateActivity

/**
 * Base Activity for our app
 *
 * Implements [KoinComponent]
 * Implements [ViewStateActivity]
 *
 *
 * @author Davide Farella
 */
abstract class BaseActivity : AppCompatActivity(), KoinComponent, ViewStateActivity {

    /** Every Activity has a [Router] for navigate though pages */
    protected val router by inject<Router>()
}
