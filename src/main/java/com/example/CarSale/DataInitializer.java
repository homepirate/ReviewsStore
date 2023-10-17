package com.example.CarSale;

import com.example.CarSale.Dtos.ModelDto;
import com.example.CarSale.Dtos.OfferDto;
import com.example.CarSale.Dtos.UserDto;
import com.example.CarSale.Dtos.UserRoleDto;
import com.example.CarSale.Models.Brand;
import com.example.CarSale.Models.Enums.Category;
import com.example.CarSale.Models.Enums.Engine;
import com.example.CarSale.Models.Enums.Role;
import com.example.CarSale.Models.Enums.Transmission;
import com.example.CarSale.Models.Model;
import com.example.CarSale.Models.UserRole;
import com.example.CarSale.Repositories.*;
import com.example.CarSale.Services.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;


@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelRepositiry modelRepositiry;
    @Autowired
    private ModelService modelService;

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferService offerService;

    @Override
    public void run(String... args) throws Exception {
//  generated role
        userRoleRepository.save(new UserRole(Role.USER));
        userRoleRepository.save(new UserRole(Role.ADMIN));

        List<UserRole> roles = userRoleRepository.findAll();
        System.out.println(roles.toString());
        List<UserRoleDto> roles_dto = userRoleService.getAll();
        System.out.println(roles_dto.toString());

//  generated users
        Faker faker = new Faker();
        Random random = new Random();

        for (int i=0; i<100; i++){
            UserDto user_dto = new UserDto();
            user_dto.setUsername(faker.name().username());
            user_dto.setPassword(faker.internet().password());
            user_dto.setFirstName(faker.name().firstName());
            user_dto.setLastName(faker.name().lastName());
            user_dto.setActive(random.nextBoolean());
            user_dto.setImageUrl(faker.internet().image());
            user_dto.setRole(roles_dto.get(random.nextInt(2)));

            userService.createUser(user_dto);
        }
        List<UserDto> admins = userService.findAllByRole("admin");
        List<UserDto> simple_users = userService.findAllByRole("user");
        List<UserDto> list_users = userService.getAll();
        System.out.println("-------------------------------------------------");
        System.out.println("ADMIN: " +  admins.size()  + ' ' + admins.get(0).getRole().getRole());
        System.out.println("USERS: " +  simple_users.size()  + ' ' + simple_users.get(0).getRole().getRole());

//  generated car brand and models
        String[] carBrands = {"Toyota", "BMW", "Mercedes-Benz", "Ford", "Volkswagen", "Audi"};
        for (String brand : carBrands) {
            Brand br = new Brand(brand);
            if (brand.equals("BMW")){
                Model model1 = new Model("3 Series", Category.CAR, "https://example.com/bmw3series.jpg", 2018, 2022, br);
                brandRepository.save(br);
                modelRepositiry.save(model1);
            } else if (brand.equals("Audi")) {
                Model model3 = new Model("A5", Category.CAR, "https://example.com/audia5.jpg", 2016, 2022, br);
                brandRepository.save(br);
                modelRepositiry.save(model3);
            } else if (brand.equals("Mercedes-Benz")) {
                Model model2 = new Model("Actros", Category.TRUCK, "https://example.com/actros.jpg", 2022, 2023, br);
                brandRepository.save(br);
                modelRepositiry.save(model2);
            } else {
                brandRepository.save(br);
            }
        }
        List<ModelDto> models =modelService.getAll();
        List<ModelDto> brand_models = brandService.getBrandModels("BMW");
        System.out.println("ALL Brand MODELS: " + brand_models.toString());

//  generated offers
        for (int i=0; i<100; i++){
            OfferDto offer = new OfferDto();
            offer.setDescription(faker.lorem().sentence());
            offer.setEngine(Engine.values()[faker.random().nextInt(Engine.values().length)]);
            offer.setImageUrl(faker.internet().image());
            offer.setMileage(faker.random().nextInt(10000, 100000));
            offer.setPrice(faker.random().nextInt(1000, 10000));
            offer.setImageUrl(faker.internet().image());

            offer.setTransmission(Transmission.values()[faker.random().nextInt(Transmission.values().length)]);
            offer.setYear(faker.random().nextInt(2016, 2020));
            offer.setSeller(list_users.get(random.nextInt(list_users.size())));
            offer.setModel(models.get(random.nextInt(models.size())));
            offerService.createOffer(offer);
        }

        List<OfferDto> some_user_offers = userService.getUserOffers(list_users.get(0).getId());
        System.out.println("User OFFERS: " + some_user_offers.size());
    }
}
