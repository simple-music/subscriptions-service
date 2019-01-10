package ru.bmstu.iu7.simplemusic.subscriptionsservice.exception

import java.lang.RuntimeException

open class ServiceException(message: String?) : RuntimeException(message)

class DuplicateException(message: String?) : ServiceException(message)

class NotFoundException(message: String?) : ServiceException(message)
