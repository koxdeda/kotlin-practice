package com.example.clientprofile.repository

import com.example.clientprofile.model.Client
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository



interface ClientRepository : JpaRepository<Client, Long?>{

    fun findAllByPhone(phone: String) : List<Client>
    fun getOneByPhone(phone: String): Client?
    override fun findAll(pageable: Pageable): Page<Client>
}