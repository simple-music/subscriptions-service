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

@Service
@Transactional
class SubscriptionServiceImpl(@Autowired val subscriptionRepository: SubscriptionRepository) : SubscriptionService {
    private val notFoundException = NotFoundException("user not found")

    override fun addSubscription(musician: String, subscriber: String): SubscriptionsStatus {
        this.subscriptionRepository.save(Subscription(
                info = SubscriptionPK(musician, subscriber)
        ))
        return this.getStatus(subscriber)!!
    }

    override fun deleteSubscription(musician: String, subscriber: String): SubscriptionsStatus? {
        val info = SubscriptionPK(musician, subscriber)
        if (!this.subscriptionRepository.existsById(info)) {
            throw this.notFoundException
        }
        this.subscriptionRepository.deleteById(info)
        return getStatus(musician)
    }

    override fun getSubscriptionsStatus(musician: String): SubscriptionsStatus {
        return this.getStatus(musician) ?: throw this.notFoundException
    }

    override fun getSubscribers(musician: String, page: Int, size: Int): Iterable<String> {
        if (!this.subscriptionRepository.existsByInfoMusician(musician)) {
            throw this.notFoundException
        }
        return this.subscriptionRepository
                .findMusicianSubscribers(musician, PageRequest.of(page, size)).content
    }

    override fun getSubscriptions(musician: String, page: Int, size: Int): Iterable<String> {
        if (!this.subscriptionRepository.existsByInfoSubscriber(musician)) {
            throw this.notFoundException
        }
        return this.subscriptionRepository
                .findMusicianSubscriptions(musician, PageRequest.of(page, size)).content
    }

    override fun deleteSubscribersAndSubscriptions(musician: String) {
        this.subscriptionRepository
                .deleteSubscriptionsByInfoMusicianOrInfoSubscriber(musician, musician)
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
