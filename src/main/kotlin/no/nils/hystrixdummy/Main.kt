package no.nils.hystrixdummy

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.ServletHandler
import javax.servlet.Servlet

fun main(args: Array<String>) {

    val server = jettyServer(HystrixMetricsStreamServlet::class.java, "/hystrix")

    for (i in 0..2000) {
        launch(CommonPool) {
            delay((1000 * i).toLong())
            val result = WeatherApiCommand(fun(): String { return "f" + i }).execute()
            println("Got result " + result)
        }
    }

    println("Launched all coroutines.")
    server.start()
    server.join()
}

private fun jettyServer(serlvet: Class<out Servlet>, path: String): Server {
    val server = Server();
    val connector = ServerConnector(server)
    connector.port = 8090

    server.connectors = arrayOf(connector)

    val servletHandler = ServletHandler()
    servletHandler.addServletWithMapping(serlvet, path)
    server.handler = servletHandler

    return server
}