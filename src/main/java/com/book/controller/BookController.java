package com.book.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.model.Book;
import com.book.repository.BookRepository;

@RestController
@RequestMapping("/api")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = new ArrayList<Book>();
		bookRepository.findAll().forEach(books::add);
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
		Optional<Book> book = bookRepository.findById(id);

		if (book.isPresent()) {
			return new ResponseEntity<>(book.get(), HttpStatus.OK);
		} 
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		bookRepository.save(book);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
	
	@PutMapping("books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book book) {
		Optional<Book> bookEdit = bookRepository.findById(id);
		Book _book = bookEdit.get();
		_book.setTitle(book.getTitle());
		_book.setAuthor(book.getAuthor());
		_book.setPrice(book.getPrice());
		return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
		try {
			bookRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
