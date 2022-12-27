package com.final_project2.command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    private static final Map<String, Command> commands;

    static {
        commands = new HashMap<>();

        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("editUser", new EditUserCommand());
        commands.put("updateUser", new UpdateUserCommand());
        commands.put("changeBlockStatus", new ChangeBlockStatusCommand());
        commands.put("showAllCars", new ShowAllCarsCommand());
        commands.put("showAllUsers", new ShowAllUsersCommand());
        commands.put("addNewCar", new AddNewCarCommand());
        commands.put("getNewCarImageUrl", new AddCarImageUrlCommand());
        commands.put("showCarCardPage", new ShowCardCarPageCommand());// показываем карточку по товару
        commands.put("getAllCarClass", new GetAllClassCommand());//берем все классы авто для меню выборки
        commands.put("getAllBrands", new GetAllBrandsCommand());//берем все бренды для меню выборки
        commands.put("getAllCarsByBrand", new GetAllCarsByBrandCommand());//берем все авто по бренду для отображения на странице
        commands.put("getAllCarsByClass", new GetAllCarsByClassCommand());//берем все авто по классу для отображения на странице
        commands.put("getAllCarsOrderByPriceAsc", new ShowAllCarsOrderByPriceAscCommand());//берем все авто с сортировкой по возрастанию цены
        commands.put("getAllCarsOrderByPriceDesc", new ShowAllCarsOrderByPriceDescCommand());//берем все авто с сортировкой по убыванию цены
        commands.put("getAllCarsOrderByBrandAsc", new ShowAllCarsOrderByBrandAscCommand());//берем все авто с сортировкой по возрастанию по бренду
        commands.put("getAllCarsOrderByBrandDesc", new ShowAllCarsOrderByBrandDescCommand());//берем все авто с сортировкой по убыванию по бренду
        commands.put("deleteCar", new DeleteCarCommand());//delete car
        commands.put("editCarImageUrl", new EditCarImageUrlCommand());//upload new image
        commands.put("editCar", new EditCarCommand());//upload new image
        commands.put("confirmOrder", new ConfirmTheOrderCommand());//confirm New Order
        commands.put("addNewOrder", new AddNewOrderCommand());//confirm New Order
        commands.put("showAllOrders", new ShowAllOrdersCommand());//show all Orders in main page
        commands.put("showCardOrderPage", new ShowCardOrderPageCommand());//show card order page
        commands.put("setOrderStatus", new SetNewOrderStatusCommand());//set new Order status by admin or manager
        commands.put("setInfoAboutDamage", new SetInfoAboutDamageCommand());//set info about Damage
        commands.put("setLocale", new SetLocaleCommand());//set locale
        commands.put("buttonAddNewCar", new ButtonAddNewCarCommand());//add new car button
        commands.put("showInfoAboutUs", new ShowInfoAboutUs());//show info about Us
        commands.put("showContactInfo", new ShowContactInfo());//show contact info
    }

    private CommandContainer() {
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
