package com.rest.app.controller;

import com.rest.app.model.Book;
import com.rest.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public List<Book>getAllBookRecords(){
        return bookRepository.findAll();
    }
    @GetMapping("{bookId}")
    public Optional<Book> getBookById(@PathVariable("bookId") Long id){
        return bookRepository.findById(id);
    }
    @PostMapping
    public Book createBookRecord( @RequestBody @Validated Book bookRecord){
        return bookRepository.save(bookRecord);
    }
    @PutMapping
    public Book updateBookRecord(@RequestBody @Validated Book bookRecord) throws FileNotFoundException {
       if (bookRecord == null || bookRecord.getBookId()== null){
           throw new FileNotFoundException("BookRecord or Id must not be null !");
       }
       Optional<Book> book =bookRepository.findById(bookRecord.getBookId());
       if (!book.isPresent()){
           throw new FileNotFoundException("Book with id:"+bookRecord.getBookId()+"is not present ");
       }
       Book book1=book.get();
       book1.setName(bookRecord.getName());
       book1.setRating(bookRecord.getRating());
       book1.setSummary(bookRecord.getSummary());
       return bookRepository.save(book1);
    }
    @DeleteMapping("{bookId}")
    public void deleteBookById(@PathVariable(value = "bookId") Long bookId) throws NoSuchFieldError{
        if (!bookRepository.findById(bookId).isPresent()){
            throw new NoSuchFieldError("book " + bookId + "not present");
        }
        bookRepository.deleteAllById(Collections.singleton(bookId));


    }

}
