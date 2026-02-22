package com.example.sistemabibliotecasolid

class BookViewer(
    private val bookRepository: BookRepository
) {
    fun showBooksStatus() {
        println("\n=== LIBRARY CATALOG STATUS ===")

        val allBooks = bookRepository.getAllBooks()

        if (allBooks.isEmpty()) {
            println("The library is empty.")
            return
        }

        allBooks.forEach { book ->
            val status = if (bookRepository.isAvailable(book)) "AVAILABLE" else "BORROWED"
            println("$status | ${book.title} by ${book.author} (ISBN: ${book.isbn})")
        }
        println("==============================\n")
    }
}