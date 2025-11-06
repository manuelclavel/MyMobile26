package com.mobile.com.mobile.mymobile26

import android.content.Context

class FlashCardResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}