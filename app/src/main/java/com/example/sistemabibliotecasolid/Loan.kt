package com.example.sistemabibliotecasolid

import java.time.LocalDate

data class Loan(
    val id: String,
    val book: Book,
    val user: User,
    val loanDate: LocalDate,
    var returnDate: LocalDate? = null
){
    val isActive: Boolean get() = returnDate == null
}
