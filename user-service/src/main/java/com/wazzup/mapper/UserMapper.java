package com.wazzup.mapper;

import com.wazzup.dto.CreateUserDTO;
import com.wazzup.dto.UserInformationDTO;
import com.wazzup.dto.UserInformationWithIdDTO;
import com.wazzup.entity.User;
import com.wazzup.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserDTO dto);
    @Mapping(source = "birthdate", target = "birthDate", dateFormat = "dd/MM/yyyy")
    @Mapping(ignore = true, target = "authorities")
    UserInformationDTO toUserInformation(User user);

    @Mapping(source = "roles",target = "authorities",qualifiedByName = "mapAuthorities")
    UserInformationWithIdDTO toUserInformationWithId(User user);

    @Named("mapAuthorities")
    default List<String> mapAuthorities(List<UserRole> userRole){
        List<String> authorities = new ArrayList<>();

        authorities = userRole.stream()
                .map(x -> x.getRoleName())
                .collect(Collectors.toList());
        return authorities;
    }
}
