package ru.obakumen.startup.controllers.users;

import net.minidev.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.obakumen.startup.models.User;
import ru.obakumen.startup.security.CustomUserDetailsService;
import ru.obakumen.startup.security.jwt.JwtProvider;
import ru.obakumen.startup.services.UsersService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public List<User> getAllRoleUser(ServletRequest servletRequest) {
        return usersService.findRoleUserAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getRoleUserByUsername(@PathVariable String username) {
        User finded_user =  usersService.findUserByUsernameAndRoleUser(username);
        if (finded_user != null)
            return new ResponseEntity<>(finded_user, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserByUsername(@PathVariable String username, @RequestBody User new_user) {
        User finded_user = usersService.findUserByUsernameAndRoleUser(username);
        if (finded_user == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else {
            BeanUtils.copyProperties(new_user, finded_user,"id");
            return new ResponseEntity<>(usersService.createNewUser(finded_user), HttpStatus.OK);
        }
    }
}
