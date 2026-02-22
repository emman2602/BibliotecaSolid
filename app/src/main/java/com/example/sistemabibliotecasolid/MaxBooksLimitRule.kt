package com.example.sistemabibliotecasolid

class MaxBooksLimitRule(
    private val maxLimit: Int = 3
): LoanRule {
    override fun validate(
        user: User,
        book: Book,
        bookRepository: BookRepository,
        loanRepository: LoanRepository
    ) {
        val activesLoans = loanRepository.getActivesLoansByUser(user)
        if(activesLoans.size >= maxLimit){
            throw Exception("${user.name} has reached the maximum limit of $maxLimit")
        }
    }
}