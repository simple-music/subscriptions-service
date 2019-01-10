package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.repository.SubscriptionRepository

@Service
class SubscriptionServiceImpl(@Autowired val subscriptionRepository: SubscriptionRepository) : SubscriptionService {

    override fun addSubscription(user: String, subscriber: String): Long {
        TODO("not implemented")
    }

    override fun getSubscriptionsStatus(user: String): SubscriptionsStatus {
        TODO("not implemented")
    }
}
