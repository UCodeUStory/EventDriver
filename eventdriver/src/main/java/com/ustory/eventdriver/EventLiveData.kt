package com.ustory.eventdriver

import android.annotation.SuppressLint
import android.arch.core.internal.SafeIterableMap
import android.arch.lifecycle.*
import android.os.Handler
import android.os.Looper
import android.support.annotation.NonNull
import android.util.Log

open class EventLiveData<T>(private val name:String) {

    private val mDataLock = Any()
    private var mMainHandler: Handler? = null
    private var value: T? = null
        set(value) {
            if (!isMainThread()) {
                postToMainThread {
                    dispatchValue(value)
                }
            } else {
                dispatchValue(value)
            }
        }

    infix fun of(eventValue:T){
        value = eventValue
    }

    @SuppressLint("RestrictedApi")
    private val mObservers = SafeIterableMap<Observer<T>, LifecycleObserver>()

    @SuppressLint("RestrictedApi")
    fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<T>) {
        if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }
        val lifecycle = LifecycleObserver(owner, observer)

        Log.i("event", "mObservers add before=" + mObservers.size())
        mObservers.putIfAbsent(observer, lifecycle)
        Log.i("event", "mObservers add end=" + mObservers.size())
        owner.lifecycle.addObserver(lifecycle)
    }

    inner class LifecycleObserver(@NonNull var mOwner: LifecycleOwner, var mObserver: Observer<T>) :
        GenericLifecycleObserver {
        @SuppressLint("RestrictedApi")
        override fun onStateChanged(source: LifecycleOwner?, event: Lifecycle.Event?) {
            if (mOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                Log.i("event", "mObservers remove before=" + mObservers.size())
                removeObserver(mObserver)
                Log.i("event", "mObservers remove end=" + mObservers.size())
                return
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun removeObserver(observer: Observer<T>) {
        mObservers.remove(observer) ?: return
        if (mObservers.size() == 0) {
            EventStore.get().remove(name)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun dispatchValue(value: T?) {
        mObservers.forEach {
            Log.i("event", "mObservers send=" + mObservers.size())
            if (it.value.mOwner.lifecycle.currentState != Lifecycle.State.DESTROYED) {
                it.value.mObserver.onChanged(value)
            }
        }
    }

    private fun postToMainThread(runnable: () -> Unit) {
        if (mMainHandler == null) {
            synchronized(mDataLock) {
                if (mMainHandler == null) {
                    mMainHandler = Handler(Looper.getMainLooper())
                }
            }
        }
        mMainHandler?.post(runnable)
    }


    private fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread.id == Thread.currentThread().id
    }


}