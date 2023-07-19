package com.palsa.mysimplecleanarchitecture.di

import com.palsa.mysimplecleanarchitecture.data.IMessageDataSource
import com.palsa.mysimplecleanarchitecture.data.MessageDataSource
import com.palsa.mysimplecleanarchitecture.data.MessageRepository
import com.palsa.mysimplecleanarchitecture.domain.IMessageRepository
import com.palsa.mysimplecleanarchitecture.domain.MessageInteractor
import com.palsa.mysimplecleanarchitecture.domain.MessageUseCase

object Injection {

    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): IMessageRepository {
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}