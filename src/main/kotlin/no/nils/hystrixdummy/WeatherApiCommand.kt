package no.nils.hystrixdummy

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import kotlinx.coroutines.experimental.delay

class WeatherApiCommand<T>(val operation: () -> T) : HystrixCommand<T>(HystrixCommandGroupKey.Factory.asKey(WeatherApiCommand::class.simpleName)) {

    override fun run(): T {
        Thread.sleep((Math.random()*500 + 10).toLong())
        return operation()
    }

}