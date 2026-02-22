package com.example.sistemabibliotecasolid

interface BookRepository {
    fun addBook(book: Book)
    fun getAllBooks(): List<Book>
    fun isAvailable(book: Book): Boolean
    fun setAvailability(book: Book, available: Boolean)
}