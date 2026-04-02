package com.mamon.tablekmp

import kotlin.math.abs
import kotlin.math.roundToInt



data class ProjectData(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val status: String = "",
    val priority: String = "",
    val progress: Float = 0f,
    val revenue: Double = 0.0,
    val teamMembers: List<String> = emptyList()
)



val sampleProjects = listOf(
    ProjectData("PRJ-812", "Apollo Payment Gateway", "FinTech", "Active", "High", 0.75f, 145000.0, listOf("SJ", "MK", "AL")),
    ProjectData("PRJ-813", "Cloud DB Migration", "Infra", "Pending", "Medium", 0.10f, 12000.0, listOf("MR", "DL")),
    ProjectData("PRJ-814", "iOS App v2.4 Launch", "Mobile", "Done", "High", 1.0f, 285000.0, listOf("EC", "BJ", "TR", "KW")),
    ProjectData("PRJ-815", "Q3 SEO Optimization", "Marketing", "Active", "Low", 0.45f, 8500.0, listOf("DK")),
    ProjectData("PRJ-816", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-817", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-818", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-819", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-820", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-821", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-822", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-823", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-824", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-825", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-826", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT")),
    ProjectData("PRJ-827", "Auth Security Audit", "Security", "Failed", "High", 0.90f, 0.0, listOf("RZ", "PT"))
)


data class AdminUserData(
    val id: String,
    val fullName: String,
    val email: String,
    val username: String,
    val status: String,
    val role: String,
    val joinedDate: String,
    val lastActive: String
)

val sampleUsers = listOf(
    AdminUserData("1", "John Smith", "john.smith@gmail.com", "jonny77", "Active", "Admin", "March 12, 2023", "1 minute ago"),
    AdminUserData("2", "Olivia Bennett", "ollyben@gmail.com", "olly659", "Inactive", "User", "June 27, 2022", "1 month ago"),
    AdminUserData("3", "Daniel Warren", "dwarren3@gmail.com", "dwarren3", "Banned", "User", "January 8, 2024", "4 days ago"),
    AdminUserData("4", "Chloe Hayes", "chloehhye@gmail.com", "chloehh", "Pending", "Guest", "October 5, 2021", "10 days ago"),
    AdminUserData("5", "Marcus Reed", "reeds777@gmail.com", "reeds7", "Suspended", "User", "February 19, 2023", "3 months ago")
)



data class DirectoryUser(
    val name: String,
    val email: String,
    val role: String,
    val groups: String,
    val status: String
)



val sampleDirectory = listOf(
    DirectoryUser("Ian Chesnut", "ian.chesnut@gmail.com", "Super Admin", "Falcons, Stallions", "Active"),
    DirectoryUser("Zeki Mokharzada", "zeki@gmail.com", "Admin", "Falcons, Stallions", "Inactive"),
    DirectoryUser("Faith Robinson", "faith@gmail.com", "Contributor", "Falcons, Stallions", "Active"),
    DirectoryUser("Scott Walter", "scott987@gmail.com", "Super Admin", "Falcons, Stallions", "Active"),
    DirectoryUser("Chris Bowen", "chris.bowen@gmail.com", "Admin", "Falcons, Stallions", "Inactive"),
    DirectoryUser("Track Aksam", "tracy.aksam@gmail.com", "Admin", "Falcons, Stallions", "Inactive"),
    DirectoryUser("Natali Emanuel", "natali@gmail.com", "Contributor", "Falcons, Stallions", "Active"),
    DirectoryUser("Dan Spanser", "dan@gmail.com", "Contributor", "Falcons, Stallions", "Inactive")
)



fun formatCurrency(amount: Double, currencySymbol: String = "$"): String {
    val isNegative = amount < 0
    val absAmount = abs(amount)

    val totalCents = (absAmount * 100).roundToInt()
    val integerPart = totalCents / 100
    val fractionalPart = totalCents % 100

    val integerString = integerPart.toString()
    val formattedInteger = buildString {
        for (i in integerString.indices) {
            if (i > 0 && (integerString.length - i) % 3 == 0) {
                append(",")
            }
            append(integerString[i])
        }
    }

    val formattedFraction = fractionalPart.toString().padStart(2, '0')

    val sign = if (isNegative) "-" else ""

    return "$sign$currencySymbol$formattedInteger.$formattedFraction"
}