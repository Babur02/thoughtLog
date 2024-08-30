package com.babar.thoughtLog.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.babar.thoughtLog.entity.TlEntry;

public interface TlEntryRepo extends MongoRepository <TlEntry,ObjectId>{

}
