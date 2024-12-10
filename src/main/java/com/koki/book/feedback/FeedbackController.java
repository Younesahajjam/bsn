package com.koki.book.feedback;

import com.koki.book.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "feedback")
public class FeedbackController {
    private final FeedbackService service;
    @PostMapping
    public ResponseEntity<Integer> saveFeedback(
            @Valid @RequestBody FeedbackRequest request,
            Authentication connectedUser
    )
    {return ResponseEntity.ok(service.save(request, connectedUser));}

    @GetMapping("book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>>findAllFeedbacksByBook(
            @RequestParam(value = "page",defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
            ,@PathVariable("book-id") Integer bookId
            ,Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllFeedbacksByBook(page,size,bookId,connectedUser));
    }
}

