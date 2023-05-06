package com.application.jrl_technical_test.Services.IServices;

import com.application.jrl_technical_test.Web.DTO.MovementPersistDTO;
import com.application.jrl_technical_test.Web.DTO.MovementUpdateDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;

public interface IMovementService {

    ServiceResponseDTO insertMovement(MovementPersistDTO movementPersistDTO) throws Exception;

    ServiceResponseDTO updateMovement(String movementId, MovementUpdateDTO movementUpdateDTO) throws Exception;

    ServiceResponseDTO editMovement(String movementId, String dataType, String value) throws Exception;

    ServiceResponseDTO removeMovement(String movementId) throws Exception;
}
