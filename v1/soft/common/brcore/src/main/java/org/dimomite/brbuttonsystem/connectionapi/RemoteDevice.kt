package org.dimomite.brbuttonsystem.connectionapi

/**
 * This is a representation of BR Button System device.
 */
interface RemoteDevice {
    /**
     * Remove device name.
     */
    fun getName(): String

    /**
     * Remote device firmware version.
     */
    fun getVersion(): String

    /**
     * Some unique identifier which allows app user
     * distinguish between different devices of the same type.
     */
    fun getSerialNumber(): String

    /**
     * Whatever implementation considers to be unique identifier for establishing connection.
     * Nice to be human-readable, but not mandatory.
     */
    fun getAddress(): String
}