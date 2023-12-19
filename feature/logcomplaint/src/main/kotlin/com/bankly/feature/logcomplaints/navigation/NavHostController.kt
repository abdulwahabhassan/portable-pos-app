package com.bankly.feature.logcomplaints.navigation

import android.net.Uri
import androidx.navigation.NavHostController

internal fun NavHostController.navigateToComplaintLoggedRoute(complaintId: String) {
    val encodedComplaintId = Uri.encode(complaintId)
    this.navigate(route = "$complaintLoggedRoute/$encodedComplaintId")
}
