package th.mfu.boot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    // ✅ Autowire the UserRepository
    @Autowired
    public UserRepository repo;

    // ✅ Register user
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Check if a user with the same username already exists
        if (repo.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT); // 409
        }

        // Save the new user
        repo.save(user);

        // Return 201 CREATED
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    // ✅ List all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> list() {
        List<User> users = repo.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK); // 200
    }

    // ✅ Delete user by ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND); // 404
        }

        repo.deleteById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK); // 200
    }
}
