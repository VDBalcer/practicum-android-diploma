package ru.practicum.android.diploma.data.repository

import android.content.Context
import android.content.Intent
import ru.practicum.android.diploma.domain.api.ExternalNavigator
import androidx.core.net.toUri

class ExternalNavigatorImpl(
    private val context: Context,
) : ExternalNavigator {

    override fun sendEmail(emailData: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:$emailData".toUri()
        }
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun callPhone(termsLink: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$termsLink".toUri()
        }
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun shareLink(shareAppLink: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareAppLink)
        }
        context.startActivity(
            Intent.createChooser(intent, "Поделиться")
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
