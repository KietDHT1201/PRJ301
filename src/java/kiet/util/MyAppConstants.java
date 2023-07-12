/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiet.util;

/**
 *
 * @author TuanKiet
 */
public class MyAppConstants {
    public class DispatchFeatures {
        public static final String LOGIN_PAGE = "";
        public static final String LOGIN_CONTROLLER = "loginController";
        public static final String SEARCH_CONTROLLER = "searchController";
        public static final String DELETE_CONTROLLER = "deleteController";
        public static final String UPDATE_CONTROLLER = "updateController";
        public static final String LOGOUT_CONTROLLER = "logoutController";
        public static final String ADD_ITEM_TO_CART_CONTROLLER = "addItemToCartController";
        public static final String VIEW_CART_PAGE = "viewCartPage";
        public static final String REMOVE_ITEM_FROM_CART_CONTROLLER = "removeItemFromCartController";
        public static final String CREATE_NEW_ACCOUNT = "createNewAccount";
        public static final String START_UP_CONTROLLER = "startUpController";
    }
    
    public class LoginFeatures {
        public static final String SEARCH_ACTION = "searchResultPage";
        public static final String INVALID_PAGE = "invalidPage";
    }
    
    public class AddItemToCartFeatures {
        public static final String SHOPPING_PAGE = "shoppingPage";
    }
    
    public class CreateNewAccountFeatures {
        public static final String CREATE_NEW_ACCOUNT_PAGE = "createNewAccountPage";
        public static final String LOGIN_PAGE = "";
    }
    
    public class DeleteFeatures {
        public static final String ERROR_PAGE = "errorPage";
    }
    
    public class LogoutFeatures {
        public static final String LOGIN_PAGE = "";
    }
    
    public class SearchFeatures {
        public static final String SEARCH_PAGE = "searchPage";
        public static final String SEARCH_RESULT_PAGE = "searchResultPage";
    }
}
