package com.justahmed99.authapp.provider;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserinfoRepository extends R2dbcRepository<UserInfoEntity, String> {

}
