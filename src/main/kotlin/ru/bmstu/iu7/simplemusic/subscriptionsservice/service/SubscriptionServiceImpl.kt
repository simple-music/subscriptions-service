package ru.bmstu.iu7.simplemusic.subscriptionsservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.Subscription
import ru.bmstu.iu7.simplemusic.subscriptionsservice.domain.SubscriptionPK
import ru.bmstu.iu7.simplemusic.subscriptionsservice.exception.NotFoundException
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.SubscriptionsStatus
import ru.bmstu.iu7.simplemusic.subscriptionsservice.repository.SubscriptionRepository
import ru.bmstu.iu7.simplemusic.subscriptionsservice.model.Subscription as SubscriptionModel

@Service
@Transactional
class SubscriptionServiceImpl(@Autowired val subscriptionRepository: SubscriptionRepository) : SubscriptionService {
    override fun addSubscription(subscription: SubscriptionModel): SubscriptionsStatus {
        this.subscriptionRepository.save(Subscription(
                info = SubscriptionPK(subscription.musician, subscription.subscriber)
        ))
        return this.getStatus(subscription.subscriber)!!
    }

    override fun getSubscriptionsStatus(musician: String): SubscriptionsStatus {
        return this.getStatus(musician) ?: throw NotFoundException("musician not found")
    }

    override fun getSubscribers(musician: String, page: Int, size: Int): Iterable<String> {
        return this.subscriptionRepository
                .findMusicianSubscribers(musician, PageRequest.of(page, size)).content
    }

    override fun getSubscriptions(musician: String, page: Int, size: Int): Iterable<String> {
        return this.subscriptionRepository
                .findMusicianSubscriptions(musician, PageRequest.of(page, size)).content
    }

    override fun deleteSubscription(subscription: SubscriptionModel): SubscriptionsStatus? {
        this.subscriptionRepository.delete(Subscription(
                info = SubscriptionPK(subscription.musician, subscription.subscriber)
        ))
        return getStatus(subscription.musician)
    }

    private fun getStatus(musician: String): SubscriptionsStatus? {
        val numSubscribers = this.subscriptionRepository
                .countSubscriptionsByInfoMusician(musician)
        val numSubscriptions = this.subscriptionRepository
                .countSubscriptionsByInfoSubscriber(musician)

        return if (numSubscribers == 0L && numSubscriptions == 0L) {
            null
        } else {
            SubscriptionsStatus(musician, numSubscribers, numSubscriptions)
        }
    }
}
