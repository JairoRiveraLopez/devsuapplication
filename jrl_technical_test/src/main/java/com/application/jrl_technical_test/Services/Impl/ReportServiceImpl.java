package com.application.jrl_technical_test.Services.Impl;

import com.application.jrl_technical_test.DAO.ClientHome;
import com.application.jrl_technical_test.Entities.Account;
import com.application.jrl_technical_test.Entities.Client;
import com.application.jrl_technical_test.Entities.Movement;
import com.application.jrl_technical_test.Security.ClientPasswordEncoder;
import com.application.jrl_technical_test.Services.IServices.IReportService;
import com.application.jrl_technical_test.Utils.CodesConstants;
import com.application.jrl_technical_test.Utils.FormatUtil;
import com.application.jrl_technical_test.Utils.MessagesUtil;
import com.application.jrl_technical_test.Utils.MovementUtil;
import com.application.jrl_technical_test.Web.DTO.ReportAccountStateDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private ClientHome clientHome;

    private static final ClientPasswordEncoder clientPasswordEncoder = new ClientPasswordEncoder();

    @Override
    public ServiceResponseDTO getAccountStateReportByClient(String password, String clientId, String minDateString, String maxDateString) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date minDate = dateFormat.parse(minDateString);
            Date maxDate = dateFormat.parse(maxDateString);
            Optional<Client> clientQuery = Optional.ofNullable(clientHome.findById(clientId));
            List<ReportAccountStateDTO> reportAccountStateDTOList = new ArrayList<>();
            if(clientQuery.isPresent()){
                if(clientPasswordEncoder.matches(password, clientQuery.get().getPassword())){
                    List<Object[]> accountStateList = clientHome.findAccountStateReportByClient(clientId, minDate, maxDate);
                    for(Object[] accountStateObject : accountStateList){
                        Movement movement = (Movement)  accountStateObject[0];
                        Account account = (Account) accountStateObject[1];
                        Client client = (Client) accountStateObject[2];

                        ReportAccountStateDTO reportAccountStateDTO = new ReportAccountStateDTO();
                        reportAccountStateDTO.setMovementDate(movement.getMovementDate());
                        reportAccountStateDTO.setClientName(FormatUtil.fullName(client.getName(), client.getLastName1(), client.getLastName2(), FormatUtil.upperCase));
                        reportAccountStateDTO.setAccountNumber(account.getAccountNumber());
                        reportAccountStateDTO.setAccountType(account.getAccountType());
                        reportAccountStateDTO.setActualBalance(account.getInitialBalance());
                        reportAccountStateDTO.setMovementState(MovementUtil.getDescriptionByValue(movement.getState()));
                        reportAccountStateDTO.setMovementType(movement.getMovementType());
                        reportAccountStateDTO.setMovementValue(movement.getAvailableBalance());
                        reportAccountStateDTOList.add(reportAccountStateDTO);
                    }
                    data.put("accountStateList", reportAccountStateDTOList);
                }
            }

            ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
            serviceResponseDTO.setStatusCode(statusCode);
            serviceResponseDTO.setInformation(data);
            return serviceResponseDTO;
        } catch (Exception e){
            throw e;
        }
    }
}
