package com.dodecaedro.library.repository;

import com.dodecaedro.library.configuration.LibraryDaoConfiguration;
import com.dodecaedro.library.data.pojo.Book;
import com.dodecaedro.library.data.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LibraryDaoConfiguration.class)
public class BookRepositoryTest {

  @Inject
  private BookRepository bookRepository;

  @Test
  public void loadAllTest() {
    List<Book> books = bookRepository.findAll();
    assertThat(books, is(not(empty())));
    assertNotNull(books);
  }

  @Test
  public void loadFindByIsbnTest() {
    Book book = bookRepository.findByIsbn("1234-5678");
    assertThat(book.getBookId(), is(1));
    assertThat(book.getTitle(), is("Guerra y paz"));
    assertThat(book.getDateTimeBought(), is(ZonedDateTime.of(2014, 9, 1, 0, 0, 0, 0, ZoneOffset.UTC)));
  }

  @Test
  @DirtiesContext
  public void testDeleteBook() {
    bookRepository.delete(1);

    Book bookDeleted = bookRepository.findOne(1);
    assertNull("book 1 should not exist", bookDeleted);
  }

  @Test
  public void testFindUsersBorrowedBook() {
    Book book = new Book();
    book.setBookId(1);
    List<User> users = bookRepository.findAllUsersThatBorrowed(book);

    assertThat(users.size(), is(3));
  }
}
