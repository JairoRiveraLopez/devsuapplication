package com.application.jrl_technical_test.Services.Impl;

import com.application.jrl_technical_test.DAO.AccountDailyWithdrawHome;
import com.application.jrl_technical_test.DAO.AccountHome;
import com.application.jrl_technical_test.DAO.MovementHome;
import com.application.jrl_technical_test.Entities.Account;
import com.application.jrl_technical_test.Entities.AccountDailyWithdraw;
import com.application.jrl_technical_test.Entities.Movement;
import com.application.jrl_technical_test.Exception.BadRequestException;
import com.application.jrl_technical_test.Exception.InternalAppException;
import com.application.jrl_technical_test.Security.ClientPasswordEncoder;
import com.application.jrl_technical_test.Services.IServices.IMovementService;
import com.application.jrl_technical_test.Services.ValidatorService;
import com.application.jrl_technical_test.Utils.CodesConstants;
import com.application.jrl_technical_test.Utils.MessagesUtil;
import com.application.jrl_technical_test.Utils.MovementUtil;
import com.application.jrl_technical_test.Web.DTO.ExceptionDTO;
import com.application.jrl_technical_test.Web.DTO.MovementPersistDTO;
import com.application.jrl_technical_test.Web.DTO.MovementUpdateDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MovementServiceImpl implements IMovementService {

    @Autowired
    private AccountHome accountHome;

    @Autowired
    private MovementHome movementHome;

    private static final ClientPasswordEncoder clientPasswordEncoder = new ClientPasswordEncoder();

    @Autowired
    private AccountDailyWithdrawHome accountDailyWithdrawHome;

    @Autowired
    private ValidatorService validatorService;


    @Override
    @Transactional()
    public ServiceResponseDTO insertMovement(MovementPersistDTO movementPersistDTO) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(!validatorService.objectHasNullAttributes(movementPersistDTO)){
            try{
                Map<String, Boolean> responseValidation = movementTransactionValidator(movementPersistDTO, null, "PERSIST", null);
                if(!responseValidation.isEmpty() && responseValidation.get("type") && responseValidation.get("account") && responseValidation.get("password")){
                    if(validateWithdrawDepositTransaction(movementPersistDTO.getValue(),movementPersistDTO.getAccountNumber(), movementPersistDTO.getMovementType())){
                        if(updateAccountsBalances(movementPersistDTO.getValue(),movementPersistDTO.getAccountNumber(), movementPersistDTO.getMovementType())){
                            Movement movement = instantiateUpdateMovement(movementPersistDTO, null, "PERSIST", null);
                            if(movement != null){
                                movementHome.persist(movement);
                                data.put("message", MessagesUtil.MOVEMENT_PERSIST_SUCCESS);
                                data.put("movementIdForTest", movement.getIdMovement());
                            }
                        }else {
                            statusCode = CodesConstants.INTERNAL_SERVER_ERROR;
                            data.put("message", MessagesUtil.MOVEMENT_FAIL_INTERNAL);
                        }
                    }else {
                        statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                        data.put("message", MessagesUtil.MOVEMENT_PERSIST_FAIL_INCORRECT_VALUE);
                    }
                }else{
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.MOVEMENT_PERSIST_FAIL_MISSING_DATA);
                }
            }catch (Exception error){
                ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "insertMovement failed", HttpStatus.BAD_REQUEST.value());
                throw new BadRequestException(exceptionDTO);

            }
        }else {
            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
            data.put("message", MessagesUtil.MOVEMENT_PERSIST_FAIL);
        }
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setStatusCode(statusCode);
        serviceResponseDTO.setInformation(data);
        return serviceResponseDTO;
    }

    @Override
    @Transactional()
    public ServiceResponseDTO updateMovement(String movementId, MovementUpdateDTO movementUpdateDTO) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(!validatorService.objectHasNullAttributes(movementUpdateDTO)){
            try {
                Optional<Movement> movementQuery = Optional.ofNullable(movementHome.findById(movementId));
                if (movementQuery.isPresent()) {
                    Map<String, Boolean> responseValidation = movementTransactionValidator(null, movementUpdateDTO, "UPDATE", movementId);
                    if (!responseValidation.isEmpty() && responseValidation.get("type") && responseValidation.get("account") && responseValidation.get("password")) {
                        if (validateWithdrawDepositTransaction(movementUpdateDTO.getValue(), movementUpdateDTO.getAccountNumber(), movementUpdateDTO.getMovementType())) {
                            if (updateAccountsBalances(movementUpdateDTO.getValue(), movementUpdateDTO.getAccountNumber(), movementUpdateDTO.getMovementType())) {
                                Movement movement = instantiateUpdateMovement(null, movementUpdateDTO, "UPDATE", movementId);
                                if (movement != null) {
                                    movementHome.merge(movement);
                                    data.put("message", MessagesUtil.MOVEMENT_UPDATE_SUCCESS);
                                }
                            } else {
                                statusCode = CodesConstants.INTERNAL_SERVER_ERROR;
                                data.put("message", MessagesUtil.MOVEMENT_FAIL_INTERNAL);
                            }
                        } else {
                            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                            data.put("message", MessagesUtil.MOVEMENT_UPDATE_FAIL_INCORRECT_VALUE);
                        }
                    } else {
                        statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                        data.put("message", MessagesUtil.MOVEMENT_UPDATE_FAIL_MISSING_DATA);
                    }
                } else {
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.MOVEMENT_UPDATE_FAIL_NOT_FOUND);
                }
            }catch (Exception error){
                ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "updateMovement failed", HttpStatus.BAD_REQUEST.value());
                throw new BadRequestException(exceptionDTO);
            }
        }else {
            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
            data.put("message", MessagesUtil.MOVEMENT_UPDATE_FAIL);
        }
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setStatusCode(statusCode);
        serviceResponseDTO.setInformation(data);
        return serviceResponseDTO;
    }

    @Override
    @Transactional()
    public ServiceResponseDTO editMovement(String movementId, String dataType, String value) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(movementId.isEmpty() || dataType.isEmpty() || value.isEmpty()){
            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
            data.put("message", MessagesUtil.MOVEMENT_EDIT_FAIL);
        }else {
            try{
                Optional<Movement> movementQuery = Optional.ofNullable(movementHome.findById(movementId));
                if(movementQuery.isPresent()){
                    Movement movement = movementQuery.get();
                    if(editMovementInformation(dataType, value, movement)){
                        data.put("message", MessagesUtil.MOVEMENT_EDIT_SUCCESS);
                    }else {
                        statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                        data.put("message", MessagesUtil.MOVEMENT_EDIT_FAIL_WRONG_DATATYPE);
                    }

                } else {
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.MOVEMENT_EDIT_FAIL_NOT_FOUND);
                }
            }catch (Exception error){
                ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "editMovement failed", HttpStatus.BAD_REQUEST.value());
                throw new BadRequestException(exceptionDTO);
            }
        }
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setStatusCode(statusCode);
        serviceResponseDTO.setInformation(data);
        return serviceResponseDTO;
    }

    @Override
    @Transactional()
    public ServiceResponseDTO removeMovement(String movementId) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        try{
            Optional<Movement> movementQuery = Optional.ofNullable(movementHome.findById(movementId));
            if(movementQuery.isPresent()) {
                if(reverseMovementByElimination(movementQuery.get())){
                    movementHome.remove(movementQuery.get());
                    data.put("message", MessagesUtil.MOVEMENT_REMOVE_SUCCESS);
                }else {
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.MOVEMENT_REMOVE_BAD_ADJUSTMENT);
                }

            }else {
                statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                data.put("message", MessagesUtil.MOVEMENT_REMOVE_FAIL);
            }
            ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
            serviceResponseDTO.setStatusCode(statusCode);
            serviceResponseDTO.setInformation(data);
            return serviceResponseDTO;
        }catch (Exception error){
            ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "removeMovement failed", HttpStatus.BAD_REQUEST.value());
            throw new BadRequestException(exceptionDTO);
        }
    }

    private Movement instantiateUpdateMovement(MovementPersistDTO movementPersistDTO, MovementUpdateDTO movementUpdateDTO,
                                              String action, String movementId) throws InternalAppException {
        try{
            Movement movement = null;
            if (action.equalsIgnoreCase("PERSIST")) {
                movement = new Movement();
                movement.setIdMovement(UUID.randomUUID().toString());
                movement.setMovementDate(new Date());
                movement.setMovementType(movementPersistDTO.getMovementType());
                movement.setValue(movementPersistDTO.getValue());
                movement.setState(MovementUtil.SUCCESS.getValue());
                Account account = accountHome.findByAccountNumber(movementPersistDTO.getAccountNumber());
                movement.setAccount(account);
                movement.setAvailableBalance(account.getInitialBalance());
                return movement;
            } else if (action.equalsIgnoreCase("UPDATE")) {
                movement = new Movement();
                movement.setIdMovement(movementId);
                movement.setMovementDate(movementUpdateDTO.getMovementDate());
                movement.setMovementType(movementUpdateDTO.getMovementType());
                movement.setValue(movementUpdateDTO.getValue());
                movement.setState(MovementUtil.SUCCESS.getValue());
                Account account = accountHome.findByAccountNumber(movementUpdateDTO.getAccountNumber());
                movement.setAccount(account);
                movement.setAvailableBalance(account.getInitialBalance());
                return movement;
            }
            return movement;
        } catch (Exception error){
            ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "instantiateUpdateMovement failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new InternalAppException(exceptionDTO);
        }
    }

    private Map<String, Boolean> movementTransactionValidator(MovementPersistDTO movementPersistDTO, MovementUpdateDTO movementUpdateDTO,
                                                              String action, String movementId) throws InternalAppException {
        try {
            Map<String, Boolean> mapResponse = new HashMap<>();
            if (action.equalsIgnoreCase("PERSIST")) {
                mapResponse.put("type", movementPersistDTO.getMovementType().equalsIgnoreCase(MovementUtil.WITHDRAW.getDescription())
                        || (movementPersistDTO.getMovementType().equalsIgnoreCase(MovementUtil.DEPOSIT.getDescription())));
                Optional<Account> accountMovement = Optional.ofNullable(accountHome.findByAccountNumber(movementPersistDTO.getAccountNumber()));
                mapResponse.put("account", accountMovement.isPresent());
                mapResponse.put("password", accountMovement.isPresent() && clientPasswordEncoder.matches(movementPersistDTO.getPassword(),
                        accountMovement.get().getClient().getPassword()));
            } else {
                if (action.equalsIgnoreCase("UPDATE")) {
                    Optional<Movement> movement = Optional.ofNullable(movementHome.findById(movementId));
                    mapResponse.put("movementId", movement.isPresent());
                    mapResponse.put("type", movementUpdateDTO.getMovementType().equalsIgnoreCase(MovementUtil.WITHDRAW.getDescription())
                            || (movementUpdateDTO.getMovementType().equalsIgnoreCase(MovementUtil.DEPOSIT.getDescription())));
                    Optional<Account> accountMovement = Optional.ofNullable(accountHome.findByAccountNumber(movementUpdateDTO.getAccountNumber()));
                    mapResponse.put("account", accountMovement.isPresent());
                    mapResponse.put("password", accountMovement.isPresent() && clientPasswordEncoder.matches(movementUpdateDTO.getPassword(),
                            accountMovement.get().getClient().getPassword()));
                }
            }
            return mapResponse;
        } catch (Exception e) {
            ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "movementTransactionValidator failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new InternalAppException(exceptionDTO);
        }
    }

    private Boolean validateWithdrawDepositTransaction(BigDecimal value, String accountNumber, String movementType) throws InternalAppException {
        try {
            Boolean response = false;
            Optional<Account> accountQuery = Optional.ofNullable(accountHome.findByAccountNumber(accountNumber));
            if (accountQuery.isPresent()) {
                Optional<AccountDailyWithdraw> accountDailyWithdrawQuery = Optional.ofNullable
                        (accountDailyWithdrawHome.findByAccountId(accountQuery.get().getAccountId()));
                if (accountDailyWithdrawQuery.isPresent()) {
                    AccountDailyWithdraw accountDailyWithdraw = accountDailyWithdrawQuery.get();
                    Account account = accountQuery.get();
                    if (accountDailyWithdraw.getMaxAmmount().subtract(value).compareTo(BigDecimal.ZERO) >= 0 &&
                            account.getInitialBalance().subtract(value).compareTo(BigDecimal.ZERO) >= 0) {
                        response = true;
                    } else {
                        if(movementType.equalsIgnoreCase(MovementUtil.DEPOSIT.getDescription())){
                            if(value.compareTo(BigDecimal.ZERO) >= 0){
                                response = true;
                            }
                        }
                    }
                }
            }
            return response;
        } catch (Exception e) {
            ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "validateWithdrawDepositTransaction failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new InternalAppException(exceptionDTO);
        }
    }

    private Boolean updateAccountsBalances(BigDecimal value, String accountNumber, String movementType) throws InternalAppException {
        try{
            Boolean response = false;
            Optional<Account> accountQuery = Optional.ofNullable(accountHome.findByAccountNumber(accountNumber));
            if(accountQuery.isPresent()){
                Optional<AccountDailyWithdraw> accountDailyWithdrawQuery = Optional.ofNullable
                        (accountDailyWithdrawHome.findByAccountId(accountQuery.get().getAccountId()));
                if (accountDailyWithdrawQuery.isPresent()) {
                        AccountDailyWithdraw accountDailyWithdraw = accountDailyWithdrawQuery.get();
                        Account account = accountQuery.get();
                    if(movementType.equalsIgnoreCase(MovementUtil.WITHDRAW.getDescription())){
                        accountDailyWithdraw.setMaxAmmount(accountDailyWithdraw.getMaxAmmount().subtract(value));
                        account.setInitialBalance(account.getInitialBalance().subtract(value));

                        accountHome.merge(account);
                        accountDailyWithdrawHome.merge(accountDailyWithdraw);
                    } else {
                        account.setInitialBalance(account.getInitialBalance().add(value));
                    }
                    response = true;
                }
            }
            return response;
        } catch (Exception e){
            ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "updateAccountsBalances failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new InternalAppException(exceptionDTO);
        }
    }

    private Boolean editMovementInformation(String dataType, String value, Movement movement) throws Exception {
        Boolean somethingChanged = true;
        try {
            switch (dataType) {
                case "movementDate":
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(value);
                    movement.setMovementDate(date);
                    movementHome.merge(movement);
                    break;
                case "state":
                    Character stateQuery = MovementUtil.getValueByDescription(value);
                    if(!stateQuery.equals('X')){
                        movement.setState(stateQuery);
                        movementHome.merge(movement);
                    }else {
                        somethingChanged = false;
                    }
                    break;
                default:
                    somethingChanged = false;
            }
            return somethingChanged;
        } catch (Exception error){
            ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "editMovementInformation failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new InternalAppException(exceptionDTO);
        }
    }

    private Boolean reverseMovementByElimination(Movement movement) throws InternalAppException {
        try{
            Boolean reverseMade = false;
            Account account = accountHome.findById(movement.getAccount().getAccountId());
            AccountDailyWithdraw accountDailyWithdraw = accountDailyWithdrawHome.findByAccountId(account.getAccountId());
            BigDecimal maxAmmout = new BigDecimal("1000");
            if(movement.getMovementType().equalsIgnoreCase(MovementUtil.WITHDRAW.getDescription())){
                if(accountDailyWithdraw.getMaxAmmount().add(movement.getValue()).compareTo(maxAmmout) >= 0){
                    accountDailyWithdraw.setMaxAmmount(maxAmmout);
                    accountDailyWithdrawHome.merge(accountDailyWithdraw);
                }else {
                    accountDailyWithdraw.setMaxAmmount(accountDailyWithdraw.getMaxAmmount().add(movement.getValue()));
                    accountDailyWithdrawHome.merge(accountDailyWithdraw);
                }

                account.setInitialBalance(account.getInitialBalance().add(movement.getValue()));
                accountHome.merge(account);

                reverseMade = true;
            }else {
                if(account.getInitialBalance().subtract(movement.getValue()).compareTo(BigDecimal.ZERO) >= 0){
                    account.setInitialBalance(account.getInitialBalance().subtract(movement.getValue()));
                    accountHome.merge(account);
                    reverseMade = true;
                }
            }
            return reverseMade;
        }catch (Exception error){
            ExceptionDTO exceptionDTO = ExceptionDTO.getExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "editMovementInformation failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new InternalAppException(exceptionDTO);
        }
    }
}
