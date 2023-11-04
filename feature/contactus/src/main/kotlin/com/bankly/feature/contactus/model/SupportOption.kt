package com.bankly.feature.contactus.model

import com.bankly.core.designsystem.icon.BanklyIcons

enum class ContactUsOption(val title: String, val icon: Int, val address: String) {
    Email("Email Us", BanklyIcons.ContactUsEmail, address = "hello@bankly.ng"),
    Whatsapp("WhatsApp", BanklyIcons.ContactUsWhatsapp, address = "08039900060"),
    Call("Call Us", BanklyIcons.ContactUsPhone, address = "08039900060")
}