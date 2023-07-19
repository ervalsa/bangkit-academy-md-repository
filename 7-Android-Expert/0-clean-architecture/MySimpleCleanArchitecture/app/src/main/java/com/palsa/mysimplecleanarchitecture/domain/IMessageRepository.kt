package com.palsa.mysimplecleanarchitecture.domain

interface IMessageRepository {

    fun getWelcomeMessage(name: String): MessageEntity
}