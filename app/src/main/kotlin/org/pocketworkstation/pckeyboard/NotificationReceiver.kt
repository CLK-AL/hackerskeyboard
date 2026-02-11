package org.pocketworkstation.pckeyboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.inputmethod.InputMethodManager

class NotificationReceiver(private val mIME: LatinIME) : BroadcastReceiver() {

    init {
        Log.i(TAG, "NotificationReceiver created, ime=$mIME")
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        Log.i(TAG, "NotificationReceiver.onReceive called, action=$action")

        when (action) {
            ACTION_SHOW -> {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.showSoftInputFromInputMethod(mIME.mToken, InputMethodManager.SHOW_FORCED)
            }
            ACTION_SETTINGS -> {
                val i = Intent(mIME, LatinIMESettings::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(i)
            }
        }
    }

    companion object {
        const val TAG = "PCKeyboard/Notification"
        const val ACTION_SHOW = "org.pocketworkstation.pckeyboard.SHOW"
        const val ACTION_SETTINGS = "org.pocketworkstation.pckeyboard.SETTINGS"
    }
}
