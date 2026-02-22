package com.example.sistemabibliotecasolid

interface LoanRepository {
    fun registerLoan(loan: Loan)
    fun updateLoan(loan: Loan)
    fun getActivesLoansByUser(user: User): List<Loan>
    fun getActivesLoansByBook(book: Book): Loan?
}