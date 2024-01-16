package com.bankly.core.common.util

import android.content.Context
import android.media.MediaPlayer
import com.bankly.core.common.R

fun playSuccessSound(context: Context) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.sound_success);
    mediaPlayer.setOnCompletionListener { mp: MediaPlayer ->
        mp.release()
    }
    mediaPlayer.start();
}