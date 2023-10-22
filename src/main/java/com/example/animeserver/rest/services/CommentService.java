package com.example.animeserver.rest.services;

import com.example.animeserver.domain.entities.Comment;
import com.example.animeserver.domain.repositories.CommentRepository;
import com.example.animeserver.rest.dto.CommentRequest;
import com.example.animeserver.rest.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private JwtService jwtService;

    public Comment create(HttpServletRequest request, CommentRequest commentDto){
        var user = userService.findByEmail(jwtService.getSubject(request));
        return Comment.of(commentDto,user);
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    public Comment findById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("comment is not found"));
    }

    public void deleteById(Long id){
        commentRepository.delete(findById(id));
    }

    @Transactional
    public void likeThisComment(Long idComment, HttpServletRequest request){
        var comment = findById(idComment);
        if(likeService.verifyComment(request,comment)){
            var like = likeService.create(request);
            like.setLikeComment(comment);
            comment.getCommentLikes().add(likeService.save(like));
            commentRepository.save(comment);
        }
    }

    public void update(Long id,CommentRequest request){
        var comment = findById(id);
        updateData(comment,request);
        commentRepository.save(comment);
    }

    private void updateData(Comment comment, CommentRequest request) {
        comment.setContent(request.getComment());
        comment.setDate(LocalDate.now());
    }

}
