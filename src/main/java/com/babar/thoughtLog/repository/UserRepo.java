package com.babar.thoughtLog.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.babar.thoughtLog.entity.User;


public interface UserRepo extends MongoRepository <User,ObjectId>{

    User findByUsername(String username);
}
