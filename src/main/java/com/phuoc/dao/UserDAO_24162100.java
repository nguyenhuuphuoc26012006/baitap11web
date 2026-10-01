package com.phuoc.dao;

import com.phuoc.model.Users_24162100;
import com.phuoc.util.DBConnection_24162100;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** Cai dat DAO cho bang Users (JDBC thuan). */
public class UserDAO_24162100 implements IUserDAO_24162100 {

    private Users_24162100 mapRow(ResultSet rs) throws Exception {
        Users_24162100 u = new Users_24162100();
        u.setUserId(rs.getInt("userId"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setFullname(rs.getString("fullname"));
        u.setPassword(rs.getString("password"));
        u.setImages(rs.getString("images"));
        u.setPhone(rs.getString("phone"));
        u.setStatus(rs.getInt("status"));
        u.setCode(rs.getString("code"));
        u.setRoleId(rs.getInt("roleId"));
        int sellerid = rs.getInt("sellerid");
        u.setSellerid(rs.wasNull() ? null : sellerid);
        return u;
    }

    @Override
    public Users_24162100 findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Users_24162100 findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Users_24162100 findById(int userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Users_24162100 user) {
        String sql = "INSERT INTO Users (username, email, fullname, password, images, phone, status, code, roleId, sellerid) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getImages());
            ps.setString(6, user.getPhone());
            ps.setInt(7, user.getStatus());
            ps.setString(8, user.getCode());
            ps.setInt(9, user.getRoleId());
            if (user.getSellerid() == null) ps.setNull(10, java.sql.Types.INTEGER);
            else ps.setInt(10, user.getSellerid());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Users_24162100 user) {
        String sql = "UPDATE Users SET username=?, email=?, fullname=?, password=?, images=?, phone=?, status=?, code=?, roleId=?, sellerid=? "
                + "WHERE userId=?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getImages());
            ps.setString(6, user.getPhone());
            ps.setInt(7, user.getStatus());
            ps.setString(8, user.getCode());
            ps.setInt(9, user.getRoleId());
            if (user.getSellerid() == null) ps.setNull(10, java.sql.Types.INTEGER);
            else ps.setInt(10, user.getSellerid());
            ps.setInt(11, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int userId) {
        String sql = "DELETE FROM Users WHERE userId = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean activateByEmailAndCode(String email, String code) {
        String sql = "UPDATE Users SET status = 1, code = NULL WHERE email = ? AND code = ?";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, code);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Users_24162100> findAll(int offset, int pageSize) {
        List<Users_24162100> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY userId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = DBConnection_24162100.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
