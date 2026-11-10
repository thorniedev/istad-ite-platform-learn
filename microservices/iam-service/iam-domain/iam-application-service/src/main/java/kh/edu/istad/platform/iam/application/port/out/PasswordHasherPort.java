package kh.edu.istad.platform.iam.application.port.out;

public interface PasswordHasherPort {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
