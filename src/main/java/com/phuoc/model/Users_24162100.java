package com.phuoc.model;

/**
 * Model tuong ung bang Users.
 */
public class Users_24162100 {
    private int userId;
    private String username;
    private String email;
    private String fullname;
    private String password;
    private String images;
    private String phone;
    private int status;   // 0 = chua kich hoat, 1 = da kich hoat
    private String code;  // ma OTP
    private int roleId;
    private Integer sellerid;

    public Users_24162100() {
    }

    public Users_24162100(int userId, String username, String email, String fullname, String password,
                           String images, String phone, int status, String code, int roleId, Integer sellerid) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.images = images;
        this.phone = phone;
        this.status = status;
        this.code = code;
        this.roleId = roleId;
        this.sellerid = sellerid;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }

    public Integer getSellerid() { return sellerid; }
    public void setSellerid(Integer sellerid) { this.sellerid = sellerid; }

    public boolean isAdmin() { return roleId == 1; }
    public boolean isSeller() { return roleId == 3; }
}
