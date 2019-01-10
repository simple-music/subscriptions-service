package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.SubscriptionPK
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.NotFoundException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.Subscription as SubscriptionModel
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription as SubscriptionDomain
import ru.bmstu.iu7.simplemusic.subscriptionsservice.repository.SubscriptionRepository

@Service
class SubscriptionServiceImpl(@Autowired val subscriptionRepository: SubscriptionRepository) : SubscriptionService {
    override fun addSubscription(subscription: SubscriptionModel): SubscriptionsStatus {
        this.subscriptionRepository.save(SubscriptionDomain(
                info = SubscriptionPK(subscription.musician, subscription.subscriber)
        ))
        return this.getStatus(subscription.subscriber)
    }

    override fun getSubscriptionsStatus(musician: String): SubscriptionsStatus {
        return this.getStatus(musician)
    }

    override fun getSubscribers(musician: String, page: Int, size: Int): Iterable<SubscriptionDomain> {
        TODO("not implemented")
    }

    override fun getSubscriptions(musician: String, page: Int, size: Int): Iterable<SubscriptionDomain> {
        TODO("not implemented")
    }

    override fun deleteSubscription(subscription: SubscriptionModel): SubscriptionsStatus {
        TODO("not implemented")
    }

    private fun getStatus(musician: String): SubscriptionsStatus {
        val numSubscribers = this.subscriptionRepository
                .countSubscriptionsByInfoMusician(musician)
        val numSubscriptions = this.subscriptionRepository
                .countSubscriptionsByInfoSubscriber(musician)

        return if (numSubscribers == 0L && numSubscriptions == 0L) {
            throw NotFoundException("musician not found")
        } else {
            SubscriptionsStatus(
                    musician, numSubscribers, numSubscriptions
            )
        }
    }
}
