package com.hoangson.xavier.core.models

sealed class Command {
    object Loading : Command()
    object NoLoading : Command()
    object Success : Command()
    object Error : Command()
}
