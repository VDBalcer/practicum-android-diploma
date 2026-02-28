package ru.practicum.android.diploma.util

object SalaryFormater {
    fun formatSalary(
        from: Int?,
        to: Int?,
        currency: String?,
    ): String {
        if (from == null && to == null) return "Зарплата не указана"

        val currencySymbol = when (currency) {
            "RUR", "RUB" -> "₽"
            "USD" -> "$"
            "EUR" -> "€"
            "GBP" -> "£"
            else -> currency ?: ""
        }

        return when {
            from != null && to != null ->
                "от $from до $to $currencySymbol"

            from != null ->
                "от $from $currencySymbol"

            else ->
                "до $to $currencySymbol"
        }
    }
}

