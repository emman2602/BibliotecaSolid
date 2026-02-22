package com.example.sistemabibliotecasolid

class InMemoryBookRepository: BookRepository {
    private val books = mutableListOf<Book>()
    private val availabilityMap = mutableMapOf<String, Boolean>()
    override fun addBook(book: Book) {
        books.add(book)
        availabilityMap[book.isbn] = true
    }

    override fun getAllBooks(): List<Book> = books.toList()

    override fun isAvailable(book: Book): Boolean = availabilityMap[book.isbn] ?: false
    override fun setAvailability(
        book: Book,
        available: Boolean
    ) {
        availabilityMap[book.isbn] = available
    }
}