package com.example.sistemabibliotecasolid

class BookAvailabilityRule: LoanRule {
    override fun validate(
        user: User,
        book: Book,
        bookRepository: BookRepository,
        loanRepository: LoanRepository
    ) {
        if(!bookRepository.isAvailable(book)){
            throw Exception("The book '${book.title}' is currently not available")
        }
    }

}