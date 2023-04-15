package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.model.User;
import SK.AASS.TELCO.app.repository.UserRepository;
import SK.AASS.TELCO.app.rest.request.LoginRequest;
import SK.AASS.TELCO.app.rest.request.UserCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void create(UserCreateRequest request){
        log.info("UserServiceImpl.create({})", request);

        User user = User.builder()
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .build();

        userRepository.save(user);
    }

    @Override
    public void update(UserCreateRequest request){
        log.info("UserServiceImpl.update({})", request);

        User user = userRepository.findByEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());

        userRepository.save(user);
    }

    @Override
    public void delete(Long id){
        log.info("UserServiceImpl.delete({})", id);

        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers(){
        log.info("UserServiceImpl.getAllUsers()");

        return userRepository.findAll();
    }

    @Override
    public User login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail());

        if(request.getPassword().equals(user.getPassword())){
            log.info("User with email {} was successfully logged in.", request.getEmail());
            return user;
        }
        else{
            log.info("User with email {} was not authorized.", request.getEmail());
            return null;
        }
    }
}
