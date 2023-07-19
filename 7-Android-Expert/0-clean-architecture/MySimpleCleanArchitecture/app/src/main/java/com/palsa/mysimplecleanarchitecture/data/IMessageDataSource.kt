package com.palsa.mysimplecleanarchitecture.data

import com.palsa.mysimplecleanarchitecture.domain.MessageEntity

interface IMessageDataSource {

    fun getMessageFromSource(name: String): MessageEntity
}