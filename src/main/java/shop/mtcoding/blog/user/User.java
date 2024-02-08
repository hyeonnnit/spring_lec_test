package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
