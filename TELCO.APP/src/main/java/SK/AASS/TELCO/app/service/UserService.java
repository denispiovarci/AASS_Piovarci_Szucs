package SK.AASS.TELCO.app.service;

import SK.AASS.TELCO.app.rest.request.UserCreateRequest;

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

//    void delete();
}
