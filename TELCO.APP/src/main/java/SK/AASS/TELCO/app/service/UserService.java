package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.model.User;
import SK.AASS.TELCO.app.rest.request.LoginRequest;
import SK.AASS.TELCO.app.rest.request.UserCreateRequest;

import java.util.List;

/**
 * User service interface
 */
public interface UserService {

    /**
     * Creates an user - used for registering the users to the system
     *
     * @param request
     */
    void create(UserCreateRequest request);

    /**
     * Updates the user attributes
     *
     * @param request
     */
    void update(UserCreateRequest request);

    /**
     * Deletes the user from DB
     *
     */
    void delete(Long id);

    /**
     * Gets list of all users
     *
     */
    List<User> getAllUsers();

    /**
     * User authorization
     *
     */
    User login(LoginRequest request);
}
