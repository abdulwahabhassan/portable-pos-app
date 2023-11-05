package com.bankly.feature.logcomplaints.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.bankly.core.common.model.TransactionData
import com.bankly.core.sealed.TransactionReceipt
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToComplaintLoggedRoute(complaintId: String) {
    val encodedComplaintId = Uri.encode(complaintId)
    this.navigate(route = "$complaintLoggedRoute/$encodedComplaintId")
}
