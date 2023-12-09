package com.example.CarSale;

import com.example.CarSale.Services.Dtos.*;
import com.example.CarSale.Models.Brand;
import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.constants.Enums.Category;
import com.example.CarSale.constants.Enums.Engine;
import com.example.CarSale.constants.Enums.Role;
import com.example.CarSale.constants.Enums.Transmission;
import com.example.CarSale.Models.Model;
import com.example.CarSale.Models.UserRole;
import com.example.CarSale.Repositories.*;
import com.example.CarSale.Services.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Component
public class DataInitializer implements CommandLineRunner {
    private UserRepository userRepository;
    private UserService userService;

    private UserRoleRepository userRoleRepository;
    private UserRoleService userRoleService;
    private BrandRepository brandRepository;
    private BrandService brandService;

    private ModelRepositiry modelRepositiry;
    private ModelService modelService;

    private OfferRepository offerRepository;
    private OfferService offerService;

    private PasswordEncoder passwordEncoder;


    public DataInitializer(UserRepository userRepository, UserService userService, UserRoleRepository userRoleRepository, UserRoleService userRoleService, BrandRepository brandRepository, BrandService brandService, ModelRepositiry modelRepositiry, ModelService modelService, OfferRepository offerRepository, OfferService offerService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userRoleRepository = userRoleRepository;
        this.userRoleService = userRoleService;
        this.brandRepository = brandRepository;
        this.brandService = brandService;
        this.modelRepositiry = modelRepositiry;
        this.modelService = modelService;
        this.offerRepository = offerRepository;
        this.offerService = offerService;
        this.passwordEncoder = passwordEncoder;
    }

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
            user_dto.setPassword(passwordEncoder.encode(faker.internet().password()));
            user_dto.setFirstName(faker.name().firstName());
            user_dto.setLastName(faker.name().lastName());
            user_dto.setActive(Boolean.TRUE);
            user_dto.setImageUrl("/img/149071.png");
            user_dto.setRole(roles_dto.get(random.nextInt(2)));

