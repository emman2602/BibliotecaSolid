package com.example.sistemabibliotecasolid

import java.time.LocalDate
import java.util.UUID

class LoanService(
    private val bookRepository: BookRepository,
    private val loanRepository: LoanRepository,
    private val rules: List<LoanRule>
) {
    fun borrowBook(user: User, book: Book): Loan {
        rules.forEach { rule ->
            rule.validate(user, book, bookRepository, loanRepository)
        }

        val loan = Loan(
            UUID.randomUUID().toString(),
            book,
            user,
            LocalDate.now()
        )

        loanRepository.registerLoan(loan)
        bookRepository.setAvailability(book, false)

        println(" Loan success: ${book.title} borrowed by ${user.name}")
        return loan
    }

    fun returnBook(book: Book){
        val activeLoan = loanRepository.getActivesLoansByBook(book)
            ?: throw Exception("The book ${book.title} is not currently borrowed")

       activeLoan.returnDate = LocalDate.now()
        loanRepository.updateLoan(activeLoan)
        bookRepository.setAvailability(book, true)
        println("Return book ${book.title} successfully: ")
    }
}