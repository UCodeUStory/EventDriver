package com.ustory.eventdriver

class EventDriver {

    /**
     * OldDriver.find("").observe(this,Observe{})
     *
     * OldDriver.find("") say T
     *
     * OldDriver say event to target
     */

    companion object {

        infix fun <T> find(eventName: String): EventLiveData<T> {
            return EventStore.get().find(eventName)
        }

        infix fun <T> notify(eventName: String): EventLiveData<T> {
            return find(eventName)
        }

    }

}