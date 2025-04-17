package AS_API.service;

import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    public void release(Long userId) {
        System.out.println("User with ID " + userId + " has been released.");
    }

    public void suspend(Long userId) {
        System.out.println("User with ID " + userId + " has been suspended.");
    }
}
