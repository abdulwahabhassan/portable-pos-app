package com.bankly.feature.logcomplaints.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToComplaintLoggedRoute(complaintId: String) {
    val encodedComplaintId = Uri.encode(Json.encodeToString(complaintId))
    this.navigate(route = "$complaintLoggedRoute/$encodedComplaintId")
}
