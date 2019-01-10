package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.SubscriptionPK
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.NotFoundException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.NewSubscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.repository.SubscriptionRepository

@Service
class SubscriptionServiceImpl(@Autowired val subscriptionRepository: SubscriptionRepository) : SubscriptionService {

    override fun addSubscription(newSubscription: NewSubscription): SubscriptionsStatus {
        this.subscriptionRepository.save(Subscription(
                info = SubscriptionPK(newSubscription.musician, newSubscription.subscriber)
        ))
        return this.getStatus(newSubscription.subscriber)
    }

    override fun getSubscriptionsStatus(musician: String): SubscriptionsStatus {
        return this.getStatus(musician)
    }

    private fun getStatus(musician: String): SubscriptionsStatus {
        val numSubscribers = this.subscriptionRepository
                .countSubscriptionsByInfo_Musician(musician)
        val numSubscriptions = this.subscriptionRepository
                .countSubscriptionsByInfo_Subscriber(musician)

        return if (numSubscribers == 0L && numSubscriptions == 0L) {
            throw NotFoundException("musician not found")
        } else {
            SubscriptionsStatus(
                    musician, numSubscribers, numSubscriptions
            )
        }
    }
}
