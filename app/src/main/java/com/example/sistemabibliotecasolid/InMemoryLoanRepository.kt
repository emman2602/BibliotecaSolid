package com.example.sistemabibliotecasolid

class InMemoryLoanRepository: LoanRepository {
    private val loans = mutableListOf<Loan>()
    override fun registerLoan(loan: Loan) {
        loans.add(loan)
    }

    override fun updateLoan(loan: Loan) {

    }

    override fun getActivesLoansByUser(user: User): List<Loan> {
        return loans.filter { it.user.id == user.id && it.isActive}
    }

    override fun getActivesLoansByBook(book: Book): Loan? {
        return loans.find { it.book.isbn == book.isbn && it.isActive }
    }

}