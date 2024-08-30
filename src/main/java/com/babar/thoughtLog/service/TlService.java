package com.babar.thoughtLog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.babar.thoughtLog.entity.TlEntry;
import com.babar.thoughtLog.entity.User;
import com.babar.thoughtLog.repository.TlEntryRepo;

@Component
public class TlService {

    @Autowired
    private TlEntryRepo tlEntryRepo;

    @Autowired
    UserService userService;

    @Transactional
    public void saveEntry(TlEntry tlEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            tlEntry.setDate(LocalDateTime.now());
            TlEntry saved = tlEntryRepo.save(tlEntry);
            user.getTlEntries().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveEntry(TlEntry tlEntry) {
        tlEntryRepo.save(tlEntry);
    }

    public List<TlEntry> getAll() {
        return tlEntryRepo.findAll();
    }

    public Optional<TlEntry> findById(ObjectId id) {
        return tlEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getTlEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        tlEntryRepo.deleteById(id);
    }
}