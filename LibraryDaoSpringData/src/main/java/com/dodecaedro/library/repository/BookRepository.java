package com.dodecaedro.library.repository;

import com.dodecaedro.library.data.pojo.Book;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Book.class, idClass = Integer.class)
public interface BookRepository {
  @Cacheable("books")
  Book findOne(Integer bookId);
  @CachePut(value = "books",  key = "#p0.bookId")
  Book save(Book book);
  Book findByIsbn(String isbn);
  List<Book> findAll();
  @CacheEvict("books")
  void delete(Integer bookId);
}
