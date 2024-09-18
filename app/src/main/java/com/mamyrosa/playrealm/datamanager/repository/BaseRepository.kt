package com.mamyrosa.playrealm.datamanager.repository

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import io.realm.kotlin.deleteFromRealm
import timber.log.Timber

open class BaseRepository<T : RealmModel>(val ofType: Class<T>) {

    private var realm: Realm? = Realm.getDefaultInstance()

    fun lastIntValue(pk: String) = realm?.where(ofType)?.max(pk) ?: 0


    fun insert(
        data: T,
        completion: ((Boolean) -> Unit)? = null
    ) {
        try {
            realm = Realm.getDefaultInstance()
            realm?.executeTransaction { realm ->
                realm.insertOrUpdate(data)
            }
            completion?.invoke(true)
            //realm?.refresh()
        } catch (ignored: Exception) {
            ignored.localizedMessage?.let { Timber.tag("EXCEPTION").e(it) }
            completion?.invoke(false)
        } finally {
            realm?.close()
        }
    }

    fun insert(
        data: List<T>,
        completion: ((Boolean) -> Unit)? = null
    ) {
        try {
            realm = Realm.getDefaultInstance()
            realm?.executeTransaction {
                it.insertOrUpdate(data)
            }
            completion?.invoke(true)
            realm?.refresh()
        } catch (ignored: Exception) {
            completion?.invoke(false)
        } finally {
            realm?.close()
        }
    }

    fun update(
        data: T,
        completion: ((Boolean) -> Unit)? = null
    ) {
        try {
            realm = Realm.getDefaultInstance()
            realm?.executeTransaction {
                it.insertOrUpdate(data)
            }
            completion?.invoke(true)
            realm?.refresh()
        } catch (ignored: Exception) {
            completion?.invoke(false)
        } finally {
            realm?.close()
        }
    }

    fun update(
        data: List<T>,
        completion: ((Boolean) -> Unit)? = null
    ) {
        try {
            realm = Realm.getDefaultInstance()
            realm?.executeTransaction {
                it.insertOrUpdate(data)
            }
            completion?.invoke(true)
            realm?.refresh()
        } catch (ignored: Exception) {
            completion?.invoke(false)
        } finally {
            realm?.close()
        }
    }

    fun delete(
        data: T,
        completion: ((Boolean) -> Unit)? = null
    ) {
        try {
            realm = Realm.getDefaultInstance()
            realm?.executeTransaction {
                data.deleteFromRealm()
            }
            completion?.invoke(true)
            realm?.refresh()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
            completion?.invoke(false)
        } finally {
            realm?.close()
        }
    }

    fun deleteAll(completion: ((Boolean) -> Unit)? = null) {
        try {
            realm = Realm.getDefaultInstance()
            realm?.executeTransaction {
                val results = it.where(ofType).findAll()
                results.deleteAllFromRealm()
            }
            completion?.invoke(true)
            realm?.refresh()
        } catch (ignored: Exception) {
            completion?.invoke(false)
        } finally {
            realm?.close()
        }
    }

    fun saveAsync(
        data: List<T>,
        completion: ((Boolean) -> Unit)? = null
    ) {
        try {
            realm?.executeTransactionAsync({
                it.insertOrUpdate(data)
            }, {
                completion?.invoke(true)
            }, {
                completion?.invoke(false)
            })
            realm?.refresh()
        } catch (exception: Exception) {
            exception.printStackTrace()
            completion?.invoke(false)
        }
    }

    fun saveAsync(
        data: T,
        completion: ((Boolean) -> Unit)? = null
    ) {
        try {
            realm?.executeTransactionAsync({
                it.insertOrUpdate(data)
            }, {
                completion?.invoke(true)
            }, {
                completion?.invoke(false)
            })
            realm?.refresh()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
            completion?.invoke(false)
        }
    }

    private fun queryAll() = realm?.where(ofType)?.findAll()

    private fun queryAllAsync(): RealmResults<T>? {
        val localRealm = Realm.getDefaultInstance()
        return localRealm.where(ofType).findAll()
    }


    fun findAllAsync(): List<T> {
        val results = queryAllAsync()
        val unmanagedResults = Realm.getDefaultInstance().copyFromRealm(results!!)
        return unmanagedResults.toList()
    }

    fun findAll() = queryAll()?.toList() ?: listOf()

    fun findById(pk: String, value: Int?) = realm?.where(ofType)?.equalTo(pk, value)?.findFirst()

    fun findById(pk: String, value: Long?) = realm?.where(ofType)?.equalTo(pk, value)?.findFirst()

    fun findById(pk: String, value: String?) = realm?.where(ofType)?.equalTo(pk, value)?.findFirst()

    fun getRealmObject(data: T): T? {
        var result: T? = null
        try {
            realm?.executeTransaction {
                result = it.copyToRealmOrUpdate(data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun isEmpty() = findAll().isEmpty()

    fun close() {
        if (realm?.isClosed == false) {
            realm?.close()
        }
    }
}