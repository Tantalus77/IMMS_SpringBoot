package imms.model;

// 用于邮箱登录和注册
public class Email {
    private String email;

    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
