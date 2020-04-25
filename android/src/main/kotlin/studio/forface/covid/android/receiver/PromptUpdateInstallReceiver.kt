package studio.forface.covid.android.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject
import studio.forface.covid.domain.usecase.updates.GetInstallableUpdate

/**
 * A [BroadcastReceiver] that will receiver prompt for install the last downloaded update
 * Implements [KoinComponent]
 *
 * @author Davide Farella
 */
class PromptUpdateInstallReceiver : BroadcastReceiver(), KoinComponent {

    private val getInstallableUpdate by inject<GetInstallableUpdate>()

    override fun onReceive(context: Context, intent: Intent?) {

        val installableUpdate = runBlocking { getInstallableUpdate.invoke()!! }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val contentUri = FileProvider.getUriForFile(
                context,
                "studio.forface.covid.android.provider",
                installableUpdate.file.delegate
            )
            val install = Intent(Intent.ACTION_VIEW).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                data = contentUri
            }
            context.startActivity(install)

        } else {
            val install = Intent(Intent.ACTION_VIEW)
            install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            install.setDataAndType(
                Uri.fromFile(installableUpdate.file.delegate),
                "application/vnd.android.package-archive"
            )
            context.startActivity(install)
        }
    }
}