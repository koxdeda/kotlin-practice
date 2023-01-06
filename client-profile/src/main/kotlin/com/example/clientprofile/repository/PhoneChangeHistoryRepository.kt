package com.example.clientprofile.repository

import com.example.clientprofile.model.PhoneChangeHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PhoneChangeHistoryRepository: JpaRepository<PhoneChangeHistory?, Long?>