package com.palsa.mysimplecleanarchitecture.domain

interface MessageUseCase {

    fun getMessage(name: String): MessageEntity
}