package com.example.CarSale;

import com.example.CarSale.Dtos.*;
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

import java.util.Arrays;
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
            brandRepository.save(br);

            if (brand.equals("BMW")) {
                Model model1 = new Model("3 Series", Category.CAR, "https://example.com/bmw3series.jpg", 2018, 2022, br);
                Model model2 = new Model("X5", Category.CAR, "https://example.com/bmwx5.jpg", 2019, 2022, br);
                Model model3 = new Model("7 Series", Category.CAR, "https://example.com/bmw7series.jpg", 2017, 2022, br);
                Model model4 = new Model("i8", Category.CAR, "https://example.com/bmwi8.jpg", 2014, 2021, br);
                Model model5 = new Model("X3", Category.CAR, "https://example.com/bmwx3.jpg", 2010, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model1, model2, model3, model4, model5));
            } else if (brand.equals("Audi")) {
                Model model6 = new Model("A4", Category.CAR, "https://example.com/audia4.jpg", 2015, 2022, br);
                Model model7 = new Model("Q5", Category.CAR, "https://example.com/audiq5.jpg", 2016, 2022, br);
                Model model8 = new Model("A7", Category.CAR, "https://example.com/audia7.jpg", 2018, 2022, br);
                Model model9 = new Model("Q3", Category.CAR, "https://example.com/audiq3.jpg", 2011, 2022, br);
                Model model10 = new Model("R8", Category.CAR, "https://example.com/audir8.jpg", 2010, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model6, model7, model8, model9, model10));
            } else if (brand.equals("Mercedes-Benz")) {
                Model model11 = new Model("C-Class", Category.CAR, "https://example.com/mercedesc.jpg", 2014, 2022, br);
                Model model12 = new Model("E-Class", Category.CAR, "https://example.com/mercedese.jpg", 2016, 2022, br);
                Model model13 = new Model("S-Class", Category.CAR, "https://example.com/mercedess.jpg", 2017, 2022, br);
                Model model14 = new Model("GLE", Category.CAR, "https://example.com/mercedesgle.jpg", 2019, 2022, br);
                Model model15 = new Model("GLA", Category.CAR, "https://example.com/mercedesgla.jpg", 2015, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model11, model12, model13, model14, model15));
            } else if (brand.equals("Toyota")) {
                Model model16 = new Model("Camry", Category.CAR, "https://example.com/toyotacamry.jpg", 2017, 2022, br);
                Model model17 = new Model("Corolla", Category.CAR, "https://example.com/toyotacorolla.jpg", 2018, 2022, br);
                Model model18 = new Model("RAV4", Category.CAR, "https://example.com/toyotarav4.jpg", 2016, 2022, br);
                Model model19 = new Model("Highlander", Category.CAR, "https://example.com/toyotahighlander.jpg", 2019, 2022, br);
                Model model20 = new Model("Tacoma", Category.TRUCK, "https://example.com/toyotatacoma.jpg", 2022, 2023, br);
                modelRepositiry.saveAll(Arrays.asList(model16, model17, model18, model19, model20));
            } else if (brand.equals("Ford")) {
                Model model21 = new Model("Mustang", Category.CAR, "https://example.com/fordmustang.jpg", 2015, 2022, br);
                Model model22 = new Model("F-150", Category.TRUCK, "https://example.com/fordf150.jpg", 2018, 2022, br);
                Model model23 = new Model("Explorer", Category.CAR, "https://example.com/fordexplorer.jpg", 2019, 2022, br);
                Model model24 = new Model("Edge", Category.CAR, "https://example.com/fordedge.jpg", 2016, 2022, br);
                Model model25 = new Model("Escape", Category.CAR, "https://example.com/fordescape.jpg", 2017, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model21, model22, model23, model24, model25));
            } else if (brand.equals("Volkswagen")) {
                Model model26 = new Model("Golf", Category.CAR, "https://example.com/vwgolf.jpg", 2014, 2022, br);
                Model model27 = new Model("Passat", Category.CAR, "https://example.com/vwpassat.jpg", 2016, 2022, br);
                Model model28 = new Model("Tiguan", Category.CAR, "https://example.co m/vwtiguan.jpg", 2017, 2022, br);
                Model model29 = new Model("Polo", Category.CAR, "https://example.com/vwpolo.jpg", 2018, 2022, br);
                Model model30 = new Model("Arteon", Category.CAR, "https://example.com/vwarteon.jpg", 2019, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model26, model27, model28, model29, model30));
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


        System.out.println(offerService.getOfferByEngine("GASOLINE"));
        System.out.println(modelService.getModelByCategory("car"));

        System.out.println(offerService.getOfferByTransmission("manual"));


        List<AllOffersWithBrandDto> offers_all_info = offerService.getAllOffersInfo();
        System.out.println(offers_all_info.size());
        for (AllOffersWithBrandDto offer : offers_all_info){
            System.out.println(offer);
        }


//        List<UserDto> userDtos = userService.getAll();
//
//        for (UserDto usr: userDtos){
//            userService.deleteUser(usr.getId());
//        }
    }
}
