package com.mamyrosa.playrealm.datamanager.service

import com.mamyrosa.playrealm.datamanager.factory.BaseFactory
import com.mamyrosa.playrealm.datamanager.repository.BaseRepository
import com.mamyrosa.playrealm.utilities.Closure
import io.realm.RealmModel

open class BaseManager<Do : RealmModel, Dto, Repository : BaseRepository<Do>, Factory : BaseFactory<Do, Dto>>(
    val repository: Repository,
    val factory: Factory,
    private val pkFieldName: String
) {

    val lastPkIntValue get() = repository.lastIntValue(pk = pkFieldName)

    open fun insert(
        data: Dto?,
        completion: Closure<Boolean>? = null
    ) {
        data?.let {
            repository.insert(
                data = factory.toDomainObject(it),
                completion = completion
            )
        }
    }

    open fun insert(
        data: List<Dto>?,
        completion: Closure<Boolean>? = null
    ) {
        data?.let {
            repository.insert(
                data = factory.toDomainObjects(it),
                completion = completion
            )
        }

    }

    open fun update(
        data: Dto?,
        completion: Closure<Boolean>? = null
    ) {
        data?.let {
            repository.update(
                data = factory.toDomainObject(it),
                completion = completion
            )
        }
    }

    open fun update(
        data: List<Dto>?,
        completion: Closure<Boolean>? = null
    ) {
        data?.let {
            repository.update(
                data = factory.toDomainObjects(it),
                completion = completion
            )
        }
    }

    open fun delete(
        data: Dto?,
        completion: Closure<Boolean>? = null
    ) {
        data?.let {
            repository.getRealmObject(factory.toDomainObject(it))?.let { realmData ->
                repository.delete(
                    data = realmData,
                    completion = completion
                )
            }
        }
    }

    open fun deleteAll(completion: Closure<Boolean>? = null) {
        repository.deleteAll(completion = completion)
    }

    open fun saveAsync(
        data: Dto?,
        completion: Closure<Boolean>? = null
    ) {
        data?.let {
            repository.saveAsync(
                data = factory.toDomainObject(it),
                completion = completion
            )
        }
    }

    open fun saveAsync(
        data: List<Dto>?,
        completion: Closure<Boolean>? = null
    ) {
        data?.let {
            repository.saveAsync(
                data = factory.toDomainObjects(it),
                completion = completion
            )
        }
    }


    open fun findAll() = factory.toDataTransferObjects(repository.findAll())

    open fun findAllAsync() = factory.toDataTransferObjects(repository.findAllAsync())

    open fun findById(value: Int?): Dto? {
        return when (val domainObject = repository.findById(pkFieldName, value)) {
            null -> null
            else -> factory.toDataTransferObject(domainObject)
        }
    }

    open fun findById(value: Long?): Dto? {
        return when (val domainObject = repository.findById(pkFieldName, value)) {
            null -> null
            else -> factory.toDataTransferObject(domainObject)
        }
    }

    open fun findById(value: String?): Dto? {
        return when (val domainObject = repository.findById(pkFieldName, value)) {
            null -> null
            else -> factory.toDataTransferObject(domainObject)
        }
    }

    open fun close() {
        repository.close()
    }

    fun isEmpty() = repository.isEmpty()

    fun isNotEmpty() = !isEmpty()
}