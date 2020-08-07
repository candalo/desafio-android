package com.picpay.desafio.android.data

/**
 * A data source is an abstraction over the data origin.
 *
 * @param <T> The data type handled by the data source
 */
interface DataSource<T> {

    /**
     * Fetch data
     *
     * @return T the data type
     */
    fun fetch(): T

}