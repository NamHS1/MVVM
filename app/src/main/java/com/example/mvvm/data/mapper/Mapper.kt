package com.example.mvvm.data.mapper

interface Mapper<in DomainObject, out UIObject> {
    fun mapFrom(item: DomainObject): UIObject
}
