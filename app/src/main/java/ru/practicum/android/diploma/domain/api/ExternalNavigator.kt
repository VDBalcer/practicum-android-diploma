package ru.practicum.android.diploma.domain.api

interface ExternalNavigator {
    fun shareLink(shareAppLink: String)

    fun callPhone(termsLink: String)

    fun sendEmail(emailData: String)
}
