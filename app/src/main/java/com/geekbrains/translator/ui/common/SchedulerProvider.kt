package com.geekbrains.translator.ui.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProvider {

    fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun io(): Scheduler {
        return Schedulers.io()
    }
}