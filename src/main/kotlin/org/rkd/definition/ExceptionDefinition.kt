package org.rkd.definition

class UserNotFoundException(message: String) : RuntimeException(message)
class UserAlreadyRegisteredException(message: String) : RuntimeException(message)
class InvalidPasswordException(message: String) : RuntimeException(message)
class UnauthorizedActionException(message: String) : RuntimeException(message)