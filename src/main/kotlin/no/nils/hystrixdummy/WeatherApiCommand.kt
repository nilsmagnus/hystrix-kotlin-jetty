package no.nils.hystrixdummy

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.HystrixCommandKey
import com.netflix.hystrix.HystrixThreadPoolKey

class WeatherApiCommand<T>(val operation: () -> T) : HystrixCommand<T>(
        Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("MyGroup"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("MyThreadPool"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("MyCommand"))) {

    override fun run(): T {
        if(Math.random()>0.95){
            throw RuntimeException()
        }
        Thread.sleep((Math.random() * 500 + 10).toLong())
        return operation()
    }

}