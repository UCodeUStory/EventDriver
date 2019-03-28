package com.ustory.eventdriver

class EventStore {

    private val eventMaps = HashMap<String, EventLiveData<*>>()

    companion object {
        @Volatile
        private var instance: EventStore? = null

        fun get() =
            instance ?: synchronized(this) {
                instance
                    ?: EventStore().also { instance = it }
            }
    }

    @Synchronized
    fun <T> find(eventName: String): EventLiveData<T> {
        if (eventMaps[eventName] != null) {
            //处理异常
            return eventMaps[eventName] as EventLiveData<T>
        } else {
            val eventLiveData = EventLiveData<T>(eventName)
            eventMaps[eventName] = eventLiveData
            return eventLiveData
        }
    }


    /**
     * 注销事件
     */

    @Synchronized
    fun remove(name: String) {
        eventMaps.remove(name)
    }

}