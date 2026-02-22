package com.example.sistemabibliotecasolid

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoanServiceTest {

    private lateinit var loanService: LoanService
    private lateinit var bookRepository: InMemoryBookRepository
    private lateinit var loanRepository: InMemoryLoanRepository

    @Before
    fun setup() {
        bookRepository = InMemoryBookRepository()
        loanRepository = InMemoryLoanRepository()

        // Initialize the service with the required business rules
        val rules = listOf(BookAvailabilityRule(), MaxBooksLimitRule())
        loanService = LoanService(bookRepository, loanRepository, rules)
    }

    @Test
    fun `should throw exception when trying to loan a book that is already lent`() {
        // Arrange
        val user = User("U1", "Alice")
        val book = Book("B1", "Clean Code", "Robert C. Martin")

        // Add book and explicitly set its availability to false
        bookRepository.addBook(book)
        bookRepository.setAvailability(book, false)

        // Act & Assert
        val exception = assertThrows(Exception::class.java) {
            loanService.borrowBook(user, book)
        }

        // Verify the exact exception message from BookAvailabilityRule
        assertTrue(exception.message!!.contains("currently not available"))
    }

    @Test
    fun `should throw exception when user tries to borrow more than 3 books`() {
        // Arrange
        val user = User("U2", "Bob")
        val book1 = Book("B1", "Book 1", "Author A")
        val book2 = Book("B2", "Book 2", "Author B")
        val book3 = Book("B3", "Book 3", "Author C")
        val book4 = Book("B4", "Book 4", "Author D")

        bookRepository.addBook(book1)
        bookRepository.addBook(book2)
        bookRepository.addBook(book3)
        bookRepository.addBook(book4)

        // Borrow the maximum allowed (3 books)
        loanService.borrowBook(user, book1)
        loanService.borrowBook(user, book2)
        loanService.borrowBook(user, book3)

        // Act & Assert
        val exception = assertThrows(Exception::class.java) {
            loanService.borrowBook(user, book4) // Attempting to borrow a 4th book
        }

        // Verify the exact exception message from MaxBooksLimitRule
        assertTrue(exception.message!!.contains("reached the maximum limit"))
    }

    @Test
    fun `should show available and lent books correctly in the repository`() {
        // Arrange
        val availableBook = Book("B1", "Design Patterns", "Gang of Four")
        val lentBook = Book("B2", "Refactoring", "Martin Fowler")
        val user = User("U3", "Charlie")

        bookRepository.addBook(availableBook)
        bookRepository.addBook(lentBook)

        // Act
        // Charlie borrows one of the books
        loanService.borrowBook(user, lentBook)

        // Assert
        // We filter using the existing methods in your BookRepository
        val allBooks = bookRepository.getAllBooks()
        val availableBooksList = allBooks.filter { bookRepository.isAvailable(it) }
        val borrowedBooksList = allBooks.filter { !bookRepository.isAvailable(it) }

        // Verify the available books list
        assertEquals(1, availableBooksList.size)
        assertTrue("Available list should contain the unborrowed book", availableBooksList.contains(availableBook))
        assertFalse("Available list should NOT contain the lent book", availableBooksList.contains(lentBook))

        // Verify the borrowed books list
        assertEquals(1, borrowedBooksList.size)
        assertTrue("Borrowed list should contain the lent book", borrowedBooksList.contains(lentBook))
        assertFalse("Borrowed list should NOT contain the available book", borrowedBooksList.contains(availableBook))
    }
}