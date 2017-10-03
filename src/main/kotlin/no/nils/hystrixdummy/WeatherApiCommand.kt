package no.nils.hystrixdummy

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey

class WeatherApiCommand<T>(val operation: () -> T) : HystrixCommand<T>(HystrixCommandGroupKey.Factory.asKey(WeatherApiCommand::class.simpleName)) {

    override fun run(): T {
        return operation()
    }

}