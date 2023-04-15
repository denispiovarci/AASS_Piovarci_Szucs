package SK.AASS.TELCO.app.rest;

import SK.AASS.TELCO.app.model.User;
import SK.AASS.TELCO.app.rest.request.LoginRequest;
import SK.AASS.TELCO.app.rest.request.UserCreateRequest;
import SK.AASS.TELCO.app.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telco/api/v1/user")
@AllArgsConstructor
@Slf4j
public class UserRest {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserCreateRequest request){
        log.info("UserRest.createUser({})", request);

        userService.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserCreateRequest request){
        log.info("UserRest.updateUser({})", request);

        userService.update(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        log.info("UserRest.deleteUser({})", id);

        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("UserRest.getAllUsers()");

        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequest request){
        log.info("UserRest.login({})", request);

        return ResponseEntity.ok().body(userService.login(request));
    }
}
