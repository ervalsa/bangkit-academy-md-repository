package com.palsa.mysimplecleanarchitecture.data

import com.palsa.mysimplecleanarchitecture.domain.MessageEntity

class MessageDataSource : IMessageDataSource {

    override fun getMessageFromSource(name: String): MessageEntity =
        MessageEntity("Hello $name! Welcome to Clean Architecture")
}