package com.project.hotel.service;
import com.project.hotel.model.entity.Reply;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReplyService {
    void save(Reply reply);
    Reply findById(Long id);
    List<Reply> findAll();
    void deleteById(Long id);
    void updateReply(Long serviceId, Reply updatedReply);
}