            userService.createUser(user_dto);
        }



        UserDto user_dto = new UserDto();
        user_dto.setUsername("evgeniy.loginov");
        user_dto.setPassword(passwordEncoder.encode("1234567890"));
        user_dto.setFirstName("Evgeniy");
        user_dto.setLastName("Loginov");
        user_dto.setActive(Boolean.TRUE);
        user_dto.setImageUrl("/img/149071.png");
        user_dto.setRole(roles_dto.get(0));
        userService.createUser(user_dto);


        user_dto = new UserDto();
        user_dto.setUsername("admin");
        user_dto.setPassword(passwordEncoder.encode("1234567890"));
        user_dto.setFirstName("Admin");
        user_dto.setLastName("Admin");
        user_dto.setActive(Boolean.TRUE);
        user_dto.setImageUrl("/img/149071.png");
        user_dto.setRole(roles_dto.get(1));
        userService.createUser(user_dto);


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
                Model model1 = new Model("3 Series", Category.CAR, "https://avatars.mds.yandex.net/get-verba/1540742/2a00000180d7fa047e2e09e71e665301c310/cattouch", 2018, 2022, br);
                Model model2 = new Model("X5", Category.CAR, "https://images.drive.ru/i/0/5e4243e7ec05c4ce55000022.jpg", 2019, 2022, br);
                Model model3 = new Model("7 Series", Category.CAR, "https://www.bmw.ru/content/dam/bmw/common/all-models/7-series/sedan/2022/highlights/bmw-7-series-sedan-cp-design-exterior-desktop.jpg", 2017, 2022, br);
                Model model4 = new Model("i8", Category.CAR, "https://s0.rbk.ru/v6_top_pics/ampresize/media/img/1/53/754788778207531.jpeg", 2014, 2021, br);
                Model model5 = new Model("X3", Category.CAR, "https://kia.irbis-auto.ru/uploads/auto_catalog/910x/photo/1507/4174447/orig/cbc1ea6e458abd9627298a3bd96ef278.jpg", 2010, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model1, model2, model3, model4, model5));
            } else if (brand.equals("Audi")) {
                Model model6 = new Model("A4", Category.CAR, "https://avatars.mds.yandex.net/get-verba/1535139/2a0000017f5901ab3c6c4f2b477b5bdf118e/thumb_m_2x", 2015, 2022, br);
                Model model7 = new Model("Q5", Category.CAR, "https://upload.wikimedia.org/wikipedia/commons/2/28/Audi_Q5_2.0_TDI_quattro_S-line_%28Facelift%29_%E2%80%93_Frontansicht%2C_23._September_2012%2C_Hilden.jpg", 2016, 2022, br);
                Model model8 = new Model("A7", Category.CAR, "https://avatars.mds.yandex.net/get-vertis-journal/3934100/2021-04-19-a282537dcc504d2f9e97a87be4860bad.jpg_1622737375105/orig", 2018, 2022, br);
                Model model9 = new Model("Q3", Category.CAR, "https://sales-audi.ru/upload/resize_cache/iblock/0f0/900_600_2/339y3tjdpv65yn5x9j7m00yug3uk0pn0.jpg", 2011, 2022, br);
                Model model10 = new Model("R8", Category.CAR, "https://autoreview.ru/images/Article/1747/Article_174764_860_575.jpg", 2010, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model6, model7, model8, model9, model10));
            } else if (brand.equals("Mercedes-Benz")) {
                Model model11 = new Model("C-Class", Category.CAR, "https://rg.ru/uploads/images/204/73/77/20C0673_083.jpg", 2014, 2022, br);
                Model model12 = new Model("E-Class", Category.CAR, "https://avatars.mds.yandex.net/get-verba/997355/2a00000176b7d3f6a4f92df665019bd8cd6f/cattouchret", 2016, 2022, br);
                Model model13 = new Model("S-Class", Category.CAR, "https://s.auto.drom.ru/i24257/c/photos/fullsize/mercedes-benz/s-class/mercedes-benz_s-class_1007768.jpg", 2017, 2022, br);
                Model model14 = new Model("GLE", Category.CAR, "https://motor.ru/imgs/2020/02/19/05/3785463/b8dfa1c58fc861c111a9326dc60fe8cd7a7fb84b.jpg", 2019, 2022, br);
                Model model15 = new Model("GLA", Category.CAR, "https://img.autoabc.lv/Mercedes-GLA/Mercedes-GLA_2017_Apvidus_2351841843.jpg", 2015, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model11, model12, model13, model14, model15));
            } else if (brand.equals("Toyota")) {
                Model model16 = new Model("Camry", Category.CAR, "/img/28652_medium.jpg", 2017, 2022, br);
                Model model17 = new Model("Corolla", Category.CAR, "/img/500x_toyota_corolla_g999.jpg", 2018, 2022, br);
                Model model18 = new Model("RAV4", Category.CAR, "/img/28657_medium.jpg", 2016, 2022, br);
                Model model19 = new Model("Highlander", Category.CAR, "/img/28665_medium.jpg", 2019, 2022, br);
                Model model20 = new Model("Tacoma", Category.TRUCK, "/img/toyota-tacoma-front1-mini.jpg", 2022, 2023, br);
                modelRepositiry.saveAll(Arrays.asList(model16, model17, model18, model19, model20));
            } else if (brand.equals("Ford")) {
                Model model21 = new Model("Mustang", Category.CAR, "https://www.gallery-aaldering.com/wp-content/uploads/gallery/27869472/27869472-60.jpg", 2015, 2022, br);
                Model model22 = new Model("F-150", Category.TRUCK, "https://avatars.mds.yandex.net/get-verba/1030388/2a000001814870534d72b1c1e8b011338365/cattouchret", 2018, 2022, br);
                Model model23 = new Model("Explorer", Category.CAR, "https://autopragmat.ru/upload/delight.webpconverter/upload/iblock/fe7/n1cgecm0cfzy6f41dshweodk6jilsoeu.jpg.webp?1693040709143612", 2019, 2022, br);
                Model model24 = new Model("Edge", Category.CAR, "https://upload.wikimedia.org/wikipedia/commons/8/8d/2019_Ford_Edge_SEL_EcoBoost_AWD_2.0L_front_4.6.19.jpg", 2016, 2022, br);
                Model model25 = new Model("Escape", Category.CAR, "https://im.kommersant.ru/Issues.photo/AUTO_News/2022/10/26/KMO_152985_09646_1_t218_103619.jpg", 2017, 2022, br);
                modelRepositiry.saveAll(Arrays.asList(model21, model22, model23, model24, model25));
            } else if (brand.equals("Volkswagen")) {
                Model model26 = new Model("Golf", Category.CAR, "https://www.ixbt.com/img/n1/news/2023/3/2/2023-vw-golf-gti-40th-anniversary-edition_large.png", 2014, 2022, br);
                Model model27 = new Model("Passat", Category.CAR, "https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/VW_Passat_B8_Limousine_2.0_TDI_Highline.JPG/305px-VW_Passat_B8_Limousine_2.0_TDI_Highline.JPG", 2016, 2022, br);
                Model model28 = new Model("Tiguan", Category.CAR, "https://avatars.mds.yandex.net/get-vertis-journal/4220003/2021-05-12-4f0c35c2a7bc4275ac2c6d683ee99d9c.jpg_1622737450830/orig", 2017, 2022, br);
                Model model29 = new Model("Polo", Category.CAR, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/Volkswagen_Polo_Sedan_2.JPG/800px-Volkswagen_Polo_Sedan_2.JPG", 2018, 2022, br);
                Model model30 = new Model("Arteon", Category.CAR, "https://avatars.mds.yandex.net/get-vertis-journal/4466156/2020-06-24-e849499623a0447eab5e42ed0c9032bc.jpg_1622736390876/orig", 2019, 2022, br);
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
            offer.setImageUrl("/img/537234-risunok-mashina-4.jpg");
            offer.setMileage(faker.random().nextInt(10000, 100000));
            offer.setPrice(faker.random().nextInt(1000, 10000));

            offer.setTransmission(Transmission.values()[faker.random().nextInt(Transmission.values().length)]);
            offer.setYear(faker.random().nextInt(2016, 2020));
            offer.setSeller(list_users.get(random.nextInt(list_users.size())));
            offer.setModel(models.get(random.nextInt(models.size())));
            offerService.createOffer(offer);
        }

//        List<OfferDto> some_user_offers = userService.getUserOffers(list_users.get(0).getId());
//        System.out.println("User OFFERS: " + some_user_offers.size());

//
//        System.out.println(offerService.getOfferByEngine("GASOLINE"));
//        System.out.println(modelService.getModelByCategory("car"));
//
//        System.out.println(offerService.getOfferByTransmission("manual"));


//        List<AllOfferWithBrandView> offers_all_info = offerService.getAllOffersInfo();
//        System.out.println(offers_all_info.size());
//        for (AllOfferWithBrandView offer : offers_all_info){
//            System.out.println(offer);
//        }

        UserDto user = (UserDto) userService.changeImgUrl(list_users.get(0).getId(), faker.internet().image());
        System.out.println(list_users.get(0).getImageUrl() + " changed: " + user);


        List<OfferDto> offers = offerService.getAll();
        Map<String, LocalDateTime> offerCMInfo1 = offerService.getCreatedandModifiedInfo(offers.get(1).getId());

//        offerService.changeImgUrl(offers.get(0).getId(), faker.internet().image());
        Map<String, LocalDateTime> offerCMInfo0 = offerService.getCreatedandModifiedInfo(offers.get(0).getId());
        System.out.println(offerCMInfo1 + " " + offerCMInfo0);

//        List<UserDto> userDtos = userService.getAll();
//
//        for (UserDto usr: userDtos){
//            userService.deleteUser(usr.getId());
//        }

//        List<ModelDto> modelDtos = modelService.getAll();
//
//        for (ModelDto mdl: modelDtos){
//            modelService.deleteModel(mdl.getId());
//        }
        System.out.println(offerRepository.findAll().get(0));
        System.out.println(offerService.getAll().get(0));
    }
}
