package com.project.hotel.service.impl;

import com.project.hotel.model.entity.Reply;
import com.project.hotel.model.entity.Service;
import com.project.hotel.repository.ReplyRepository;
import com.project.hotel.repository.ServiceRepository;
import com.project.hotel.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public void save(Reply reply) {
        replyRepository.save(reply);
    }

    @Override
    public Reply findById(Long id) {
        Optional<Reply> result = replyRepository.findById(id);
        Reply reply = null;
        if(result.isPresent()){
            reply = result.get();
        }
        else{
            throw new RuntimeException("Không thấy reply có id: " + id);
        }
        return reply;
    }

    @Override
    public List<Reply> findAll() {
        return replyRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {

        replyRepository.deleteById(id);
    }

    @Override
    public void updateReply(Long replyId, Reply updatedReply) {
        Optional<Reply> optionalReply = replyRepository.findById(replyId);
        if (optionalReply.isPresent()) {
            Reply reply = optionalReply.get();
            replyRepository.save(reply);
        } else {
            throw new EntityNotFoundException("Reply with id " + replyId + " not found");

        }
    }

}
