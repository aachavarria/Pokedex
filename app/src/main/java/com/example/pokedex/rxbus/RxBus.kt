package com.example.pokedex.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class RxBus private constructor() {
    private val publisher: PublishSubject<String> = PublishSubject.create()
    private val behavior: BehaviorSubject<String> = BehaviorSubject.create()
    fun publish(event: String?) {
        if (event != null) {
            publisher.onNext(event)
            behavior.onNext(event)
        }
    }

    fun listen(): Observable<String> {
        return publisher
    }

    fun listenLastOne(): Observable<String> {
        return behavior
    }

    companion object {
        private var mInstance: RxBus? = null
        val instance: RxBus?
            get() {
                if (mInstance == null) {
                    mInstance = RxBus()
                }
                return mInstance
            }
    }
}