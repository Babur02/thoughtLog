package com.babar.thoughtLog.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babar.thoughtLog.entity.TlEntry;
import com.babar.thoughtLog.entity.User;
import com.babar.thoughtLog.service.TlService;
import com.babar.thoughtLog.service.UserService;

@RestController
@RequestMapping("/tl")
public class TlEntryController {

    @Autowired
    private TlService tlService;

    @Autowired
    UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllTlEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<TlEntry> all = user.getTlEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<TlEntry> createEntry(@RequestBody TlEntry myEntry, @PathVariable String userName) {
        try {
            tlService.saveEntry(myEntry, userName);
            myEntry.setDate(LocalDateTime.now());
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<TlEntry> getTlEntryById(@PathVariable ObjectId myId) {
        Optional<TlEntry> tlEntry = tlService.findById(myId);
        if (tlEntry.isPresent()) {
            return new ResponseEntity<>(tlEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> delTlEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
        tlService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updTlEntryById(@PathVariable ObjectId myId, @RequestBody TlEntry newEntry,
            @PathVariable String userName) {
        TlEntry old = tlService.findById(myId).orElse(null);
        if (old != null) {
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ?
        newEntry.getTitle()
        : old.getTitle());
        old.setContent(
        newEntry.getContent() != null && !newEntry.equals("") ? newEntry.getContent()
        : old.getContent());
        tlService.saveEntry(newEntry);
        return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
