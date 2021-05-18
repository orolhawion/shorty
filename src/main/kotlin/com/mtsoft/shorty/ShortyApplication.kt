package com.mtsoft.shorty

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.BufferedImageHttpMessageConverter

@SpringBootApplication
class ShortyApplication {

    @Bean
    fun createImageHttpMessageConverter() = BufferedImageHttpMessageConverter()
}

fun main(args: Array<String>) {
    runApplication<ShortyApplication>(*args)
}
