package com.koki.book.feedback;

import com.koki.book.book.Book;
import com.koki.book.book.BookRepository;
import com.koki.book.common.PageResponse;
import com.koki.book.exception.OperationNotPermittedException;
import com.koki.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackMaper feedbackMaper;
    private final FeedBackRepository feedBackRepository;
    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book=bookRepository.findById(request.bookId())
                .orElseThrow(()-> new EntityNotFoundException("book was not found"));
        User user=(User)connectedUser.getPrincipal() ;
        if (book.isArchived() || !book.isShareable() ){
            throw new OperationNotPermittedException("cannot give feedback for this book");
        }
        if (Objects.equals(book.getOwner().getId(),user.getId())){
            throw new OperationNotPermittedException("cannot give a feedback for your own book");
        }
        Feedback feedback=feedbackMaper.toFeedback(request);
        return feedBackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbacksByBook(
            int page
            , int size
            , Integer bookId
            , Authentication connectedUser) {
        User user=((User)connectedUser.getPrincipal());
        Pageable pageable= PageRequest.of(page,size);
        Page<Feedback>feedbacks=feedBackRepository.findAllByBookId(bookId,pageable);
        List<FeedbackResponse>feedbackResponses=feedbacks.stream().map(
              f ->  feedbackMaper.toFeedbackResponse(f,user.getId())
        ).toList();
        return new  PageResponse<>(
                feedbackResponses,
        feedbacks.getNumber(),
        feedbacks.getSize(),
        feedbacks.getTotalPages(),
        feedbacks.getTotalElements(),
        feedbacks.isFirst(),
        feedbacks.isLast()
                );

    }
}
