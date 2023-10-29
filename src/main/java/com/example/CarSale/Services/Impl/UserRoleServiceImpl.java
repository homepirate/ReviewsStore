package com.example.CarSale.Services.Impl;

import com.example.CarSale.Services.Dtos.UserRoleDto;
import com.example.CarSale.constants.Enums.Role;
import com.example.CarSale.Models.UserRole;
import com.example.CarSale.Repositories.UserRoleRepository;
import com.example.CarSale.Services.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {


    private UserRoleRepository userRoleRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserRoleServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
 @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRoleDto> getAll() {
        return userRoleRepository.findAll()
                .stream().map((userRole) -> modelMapper.map(userRole, UserRoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserRoleDto createUserRole(String roleName) {
        try {
            Role role = Role.valueOf(roleName.toUpperCase());
            UserRole userRole_model = modelMapper.map(new UserRole(role), UserRole.class);
            return modelMapper.map(userRoleRepository.save(userRole_model), UserRoleDto.class);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public void deleteUserRole(UUID userRoleId) {
        userRoleRepository.deleteById(userRoleId);
    }
}
