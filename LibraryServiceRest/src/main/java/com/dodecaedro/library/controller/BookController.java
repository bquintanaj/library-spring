package com.dodecaedro.library.controller;

import com.dodecaedro.library.data.pojo.Book;
import com.dodecaedro.library.repository.BookRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

  @Inject
  private BookRepository bookRepository;

  @RequestMapping(value = "/{bookId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Book> getBookByBookId(@PathVariable Integer bookId) {
    Book book = bookRepository.findOne(bookId);
    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Book> createNewBook(@RequestBody Book book, UriComponentsBuilder builder) {
    bookRepository.save(book);

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(
      builder.path("/books/{id}")
        .buildAndExpand(book.getBookId().toString()).toUri());

    return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBook(@PathVariable("id") Integer bookId) {
    this.bookRepository.delete(bookId);
  }
}