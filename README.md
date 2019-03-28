# EventDriver

在MVVM模式中，我们使用LiveData来实现通信,好处是可以通过生命周期去取消订阅

在实际开发中，我们事件需要再组件的onDestory 都可以接受到，并且不需要每次订阅都得到最新的消息

EventDriver 设计就是解决这一问题

### 订阅消息

 
     EventDriver.find<String>("tom").observe(this, Observer {
         
        })
### 发送消息

     EventDriver.notify<String>("tom") of "goodMorning"
