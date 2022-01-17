package io.betterreadsapp.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
public class BookController {

    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    @Autowired
    BookRepository bookRepository;

    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            String coverImageUrl = "/images/no_image.png";
            if(book.getCoverIds() != null && book.getCoverIds().size() > 0){
                coverImageUrl = COVER_IMAGE_ROOT+book.getCoverIds().get(0)+"-L.jpg";
            }
            model.addAttribute("coverImage", coverImageUrl);
            model.addAttribute("book", book);
            return "book"; // it renders the book.html page from thymeleaf and displays the details of book obj as we want in html.
        }
        return "book-not-found";
    }
}
