package Client.Models;
import java.sql.*;

public record User(
        String username,
        String name,
        String address,
        Date dob,
        char gender,
        String email,
        boolean status,
        String password,
        Timestamp createdAt,
        boolean isLocked
) {}
