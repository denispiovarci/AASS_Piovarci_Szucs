package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.model.User;
import SK.AASS.TELCO.app.repository.UserRepository;
import SK.AASS.TELCO.app.rest.request.UserCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
