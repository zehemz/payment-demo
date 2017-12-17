package com.lucasbais.paymentdemo.internal.di


interface HasComponent<out COMPONENT> {
    fun getComponent(): COMPONENT
}