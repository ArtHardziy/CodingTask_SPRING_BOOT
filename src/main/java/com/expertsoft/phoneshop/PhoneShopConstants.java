package com.expertsoft.phoneshop;

public class PhoneShopConstants {

    public static final String ROOT_PATH = "/";
    public static final String PHONES_PATH = "/phones";
    public static final String ADMIN_PATH = "/admin";
    public static final String LOGIN_PATH = "/login";

    public static final String PHONE = "phone";
    public static final String PHONE_DETAILS_PAGE = "phoneDetailsPage";
    public static final String PHONE_ID_ATTRIBUTE = "phoneId";
    public static final String PHONE_ID_REQUEST_PATH = "/{phoneId}";

    public static final String LOCATION_HEADER = "Location";
    public static final String ROOT_CONTEXT_PATH = "/";

    public static final String LOGIN_PAGE = "loginPage";
    public static final String ADMIN_PANEL_PAGE = "admin/adminPanelPage";

    public static final String PHONE_LIST_PAGE = "phoneListPage";
    public static final String PHONES_PAGE = "phonesPage";
    public static final String SEARCH_QUERY = "searchQuery";
    public static final String FROM_PRICE = "fromPrice";
    public static final String TO_PRICE = "toPrice";
    public static final String SEARCH_FORM_MODEL = "searchFormModel";
    public static final String SEARCH_FORM_MODEL_ERROR = "searchFormModelError";
    public static final String REGISTERED_USERS = "registeredUsers";
    public static final String PLP_PAGING_NUMBERS = "plpPagingNumbers";

    public static final String ID_GITHUB_USER_ATTR = "id";
    public static final String LOGIN_GITHUB_USER_ATTR = "login";
    public static final String EMAIL_GITHUB_USER_ATTR = "email";
    public static final String AVATAR_URL_GITHUB_USER_ATTR = "avatar_url";

    public static final String ID_GOOGLE_USER_ATTR = "sub";
    public static final String NAME_GOOGLE_USER_ATTR = "name";
    public static final String EMAIL_GOOGLE_USER_ATTR = "email";
    public static final String IMAGE_URL_GOOGLE_ATTR = "picture";
    public static final String SEARCH_FORM_PRICE_VALIDATION_EXC_MSG = "Quantity must be a numeric value";


    private PhoneShopConstants() {
        // instance not allowed
    }
}
