package com.jr.mtgcards.library.repository

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable

abstract class BaseEntity<ID> : Persistable<ID> {
  @Transient @JsonIgnore val _id: ID
  @Transient @JsonIgnore var insert: Boolean = false

  constructor(id: ID) {
    this._id = id
  }

  override fun getId(): ID = _id

  override fun isNew(): Boolean = insert
}
