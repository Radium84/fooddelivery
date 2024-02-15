package ru.edu.sberbank.services;

import ru.edu.sberbank.entity.Auth;
import ru.edu.sberbank.entity.OurUser;

public interface AuthenticationService {
    OurUser signInAndReturnJwt(Auth signInRequest);
}
