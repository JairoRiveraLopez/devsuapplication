package com.application.jrl_technical_test.Utils;

public class MessagesUtil {

    public static final String CLIENT_PERSIST_SUCCESS = "The client has been successfully created.";

    public static final String CLIENT_PERSIST_FAIL = "The client has not been created because some of the mandatory data is missing.";

    public static final String CLIENT_UPDATE_SUCCESS = "The client has been successfully updated.";

    public static final String CLIENT_UPDATE_FAIL_NOT_FOUND = "The client has not been updated because it hasn't been created.";

    public static final String CLIENT_UPDATE_FAIL_MISSING_DATA = "The client has not been updated because some of the mandatory data is missing.";
    public static final String CLIENT_EDIT_SUCCESS = "The client has been successfully edited.";
    public static final String CLIENT_EDIT_FAIL = "The client cannot be edited because the information provided is not complete.";
    public static final String CLIENT_EDIT_FAIL_NOT_FOUND = "The client has not been edited because it hasn't been created.";

    public static final String CLIENT_EDIT_FAIL_WRONG_DATATYPE = "The client has not been edited because the attribute you want to edit doesn't exists or is not allowed.";

    public static final String CLIENT_REMOVE_SUCCESS = "The client has been successfully removed.";

    public static final String CLIENT_REMOVE_FAIL = "The client has not been eliminated because it wasn't found.";

    public static final String CLIENT_FIND_IDENTIFICATION_NOT_FOUND = "The client you requested was not found.";

    public static final String ACCOUNT_PERSIST_SUCCESS = "The account has been successfully created.";

    public static final String ACCOUNT_PERSIST_FAIL = "The account has not been created because some of the mandatory data is missing.";

    public static final String ACCOUNT_PERSIST_WRONG_CLIENT = "The account has not been created because the client specified doesn't exists.";
    public static final String ACCOUNT_UPDATE_SUCCESS = "The account has been successfully updated.";

    public static final String ACCOUNT_UPDATE_FAIL_NOT_FOUND = "The account has not been updated because it hasn't been created.";
    public static final String ACCOUNT_UPDATE_FAIL_MISSING_DATA = "The account has not been updated because some of the mandatory data is missing.";
    public static final String ACCOUNT_FIND_IDENTIFICATION_NOT_FOUND = "The account you requested was not found.";
    public static final String ACCOUNT_EDIT_FAIL = "The account cannot be edited because the information provided is not complete.";
    public static final String ACCOUNT_EDIT_SUCCESS = "The account has been successfully edited.";
    public static final String ACCOUNT_EDIT_FAIL_WRONG_DATATYPE = "The account has not been edited because the attribute you want to edit doesn't exists or is not allowed.";
    public static final String ACCOUNT_EDIT_FAIL_NOT_FOUND = "The account has not been edited because it hasn't been created.";
    public static final String ACCOUNT_REMOVE_SUCCESS = "The account has been successfully removed.";
    public static final String ACCOUNT_REMOVE_FAIL_REMOVING_MOVEMENTS = "The account has not been eliminated because there is an error with it's movements";
    public static final String ACCOUNT_REMOVE_FAIL = "The account has not been eliminated because it wasn't found.";

    public static final String MOVEMENT_PERSIST_FAIL = "The movement has not been created because some of the mandatory data is missing.";
    public static final String MOVEMENT_PERSIST_FAIL_MISSING_DATA = "The movement has not been created because the provided data is wrong or inconsistent.";
    public static final String MOVEMENT_PERSIST_FAIL_INCORRECT_VALUE = "The movement has not been created because the amount you want to transact is more that daily limit or more than your account balance.";
    public static final String MOVEMENT_FAIL_INTERNAL = "Something happened with the transaction. Don't worry, your account hasn't changed. Try again later.";
    public static final String MOVEMENT_PERSIST_SUCCESS = "The movement has been successfully executed.";

    public static final String MOVEMENT_UPDATE_FAIL_NOT_FOUND = "The movement has not been updated because it hasn't been created.";
    public static final String MOVEMENT_UPDATE_SUCCESS = "The movement has been successfully updated.";
    public static final String MOVEMENT_UPDATE_FAIL_INCORRECT_VALUE = "The movement has not been update because the amount you want to transact is more that daily limit or more than your account balance.";

    public static final String MOVEMENT_UPDATE_FAIL_MISSING_DATA = "The movement has not been updated because the provided data is wrong or inconsistent.";
    public static final String MOVEMENT_UPDATE_FAIL = "The movement has not been updated because some of the mandatory data is missing.";
    public static final String MOVEMENT_EDIT_FAIL = "The movement cannot be edited because the information provided is not complete.";
    public static final String MOVEMENT_EDIT_SUCCESS = "The movement has been successfully edited.";
    public static final String MOVEMENT_EDIT_FAIL_WRONG_DATATYPE = "The movement has not been edited because the attribute you want to edit doesn't exists or is not allowed.";
    public static final String MOVEMENT_EDIT_FAIL_NOT_FOUND = "The movement has not been edited because it hasn't been created.";

    public static final String MOVEMENT_REMOVE_SUCCESS = "The movement has been successfully removed.";

    public static final String MOVEMENT_REMOVE_FAIL = "The movement has not been eliminated because it wasn't found.";

    public static final String MOVEMENT_REMOVE_BAD_ADJUSTMENT = "The movement has not been eliminated because the account balance would be negative";


}
