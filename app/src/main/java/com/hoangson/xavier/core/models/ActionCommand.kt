package com.hoangson.xavier.core.models

sealed class ActionCommand {
    object Load : ActionCommand()
    object LoadDone : ActionCommand()
    object SwipeRefresh : ActionCommand()
    object Retry : ActionCommand()
}