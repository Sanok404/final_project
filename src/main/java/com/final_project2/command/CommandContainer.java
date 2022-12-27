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
        commands.put("showCarCardPage", new ShowCardCarPageCommand());
        commands.put("getAllCarClass", new GetAllClassCommand());
        commands.put("getAllBrands", new GetAllBrandsCommand());
        commands.put("getAllCarsByBrand", new GetAllCarsByBrandCommand());
        commands.put("getAllCarsByClass", new GetAllCarsByClassCommand());
        commands.put("getAllCarsOrderByPriceAsc", new ShowAllCarsOrderByPriceAscCommand());
        commands.put("getAllCarsOrderByPriceDesc", new ShowAllCarsOrderByPriceDescCommand());
        commands.put("getAllCarsOrderByBrandAsc", new ShowAllCarsOrderByBrandAscCommand());
        commands.put("getAllCarsOrderByBrandDesc", new ShowAllCarsOrderByBrandDescCommand());
        commands.put("deleteCar", new DeleteCarCommand());
        commands.put("editCarImageUrl", new EditCarImageUrlCommand());
        commands.put("editCar", new EditCarCommand());
        commands.put("confirmOrder", new ConfirmTheOrderCommand());
        commands.put("addNewOrder", new AddNewOrderCommand());
        commands.put("showAllOrders", new ShowAllOrdersCommand());
        commands.put("showCardOrderPage", new ShowCardOrderPageCommand());
        commands.put("setOrderStatus", new SetNewOrderStatusCommand());
        commands.put("setInfoAboutDamage", new SetInfoAboutDamageCommand());
        commands.put("setLocale", new SetLocaleCommand());
        commands.put("buttonAddNewCar", new ButtonAddNewCarCommand());
        commands.put("showInfoAboutUs", new ShowInfoAboutUs());
        commands.put("showContactInfo", new ShowContactInfo());
    }

    private CommandContainer() {
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
