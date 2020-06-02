package studio.forface.covid.android.ui

import android.content.BroadcastReceiver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.content.FileProvider
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject
import studio.forface.covid.android.Router
import studio.forface.covid.domain.unsupported
import studio.forface.covid.domain.usecase.updates.GetInstallableUpdate

/**
 * A [BroadcastReceiver] that will receiver prompt for install the last downloaded update
 * Implements [KoinComponent]
 *
 * @author Davide Farella
 */
class PromptUpdateInstallActivity : BaseActivity() {

    private val getInstallableUpdate by inject<GetInstallableUpdate>()
    override val router: Router
        get() = unsupported

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apkFile = runBlocking { getInstallableUpdate.invoke()!! }
            .file.delegate

        val installIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val contentUri = FileProvider.getUriForFile(
                this,
                AUTHORITY,
                apkFile
            )
            Intent(Intent.ACTION_VIEW).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                setDataAndType(contentUri, "application/vnd.android.package-archive")
            }.also {
                grantUriPermission(
                    it.resolveActivity(packageManager).packageName,
                    contentUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }

        } else {
            Intent(Intent.ACTION_VIEW).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                setDataAndType(
                    Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive"
                )
            }
        }

        startActivity(installIntent)
        finish()
    }

    override fun initUi() {
        // NO UI
    }
}

private const val AUTHORITY = "studio.forface.covid.android.provider"
