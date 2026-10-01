package com.phuoc.dao;

import com.phuoc.model.Users_24162100;
import java.util.List;

/** Interface DAO cho bang Users. */
public interface IUserDAO_24162100 {
    Users_24162100 findByUsername(String username);
    Users_24162100 findByEmail(String email);
    Users_24162100 findById(int userId);
    boolean insert(Users_24162100 user);
    boolean update(Users_24162100 user);
    boolean delete(int userId);
    boolean activateByEmailAndCode(String email, String code);
    List<Users_24162100> findAll(int offset, int pageSize);
    int countAll();
}
