package com.mamyrosa.playrealm.datamanager.factory

interface BaseFactory<DO, DTO> {

    /**
     * Convert data transfer object to domain object
     * Not nullable
     * @param value : Not nullable - Data Transfer Object
     * @return DO Domain Object
     */
    fun toDomainObject(value: DTO): DO


    /**
     * Convert domain object to data transfer object
     * @param value : Not nullable - Domain Object
     * @return DTO Data Transfer Object
     */
    fun toDataTransferObject(value: DO): DTO

    /**
     * Convert list of data transfer objects to list of domain objects
     * @param list List of Data Transfer Objects
     * @return List of Domain Objects
     */
    fun toDomainObjects(list: List<DTO>) = list.map { toDomainObject(it) }


    /**
     * Convert list of domain objects to list of data transfer objects
     * @param list List of Domain Objects
     * @return List of Data Transfer Objects
     */
    fun toDataTransferObjects(list: List<DO>) = list.map { toDataTransferObject(it) }
}