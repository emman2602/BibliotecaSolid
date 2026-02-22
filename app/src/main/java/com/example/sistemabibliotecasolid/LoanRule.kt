package com.example.sistemabibliotecasolid

interface LoanRule {
    fun validate(
        user: User,
        book: Book,
        bookRepository: BookRepository,
        loanRepository: LoanRepository
    )
}