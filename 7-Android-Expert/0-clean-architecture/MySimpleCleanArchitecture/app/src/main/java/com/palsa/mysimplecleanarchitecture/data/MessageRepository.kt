package com.palsa.mysimplecleanarchitecture.data

import com.palsa.mysimplecleanarchitecture.domain.IMessageRepository
import com.palsa.mysimplecleanarchitecture.domain.MessageEntity

class MessageRepository(private val messageDataSource: IMessageDataSource) : IMessageRepository {

    override fun getWelcomeMessage(name: String): MessageEntity =
        messageDataSource.getMessageFromSource(name)
}