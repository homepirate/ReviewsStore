package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.UserRoleDto;
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

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<UserRoleDto> getAll() {
        return userRoleRepository.findAll()
                .stream().map((userRole) -> modelMapper.map(userRole, UserRoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserRoleDto createUserRole(UserRoleDto userRoleDto) {
        UserRole userRole_model = modelMapper.map(userRoleDto, UserRole.class);
        return modelMapper.map(userRoleRepository.save(userRole_model), UserRoleDto.class);
    }

    @Override
    public void deleteUserRole(UUID userRoleId) {
        userRoleRepository.deleteById(userRoleId);
    }
}
