package com.example.viewer_2020

import android.util.Log

class RefreshManager {
    val listeners = mutableMapOf<String, () -> Unit>()

    fun addRefreshListener(name: String, listener: () -> Unit){
        listeners[name] = listener
    }

    fun refresh(){
        listeners.forEach {
            it.value.invoke()
            Log.d("refresh", "refreshed: ${it.key}")
        }
    }

    fun refresh(name: String){
            listeners[name]?.let { it() }
    }
}