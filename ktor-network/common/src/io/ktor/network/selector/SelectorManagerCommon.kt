package io.ktor.network.selector

import io.ktor.util.*

/**
 * Selector manager is a service that manages NIO selectors and selection threads
 */
interface SelectorManager {
    /**
     * Notifies the selector that selectable has been closed.
     */
    fun notifyClosed(selectable: Selectable)

    /**
     * Suspends until [interest] is selected for [selectable]
     * May cause manager to allocate and run selector instance if not yet created.
     *
     * Only one selection is allowed per [interest] per [selectable] but you can
     * select for different interests for the same selectable simultaneously.
     * In other words you can select for read and write at the same time but should never
     * try to read twice for the same selectable.
     */
    suspend fun select(selectable: Selectable, interest: SelectInterest)

    companion object
}

/**
 * Select interest kind
 * @property [flag] to be set in NIO selector
 */
@Suppress("KDocMissingDocumentation")
@KtorExperimentalAPI
expect enum class SelectInterest {
    READ, WRITE, ACCEPT, CONNECT;

    val flag: Int

    companion object {
        @InternalAPI
        val AllInterests: Array<SelectInterest>

        /**
         * interest's flags in enum entry order
         */
        @InternalAPI
        val size: Int

        @InternalAPI
        val flags: IntArray
    }
}
